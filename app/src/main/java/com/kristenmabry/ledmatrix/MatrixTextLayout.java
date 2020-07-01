package com.kristenmabry.ledmatrix;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class MatrixTextLayout implements Parcelable {
    private String filename;
    private String name;
    private int sortOrder;
    private boolean isNew;
    private String line1;
    private String line2;
    private int[][] colors1;
    private int[][] colors2;

    protected MatrixTextLayout(Parcel in) {
        filename = in.readString();
        name = in.readString();
        line1 = in.readString();
        line2 = in.readString();
        sortOrder = in.readInt();
        isNew = in.readBoolean();
        colors1 = new int[5][3];
        colors2 = new int[5][3];
        for (int j = 0; j < 5; ++j) {
            in.readIntArray(colors1[j]);
            in.readIntArray(colors2[j]);
        }
    }

    public MatrixTextLayout(String line1, String line2, int[][] colors1, int[][] colors2, boolean isNew) {
        this.filename = null;
        this.line1 = line1;
        this.line2 = line2;
        this.colors1 = colors1;
        this.colors2 = colors2;
        this.isNew = isNew;
    }

    public static final Creator<MatrixTextLayout> CREATOR = new Creator<MatrixTextLayout>() {
        @Override
        public MatrixTextLayout createFromParcel(Parcel in) {
            return new MatrixTextLayout(in);
        }

        @Override
        public MatrixTextLayout[] newArray(int size) {
            return new MatrixTextLayout[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(filename);
        parcel.writeString(name);
        parcel.writeString(line1);
        parcel.writeString(line2);
        parcel.writeInt(sortOrder);
        parcel.writeBoolean(isNew);
        for (int j = 0; j < 5; ++j) {
            parcel.writeIntArray(colors1[j]);
            parcel.writeIntArray(colors2[j]);
        }
    }

    public void setDetails(String name, int sortOrder) {
        this.name = name;
        this.sortOrder = sortOrder;
    }

    public String getFileName() {
        return this.filename;
    }

    public String getName() {
        return this.name;
    }

    public boolean getIsNew() {
        return this.isNew;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public int[][] getColors1() {
        return colors1;
    }

    public int[][] getColors2() {
        return colors2;
    }
}
