package qzhenghao.cn.yumunote.warm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

import java.util.Date;

import qzhenghao.cn.yumunote.NoteApplication;

/**
 * @author suiyue
 * @ClassName cc
 * @Description TODO
 * @date 2019/10/20 15:19
 */
public class ClockManager {

    private static ClockManager instance = new ClockManager();

    private ClockManager() {
    }

    public static ClockManager getInstance() {
        return instance;
    }

    /**
     * 获取系统闹钟服务
     * @return
     */
    private static AlarmManager getAlarmManager() {
        return (AlarmManager) NoteApplication.getContext().getSystemService(Context.ALARM_SERVICE);
    }

    /**
     * 取消闹钟
     * @param pendingIntent
     */
    public void cancelAlarm(PendingIntent pendingIntent) {
        getAlarmManager().cancel(pendingIntent);
    }

    /**
     * 添加闹钟
     * @param pendingIntent 执行动作
     * @param performTime  执行时间
     */
    public void addAlarm(PendingIntent pendingIntent, Date performTime) {
        cancelAlarm(pendingIntent);
        getAlarmManager().set(AlarmManager.RTC_WAKEUP, performTime.getTime(), pendingIntent);
    }
}