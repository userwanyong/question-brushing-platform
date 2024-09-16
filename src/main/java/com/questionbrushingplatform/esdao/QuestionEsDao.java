package com.questionbrushingplatform.esdao;

import com.questionbrushingplatform.pojo.dto.QuestionEsDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * @author 永
 */
public interface QuestionEsDao extends ElasticsearchRepository<QuestionEsDTO, Long> {

}
