package org.luckyjourney.schedul;

import org.luckyjourney.entity.video.Video;
import org.luckyjourney.entity.vo.HotVideo;

import java.util.*;


public class TopK {
    
    // PriorityQueue 和普通队列（Queue）一样支持添加（offer）、移除（poll）和查看队头（peek），但不是按加入顺序出队，而是按“优先级顺序”出队。
    //
    //比如默认情况是最小堆结构：
    //谁数值小，谁优先级高，谁先被 poll 出来。

    private int k = 0;

    private Queue<HotVideo> queue;

    public TopK(int k,Queue<HotVideo> queue){
        this.k = k;
        this.queue = queue;
    }

    public void add(HotVideo hotVideo) {
        //如果队列当前还没有满，就直接加入。
        //
        //如果已经满了，就比较当前最小热度（堆顶）：
        //
        //如果新视频更热门（热度更高），就淘汰堆顶，加入新的视频。
        //
        //否则忽略（因为新视频不够热，不配进榜）。
        if (queue.size() < k) {
            queue.add(hotVideo);
        } else if (queue.peek().getHot() < hotVideo.getHot()){
            queue.poll();  // 移除最小的
            queue.add(hotVideo);
        }

        // 这里的 k 是 TopK 的成员变量，是你自己定义的“最多保留多少个”。在你的代码里是 10，所以只保留热度最高的 10 个视频。
        return;
    }


    public List<HotVideo> get(){
        final ArrayList<HotVideo> list = new ArrayList<>(queue.size());
        while (!queue.isEmpty()) {
            list.add(queue.poll());
        }
        Collections.reverse(list);
        return list;
    }


}
