package qzhenghao.cn.yumunote.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库工具类
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_FOLDERS = "folders";
    public static final String TABLE_NOTES = "notes";

    //默认建表语句 默认文件夹 回收站
    //使用blob类型存对象 有点偷懒..
    private static final String CREATE_TABLE_FOLDERS =

            "create table folders ("
                    +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "folderName TEXT)";

    private static final String CREATE_TABLE_NOTES =
            "create table notes ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "folders_id integer(4),"
                    + "content TEXT not null,"
                    + "is_warn integer not null,"
                    + "warn_data integer,"
                    + "up_data integer,"
                    + "FOREIGN KEY (folders_id ) REFERENCES folders(id))";

    private Context mContext;
    private static DBHelper dbHelper = null;
    private DBHelper(Context context) {
        //数据库名
        super(context, "YuMuNote.db", null, 1);
        this.mContext = context;
    }

    /**
     * 单例模式
     * @param context
     * @return
     */
    public static DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    /**
     * 初始化
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FOLDERS);
        db.execSQL(CREATE_TABLE_NOTES);
       //MsgToast.showToast(mContext, "谢谢你的使用!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TO DO
    }



    /**
     * 完全删除
     * @param name
     */
    public void drop_table_deep(String name){
        String drop =
                "drop table " + name;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(drop);

    }

    /**
     * 文件夹更新
     * @param oldName
     * @param newName
     */
    public void update_table(String oldName, String newName) {

        String update = " alter table " + oldName + " rename to " + newName;


        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(update);
    }

    /**
     * 文件夹是否存在
     * @param name
     * @return
     */
    public boolean folderIsExist(String name) {

        boolean result = false;
        if (name != null) {

            try {
                SQLiteDatabase db = this.getReadableDatabase();
                String sql = "select count(*) as c from sqlite_master where type ='table' " +
                        "and name ='" +name+ "' ";
                Cursor cursor = db.rawQuery(sql, null);
                if (cursor.moveToNext()) {
                    int count = cursor.getInt(0);
                    if (count > 0) {
                        result = true;
                    }
                }
                cursor.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;

    }
}

