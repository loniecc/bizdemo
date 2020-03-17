package com.lonie.biz.domain.repostitory;

import com.lonie.biz.domain.model.UserDO;
import com.lonie.biz.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author huzeming Created time 2020/3/17 : 8:36 下午 Desc:
 */

@Repository
public class UserRepository {

    @Autowired
    private UserMapper mUserMapper;

    public UserDO queryUserInfo(long userId) {
        return mUserMapper.selectUserById(userId);
    }

}
