package com.questionbrushingplatform.pojo.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Data
public class UserQuery extends PageQuery {

    private String userAccount;
    private String userName;
    private String userRole;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
