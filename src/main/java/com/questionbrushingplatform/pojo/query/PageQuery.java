package com.questionbrushingplatform.pojo.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * 分页查询条件
 */
@Data
public class PageQuery {
    private Integer pageNo=1; //页码
    private Integer pageSize=5; //每页数据条数
    private String sortBy; //排序字段
    private Boolean isAsc=true; //是否升序

    /**
     * 转换为MybatisPlus分页对象
     * @param items 排序条件
     * @return
     */
    public <T> Page<T> toMpPage(OrderItem ... items){
        // 1.分页条件
        Page<T> page = Page.of(pageNo, pageSize);
        // 2.排序条件
        if (StrUtil.isNotBlank(sortBy)) {
            page.addOrder(new OrderItem(sortBy, isAsc));
        }else if(items != null){
            page.addOrder(items);
        }
        return page;
    }

    /**
     * 转换为MybatisPlus分页对象
     * @param defaultSortBy 默认排序字段
     * @param isAsc 是否升序
     * @return
     */
    public <T> Page<T> toMpPage(String defaultSortBy, boolean isAsc){
        return this.toMpPage(new OrderItem(defaultSortBy, isAsc));
    }

}