package com.pix.testgreendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.pix.testgreendao.bean.Student;
import com.pix.testgreendao.service.StudentFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StudentFactory.getStudentService().addStudent(new Student(1 + "","zhangsan1",20));
        StudentFactory.getStudentService().addStudent(new Student(2 + "","zhangsan2",20));
        StudentFactory.getStudentService().addStudent(new Student(3 + "","zhangsan3",20));
        StudentFactory.getStudentService().addStudent(new Student(4 + "","zhangsan4",20));
        List<Student> students = StudentFactory.getStudentService().getAllStudents();
        if(students!=null) {
            for(Student stu : students) {
                Log.d(TAG,"student:id:" + stu.getId() + ",name:" + stu.getName() + ",age:" + stu.getAge());
            }
        }
    }
}
