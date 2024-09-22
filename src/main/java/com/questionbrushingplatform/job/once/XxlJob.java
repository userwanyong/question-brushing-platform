//package com.questionbrushingplatform.job.once;
//
//import cn.hutool.core.collection.CollUtil;
//import com.questionbrushingplatform.dto.request.QuestionEsRequestDTO;
//import com.questionbrushingplatform.entity.Question;
//import com.questionbrushingplatform.esdao.QuestionEsDao;
//import com.questionbrushingplatform.service.QuestionService;
//import com.xxl.job.core.handler.IJobHandler;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class XxlJob extends IJobHandler {
//
//    @Resource
//    private QuestionService questionService;
//    @Resource
//    private QuestionEsDao questionEsDao;
//    @Override
//    public void execute() throws Exception {
//        // 初始化 log
//        Logger log = LoggerFactory.getLogger(XxlJob.class);
//
//        //全量获取题目
//        List<Question> questionList = questionService.list();
//        if (CollUtil.isEmpty(questionList)){
//            return;
//        }
//        //转为ES实体类
//        List<QuestionEsRequestDTO> questionEsDTOList = questionList.stream()
//                .map(QuestionEsRequestDTO::objToDto)
//                .collect(Collectors.toList());
//        //分页批量插入到ES
//        final int pageSize = 500;
//        int total = questionEsDTOList.size();
//        log.info("QuestionService.list() 共 [{}] 条数据", total);
//        for (int i = 0; i < total; i+=pageSize) {
//            //注意同步的数据下标不能超过总数据量
//            int end = Math.min(i + pageSize, total);
//            questionEsDao.saveAll(questionEsDTOList.subList(i, end));
//        }
//        log.info("QuestionService.list() 全量同步完成");
//    }
//
//}
