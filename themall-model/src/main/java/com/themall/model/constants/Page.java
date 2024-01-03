package com.themall.model.constants;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import lombok.Data;

import java.util.List;

/**
 * @author poo0054
 */
@Data
public class Page<T> implements IPage<T> {
    private static final long serialVersionUID = -8924595553317487728L;

    /**
     * 当前数据
     */
    private List<T> rows;

    /**
     * 当前页数
     */
    private Integer page;
    /**
     * 总数
     */
    private Integer total;

    /**
     * 每页多少
     */
    private Integer pageSize;

    private List<OrderItem> orders;

    @Override
    public List<OrderItem> orders() {
        return orders;
    }

    @Override
    public List<T> getRecords() {
        return rows;
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        this.rows = records;
        return this;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public IPage<T> setTotal(long total) {
        this.total = Math.toIntExact(total);
        return this;
    }

    @Override
    public long getSize() {
        return pageSize;
    }

    @Override
    public IPage<T> setSize(long size) {
        this.pageSize = Math.toIntExact(size);
        return this;
    }

    @Override
    public long getCurrent() {
        return page;
    }

    @Override
    public IPage<T> setCurrent(long current) {
        this.page = Math.toIntExact(current);
        return this;
    }
}
