package qzhenghao.cn.yumunote.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class FolderItemBean implements Parcelable {
    public static final String FOLDERNAME = "folderName";
    public static final String ID = "id";

    private String folderName;
    private int id;

    public FolderItemBean() {
    }


    protected FolderItemBean(Parcel in) {
        folderName = in.readString();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(folderName);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FolderItemBean> CREATOR = new Creator<FolderItemBean>() {
        @Override
        public FolderItemBean createFromParcel(Parcel in) {
            return new FolderItemBean(in);
        }

        @Override
        public FolderItemBean[] newArray(int size) {
            return new FolderItemBean[size];
        }
    };

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFolderName() {

        return folderName;
    }



    @Override
    public String toString() {
        return "FolderItemBean{" +
                "folderName='" + folderName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }




}
