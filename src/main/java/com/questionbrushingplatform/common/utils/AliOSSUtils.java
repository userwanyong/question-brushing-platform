package com.questionbrushingplatform.common.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 阿里云OSS工具类
 */
@Component
public class AliOSSUtils {

    private String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    private String accessKeyId = "LTAI5tHUGP2kG6W6Vmjxa3fs";
    private String accessKeySecret = "y6FftEgVKn1fGuusJBDnhiKSyrwDLw";
    private String bucketName = "qbp-file";

    /**
     * 上传图片到OSS
     */
    public String upload(MultipartFile file) throws IOException {
        //获取上传的文件的输入流
        InputStream inputStream = file.getInputStream();
        //避免文件覆盖
        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + originalFileName.substring(originalFileName.lastIndexOf("."));
        //上传文件到OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);
        //文件访问路径
        String url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
        //关闭OSSClient
        ossClient.shutdown();
        //返回OSS上的路径
        return url;
    }


}
