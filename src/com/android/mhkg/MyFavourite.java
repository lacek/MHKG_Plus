package com.android.mhkg;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MyFavourite extends Activity implements TableInfo {  
	
     private Helper favHelper;  
     private ListView list;
     private static String[] FROM = { ID , TITLE , URL };  
     private static String ORDER_BY = ID + " DESC" ;  
     
     @Override  
     public void onCreate(Bundle savedInstanceState) {  
    	 super.onCreate(savedInstanceState);  
         setContentView(R.layout.my_favourite);  
         list = (ListView)findViewById(R.id.list);
         favHelper = new Helper(this);  
         showList();   
         
         // Get Action Bar
         ActionBar actionBar = getActionBar();  
 		 actionBar.setHomeButtonEnabled(true);
 		 actionBar.setDisplayHomeAsUpEnabled(true);
     }  
     

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.reset, menu);
         return true;
     }
     
     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         // Handle item selection
         switch (item.getItemId()) {
 			case android.R.id.home:
 				// App icon in action bar clicked; go home
 				NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
 				return true;
            case R.id.reset:
            	 SQLiteDatabase db = favHelper.getWritableDatabase();
            	 db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); 
                 db.execSQL("CREATE TABLE " + TABLE_NAME + " ("   
                         + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "   
                         + TITLE + " TEXT NOT NULL, "   
                         + URL + " TEXT NOT NULL);" );  
                 this.onCreate(null);
            	 return true;
            default:
                 return super.onOptionsItemSelected(item);
         }
     }
     
     protected void onDestroy() {  
    	 if (favHelper != null) {  
    	     favHelper.close();  
    	 }  
    	 super.onDestroy();
     } 
     
	 private void showList() {   
	     SQLiteDatabase db = favHelper.getReadableDatabase(); 
         Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);  
    	 // Creates a new SimpleCursorAdapter
    	 final SimpleCursorAdapter mCursorAdapter = new SimpleCursorAdapter(
    	     getApplicationContext(),               // The application's Context object
    	     R.layout.list_row,                  // A layout in XML for one row in the ListView
    	     cursor,                               // The result from the query
    	     new String[]{
    	    	 TITLE
    	     },                       			// A string array of column names in the cursor
    	     new int[]{
    	    	 R.id.list_title
    	     },                        // An integer array of view IDs in the row layout
    	     0);                                    // Flags (usually none are needed)

    	 // Sets the adapter for the ListView
    	 list.setAdapter(mCursorAdapter);
    	 list.setOnItemClickListener(new OnItemClickListener() {
    		 public void onItemClick(AdapterView<?> a, View v, int position, long id) {
    			// Get the cursor, positioned to the corresponding row in the result set
    			Cursor cursor = (Cursor) list.getItemAtPosition(position);
    			String favURL = cursor.getString(cursor.getColumnIndexOrThrow(URL));
    			android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE); 
    		    android.content.ClipData clip = android.content.ClipData.newPlainText("My Favourite URL", favURL);
    		    clipboard.setPrimaryClip(clip);
    			Toast.makeText(getApplicationContext(), R.string.copied, Toast.LENGTH_SHORT).show();
    		 }
    	 });
     }  
}  