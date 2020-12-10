package com.wondersgroup.mp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime  gmtCreate;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime gmtModified;

}

