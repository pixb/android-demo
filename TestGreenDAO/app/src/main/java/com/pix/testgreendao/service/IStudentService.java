package com.pix.testgreendao.service;

import com.pix.testgreendao.bean.Student;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */

public interface IStudentService {
    /**
     * 插入一个学生
     * @param o
     * @return
     */
    public boolean addStudent(Student o);

    public List<Student> getAllStudents();
}
