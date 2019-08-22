package com.pix.testgreendao.service;

import com.pix.testgreendao.bean.Student;
import com.pix.testgreendao.greendao.StudentDao;
import com.pix.testgreendao.manager.GreenDaoUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */

public class StudentServiceImpl implements IStudentService {
    StudentDao dao;

    public StudentServiceImpl() {
        dao = GreenDaoUtils.getInstance().getmDaoSession().getStudentDao();
    }
    @Override
    public boolean addStudent(Student o) {
        dao.insert(o);
        return true;
    }

    @Override
    public List<Student> getAllStudents() {
        QueryBuilder<Student> qb = dao.queryBuilder();
        return qb.list();
    }
}
