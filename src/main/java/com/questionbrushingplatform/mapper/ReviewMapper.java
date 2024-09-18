package com.questionbrushingplatform.mapper;

import com.questionbrushingplatform.entity.Review;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wenruohan
* @description 针对表【review(审核表)】的数据库操作Mapper
* @createDate 2024-09-17 21:53:07
* @Entity com.questionbrushingplatform.entity.Review
*/
@Mapper
public interface ReviewMapper extends BaseMapper<Review> {

}




