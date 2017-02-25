package com.hxjx.appliationplugin.lib.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PieDataEntity implements Parcelable{

    private String name;
    private float value;
    private float percent;
    private int color = 0;
    private float angle = 0;
    public PieDataEntity() {
        super();
    }
    public PieDataEntity(String name, float value, int color) {
        super();
        this.name = name;
        this.value = value;
        this.color = color;
    }

    @Override
    public String toString() {
        return "PieDataEntity{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getpercent() {
        return percent;
    }

    public void setpercent(float percent) {
        this.percent = percent;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(value);
        dest.writeFloat(percent);
        dest.writeInt(color);
        dest.writeFloat(angle);

    }
    public static final Creator<PieDataEntity> CREATOR = new Creator<PieDataEntity>() {
        @Override
        public PieDataEntity createFromParcel(Parcel source) {
            PieDataEntity task = new PieDataEntity();
            task.name=source.readString();
            task.value=source.readFloat();
            task.percent=source.readFloat();
            task.color=source.readInt();
            task.angle=source.readFloat();
            return task;
        }

        @Override
        public PieDataEntity[] newArray(int size) {
            return new PieDataEntity[size];
        }
    };
}
