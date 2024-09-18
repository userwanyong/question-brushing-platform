package com.questionbrushingplatform.job.cycle;

import cn.hutool.core.collection.CollUtil;
import com.questionbrushingplatform.dto.request.QuestionEsRequestDTO;
import com.questionbrushingplatform.esdao.QuestionEsDao;
import com.questionbrushingplatform.mapper.QuestionMapper;

import com.questionbrushingplatform.entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 增量同步题目到es
 * @author 永
 */
@Component
@Slf4j
public class IncSyncQuestionToEs {
    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionEsDao questionEsDao;

    /**
     * 每分钟执行一次
     */
    @Scheduled(fixedRate = 60*1000)
    public void run() {
        //查询近5分钟内的数据
        long FIVE_MINUTES = 5*60*1000L;
        Date fiveMinutesAgoDate = new Date(new Date().getTime() - FIVE_MINUTES);
        List<Question> questionList = questionMapper.listQuestionWithDelete(fiveMinutesAgoDate);
        if (CollUtil.isEmpty(questionList)){
            log.info("暂无新增数据");
            return;
        }
        List<QuestionEsRequestDTO> questionEsDTOList = questionList.stream()
                .map(QuestionEsRequestDTO::objToDto)
                .collect(Collectors.toList());
        final int pageSize = 500;
        int total = questionEsDTOList.size();
        log.info("QuestionService.list() 共 [{}] 条数据", total);
        for (int i = 0; i < total; i+=pageSize) {
            //注意同步的数据下标不能超过总数据量
            int end = Math.min(i + pageSize, total);
            log.info("同步数据中 [{}/{}]", i, total);
            questionEsDao.saveAll(questionEsDTOList.subList(i, end));
        }
        log.info("QuestionService.list() 增量同步完成");
    }
}
