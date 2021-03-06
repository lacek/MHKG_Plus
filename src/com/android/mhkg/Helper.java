package com.android.mhkg;

import android.content.Context;  
import android.database.sqlite.SQLiteDatabase;  
import android.database.sqlite.SQLiteOpenHelper;  
public class Helper extends SQLiteOpenHelper implements TableInfo{  
	
     private static final String DATABASE_NAME = "myFav.db" ;        
     private static final int DATABASE_VERSION = 4;     
     
     public Helper(Context context) {                              
          super(context, DATABASE_NAME, null, DATABASE_VERSION);  
     }  
     
     @Override  
     public void onCreate(SQLiteDatabase db) {       
          db.execSQL("CREATE TABLE " + TABLE_NAME + " ("   
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "   
                    + TITLE + " TEXT NOT NULL, "   
                    + URL + " TEXT NOT NULL);" );            
     }  
     
     @Override  
     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
          db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); 
          onCreate(db);          
     }  
}  