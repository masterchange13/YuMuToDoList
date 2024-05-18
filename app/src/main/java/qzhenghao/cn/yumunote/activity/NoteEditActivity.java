package qzhenghao.cn.yumunote.activity;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import qzhenghao.cn.yumunote.R;
import qzhenghao.cn.yumunote.bean.NoteItemBean;
import qzhenghao.cn.yumunote.dao.DBManager;
import qzhenghao.cn.yumunote.receiver.ClockReceiver;
import qzhenghao.cn.yumunote.utils.RelativeDateFormat;
import qzhenghao.cn.yumunote.utils.StringUtil;
import qzhenghao.cn.yumunote.warm.ClockManager;
import qzhenghao.cn.yumunote.service.ClockService;

public class NoteEditActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected TextView toolbar_center_title;
    protected boolean isShowToolbar = true;
    protected boolean isSetNavigationIcon = true;
    protected boolean isSetLogo = false;
    protected String title = "编辑";
    protected String centertitle = "编辑";
    protected String subtitle;
    private ImageView selectTimeButton;
    private TextView warmTimeText;
    private EditText contentText;
    private String time;
    private String FOLDER_ID;
    private NoteItemBean noteItemBean;
    private DBManager dbManager;
    private String NOTE_ID;
    final int[] IS_WARMFLAG = {0};
    private ClockManager mClockManager = ClockManager.getInstance();
    private boolean IS_UPDATE;
    private boolean IS_NEW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        Intent intent = getIntent();
        FOLDER_ID = intent.getStringExtra("folder_id");
        NOTE_ID = intent.getStringExtra("note_id");
        Log.e("qzh1_ee", FOLDER_ID + "");
        Log.e("qzh1_e2", NOTE_ID + "");
        initView();
        initData();
    }

    private void initData() {
        dbManager = new DBManager(this);
        try {
            loadData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void loadData() throws ParseException {
        //判断是新建还是编辑
        if (StringUtil.isNullOrEmpty(FOLDER_ID) && !StringUtil.isNullOrEmpty(NOTE_ID)) {
            IS_UPDATE = true;
            IS_NEW = false;
            //编辑
            noteItemBean = new NoteItemBean();
            try {
                noteItemBean = dbManager.queryOneNotes(Integer.valueOf(NOTE_ID));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //放入数据
            contentText.setText(noteItemBean.getContent());
            if (noteItemBean.getIs_warn() != 0) {
                warmTimeText.setText(RelativeDateFormat.dateToString(noteItemBean.getWarn_data()));
                selectTimeButton.setImageResource(R.drawable.note_clock_waker);
                //如果提醒时间过了，加删除线
                if (noteItemBean.getWarn_data().getTime() < new Date().getTime()) {
                    warmTimeText.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    selectTimeButton.setImageResource(R.drawable.note_clock_sleep);
                }
            } else {
                selectTimeButton.setImageResource(R.drawable.note_clock_sleep);
            }

        } else if (StringUtil.isNullOrEmpty(NOTE_ID) && !StringUtil.isNullOrEmpty(FOLDER_ID)) {
            //新建
            IS_NEW = true;
            IS_UPDATE = false;
            noteItemBean = new NoteItemBean();
        } else {
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void initView() {
        setActivityToolbar();

        selectTimeButton = findViewById(R.id.warm);
        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPick();
            }
        });


        warmTimeText = findViewById(R.id.warm_time);
        contentText = findViewById(R.id.memo_content);

    }

    private void setActivityToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_note_edit);
        toolbar_center_title = (TextView) findViewById(R.id.toolbar_note_edit_center_title);
        if (toolbar != null) {
            if (isShowToolbar) {
                setSupportActionBar(toolbar);
            } else {
                toolbar.setVisibility(View.GONE);
            }
            if (title != null && !title.equals("")) {
                toolbar.setTitle(title);
            } else {
                toolbar.setTitle("");
            }
            if (subtitle != null && !subtitle.equals("")) {
                toolbar.setSubtitle(subtitle);
            }
            if (isSetNavigationIcon) {
//                由于要兼容低版本，所以采用这个划杠的方法，需要自己根据需求替换图片
                toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu_back));
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (IS_NEW) {
                            Intent intent = new Intent();
                            setResult(1, intent);
                        }
                        if (IS_UPDATE) {
                            Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
                            intent.putExtra("folder_id", String.valueOf(noteItemBean.getFolders_id()));
                            finish();
                            startActivity(intent);
                        }

                        finish();
                    }
                });
            }
            if (isSetLogo) {
                //需要自己根据需求进行替换图片
                toolbar.setLogo(getResources().getDrawable(R.drawable.simle_logo_01));
            }
            if (toolbar_center_title != null) {
                if (centertitle != null && !centertitle.equals("")) {
                    toolbar_center_title.setText(centertitle);
                } else {
                    toolbar_center_title.setText("");
                }

            }
        }
    }

    //提醒
    private PendingIntent buildIntent(NoteItemBean noteItemBean) {
        Intent intent = new Intent();
        intent.putExtra("noteItemBean", String.valueOf(noteItemBean.getId()));
        intent.setClass(this, ClockService.class);
        Random r = new Random();
        return PendingIntent.getService(this, r.nextInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_edit, menu);
        //默认隐藏setting按钮
        if (toolbar != null) {
            MenuItem menuItem = toolbar.getMenu().getItem(0);
            if (menuItem != null) {
                menuItem.setTitle("添加");
                menuItem.setIcon(R.drawable.icon_check);
                menuItem.setVisible(true);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_note_save:
                //添加//更新
                try {
                    noteEdit();
                    if (IS_NEW) {
                        Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void noteEdit() throws ParseException {
        //更新
        if (IS_UPDATE) {
            Editable text = contentText.getText();
            noteItemBean.setContent(text.toString());
            //如果设置了就更新
            if (IS_WARMFLAG[0] == 1) {
                noteItemBean.setIs_warn(IS_WARMFLAG[0]);
                Date date = RelativeDateFormat.stringToDate(time);
                noteItemBean.setWarn_data(date);
            }
            noteItemBean.setUp_data(new Date());

            if (IS_WARMFLAG[0] == 1) {
                //时间有效
                if (noteItemBean.getWarn_data().getTime() - new Date().getTime() > 0) {
                    //添加闹钟
                    mClockManager.addAlarm(buildIntent(noteItemBean), noteItemBean.getWarn_data());
                }
            }
            Log.e("qzh111", noteItemBean + "1");
            dbManager.updateNote(noteItemBean);
        }
        //新建
        if (IS_NEW) {
            NoteItemBean noteItemBean1 = new NoteItemBean();
            noteItemBean1.setFolders_id(Integer.parseInt(FOLDER_ID));
            noteItemBean1.setIs_warn(0);
            if (!StringUtil.isNullOrEmpty(time)&&IS_WARMFLAG[0] == 1) {
                Date date = RelativeDateFormat.stringToDate(time);
                noteItemBean1.setWarn_data(date);
                //时间有效
                if (noteItemBean1.getWarn_data().getTime() - new Date().getTime() > 0) {
                    //添加闹钟
                    mClockManager.addAlarm(buildIntent(noteItemBean1), noteItemBean1.getWarn_data());
                    noteItemBean1.setIs_warn(IS_WARMFLAG[0]);
                }
            }
            noteItemBean1.setUp_data(new Date());
            noteItemBean1.setContent(contentText.getText().toString());
            long l = dbManager.insertNote(noteItemBean1);
        }

    }

    /**
     * 时间选择器
     * 第一次显示日期选择器, 点击确定后显示时间选择器
     */
    private void showDialogPick() {

        //获取Calendar对象，用于获取当前时间
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //实例化TimePickerDialog对象
        final TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            //选择完时间后会调用该回调函数
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //设置TextView显示最终选择的时间
                time += " " + hourOfDay + ":" + minute;
                Toast.makeText(NoteEditActivity.this, "click time is " + time, Toast.LENGTH_SHORT).show();
                warmTimeText.setPaintFlags(warmTimeText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                try {
                    Date date = RelativeDateFormat.stringToDate(time);
                    if (date.getTime() - new Date().getTime() < 0) {
                        warmTimeText.setPaintFlags(warmTimeText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        selectTimeButton.setImageResource(R.drawable.note_clock_sleep);
                    }else {
                        warmTimeText.setPaintFlags(warmTimeText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        selectTimeButton.setImageResource(R.drawable.note_clock_waker);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                warmTimeText.setText(time);
                IS_WARMFLAG[0] = 1;
                //如果提醒时间过了，加删除线
            }
        }, hour, minute, true);
        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                time = null;
                IS_WARMFLAG[0] = 0;
                selectTimeButton.setImageResource(R.drawable.note_clock_sleep);
            }
        });
        //实例化DatePickerDialog对象
        DatePickerDialog datePickerDialog = new DatePickerDialog(NoteEditActivity.this, new DatePickerDialog.OnDateSetListener() {
            //选择完日期后会调用该回调函数
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                time = "";
                //因为monthOfYear会比实际月份少一月所以这边要加1
                time += year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                //选择完日期后弹出选择时间对话框
                timePickerDialog.show();
            }
        }, year, month, day);
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                time = null;
                IS_WARMFLAG[0] = 0;
                selectTimeButton.setImageResource(R.drawable.note_clock_sleep);
            }
        });
        //弹出选择日期对话框
        datePickerDialog.show();
    }

}
