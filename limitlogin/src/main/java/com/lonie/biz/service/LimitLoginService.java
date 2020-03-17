package com.lonie.biz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lonie.biz.common.dto.BizResult;
import com.lonie.biz.common.redis.RedisCommonComponent;
import com.lonie.biz.common.redis.RedisStringComponent;
import com.lonie.biz.domain.model.UserDO;
import com.lonie.biz.domain.repostitory.UserRepository;

/**
 * @author huzeming Created time 2020/3/16 : 1:36 下午 Desc:
 */

@Service
public class LimitLoginService {

    String login_limit = "user:login:";

    @Autowired
    private RedisStringComponent mRedisStringComponent;

    @Autowired
    private RedisCommonComponent mRedisCommonComponent;

    @Autowired
    private UserRepository mUserRepository;

    public BizResult<Boolean> login(long userId, String passwd) {

        long expireAfterTime = hasLimit(userId);

        if (expireAfterTime > 0) {
            return new BizResult<Boolean>().setSuccess(false).setResultMsg("该用户已被限制登陆，" + expireAfterTime + " 秒后可重新登陆");
        }

        UserDO userDO = mUserRepository.queryUserInfo(userId);

        if (userDO == null) {
            return new BizResult<Boolean>().setSuccess(false).setResultMsg("该用户不存在");
        }

        if (passwd.equalsIgnoreCase(userDO.getPasswd())) {
            clearUserLimit(userId);
            return new BizResult<Boolean>().setSuccess(true).setResultMsg("登陆成功");
        } else {
            long errorTime = updateUserLimit(userId);

            if (errorTime > 4) {
                return new BizResult<Boolean>().setSuccess(false).setResultMsg("该用户已被限制登陆，1小时之后可重新登陆");
            } else {
                return new BizResult<Boolean>().setSuccess(false).setResultMsg(
                    "密码错误, " + (5 - errorTime) + "次错误后将锁定登陆1h");
            }

        }

    }

    private void clearUserLimit(long userId) {
        String key = login_limit + userId;
        mRedisCommonComponent.delKey(key);
    }

    private long updateUserLimit(long userId) {
        String key = login_limit + userId;
        long errorTime = mRedisStringComponent.incr(key);

        if (errorTime > 5) {
            mRedisStringComponent.setValue(key, errorTime + "", 3600);
        }
        return errorTime;

    }

    private long hasLimit(long userId) {

        String key = login_limit + userId;
        if (!mRedisCommonComponent.keyExist(login_limit + userId)) {
            return 0;
        }

        String errorTimes = mRedisStringComponent.getValue(key);

        if (Integer.parseInt(errorTimes) >= 5) {
            return mRedisStringComponent.getExpireLeftTime(key);
        } else {
            return 0;
        }

    }

}
