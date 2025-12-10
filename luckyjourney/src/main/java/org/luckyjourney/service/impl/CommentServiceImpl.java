package org.luckyjourney.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.luckyjourney.entity.user.User;
import org.luckyjourney.entity.video.Video;
import org.luckyjourney.entity.vo.Comment;
import org.luckyjourney.entity.vo.CommentLike;
import org.luckyjourney.exception.BaseException;
import org.luckyjourney.holder.UserHolder;
import org.luckyjourney.mapper.CommentMapper;
import org.luckyjourney.service.CommentLikeService;
import org.luckyjourney.service.CommentService;
import org.luckyjourney.service.user.UserService;
import org.luckyjourney.service.video.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author bgmyangzhu
 * @date 2025/11/29 21:55
 */
@Service
@Transactional
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CommentLikeService commentLikeService;
    
    @Autowired
    private VideoService videoService;

    @Override
    public Comment publishComment(
            Long videoId,
            Long parentId,   // 0 = 一级，其它 = 回复
            Long rootId,     // 一级时可不传或传 null，二级必须传一级ID
            Long toUserId,
            String content
    ) {
        Comment comment = new Comment();
        comment.setBusinessId(videoId);
        comment.setFromUserId(UserHolder.get());
        comment.setContent(content);

        if (parentId == null || parentId == 0) {
            //  一级评论
            comment.setParentId(0L);
            comment.setRootId(0L); //设置占位值，sql中rootId不能为null
            save(comment);
            Video video = videoService.getVideoById(videoId, UserHolder.get());
            Long commentCount  = video.getCommentCount() + 1;
            video.setCommentCount(commentCount);
            videoService.updateById(video);

            //  关键：rootId = 自己的主键
            comment.setRootId(comment.getId());
            updateById(comment);
            List<Comment> list = new ArrayList<>();
            list.add(comment);
            fillUserInfoAndLike(list, UserHolder.get());
            return comment;

        } else {
            //  二级及以上评论（逻辑全部统一）
            comment.setParentId(parentId);
            comment.setRootId(rootId);     // 必须是一级评论ID
            comment.setToUserId(toUserId);
            Video video1 = videoService.getVideoById(videoId,UserHolder.get());
            Long commentCount = video1.getCommentCount() + 1;
            video1.setCommentCount(commentCount);
            videoService.updateById(video1);
            save(comment);
            List<Comment> list = new ArrayList<>();
            list.add(comment);
            fillUserInfoAndLike(list, UserHolder.get());
            return comment;
        }
    }
    @Override
    public IPage<Comment> getRootCommentsWithChildren(Long videoId, Long userId, Long current, Long size) {

        // 查询一级评论
        IPage<Comment> rootPage = page(
                new Page<>(current, size),
                new LambdaQueryWrapper<Comment>()
                        .eq(Comment::getBusinessId, videoId)
                        .eq(Comment::getParentId, 0)
                        .orderByAsc(Comment::getGmtCreated)
        );

        List<Comment> rootComments = rootPage.getRecords();
        if (rootComments.isEmpty()) {
            return rootPage;
        }

        // 一级评论的id集合
        List<Long> rootIds = rootComments.stream().map(Comment::getId).collect(Collectors.toList());

        // 查询所有二级评论
        List<Comment> childComments = list(
                new LambdaQueryWrapper<Comment>()
                        .in(Comment::getRootId, rootIds)
                        .ne(Comment::getParentId, 0)
                        .orderByAsc(Comment::getGmtCreated)
        );

        // 所有评论统一补充用户信息和点赞状态
        List<Comment> allComments = new ArrayList<>();
        allComments.addAll(rootComments);
        allComments.addAll(childComments);
        fillUserInfoAndLike(allComments, userId);

        // 将二级评论分配给对应一级评论的children
        Map<Long, List<Comment>> childrenMap = childComments.stream()
                                                            .collect(Collectors.groupingBy(Comment::getRootId));

        for (Comment root : rootComments) {
            root.setChildren(childrenMap.getOrDefault(root.getId(), Collections.emptyList()));
        }

        return rootPage;
    }
    
    @Override
    public boolean likeComment(Long commentId) {
        Comment comment = getById(commentId);
        if (comment == null) throw new BaseException("指定评论不存在");

        CommentLike commentLike = new CommentLike();
        commentLike.setCommentId(commentId);
        commentLike.setUserId(UserHolder.get());
        boolean result = commentLikeService.like(commentLike);
        updateLike(comment, result ? 1L : -1L);
        
        return result;
    }
  
    public void updateLike(Comment comment, Long value) {
        UpdateWrapper<Comment> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("like_count = like_count + " + value);
        updateWrapper.lambda().eq(Comment::getId,comment.getId()).eq(Comment::getLikeCount, comment.getLikeCount());
        update(comment,updateWrapper);
    }

   

    private void fillUserInfoAndLike(List<Comment> comments, Long userId) {

        if (comments.isEmpty()) return;

        // ==== 收集所有涉及的用户ID，包括被回复的用户 ====
        Set<Long> userIds = new HashSet<>();
        for (Comment c : comments) {
            userIds.add(c.getFromUserId());
            if (c.getToUserId() != null && c.getToUserId() != 0) {
                userIds.add(c.getToUserId());
            }
        }

        // ==== 获取用户信息 Map<Long, User> ====
        Map<Long, User> userMap = userService.getBaseInfoMap(userIds);

        // ==== 设置头像和昵称 ====
        for (Comment c : comments) {
            User fromUser = userMap.get(c.getFromUserId());
            if (fromUser != null) {
                c.setFromUserAvatar(fromUser.getAvatar());
                c.setFromUserName(fromUser.getNickName());
            }

            if (c.getToUserId() != null && c.getToUserId() != 0) {
                User toUser = userMap.get(c.getToUserId());
                if (toUser != null) {
                    c.setToUserName(toUser.getNickName());
                }
            }
        }

        // ==== 我是否点赞过 ====
        List<Long> commentIds = comments.stream()
                                        .map(Comment::getId)
                                        .collect(Collectors.toList());
        List<Long> likedIds = commentLikeService.selectLikedCommentIds(commentIds, userId);
        Set<Long> likedSet = new HashSet<>(likedIds);

        for (Comment c : comments) {
            c.setLiked(likedSet.contains(c.getId()));
        }
    }

}
