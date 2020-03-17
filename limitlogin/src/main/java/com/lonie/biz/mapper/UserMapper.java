package com.lonie.biz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.lonie.biz.domain.model.UserDO;

/**
 * @author huzeming Created time 2020/3/16 : 12:19 上午 Desc:
 */

@Mapper
public interface UserMapper {

    List<UserDO> queryAllUser();


    UserDO selectUserById(@Param("id") long id);

}
