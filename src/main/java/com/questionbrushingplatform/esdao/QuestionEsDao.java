package com.questionbrushingplatform.esdao;

import com.questionbrushingplatform.dto.request.QuestionEsRequestDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * @author 永
 */
public interface QuestionEsDao extends ElasticsearchRepository<QuestionEsRequestDTO, Long> {

}
