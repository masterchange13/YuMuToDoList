package qzhenghao.cn.yumunote.bean;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NoteItemBean implements Parcelable {
    public static final String ID = "id";
    public static final String FOLDERS_ID = "folders_id";
    public static final String CONTENT = "content";
    public static final String UP_DATA = "up_data";
    public static final String IS_WARN = "is_warn";
    public static final String WARN_DATA = "warn_data";

    private int id;
    private int folders_id;
    private String content;
    private Date up_data;
    private Date warn_data;
    //0不提醒,1提醒
    private int is_warn;
    //提醒时间


    protected NoteItemBean(Parcel in) {
        id = in.readInt();
        folders_id = in.readInt();
        content = in.readString();
        is_warn = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(folders_id);
        dest.writeString(content);
        dest.writeInt(is_warn);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteItemBean> CREATOR = new Creator<NoteItemBean>() {
        @Override
        public NoteItemBean createFromParcel(Parcel in) {
            return new NoteItemBean(in);
        }

        @Override
        public NoteItemBean[] newArray(int size) {
            return new NoteItemBean[size];
        }
    };

    @Override
    public String toString() {
        return "NoteItemBean{" +
                "id=" + id +
                ", folders_id=" + folders_id +
                ", content='" + content + '\'' +
                ", up_data=" + up_data +
                ", is_warn=" + is_warn +
                ", warn_data=" + warn_data +
                '}';
    }

    public int getId() {
        return id;
    }

    public NoteItemBean() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFolders_id() {
        return folders_id;
    }

    public void setFolders_id(int folders_id) {
        this.folders_id = folders_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUp_data() {
        return up_data;
    }

    public void setUp_data(Date up_data) {
        this.up_data = up_data;
    }

    public int getIs_warn() {
        return is_warn;
    }

    public void setIs_warn(int is_warn) {
        this.is_warn = is_warn;
    }

    public Date getWarn_data() {
        return warn_data;
    }

    public void setWarn_data(Date warn_data) {
        this.warn_data = warn_data;
    }



}
