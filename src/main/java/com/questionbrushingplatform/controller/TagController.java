package com.questionbrushingplatform.controller;

import com.questionbrushingplatform.common.resp.BaseResponse;
import com.questionbrushingplatform.common.resp.ResponseCode;
import com.questionbrushingplatform.entity.Tag;
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
    public BaseResponse<Tag> add(@RequestBody String name) {
        if (tagService.getTagByName(name) != null) {
            log.error("TagController.add error: the tag name already exists which is {}", name);
            return new BaseResponse<>(ResponseCode.PARAMETER_ERROR);
        }
        Tag tag = new Tag();
        tag.setName(name);
        try {
            tagService.addTag(tag);
        } catch (Exception e) {
            log.error("TagController.add error: add tag failed which is {}", tag);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
        tag = tagService.getTagByName(name);
        log.info("TagController.add success: add tag success which is {}", tag);
        return new BaseResponse<>(ResponseCode.SUCCESS, tag);
    }

    /**
     * 删除标签
     * @param id 标签ID
     * @return Result<Boolean>
     */
    @PostMapping("/delete/{id}")
    public BaseResponse<Boolean> delete(@PathVariable Integer id) {
        if (!tagService.deleteTag(id)) {
            log.error("TagController.delete error: delete tag failed which is {}", id);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
        log.info("TagController.delete success: delete tag success which is {}", id);
        return new BaseResponse<>(ResponseCode.SUCCESS);
    }

    /**
     * 更新标签
     * @param id 标签ID
     * @param name 标签名称
     * @return BaseResponse<Tag>
     */
    @PostMapping("/update/{id}")
    public BaseResponse<Tag> update(@PathVariable Integer id, @RequestBody String name) {
        Tag res = tagService.getTagById(id);
        if (res == null) {
            log.error("TagController.update error: the tag does not exist which id is {}", id);
            return new BaseResponse<>(ResponseCode.NO_DATA);
        }
        res.setName(name);
        if (!tagService.updateTag(res)) {
            log.error("TagController.update error: update tag failed which is {}", res);
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
        }
        log.info("TagController.update success: update tag success which is {}", res);
        return new BaseResponse<>(ResponseCode.SUCCESS, res);
    }

    /**
     * 获取标签
     * @param id 标签ID
     * @return Result<Tag>
     */
    @GetMapping("/get/{id}")
    public BaseResponse<Tag> get(@PathVariable Integer id) {
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