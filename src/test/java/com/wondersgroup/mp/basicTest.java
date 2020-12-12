package com.wondersgroup.mp;

import com.wondersgroup.mp.entity.User;
import com.wondersgroup.mp.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author: lizhu@wondesgroup.com
 * @date: 2020/12/10 14:48
 * @description:  mp 的基础操作
 */
@SpringBootTest
@Slf4j
public  class basicTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void  test(){
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void testInsert(){
        User user = new User();
        user.setName("kwhua_mybatis-plus_insertTest");
        user.setAge(15);
        user.setEmail("310697723@qq.com");
        int result = userMapper.insert(user);// 帮我们自动生成id
        System.out.println(result); // 受影响的行数
        System.out.println(user); // 看到id会自动填充。


    }

    @Test
    public void testUpdate(){
        User user = new User();
        // 通过条件自动拼接动态sql
        user.setId(1336938968230400001L);
        user.setName("kwhua_mybatis-plus_updateTest");
        user.setAge(20);
        // 注意：updateById 但是参数是一个对象！
        int i = userMapper.updateById(user);
        System.out.println(i);
    }

    // 测试乐观锁成功！
    @Test
    public void testOptimisticLocker(){
        // 1、查询用户信息
        User user = userMapper.selectById(1L);
        // 2、修改用户信息
        user.setName("kwhua");
        user.setEmail("123456@qq.com");
        // 3、执行更新操作
        userMapper.updateById(user);
    }



}