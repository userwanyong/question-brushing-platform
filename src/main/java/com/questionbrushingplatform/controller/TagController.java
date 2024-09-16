package com.questionbrushingplatform.controller;

import com.questionbrushingplatform.common.resp.BaseResponse;
import com.questionbrushingplatform.common.resp.ResponseCode;
import com.questionbrushingplatform.pojo.entity.Tag;
import com.questionbrushingplatform.service.TagService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 题目标签模块
 * @author wenruohan
 */
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
@Api(tags = "标签模块")
public class TagController {

    private final TagService tagService;

    /**
     * 新增标签
     * @param name 标签名称
     * @return Result<Boolean>
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> add(String name) {
        if (tagService.getTagByName(name) != null) {
            return new BaseResponse<>(ResponseCode.PARAMETER_ERROR);
        }
        Tag tag = new Tag();
        tag.setName(name);
        if (!tagService.addTag(tag)) {
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
        return new BaseResponse<>(ResponseCode.SUCCESS);
    }

    /**
     * 删除标签
     * @param id 标签ID
     * @return Result<Boolean>
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> delete(Integer id) {
        if (!tagService.deleteTag(id)) {
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
        return new BaseResponse<>(ResponseCode.SUCCESS);
    }

    /**
     * 更新标签
     * @param tag 标签
     * @return Result<Boolean>
     */
    @PostMapping("/update")
    public BaseResponse<Tag> update(@RequestBody Tag tag) {
        Tag res = tagService.getTagById(tag.getId());
        if (res == null) {
            return new BaseResponse<Tag>(ResponseCode.NO_DATA, tag);
        }
        res.setUpdatedTime(new Date());
        if (!tagService.updateTag(res)) {
            return new BaseResponse<Tag>(ResponseCode.SYSTEM_ERROR, tag);
        }
        return new BaseResponse<Tag>(ResponseCode.SUCCESS, tag);
    }

    /**
     * 获取标签
     * @param id 标签ID
     * @return Result<Tag>
     */
    @GetMapping("/get")
    public BaseResponse<Tag> get(@RequestParam Integer id) {
        Tag tag = tagService.getTagById(id);
        if (tag == null) {
            return new BaseResponse<>(ResponseCode.NO_DATA);
        }
        return new BaseResponse<>(ResponseCode.SUCCESS, tag);
    }

    /**
     * 获取所有标签
     * @return Result<List<Tag>>
     */
    @GetMapping("/list")
    public BaseResponse<List<Tag>> list(@RequestParam String name) {
        if (name.isEmpty()) {
            return new BaseResponse<>(ResponseCode.SUCCESS, tagService.listTags());
        }
        return new BaseResponse<>(ResponseCode.SUCCESS, tagService.listTagsByName(name));
    }
}
