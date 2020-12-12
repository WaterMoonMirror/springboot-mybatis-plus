package com.wondersgroup.mp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wondersgroup.mp.entity.User;
import com.wondersgroup.mp.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: lizhu@wondesgroup.com
 * @date: 2020/12/11 10:42
 * @description: mp 的查询操作
 */
@SpringBootTest
public class SelectTest {

    @Autowired
    private UserMapper userMapper;

    // 测试查询
    @Test
    public void testSelectById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    // 测试批量查询！
    @Test
    public void testSelectByBatchId() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    // 按条件查询之一使用map操作
    @Test
    public void testSelectByBatchIds() {
        HashMap<String, Object> map = new HashMap<>();
        // 自定义要查询
        map.put("name", "kwhua");
        map.put("age", 15);

        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    // 测试分页查询
    @Test
    public void testPage() {
        // 参数一：当前页
        // 参数二：页面大小
        Page<User> page = new Page<>(2, 5);
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());
    }

    /**
     * 条件构造器(Wrapper)
     */
// .isNotNull .gt
    @Test
    void contextLoads() {
        // 查询name不为空的用户，并且邮箱不为空的用户，年龄大于等于12
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .isNotNull("name") //不为空
                .isNotNull("email")
                .ge("age", 18);
        userMapper.selectList(wrapper).forEach(System.out::println); // 和我们刚才学习的map对比一下
    }

    // .eq 等于
    @Test
    void test2() {
        // 查询名字kwhua
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "kwhua");
        User user = userMapper.selectOne(wrapper); // 查询一个数据用selectOne，查询多个结果使用List 或者 Map
        System.out.println(user);
    }

// .between

    @Test
    void test3() {
        // 查询年龄在 20 ~ 30 岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age", 20, 30); // 区间
        Integer count = userMapper.selectCount(wrapper);// 查询结果数
        System.out.println(count);
    }

    /// .like
// 模糊查询
    @Test
    void test4() {
        // 查询名字中不带e且 邮箱以t开头的数据
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 左 likelift  %t  ，右 likeRight  t%
        wrapper
                .notLike("name", "e")
                .likeRight("email", "t");

        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    // .insql
// 模糊查询
    @Test
    void test5() {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // id 在子查询中查出来
        wrapper.inSql("id", "select id from user where id<3");

        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    // .orderByAsc
//测试六
    @Test
    void test6() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 通过id进行排序
        wrapper.orderByAsc("id");

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * 测试用例
     */
/*
    名字中包含并且a年龄小于28
    name like '%a%'  and  age <28
 */
    @Test
    void sample1() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name", "a")
                .lt("age", 28);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
    // 使用Lambda实现
    @Test
    void sample2() {
        LambdaQueryWrapper<User> wrapper= Wrappers.lambdaQuery(User.class);
        wrapper.like(User::getName,"a").lt(User::getAge,28);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * *2、名字中包含a年并且龄大于等于20且小于等于40并且email不为空
     * name like'%a%'and age between 20 and 40 and email is not null
     */
    @Test
    void sample3() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name","a").between("age",20,40).isNotNull("email");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
    // 使用Lambda实现
    @Test
    void sample4() {
        LambdaQueryWrapper<User> wrapper= Wrappers.lambdaQuery(User.class);
        wrapper.like(User::getName,"a").between(User::getAge,20,40).isNotNull(User::getEmail);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * 3、名字为k姓或者年龄大于等于18，按照年龄降序排列，年龄相同按照id升序排列
     * name like'王%'or age>=25order by age desc， id asc
     */

    @Test
    void sample5() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.likeRight("name","k").or().ge("age",18).orderByDesc("age").orderByAsc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
    // 使用Lambda实现
    @Test
    void sample6() {
        LambdaQueryWrapper<User> wrapper= Wrappers.lambdaQuery(User.class);
        wrapper.likeRight(User::getName,"k").or().ge(User::getAge,18).orderByDesc(User::getAge).orderByAsc(User::getId);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
    /**
     * 4、创建日期为2019年11月1日并且直属上级为名字为K姓
     * 'date_format(mgt_create， '%Y-%m-%d) and manager_id in(select id from user where name like'k%')
     */
    @Test
    void sample7() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.apply("date_format(gmt_create,'%Y-%m-%d')={0}","2019-11-01")
                .inSql("manager_id","select id from user where name like'k%'");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
    // 使用Lambda实现
    @Test
    void sample8() {
        LambdaQueryWrapper<User> wrapper= Wrappers.lambdaQuery(User.class);
        wrapper.apply("date_format(gmt_create,'%Y-%m-%d')={0}","2019-11-01")
                .inSql(User::getManagerId,"select id from user where name like'k%'");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
    /**
     * 5、名字为王姓并且(年龄小于40或邮箱不为空)
     * name like'王%'and(age<40or email is not null)
     */
    @Test
    void sample9() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.likeRight("name","王").and(t->t.lt("age",40).or().isNotNull("email"));
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
    // 使用Lambda实现
    @Test
    void sample10() {
        LambdaQueryWrapper<User> wrapper= Wrappers.lambdaQuery(User.class);
        wrapper.apply("date_format(gmt_create,'%Y-%m-%d')={0}","2019-11-01")
                .inSql(User::getManagerId,"select id from user where name like'k%'");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
    /**
     * (age<40or email is not null) and name like'王%'
     */
    @Test
    void sample11() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.nested(t->t.lt("age",40).or().isNotNull("email")).likeRight("name","王");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
    // 使用Lambda实现
    @Test
    void sample12() {
        LambdaQueryWrapper<User> wrapper= Wrappers.lambdaQuery(User.class);
        wrapper.nested(t->t.lt(User::getAge,40).or().isNotNull(User::getEmail)).likeRight(User::getName,"王");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
    /**
     * 8、年龄为30、31、34、35
     * age in(30、31、34、35)
     */
    @Test
    void sample13() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.in("age",Arrays.asList(30,31,34,35));
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
    // 使用Lambda实现
    @Test
    void sample14() {
        LambdaQueryWrapper<User> wrapper= Wrappers.lambdaQuery(User.class);
        wrapper.in(User::getAge,Arrays.asList(30,31,34,35));
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
    /**
     * 9、只返回满足条件的其中一条语句即可
     * limit 1
     */
    @Test
    void sample15() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.in("age",Arrays.asList(30,31,34,35)).last("limit 1");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
    // 使用Lambda实现
    @Test
    void sample16() {
        LambdaQueryWrapper<User> wrapper= Wrappers.lambdaQuery(User.class);
        wrapper.in(User::getAge,Arrays.asList(30,31,34,35)).last("limit 1");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * 11、按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。
     * 并且只取年龄总和小于500的组。
     * select avg(age) avg_age， min(age) min_age， max(age) max_age
     * from user
     * group by manager_id
     * having sum(age) < 500
     */
    @Test
    void sample17() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("avg(age) avg_age","min(age) min_age","max(age) max_age")
                .groupBy("manager_id").having("sum(age) < {0}",500);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
    // 使用Lambda实现   无法实现
    @Test
    void sample18() {
        LambdaQueryWrapper<User> wrapper= Wrappers.lambdaQuery(User.class);
        wrapper.select(User::getAge,User::getManagerId)
                .groupBy(User::getManagerId).having("sum(age) < {0}",500);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
}


