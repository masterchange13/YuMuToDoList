package qzhenghao.cn.yumunote.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import qzhenghao.cn.yumunote.activity.AlarmClockActivity;
import qzhenghao.cn.yumunote.bean.NoteItemBean;
import qzhenghao.cn.yumunote.utils.WakeLockUtil;

/**
 *
 */
public class ClockReceiver extends BroadcastReceiver {
    private static final String TAG = "ClockReceiver";
    public static final String EXTRA_EVENT = "noteItemBean";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent.getAction());
        WakeLockUtil.wakeUpAndUnlock();
        postToClockActivity(context, intent);
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

    public ClockReceiver() {
        super();
        Log.d(TAG, "ClockReceiver: Constructor");
    }
}
