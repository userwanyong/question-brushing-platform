package com.questionbrushingplatform.esdao;

import com.questionbrushingplatform.pojo.dto.QuestionEsDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface QuestionEsDao extends ElasticsearchRepository<QuestionEsDTO, Long> {

}
