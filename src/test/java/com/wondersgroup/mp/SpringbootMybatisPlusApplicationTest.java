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
 * @description:
 */
@SpringBootTest
@Slf4j
public  class SpringbootMybatisPlusApplicationTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void  test(){
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

}