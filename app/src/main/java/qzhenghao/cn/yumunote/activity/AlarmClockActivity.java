package qzhenghao.cn.yumunote.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;

import qzhenghao.cn.yumunote.R;
import qzhenghao.cn.yumunote.bean.NoteItemBean;
import qzhenghao.cn.yumunote.dao.DBManager;
import qzhenghao.cn.yumunote.utils.AlertDialogUtil;

public class AlarmClockActivity extends AppCompatActivity {
    private static final String TAG = "qzh12";
    //闹铃
    private MediaPlayer mediaPlayer;
    //震动
    private Vibrator mVibrator;
    private NoteItemBean noteItemBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmclock);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initData();
        clock();
    }


    protected void initData() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.clock);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (getIntent().getStringExtra("noteItemBean") != null) {
            String id= getIntent().getStringExtra("noteItemBean");
            Log.e("qzh116", id+ "df");
            DBManager dbManager = new DBManager(getApplicationContext());
            try {
                noteItemBean = dbManager.queryOneNotes(Integer.valueOf(id));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            finish();
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        clock();
        Log.e("qzh14", 1 + "df");
    }

    private void clock() {
        mediaPlayer.start();
        long[] pattern = new long[]{1500, 1000};
        mVibrator.vibrate(pattern, 0);
        //获取自定义布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.alarmclock_layout, null);
        TextView textView = inflate.findViewById(R.id.tv_event);
        textView.setText(String.format("您有事件：%1$s，请记得去完成！", noteItemBean.getContent()));
        Button btnConfirm = inflate.findViewById(R.id.btn_confirm);
        final AlertDialog alertDialog = AlertDialogUtil.showDialog(this, inflate);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mVibrator.cancel();
                alertDialog.dismiss();
                finish();
            }
        });
        alertDialog.show();
    }


}
