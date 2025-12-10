package org.luckyjourney.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author bgmyangzhu
 * @date 2025/11/30 2:01
 */
@Data
public class CommentLike {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long commentId;
    private Long userId;
}
