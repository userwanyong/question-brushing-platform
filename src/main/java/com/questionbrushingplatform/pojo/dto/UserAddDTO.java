package com.questionbrushingplatform.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 永
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddDTO {


    private String userAccount;

    private String userPassword;

    private String userName;

    private String userAvatar;

    private String userProfile;

    private String userRole;


}
