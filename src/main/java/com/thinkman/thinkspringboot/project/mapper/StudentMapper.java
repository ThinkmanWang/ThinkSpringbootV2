package com.thinkman.thinkspringboot.project.mapper;

import com.thinkman.thinkspringboot.project.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM students")
    public List<Student> getAll();

    @Select("SELECT " +
            "  * " +
            "FROM " +
            "  students " +
            "WHERE " +
            "  s_id = #{id}")
    public Student getByID(@Param("id") int nId);
}
