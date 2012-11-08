package com.android.mhkg;

import android.content.Context;  
import android.database.sqlite.SQLiteDatabase;  
import android.database.sqlite.SQLiteOpenHelper;  
public class Helper2 extends SQLiteOpenHelper implements TableInfo{  
	
     private static final String DATABASE_NAME = "myHis.db" ;        
     private static final int DATABASE_VERSION = 1;     
     
     public Helper2(Context context) {                              
          super(context, DATABASE_NAME, null, DATABASE_VERSION);  
     }  
     
     @Override  
     public void onCreate(SQLiteDatabase db) {       
          db.execSQL("CREATE TABLE " + TABLE2_NAME + " ("   
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "   
                    + TITLE + " TEXT NOT NULL, "   
                    + URL + " TEXT NOT NULL);" );            
     }  
     
     @Override  
     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
          db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME); 
          onCreate(db);          
     }  
}  