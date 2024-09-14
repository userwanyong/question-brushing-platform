package com.questionbrushingplatform.controller;

import com.questionbrushingplatform.common.result.Result;
import com.questionbrushingplatform.common.utils.AliOSSUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@Slf4j
@Api(tags = "文件上传")
public class FileController {

    @Autowired
    private AliOSSUtils aliOSSUtils;

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件")
    public Result upload(MultipartFile file) throws IOException {
        log.info("文件上传，文件名:{}",file.getOriginalFilename());
        //调用阿里云OSS工具类进行上传
        String url = aliOSSUtils.upload(file);
        log.info("文件上传成功，文件访问地址为：{}",url);
        return Result.success(url);
    }

}
