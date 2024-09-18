package com.questionbrushingplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.questionbrushingplatform.entity.Question;
import com.questionbrushingplatform.entity.Review;
import com.questionbrushingplatform.service.ReviewService;
import com.questionbrushingplatform.mapper.ReviewMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author wenruohan
* @description 针对表【review(审核表)】的数据库操作Service实现
* @createDate 2024-09-17 21:53:07
*/
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review>
    implements ReviewService{

    @Override
    public List<Review> getReviewQuestionList() {
        return list();
    }
}




