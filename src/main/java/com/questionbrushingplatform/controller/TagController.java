package com.questionbrushingplatform.controller;

import com.questionbrushingplatform.common.result.Result;
import com.questionbrushingplatform.pojo.entity.Tag;
import com.questionbrushingplatform.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    /**
     * 新增标签
     * @param name 标签名称
     * @return Result<Boolean>
     */
    @PostMapping("/add")
    public Result<Boolean> add(String name) {
        if (tagService.getTagByName(name) != null) {
            return Result.error("标签已存在");
        }
        Tag tag = new Tag();
        tag.setName(name);
        return Result.success(tagService.addTag(tag));
    }

    /**
     * 删除标签
     * @param id 标签ID
     * @return Result<Boolean>
     */
    @PostMapping("/delete")
    public Result<Boolean> delete(Integer id) {
        return Result.success(tagService.deleteTag(id));
    }

    /**
     * 更新标签
     * @param id 标签ID
     * @param name 标签名称
     * @return Result<Boolean>
     */
    @PostMapping("/update")
    public Result<Boolean> update(Integer id, String name) {
        Tag tag = tagService.getTagById(id);
        if (tag == null) {
            return Result.error("标签不存在");
        }
        tag.setName(name);
        return Result.success(tagService.updateTag(tag));
    }

    /**
     * 获取标签
     * @param id 标签ID
     * @return Result<Tag>
     */
    @GetMapping("/get")
    public Result<Tag> get(@RequestParam Integer id) {
        return Result.success(tagService.getTagById(id));
    }

    /**
     * 获取所有标签
     * @return Result<List<Tag>>
     */
    @GetMapping("/list")
    public Result<List<Tag>> list(@RequestParam String name) {
        if (name.isEmpty()) {
            return Result.success(tagService.listTags());
        }
        return Result.success(tagService.listTagsByName(name));
    }
}
