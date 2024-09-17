package com.questionbrushingplatform.service;

import com.questionbrushingplatform.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author wenruohan
* @description 针对表【tag】的数据库操作Service
* @createDate 2024-09-16 16:47:24
*/
public interface TagService extends IService<Tag> {
    boolean addTag(Tag tag);
    boolean addTags(List<Tag> tags);
    boolean deleteTag(Integer id);
    boolean updateTag(Tag tag);
    Tag getTagById(Integer id);
    Tag getTagByName(String name);
    List<Tag> listTags();
    List<Tag> listTagsByName(String name);
}
