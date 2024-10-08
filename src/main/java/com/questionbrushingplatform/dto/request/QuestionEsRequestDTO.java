package com.questionbrushingplatform.dto.request;

import com.questionbrushingplatform.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 永
 */
@Document(indexName = "question")
@Data
public class QuestionEsRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String DATE_TIME_PATTERN="yyyy-MM-dd HH:mm:ss";

    @Id
    private Long id;

    private String title;

    private String content;

    private String answer;

    private List<String> tags;

    private Long userId;

    private Integer questionBankId;

    @Field(type= FieldType.Date,format = {},pattern = DATE_TIME_PATTERN)
    private LocalDateTime createdTime;

    @Field(type= FieldType.Date,format = {},pattern = DATE_TIME_PATTERN)
    private LocalDateTime updatedTime;

    @Field(type= FieldType.Date,format = {},pattern = DATE_TIME_PATTERN)
    private LocalDateTime editTime;

    private Integer isDelete;



    /**
     * 对象转包装类
     * @param question
     * @return
     */
    public static QuestionEsRequestDTO objToDto(Question question) {
        if (question==null){
            return null;
        }
        QuestionEsRequestDTO questionEsDTO = new QuestionEsRequestDTO();
        BeanUtils.copyProperties(question,questionEsDTO);
        //TODO
//        String tagsStr = question.getTags();
//        if (StrUtil.isNotBlank(tagsStr)){
//            questionEsDTO.setTags(JSONUtil.toList(tagsStr, String.class));
//        }
        return questionEsDTO;
    }


    /**
     * 包装类转对象
     * @param questionEsDTO
     * @return
     */
    public static Question dtoToObj (QuestionEsRequestDTO questionEsDTO) {
        if (questionEsDTO==null){
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionEsDTO,question);
        //TODO
//        List<String> tagList = questionEsDTO.getTags();
//        if (CollUtil.isNotEmpty(tagList)){
//            question.setTags(JSONUtil.toJsonStr(tagList));
//        }
        return question;
    }

}
