package com.cn.travel.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	public static final String CREATE_USER="create table User("
			+"id integer primary key autoincrement,"
			+"username text,"
			+"phonenumber,"
			+"password text)";
	private Context mContext;
	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		mContext=context;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_USER);
		Toast.makeText(mContext, "successed成功", Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
//    static String name="Travel.db";
//    static int dbVersion=1;
//    public DatabaseHelper(Context context) {
//        super(context, name, null, dbVersion);
//    }
//    //只在创建的时候用一次
//    public void onCreate(SQLiteDatabase db) {
//        String sql="create table user(id integer primary key autoincrement,username varchar(20),password varchar(20),age integer,sex varchar(2))";
//        db.execSQL(sql);
//    }
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }

}