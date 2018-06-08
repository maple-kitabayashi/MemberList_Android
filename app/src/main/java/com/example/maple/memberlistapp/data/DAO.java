package com.example.maple.memberlistapp.data;


import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class DAO {
    public static DAO sDAO = new DAO();
    private Realm mRealm;

    public RealmResults<MemberDataJava> readData() {return mRealm.where(MemberDataJava.class).findAll();}

    public void writeData(/*String name, String birthDay, String skill, String hobby*/) {
        mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                MemberDataJava data = mRealm.createObject(MemberDataJava.class, UUID.randomUUID().toString());
                data.setName("kita");
            }
        });
    }
}
