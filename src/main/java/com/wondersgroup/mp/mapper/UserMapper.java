package com.wondersgroup.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wondersgroup.mp.entity.User;
import org.springframework.stereotype.Repository;
// 在对应的Mapper上面继承基本的类 BaseMapper
@Repository // 代表持久层
public interface UserMapper extends BaseMapper<User> {
  // 所有的CRUD操作都已经编写完成了
}

