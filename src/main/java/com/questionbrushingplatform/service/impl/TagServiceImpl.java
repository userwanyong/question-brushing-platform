package com.questionbrushingplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.questionbrushingplatform.pojo.entity.Tag;
import com.questionbrushingplatform.service.TagService;
import com.questionbrushingplatform.mapper.TagMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author wenruohan
* @description 针对表【tag】的数据库操作Service实现
* @createDate 2024-09-16 16:47:24
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Override
    public boolean addTag(Tag tag) {
        return save(tag);
    }

    @Override
    public boolean addTags(List<Tag> tags) {
        return saveBatch(tags);
    }

    @Override
    public boolean deleteTag(Integer id) {
        return removeById(id);
    }

    @Override
    public boolean updateTag(Tag tag) {
        return updateById(tag);
    }

    @Override
    public Tag getTagById(Integer id) {
        return getById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return getOne(queryWrapper);
    }

    @Override
    public List<Tag> listTags() {
        return list();
    }

    @Override
    public List<Tag> listTagsByName(String name) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        return list(queryWrapper);
    }
}




