package org.luckyjourney.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.luckyjourney.entity.Captcha;
import org.luckyjourney.entity.vo.CommentLike;
import org.luckyjourney.holder.UserHolder;
import org.luckyjourney.mapper.CaptchaMapper;
import org.luckyjourney.mapper.CommentLikeMapper;
import org.luckyjourney.service.CommentLikeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bgmyangzhu
 * @date 2025/11/30 2:07
 */
@Service
@Transactional
public class CommentLikeImpl extends ServiceImpl<CommentLikeMapper, CommentLike> implements CommentLikeService {
    /**
     * 查询用户点赞过的评论ID列表
     *
     * @param commentIds 评论ID列表
     * @param userId     用户ID
     * @return 用户点赞过的评论ID集合
     */
    public List<Long> selectLikedCommentIds(List<Long> commentIds, Long userId) {
        if (commentIds == null || commentIds.isEmpty() || userId == null) {
            return Collections.emptyList();
        }
        return this.list(new LambdaQueryWrapper<CommentLike>()
                           .in(CommentLike::getCommentId, commentIds)
                           .eq(CommentLike::getUserId, userId))
                   .stream()
                   .map(CommentLike::getCommentId)
                   .collect(Collectors.toList());
    }

    @Override
    public boolean like(CommentLike commentLike) {

        CommentLike one = getOne(
                new LambdaQueryWrapper<CommentLike>()
                        .eq(CommentLike::getUserId, commentLike.getUserId())
                        .eq(CommentLike::getCommentId, commentLike.getCommentId())
        );

        // 已点赞 → 取消点赞
        if (one != null) {
            removeById(one.getId());
            return false;
        }

        // 未点赞 → 点赞
        return save(commentLike);
    }
}
