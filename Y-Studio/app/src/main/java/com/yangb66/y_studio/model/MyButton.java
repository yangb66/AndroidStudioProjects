package com.yangb66.y_studio.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 彪 on 2017/12/15.
 */

public class MyButton implements Parcelable{
    public MyButton(Parcel source){

    }

    public MyButton(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public Parcelable.Creator<MyButton> CREATOR= new Parcelable.Creator<MyButton>() {
        @Override
        public MyButton createFromParcel(Parcel source) {
            return new MyButton(source);
        }

        @Override
        public MyButton[] newArray(int size) {
            return new MyButton[size];
        }
    };
}
