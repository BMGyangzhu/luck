package org.luckyjourney.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.luckyjourney.entity.Captcha;
import org.luckyjourney.entity.vo.Comment;

import java.util.List;

/**
 * @author bgmyangzhu
 * @date 2025/11/29 21:55
 */
public interface CommentService  extends IService<Comment> {


    Comment publishComment(
            Long videoId,
            Long parentId,   // 0 = 一级，其它 = 回复
            Long rootId,     // 一级时可不传或传 null，二级必须传一级ID
            Long toUserId,
            String content
    );

    IPage<Comment> getRootCommentsWithChildren(Long videoId, Long userId, Long current, Long size);


    boolean likeComment(Long commentId);
}
