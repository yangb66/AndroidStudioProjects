package com.yangb66.birthday;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 彪 on 2017/12/7.
 */

public class Member implements Parcelable{
    public String name, birthday, gift;

    public Member(String name0, String birthday0, String gift0){
        name = name0;
        birthday = birthday0;
        gift = gift0;
    }

    public Member(Parcel p){
        name = p.readString();
        birthday = p.readString();
        gift = p.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(birthday);
        dest.writeString(gift);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Parcelable.Creator<Member> CREATOR = new Parcelable.Creator<Member>(){
        @Override
        public Member createFromParcel(Parcel source) {
            return new Member(source);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };
}
