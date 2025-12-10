package org.luckyjourney.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.luckyjourney.entity.vo.Comment;
import org.luckyjourney.entity.vo.CommentLike;
import org.luckyjourney.holder.UserHolder;
import org.luckyjourney.service.CommentLikeService;
import org.luckyjourney.service.CommentService;
import org.luckyjourney.util.JwtUtils;
import org.luckyjourney.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author bgmyangzhu
 * @date 2025/11/29 21:42
 */
@RestController
@RequestMapping("/luckyjourney/comment")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private CommentLikeService commentLikeService;


    @GetMapping("/root")
    public R getRootComments(
            @RequestParam Long videoId,
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "5") Long size,
            HttpServletRequest request
    ) {
        Long userId = UserHolder.get();

        IPage<Comment> page =
                commentService.getRootCommentsWithChildren(videoId, userId, current, size);

        return R.ok().data(page);
    }
   
    @PostMapping
    public R pushComment(
            @RequestParam Long videoId,
            @RequestParam Long parentId,
            @RequestParam(required = false) Long rootId,
            @RequestParam(required = false) Long toUserId,
            @RequestParam String content
    ){
        Comment comment = commentService.publishComment(videoId, parentId, rootId, toUserId, content);
        return R.ok().data(comment);
    }
    
    @PostMapping("/like/{commentId}")
    public R like(@PathVariable Long commentId) {
        String msg = "已点赞";
        if(!commentService.likeComment(commentId)){
            msg = "取消点赞";
        }
        return R.ok().message(msg);
    }
}
