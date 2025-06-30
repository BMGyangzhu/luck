package org.luckyjourney.util;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.StandardCharsets;

public class GuavaBloomFilterUtil {

    private BloomFilter<String> bloomFilter;
    private final long expectedInsertions;
    private final double falsePositiveRate;

    /**
     * 构造函数，用于初始化布隆过滤器
     *
     * @param expectedInsertions 预计插入的元素数量
     * @param falsePositiveRate  误判率（0 到 1 之间）
     */
    public GuavaBloomFilterUtil(long expectedInsertions, double falsePositiveRate) {
        this.expectedInsertions = expectedInsertions;
        this.falsePositiveRate = falsePositiveRate;
        initializeBloomFilter();
    }

    /**
     * 初始化布隆过滤器
     */
    private void initializeBloomFilter() {
        this.bloomFilter = BloomFilter.create(
                Funnels.stringFunnel(StandardCharsets.UTF_8),
                expectedInsertions,
                falsePositiveRate
        );
    }

    /**
     * 添加元素到布隆过滤器
     *
     * @param key 要添加的元素
     */
    public void add(String key) {
        bloomFilter.put(key);
    }

    /**
     * 判断元素是否可能存在于布隆过滤器
     *
     * @param key 要检查的元素
     * @return 如果可能存在，返回 true；否则返回 false
     */
    public boolean mightContain(String key) {
        return bloomFilter.mightContain(key);
    }

    /**
     * 获取布隆过滤器的当前误判率
     *
     * @return 布隆过滤器的误判率
     */
    public double estimatedFalsePositiveProbability() {
        return bloomFilter.expectedFpp();
    }

    /**
     * 重新初始化布隆过滤器
     */
    public void reset() {
        initializeBloomFilter();
    }
}
