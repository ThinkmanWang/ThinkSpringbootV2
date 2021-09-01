package com.thinkman.thinkspringboot.project.service;

import com.thinkman.thinkspringboot.project.mapper.StudentMapper;
import com.thinkman.thinkspringboot.project.model.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentMapper studentMapper;

    public List<Student> getAll() {
        return studentMapper.getAll();
    }

    public Student getByID(int nId) {
        return studentMapper.getByID(nId);
    }
}
