package com.wondersgroup.mp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @TableId(type = IdType.AUTO) //自增
  private Long id;
  private String name;
  private Integer age;
  private String email;
  @Version //乐观锁Version注解
  private Integer version;
  // 自动填充注解
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime  gmtCreate;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime gmtModified;

}

