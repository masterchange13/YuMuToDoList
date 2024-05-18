package qzhenghao.cn.yumunote.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import qzhenghao.cn.yumunote.activity.AlarmClockActivity;
import qzhenghao.cn.yumunote.utils.WakeLockUtil;

/**
 * @author suiyue
 * @Description TODO
 * @date 2019/10/20 15:19
 */
public class ClockService extends Service {
    private static final String TAG = "qzh1ClockService";

    public ClockService() {
        Log.e(TAG, "ClockService: Constructor");
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        WakeLockUtil.wakeUpAndUnlock();
        postToClockActivity(getApplicationContext(), intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void postToClockActivity(Context context, Intent intent) {
        Intent i = new Intent();
        i.setClass(context, AlarmClockActivity.class);
        String noteItemBean;

        if (intent.getStringExtra("noteItemBean") != null) {
            noteItemBean = intent.getStringExtra("noteItemBean");
        } else {
            return;
        }
        i.putExtra("noteItemBean", noteItemBean);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}