package com.pix.testgreendao.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.pix.testgreendao.BaseApplication;
import com.pix.testgreendao.greendao.DaoMaster;
import com.pix.testgreendao.greendao.DaoSession;
import com.pix.testgreendao.greendao.StudentDao;

import org.greenrobot.greendao.database.Database;

public class GreenDaoUtils {
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    DAOOpenHelper mHelper;

    private static GreenDaoUtils mInstance;

    private GreenDaoUtils(){}

    public static GreenDaoUtils getInstance(){
        if(mInstance==null){
            synchronized (GreenDaoUtils.class){
                if(mInstance==null){
                    mInstance = new GreenDaoUtils();
                }
            }
        }
        return mInstance;
    }

    public void initGreenDao(){
        mHelper=new DAOOpenHelper(BaseApplication.getInstance().getApplicationContext(),"test_green_dao",null);
        db=mHelper.getWritableDatabase();
        mDaoMaster=new DaoMaster(db);
        mDaoSession=mDaoMaster.newSession();
    }

    public DaoSession getmDaoSession() {
        if (mDaoMaster==null){
            initGreenDao();
        }
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        if (db==null){
            initGreenDao();
        }
        return db;
    }

    public static class DAOOpenHelper extends DaoMaster.OpenHelper {


        public DAOOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DAOOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }
        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            switch (newVersion){
                case 2:
                    //数据库升级时候用，数据库升到版本2；
                    StudentDao.dropTable(db, true);
                    StudentDao.createTable(db,false);
                    break;
                default:
                    break;
            }

        }
    }

}
