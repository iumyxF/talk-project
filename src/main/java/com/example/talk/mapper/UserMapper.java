package com.example.talk.mapper;

import com.example.talk.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author iumyxF
 * @description 针对表【sys_user】的数据库操作Mapper
 * @createDate 2023-12-01 14:54:50
 * @Entity com.example.talk.model.domain.User
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




