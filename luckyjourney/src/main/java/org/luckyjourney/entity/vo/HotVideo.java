package org.luckyjourney.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;


@Data
@NoArgsConstructor
@ToString
public class HotVideo implements  Serializable {

    private static final long serialVersionUID = 1L;

    // 用来调整热度单位
    String hotFormat;

    Double hot;

    Long videoId;

    String title;

    public HotVideo(Double hot,Long videoId,String title){
        this.hot = hot;
        this.videoId = videoId;
        this.title = title;
    }


    // 作用：将原始的热度值 hot（如 50000）转化为“万”为单位的字符串，保留 1 位小数（如 5.0万）。
    public void hotFormat(){
        BigDecimal bigDecimal = new BigDecimal(this.hot);
        BigDecimal decimal = bigDecimal.divide(new BigDecimal("10000"));
        DecimalFormat formater = new DecimalFormat("0.0");
        formater.setRoundingMode(RoundingMode.HALF_UP);    // 5000008.89
        String formatNum = formater.format(decimal);
        this.setHotFormat( formatNum+"万");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotVideo hotVideo = (HotVideo) o;
        // 这里用的是 getClass() 而不是 instanceof，意味着只允许精确匹配同一个类的实例，子类也不行。
        return Objects.equals(videoId, hotVideo.videoId) &&
                Objects.equals(title, hotVideo.title);
        // 比较了 videoId，title是否相等，只有当 video和 title 都相等时，两个 HotVideo 对象才被认为是相等的
    }

    // 与 equals 一致，基于 videoId 和 title 生成哈希值。
    @Override
    public int hashCode() {
        return Objects.hash(videoId, title);
    }
}
