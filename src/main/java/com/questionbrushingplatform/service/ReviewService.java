package com.questionbrushingplatform.service;

import com.questionbrushingplatform.entity.Question;
import com.questionbrushingplatform.entity.Review;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author wenruohan
* @description 针对表【review(审核表)】的数据库操作Service
* @createDate 2024-09-17 21:53:07
*/
public interface ReviewService extends IService<Review> {

    List<Review> getReviewQuestionList();

}
