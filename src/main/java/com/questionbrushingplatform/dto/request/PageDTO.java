package com.questionbrushingplatform.dto.request;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页结果封装
 * @author 永
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> {
    private Long total; //总条数
    private Long pages; //总页数
    private List<T> list; //集合


    /**
     * 将MybatisPlus分页结果转为 VO分页结果
     * @param p      mybatis-plus的分页结果
     * @param voClass VO的Class
     * @param <PO>    PO的Class
     * @param <VO>    VO的Class
     * @return VO的分页结果
     */
    public static <PO, VO> PageDTO<VO> of(Page<PO> p, Class<VO> voClass) {
        PageDTO<VO> dto = new PageDTO<>();
        //总条数
        dto.setTotal(p.getTotal());
        //总页数
        dto.setPages(p.getPages());
        //当前页数据
        List<PO> records = p.getRecords();
        if (CollUtil.isEmpty(records)) {
            dto.setList(Collections.emptyList());
            return dto;
        }
        //拷贝所需的VO属性
        dto.setList(BeanUtil.copyToList(records, voClass));
        //返回
        return dto;
    }

    /**
     * 将MybatisPlus分页结果转为 VO分页结果，允许用户自定义PO到VO的转换方式
     * @param p      mybatis-plus的分页结果
     * @param converter  PO到VO的转换函数
     * @param <PO>    PO的Class
     * @param <VO>    VO的Class
     * @return VO的分页结果
     */
    public static <PO, VO> PageDTO<VO> of(Page<PO> p, Function<PO, VO> converter) {
        PageDTO<VO> dto = new PageDTO<>();
        //总条数
        dto.setTotal(p.getTotal());
        //总页数
        dto.setPages(p.getPages());
        //当前页数据
        List<PO> records = p.getRecords();
        if (CollUtil.isEmpty(records)) {
            dto.setList(Collections.emptyList());
            return dto;
        }
        //拷贝所需的VO属性
        dto.setList(records.stream().map(converter).collect(Collectors.toList()));
        //返回
        return dto;
    }


}