package com.questionbrushingplatform.controller;

import com.questionbrushingplatform.common.resp.BaseResponse;
import com.questionbrushingplatform.common.resp.ResponseCode;
import com.questionbrushingplatform.pojo.entity.Tag;
import com.questionbrushingplatform.service.TagService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 题目标签模块
 * @author wenruohan
 */
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
@Api(tags = "标签模块")
@Slf4j
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
            log.error("TagController.add error: the tag name already exists which is {}", name);
            return new BaseResponse<>(ResponseCode.PARAMETER_ERROR);
        }
        Tag tag = new Tag();
        tag.setName(name);
        if (!tagService.addTag(tag)) {
            log.error("TagController.add error: add tag failed which is {}", tag);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
        log.info("TagController.add success: add tag success which is {}", tag);
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
            log.error("TagController.delete error: delete tag failed which is {}", id);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
        log.info("TagController.delete success: delete tag success which is {}", id);
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
            log.error("TagController.update error: the tag does not exist which is {}", tag);
            return new BaseResponse<Tag>(ResponseCode.NO_DATA, tag);
        }
        if (!tagService.updateTag(tag)) {
            log.error("TagController.update error: update tag failed which is {}", tag);
            return new BaseResponse<Tag>(ResponseCode.SYSTEM_ERROR, tag);
        }
        log.info("TagController.update success: update tag success which is {}", tag);
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
            log.error("TagController.get error: the tag does not exist which is {}", id);
            return new BaseResponse<>(ResponseCode.NO_DATA);
        }
        log.info("TagController.get success: get tag success which is {}", tag);
        return new BaseResponse<>(ResponseCode.SUCCESS, tag);
    }

    /**
     * 获取所有标签
     * @return Result<List<Tag>>
     */
    @GetMapping("/list")
    public BaseResponse<List<Tag>> list(@RequestParam String name) {
        if (name.isEmpty()) {
            log.info("TagController.list success: list tags success");
            return new BaseResponse<>(ResponseCode.SUCCESS, tagService.listTags());
        }
        log.info("TagController.list success: list tags success which is {}", name);
        return new BaseResponse<>(ResponseCode.SUCCESS, tagService.listTagsByName(name));
    }
}