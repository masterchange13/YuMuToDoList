package qzhenghao.cn.yumunote.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import qzhenghao.cn.yumunote.bean.FolderItemBean;
import qzhenghao.cn.yumunote.bean.NoteItemBean;
import qzhenghao.cn.yumunote.utils.RelativeDateFormat;
import qzhenghao.cn.yumunote.utils.StringUtil;

/**
 * 数据库管理类
 */

public class DBManager {

    private Context mContext;
    private NoteItemBean noteItemBean;

    public DBManager(Context context) {
        mContext = context;
    }

    /**
     * 插入文件夹
     */
    public long insertFolder(String folderName) {

        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FolderItemBean.FOLDERNAME, folderName.trim());
        //插入数据 用ContentValues对象也即HashMap操作,并返回ID号
        Long folderId = database.insert(DBHelper.TABLE_FOLDERS, FolderItemBean.ID, values);
        database.close();
        return folderId;

    }


    /**
     * 插入一条备忘录
     */
    public void insertDB() throws ParseException {

        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Date date = new Date();
        database.beginTransaction(); // 手动设置开始事务
//        database.execSQL("insert into " + DBHelper.TABLE_FOLDERS + "(" + FolderItemBean.FOLDERNAME + ") values (" + "'事件提醒'" + ")");
//        database.execSQL("insert into " + DBHelper.TABLE_FOLDERS + "(" + FolderItemBean.FOLDERNAME + ") values (" + "'Android笔记'" + ")");
//        database.execSQL("insert into " + DBHelper.TABLE_FOLDERS + "(" + FolderItemBean.FOLDERNAME + ") values (" + "'数据库基础知识'" + ")");
//        database.execSQL("insert into " + DBHelper.TABLE_FOLDERS + "(" + FolderItemBean.FOLDERNAME + ") values (" + "'java基础知识'" + ")");
//        database.execSQL("insert into " + DBHelper.TABLE_FOLDERS + "(" + FolderItemBean.FOLDERNAME + ") values (" + "'javaEE+数据库'" + ")");
//        database.execSQL("insert into " + DBHelper.TABLE_FOLDERS + "(" + FolderItemBean.FOLDERNAME + ") values (" + "'JVM面试'" + ")");
//        database.execSQL("insert into " + DBHelper.TABLE_FOLDERS + "(" + FolderItemBean.FOLDERNAME + ") values (" + "'SSM'" + ")");
//        database.execSQL("insert into " + DBHelper.TABLE_FOLDERS + "(" + FolderItemBean.FOLDERNAME + ") values (" + "'Springboot'" + ")");
//        database.execSQL("insert into " + DBHelper.TABLE_FOLDERS + "(" + FolderItemBean.FOLDERNAME + ") values (" + "'NodeJS笔记'" + ")");
//        database.execSQL("insert into " + DBHelper.TABLE_FOLDERS + "(" + FolderItemBean.FOLDERNAME + ") values (" + "'python笔记'" + ")");
//        database.execSQL("insert into " + DBHelper.TABLE_FOLDERS + "(" + FolderItemBean.FOLDERNAME + ") values (" + "'Linux笔记'" + ")");
//        database.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
//        database.endTransaction(); // 处理完成
//        database.beginTransaction(); // 手动设置开始事务
//        database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID +","+NoteItemBean.CONTENT +","+NoteItemBean.IS_WARN +","+NoteItemBean.UP_DATA +") values ('" + 1+"', "+"'明天8点春游',"+"'0','"+date.getTime() + "')");
//        database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID +","+NoteItemBean.CONTENT +","+NoteItemBean.IS_WARN +","+NoteItemBean.UP_DATA +") values ('" + 1+"', "+"'考试本周末举行，在北四808，数学英语',"+"'0','"+date.getTime() + "')");
//        database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID +","+NoteItemBean.CONTENT +","+NoteItemBean.IS_WARN +","+NoteItemBean.UP_DATA +") values ('" + 1+"', "+"'双十一买苹果全套',"+"'0','"+date.getTime() + "')");
//        database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID +","+NoteItemBean.CONTENT +","+NoteItemBean.IS_WARN +","+NoteItemBean.UP_DATA +") values ('" + 1+"', "+"'星期一 Android，数据库，linux，文献',"+"'0','"+date.getTime() + "')");
//        database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID +","+NoteItemBean.CONTENT +","+NoteItemBean.IS_WARN +","+NoteItemBean.UP_DATA +") values ('" + 1+"', "+"'星期二 linux，文献',"+"'0','"+date.getTime() + "')");
//        database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID +","+NoteItemBean.CONTENT +","+NoteItemBean.IS_WARN +","+NoteItemBean.UP_DATA +") values ('" + 1+"', "+"'星期三 英语，数学，文献',"+"'0','"+date.getTime() + "')");
//        database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID +","+NoteItemBean.CONTENT +","+NoteItemBean.IS_WARN +","+NoteItemBean.UP_DATA +") values ('" + 1+"', "+"'星期四 英语，物理，科学，文献',"+"'0','"+date.getTime() + "')");
//        database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID +","+NoteItemBean.CONTENT +","+NoteItemBean.IS_WARN +","+NoteItemBean.UP_DATA +") values ('" + 1+"', "+"'星期五 语文，linux，科学，文献',"+"'0','"+date.getTime() + "')");
//        database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID +","+NoteItemBean.CONTENT +","+NoteItemBean.IS_WARN +","+NoteItemBean.UP_DATA +") values ('" + 1+"', "+"'大作业：实现android系统应用程序实现备忘录系统功能',"+"'0','"+date.getTime() + "')");
        for (int k=3;k<11; k++)
        {
            database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID + "," + NoteItemBean.CONTENT + "," + NoteItemBean.IS_WARN + "," + NoteItemBean.UP_DATA + ") values ('" + k + "', " + "'android四大组件之一service 服务\nA started service\n" +
                    "被开启的service通过其他组件调用startService()被创建。\n" +
                    "这种service可以无限地运行下去，必须调用stopSelf()方法或者其他组件调用stopService()方法来停止它。\n" +
                    "当service被停止时，系统会销毁它。\n" +
                    "A bound service\n" +
                    "被绑定的service是当其他组件（一个客户）调用bindService()来创建的。\n" +
                    "客户可以通过一个IBinder接口和service进行通信。\n" +
                    "客户可以通过unbindService()方法来关闭这种连接。\n" +
                    "一个service可以同时和多个客户绑定，当多个客户都解除绑定之后，系统会销毁service。'," + "'0','" + date.getTime() + "')");
            database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID + "," + NoteItemBean.CONTENT + "," + NoteItemBean.IS_WARN + "," + NoteItemBean.UP_DATA + ") values ('" + k + "', " + "'activity\n" +
                    "（1）一个Activity通常就是一个单独的屏幕（窗口）。\n" +
                    "\n" +
                    "（2）Activity之间通过Intent进行通信。\n" +
                    "\n" +
                    "（3）android应用中每一个Activity都必须要在AndroidManifest.xml配置文件中声明，否则系统将不识别也不执行该Activity。'," + "'0','" + date.getTime() + "')");
            database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID + "," + NoteItemBean.CONTENT + "," + NoteItemBean.IS_WARN + "," + NoteItemBean.UP_DATA + ") values ('" + k + "', " + "'content provider\n" +
                    "（1）android平台提供了Content Provider使一个应用程序的指定数据集提供给其他应用程序。其他应用可以通过ContentResolver类从该内容提供者中获取或存入数据。'," + "'0','" + date.getTime() + "')");
            database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID + "," + NoteItemBean.CONTENT + "," + NoteItemBean.IS_WARN + "," + NoteItemBean.UP_DATA + ") values ('" + k + "', " + "'broadcast receiver\n" +
                    "（1）你的应用可以使用它对外部事件进行过滤，只对感兴趣的外部事件(如当电话呼入时，或者数据网络可用时)进行接收并做出响应。广播接收器没有用户界面。然而，它们可以启动一个activity或serice来响应它们收到的信息，或者用NotificationManager来通知用户。通知可以用很多种方式来吸引用户的注意力，例如闪动背灯、震动、播放声音等。一般来说是在状态栏上放一个持久的图标，用户可以打开它并获取消息。'," + "'0','" + date.getTime() + "')");
            database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID + "," + NoteItemBean.CONTENT + "," + NoteItemBean.IS_WARN + "," + NoteItemBean.UP_DATA + ") values ('" + k + "', " + "'Intent介绍\n" +
                    "Intent是Android系统用来抽象描述要执行的一个操作，也可以在不同组件之间进行沟通和消息传递。'," + "'0','" + date.getTime() + "')");
            database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID + "," + NoteItemBean.CONTENT + "," + NoteItemBean.IS_WARN + "," + NoteItemBean.UP_DATA + ") values ('" + k + "', " + "'星期三 英语，数学，文献'," + "'0','" + date.getTime() + "')");
            database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID + "," + NoteItemBean.CONTENT + "," + NoteItemBean.IS_WARN + "," + NoteItemBean.UP_DATA + ") values ('" + k + "', " + "'星期四 英语，物理，科学，文献'," + "'0','" + date.getTime() + "')");
            database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID + "," + NoteItemBean.CONTENT + "," + NoteItemBean.IS_WARN + "," + NoteItemBean.UP_DATA + ") values ('" + k + "', " + "'星期五 语文，linux，科学，文献'," + "'0','" + date.getTime() + "')");
            database.execSQL("insert into " + DBHelper.TABLE_NOTES + "(" + NoteItemBean.FOLDERS_ID + "," + NoteItemBean.CONTENT + "," + NoteItemBean.IS_WARN + "," + NoteItemBean.UP_DATA + ") values ('" + k + "', " + "'大作业：实现android系统应用程序实现备忘录系统功能'," + "'0','" + date.getTime() + "')");

        }
        database.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
        database.endTransaction(); // 处理完成

        database.close();
    }


    /**
     * 插入一条备忘录
     */
    public long insertNote(NoteItemBean noteItemBean) throws ParseException {

        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NoteItemBean.CONTENT, noteItemBean.getContent());
        values.put(NoteItemBean.FOLDERS_ID, noteItemBean.getFolders_id());
        values.put(NoteItemBean.IS_WARN, noteItemBean.getIs_warn());
        if (noteItemBean.getWarn_data() != null) {
            values.put(NoteItemBean.WARN_DATA, noteItemBean.getWarn_data().getTime());
        }
        values.put(NoteItemBean.UP_DATA, noteItemBean.getUp_data().getTime());
        //插入数据 用ContentValues对象也即HashMap操作,并返回ID号
        Log.e("qzh1_xjdata1", noteItemBean + "|||");
        Long noteId = database.insert(DBHelper.TABLE_NOTES, NoteItemBean.ID, values);
        database.close();
        return noteId;
    }

    /**
     * 查询一个文件夹
     *
     * @return
     */
    public FolderItemBean queryOneFolder(long id) {
        FolderItemBean folderItemBean = null;
        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] columns = new String[]{FolderItemBean.ID, FolderItemBean.FOLDERNAME};
        String selection = FolderItemBean.ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        Cursor cursor = database.query(DBHelper.TABLE_FOLDERS, columns, selection, selectionArgs, null, null, null, null);
        if (cursor.moveToFirst()) {
            folderItemBean = new FolderItemBean();
            folderItemBean.setId(cursor.getInt(0));
            folderItemBean.setFolderName(cursor.getString(1));
            return folderItemBean;
        }
        return folderItemBean;
    }

    /**
     * 查询所有文件夹
     *
     * @return
     */
    public List<FolderItemBean> queryAllFolder() {
        List<FolderItemBean> folderItemBeanList = new ArrayList<>();
        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] columns = new String[]{FolderItemBean.ID, FolderItemBean.FOLDERNAME};
        Cursor cursor = database.query(DBHelper.TABLE_FOLDERS, columns, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
            FolderItemBean folderItemBean = new FolderItemBean();
            folderItemBean.setId(Integer.parseInt(cursor.getString(0)));

            folderItemBean.setFolderName(cursor.getString(1));
            folderItemBeanList.add(folderItemBean);
            cursor.moveToNext();
        }
        return folderItemBeanList;
    }

    /**
     * 查询所有备忘录
     *
     * @return
     */
    public List<NoteItemBean> queryAllNotes(Integer foldreId) throws ParseException {
        List<NoteItemBean> noteItemBeanList = new ArrayList<>();
        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] columns = new String[]{NoteItemBean.ID, NoteItemBean.FOLDERS_ID, NoteItemBean.CONTENT, NoteItemBean.IS_WARN, NoteItemBean.WARN_DATA, NoteItemBean.UP_DATA};
        String selection = NoteItemBean.FOLDERS_ID + "=?";
        String[] selectionArgs = new String[]{foldreId.toString()};
        String orderBy = "id";
        Cursor cursor = database.query(DBHelper.TABLE_NOTES, columns, selection, selectionArgs, null, null, orderBy, null);
        cursor.moveToFirst();
        NoteItemBean noteItemBean;
        while (!cursor.isAfterLast()) {
            noteItemBean = new NoteItemBean();
            noteItemBean.setId(cursor.getInt(0));

            noteItemBean.setFolders_id(cursor.getInt(1));
            noteItemBean.setContent(cursor.getString(2));
            noteItemBean.setIs_warn(cursor.getInt(3));
            if (!StringUtil.isNullOrEmpty(cursor.getString(4))) {
                noteItemBean.setWarn_data(new Date(new Long(cursor.getString(4))));
            }
            String string = cursor.getString(5);
            if (!StringUtil.isNullOrEmpty(string)) {
                noteItemBean.setUp_data(new Date(new Long(cursor.getString(5))));
            }
            noteItemBeanList.add(noteItemBean);
            cursor.moveToNext();
        }
        return noteItemBeanList;
    }

    /**
     * 查询一条备忘录
     *
     * @return
     */
    public NoteItemBean queryOneNotes(Integer noteId) throws ParseException {
        NoteItemBean noteItemBean = null;
        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String[] columns = new String[]{NoteItemBean.ID, NoteItemBean.FOLDERS_ID, NoteItemBean.CONTENT, NoteItemBean.IS_WARN, NoteItemBean.WARN_DATA, NoteItemBean.UP_DATA};
        String selection = NoteItemBean.ID + "=?";
        String[] selectionArgs = new String[]{noteId.toString()};
        String orderBy = "id";
        Cursor cursor = database.query(DBHelper.TABLE_NOTES, columns, selection, selectionArgs, null, null, orderBy, null);
        if (cursor.moveToFirst()) {
            noteItemBean = new NoteItemBean();
            noteItemBean.setId(cursor.getInt(0));
            noteItemBean.setFolders_id(cursor.getInt(1));
            noteItemBean.setContent(cursor.getString(2));
            noteItemBean.setIs_warn(cursor.getInt(3));
            if (!StringUtil.isNullOrEmpty(cursor.getString(4))) {
                noteItemBean.setWarn_data(new Date(new Long(cursor.getString(4))));
            }
            String string = cursor.getString(5);
            if (!StringUtil.isNullOrEmpty(string)) {
                noteItemBean.setUp_data(new Date(new Long(cursor.getString(5))));
            }
        }
        return noteItemBean;
    }

    /**
     * 更新一条备忘录内容
     *
     * @param noteItemBean
     * @return
     */
    public Integer updateNote(NoteItemBean noteItemBean) {

        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (noteItemBean.getUp_data() != null) {
            contentValues.put(NoteItemBean.UP_DATA, noteItemBean.getUp_data().getTime());
        }
        contentValues.put(NoteItemBean.CONTENT, noteItemBean.getContent());
        if (noteItemBean.getWarn_data() != null) {
            contentValues.put(NoteItemBean.WARN_DATA, noteItemBean.getWarn_data().getTime());

        }
        contentValues.put(NoteItemBean.IS_WARN, String.valueOf(noteItemBean.getIs_warn()));
        String selection = NoteItemBean.ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(noteItemBean.getId())};
        int noteid = database.update(DBHelper.TABLE_NOTES, contentValues, selection, selectionArgs);
        return noteid;
    }

    /**
     * 删除一条备忘录
     *
     * @param noteId
     */
    public void delNote(Integer noteId) {
        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String selection = NoteItemBean.ID + "=?";
        String[] selectionArgs = new String[]{noteId.toString()};
        database.delete(DBHelper.TABLE_NOTES, selection, selectionArgs);
    }

    /**
     * 删除一条文件夹
     *
     * @param folderId
     */
    public void delFolder(Integer folderId) {
        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String selection = FolderItemBean.ID + "=?";
        String[] selectionArgs = new String[]{folderId.toString()};
        database.delete(DBHelper.TABLE_FOLDERS, selection, selectionArgs);
        String selection1 = NoteItemBean.FOLDERS_ID + "=?";
        database.delete(DBHelper.TABLE_NOTES, selection1, selectionArgs);
    }


    /**
     * 搜索Note
     *
     * @param likeString
     * @return
     */
    public List<NoteItemBean> search(String likeString) {
        List<NoteItemBean> noteItemBeanList = new ArrayList<>();
        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String sql = null;
        String sqlsno = likeString.length() > 0 ? NoteItemBean.CONTENT + " like '%" + likeString.trim() + "%'" : "";
        String sqlname = likeString.length() > 0 ? NoteItemBean.UP_DATA + " like '%" + likeString.trim() + "%'" : "";
        if ((!"".equals(sqlsno)) && (!"".equals(sqlname))) {
            sql = sqlsno + " or " + sqlname;
        } else if (!"".equals(sqlsno)) {
            sql = sqlsno;
        } else if (!"".equals(sqlname)) {
            sql = sqlname;
        }
        String[] columns = new String[]{NoteItemBean.ID, NoteItemBean.FOLDERS_ID, NoteItemBean.CONTENT, NoteItemBean.IS_WARN, NoteItemBean.WARN_DATA, NoteItemBean.UP_DATA};
        Cursor cursor = database.query(true, DBHelper.TABLE_NOTES,
                columns,
                sql,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
            noteItemBean = new NoteItemBean();
            noteItemBean.setId(cursor.getInt(0));

            noteItemBean.setFolders_id(cursor.getInt(1));
            noteItemBean.setContent(cursor.getString(2));
            noteItemBean.setIs_warn(cursor.getInt(3));
            if (!StringUtil.isNullOrEmpty(cursor.getString(4))) {
                noteItemBean.setWarn_data(new Date(new Long(cursor.getString(4))));
            }
            String string = cursor.getString(5);
            if (!StringUtil.isNullOrEmpty(string)) {
                noteItemBean.setUp_data(new Date(new Long(cursor.getString(5))));
            }
            noteItemBeanList.add(noteItemBean);
            cursor.moveToNext();
        }
        Log.e("qzh14", noteItemBeanList + "22q");
        return noteItemBeanList;
    }


    /**
     * 清空文件夹
     *
     * @param folderName
     * @return
     */
    public int clearAllFolder(String folderName) {
        DBHelper dbHelper = DBHelper.getInstance(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        return database.delete(folderName, null, null);
    }


}
