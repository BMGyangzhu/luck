package org.luckyjourney.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.luckyjourney.entity.vo.Comment;
import org.luckyjourney.entity.vo.CommentLike;

import java.util.List;

/**
 * @author bgmyangzhu
 * @date 2025/11/30 2:07
 */
public interface CommentLikeMapper  extends BaseMapper<CommentLike> {
//    @Select("SELECT comment_id FROM comment_like WHERE comment_id IN #{commentIds} AND user_id = #{userId}")
//    List<Long> selectLikedCommentIds(@Param("commentIds") List<Long> commentIds,
//                                     @Param("userId") Long userId);
}

