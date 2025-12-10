package org.luckyjourney.entity.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.luckyjourney.entity.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * @author bgmyangzhu
 * @date 2025/11/29 21:44
 */
@Data
public class Comment  {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 逻辑删除，0：未删除，1：删除，默认为0
     */
    @TableLogic
    private Boolean isDeleted;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreated; 
    private Long parentId;
    private Long rootId;
    private String content;
    private Long fromUserId;
    private Long toUserId;
    private Long businessId;
    private Long likeCount;

    @TableField(exist = false)
    private List<Comment> children;

    @TableField(exist = false)
    private Boolean liked;

    @TableField(exist = false)
    private Long fromUserAvatar;

    @TableField(exist = false)
    private String fromUserName;
    
    @TableField(exist = false)
    private String toUserName;
    
}
