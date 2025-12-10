package org.luckyjourney.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.luckyjourney.entity.Captcha;
import org.luckyjourney.entity.vo.CommentLike;

import java.util.List;

/**
 * @author bgmyangzhu
 * @date 2025/11/30 2:06
 */
public interface CommentLikeService  extends IService<CommentLike> {
    public List<Long> selectLikedCommentIds(List<Long> commentIds, Long userId); 
    
    public boolean like(CommentLike commentLike);
    
    
}
