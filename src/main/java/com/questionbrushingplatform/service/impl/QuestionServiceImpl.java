package com.questionbrushingplatform.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.questionbrushingplatform.mapper.QuestionMapper;
import com.questionbrushingplatform.pojo.dto.QuestionEsDTO;
import com.questionbrushingplatform.pojo.entity.Question;
import com.questionbrushingplatform.pojo.query.QuestionQuery;
import com.questionbrushingplatform.service.QuestionService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    /**
     * 从ES查询题目
     * @param questionQuery
     * @return
     */
    @Override
    public Page<Question> searchFromEs(QuestionQuery questionQuery) {
        //获取参数
        Long id = questionQuery.getId();
        Long notId = questionQuery.getNotId();
        String searchText = questionQuery.getSearchText();
        List<String> tags = questionQuery.getTags();
        Long userId = questionQuery.getUserId();
        Long questionBankId = questionQuery.getQuestionBankId();
        //Es的起始页为0
        int current = questionQuery.getPageNo() - 1;
        int pageSize = questionQuery.getPageSize();
        String sortBy = questionQuery.getSortBy();
        Boolean isAsc = questionQuery.getIsAsc();
        //构造查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //过滤
        boolQueryBuilder.filter(QueryBuilders.termQuery("isDelete",0));
        if (id != null){
            boolQueryBuilder.filter(QueryBuilders.termQuery("id",id));
        }
        if (notId != null){
            boolQueryBuilder.mustNot(QueryBuilders.termQuery("id",notId));
        }
        if (userId!=null){
            boolQueryBuilder.filter(QueryBuilders.termQuery("userId",userId));
        }
        if (questionBankId!=null){
            boolQueryBuilder.filter(QueryBuilders.termQuery("questionBankId",questionBankId));
        }
        //必须包含所有标签
        if (CollUtil.isNotEmpty(tags)){
            for (String tag : tags) {
                boolQueryBuilder.filter(QueryBuilders.termQuery("tags",tag));
            }
        }
        //按关键词检索
        if (StringUtils.isNotBlank(searchText)){
            boolQueryBuilder.should(QueryBuilders.matchQuery("title",searchText));
            boolQueryBuilder.should(QueryBuilders.matchQuery("content",searchText));
            boolQueryBuilder.should(QueryBuilders.matchQuery("answer",searchText));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        //排序
        SortBuilder<?> sortBuilder = SortBuilders.scoreSort();
        if (StringUtils.isNotBlank(sortBy)){
            sortBuilder = SortBuilders.fieldSort(sortBy);
            sortBuilder.order(isAsc ? SortOrder.ASC : SortOrder.DESC);
        }
        //分页
        PageRequest pageRequest = PageRequest.of(current, pageSize);
        //构造查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageRequest)
                .withSorts(sortBuilder)
                .build();
        SearchHits<QuestionEsDTO> searchHits = elasticsearchRestTemplate.search(searchQuery, QuestionEsDTO.class);
        //复用Mybatis-plus的分页对象，封装返回结果
        Page<Question> page = new Page<>();
        page.setTotal(searchHits.getTotalHits());
        ArrayList<Question> resourceList = new ArrayList<>();
        if (searchHits.hasSearchHits()){
            List<SearchHit<QuestionEsDTO>> searchHitList = searchHits.getSearchHits();
            for (SearchHit<QuestionEsDTO> questionEsDTOSearchHit : searchHitList) {

                resourceList.add(QuestionEsDTO.dtoToObj(questionEsDTOSearchHit.getContent()));
            }
        }
        page.setRecords(resourceList);
        return page;
    }

}
