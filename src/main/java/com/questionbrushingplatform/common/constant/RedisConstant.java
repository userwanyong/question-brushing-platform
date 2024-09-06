package com.questionbrushingplatform.common.constant;

/**
 * Redis常量
 */
public interface RedisConstant {

    /**
     * 用户签到记录Redis key前缀
     */
    String USER_SIGN_IN_REDIS_KET_PREFIX = "user:signins";

    /**
     * 获取用户签到记录Redis key
     * @param year
     * @param userId
     * @return
     */
    static String getUserSignInRedisKey(int year,long userId) {
        return String.format("%s:%s:%s", USER_SIGN_IN_REDIS_KET_PREFIX, year, userId);
    }
}
