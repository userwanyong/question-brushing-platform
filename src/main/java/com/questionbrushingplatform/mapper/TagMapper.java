package com.questionbrushingplatform.mapper;

import com.questionbrushingplatform.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wenruohan
* @description 针对表【tag】的数据库操作Mapper
* @createDate 2024-09-16 16:47:24
* @Entity com.questionbrushingplatform.pojo.entity.Tag
*/
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}




