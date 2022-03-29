package com.thinkman.thinkspringboot.project.model.db;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 测试
 * </p>
 *
 * @author Thinkman
 * @since 2022-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_test")
public class TTest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 测试
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;


}
