package com.android.mhkg;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.handmark.pulltorefresh.library.PullToRefreshWebView;

public class MainActivity extends Activity implements TableInfo {
	
	WebView myBrowser;
	PullToRefreshWebView mPullRefreshWebView;
	ProgressDialog initProgress;
	private Helper favHelper;  
	private Helper2 hisHelper;  
	
	// Called when the activity is first created
    @Override
    public void onCreate(Bundle savedInstanceState) {   	
    	
        super.onCreate(savedInstanceState);
        
        // Adds Progrss bar Support 
        this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        
        // Makes Progress bar Visible  
        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON); 
        
        // Set Content View
        setContentView(R.layout.activity_main);    
        
        // Call Helper
        favHelper = new Helper(this);
        hisHelper = new Helper2(this);

        // Get Webview 	& Pull-to-Refresh
		mPullRefreshWebView = (PullToRefreshWebView) findViewById(R.id.pull_refresh_webview);  
        myBrowser = mPullRefreshWebView.getRefreshableView();       
        WebSettings websettings = myBrowser.getSettings();
        
        // Maximize the performance
        websettings.setEnableSmoothTransition(true);
        websettings.setRenderPriority(RenderPriority.HIGH);
        
        // Allow Plugins
        websettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
               
        // Disable OverScroll
        mPullRefreshWebView.setPullToRefreshOverScrollEnabled(false);
              
        // the init state of progress dialog
        initProgress = ProgressDialog.show(this, "", getString(R.string.init),true);
        
        // Get Data from Settings activity
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean enable_zoom = sharedPrefs.getBoolean("enable_zoom", true); 
        boolean load_image = sharedPrefs.getBoolean("load_image", true); 
        String cache_size = sharedPrefs.getString("cache_size", "10");
        boolean use_cache = sharedPrefs.getBoolean("use_cache", true); 
        final boolean black_theme = sharedPrefs.getBoolean("black_theme", false); 
        String preset = sharedPrefs.getString("preset", "c1"); 
        final String timeout = sharedPrefs.getString("timeout", "10");
        boolean enable_push = sharedPrefs.getBoolean("enable_push", true); 
        final boolean enable_history = sharedPrefs.getBoolean("enable_history", true); 
        
        // Receive Push Notification
        if (enable_push == true){
        	JPushInterface.setDebugMode(true);
        	JPushInterface.init(this);
        }
        else {
        	JPushInterface.setDebugMode(false);
        }
        
        // Set URL to be loaded
        if (preset.equals("c1")){
        	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=BW");  
        } 
        else if(preset.equals("c2")) {
        	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=ET");  
        } 
        else if(preset.equals("c3")) {
        	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=CA");  
        } 
        else if(preset.equals("c4")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=FN");  
        } 
        else if(preset.equals("c5")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=GM");  
        } 
        else if(preset.equals("c6")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=HW");  
        } 
        else if(preset.equals("c7")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=IN");  
        } 
        else if(preset.equals("c8")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=SW");  
        } 
        else if(preset.equals("c9")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=MP");  
        } 
        else if(preset.equals("c10")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=SP");  
        } 
        else if(preset.equals("c11")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=LV");  
        } 
        else if(preset.equals("c12")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=SY");  
        } 
        else if(preset.equals("c13")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=ED");  
        } 
        else if(preset.equals("c14")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=TR");  
        } 
        else if(preset.equals("c15")) {
        	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=CO");  
        } 
        else if(preset.equals("c16")) {
        	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=AN");  
        } 
        else if(preset.equals("c17")) {
        	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=TO");  
        } 
        else if(preset.equals("c18")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=MU");  
        } 
        else if(preset.equals("c19")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=VI");  
        } 
        else if(preset.equals("c20")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=DC");  
        } 
        else if(preset.equals("c21")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=ST");  
        } 
        else if(preset.equals("c22")) {
        	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=TS");  
        } 
        else if(preset.equals("c23")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=RA");  
        } 
        else if(preset.equals("c24")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=MB");  
        } 
        else if(preset.equals("c25")) {
        	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=AC");  
        } 
        else if(preset.equals("c26")) {
    		myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=EP");  
        }

        // Zoom in Support
        websettings.setSupportZoom(enable_zoom);  
        websettings.setBuiltInZoomControls(enable_zoom);
        websettings.setUseWideViewPort(enable_zoom);
         
        // JavaScript Support
        websettings.setJavaScriptEnabled(true);
           
        // Load Images
        if (load_image == true) {
            websettings.setBlockNetworkImage(false); 
        } 
        else {
            websettings.setBlockNetworkImage(true); 
        }
        
        // For a normal page load, the cache is checked and content is re-validated as needed.
        // When navigating back, content is not revalidated, instead the content is just pulled from the cache.
        if (use_cache == true) {
            websettings.setCacheMode(WebSettings.LOAD_NORMAL); 
        } 
        else {
            websettings.setCacheMode(WebSettings.LOAD_NO_CACHE);                
        }      
        websettings.setAppCacheEnabled(use_cache);

        // Set Cache Size
        websettings.setAppCacheMaxSize(Integer.parseInt(cache_size) * 1024 * 1024);
              
        final Activity MyActivity = this;
        myBrowser.setWebChromeClient(new WebChromeClient() {
        	   public void onProgressChanged(WebView view, int newProgress) {
        		   // Activities and WebViews measure progress with different scales.
        		   MyActivity.setProgress(newProgress * 100);
        	                	        
        		   // The progress meter will automatically disappear when we reach 100%        		   
        		   if (newProgress == 100) { 
        			   mPullRefreshWebView.onRefreshComplete();
       			   }
        	   }
        });
        
        myBrowser.setWebViewClient(new WebViewClient() {
                   
        	 // Set CSS files
        	 @Override
        	 public WebResourceResponse shouldInterceptRequest(final WebView view, String url) {
                 if (url.contains(".css")) {
                     return getCssWebResourceResponseFromAsset();
                 } else {
                     return super.shouldInterceptRequest(view, url);
                 }
             }

             private WebResourceResponse getCssWebResourceResponseFromAsset() {
            	 if (black_theme == true ){
        		 	try {
        		 		return getUtf8EncodedCssWebResourceResponse(getAssets().open("black.css"));        		 		
        		 	} catch (IOException e) {
        		 		return null;
        		 	}
            	 } 
            	 else {
        		 	try {
        		 		return getUtf8EncodedCssWebResourceResponse(getAssets().open("white.css"));
        		 	} catch (IOException e) {
        		 		return null;
        		 	}
        		 }
             }

             private WebResourceResponse getUtf8EncodedCssWebResourceResponse(InputStream data) {
                 return new WebResourceResponse("text/css", "UTF-8", data);
             }
                            
             public boolean shouldOverrideUrlLoading(WebView view, String url) {
                 if (Uri.parse(url).getHost().equals("m.hkgolden.com")) { 
                	 if (enable_history == true) {
                         // Add to My History automatically
                    	 AddHis addHis = new AddHis();
                       	 addHis.execute(url);
                	 } else {
                	     // Do nothing
                	 }
                   	 // This is my web site, so do not override; let my WebView load the page
                     return false;
                 } 
                 else {
                	 // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
                	 Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                	 startActivity(intent);
                	 return true;
                 }
             }   	
  
             public void onPageStarted(WebView view, String url, Bitmap favicon) {
            	 isNetworkAvailable(h,  Integer.parseInt(timeout) * 1000); 
                 super.onPageStarted(view, url, favicon);
             }             
     	});            
    }
    
    // Add to My Favourite
    private class AddFav extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
    		try {
    			Document doc = Jsoup.connect(params[0]).get();
    	        //Elements  
    			String title = doc.body().getElementsByClass("ViewTitle").text();  
    			return title;
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			return null;  
    		}
			
		}
    	
		protected void onPostExecute(String title) {
			SQLiteDatabase db = favHelper.getWritableDatabase(); 
			if (myBrowser.getUrl().contains("view.aspx?message")) {
				Cursor cursor = db.query(TABLE_NAME, new String[] { ID,
                        URL, TITLE }, TITLE + " = '" + title + "'" , null, null, null, null);  
				if(cursor.getCount() == 0) {				         
					ContentValues values = new ContentValues(); 
					values.put(TITLE, title); 
					values.put(URL, myBrowser.getUrl());   
					db.insertOrThrow(TABLE_NAME, null, values);
					Toast.makeText(getApplicationContext(), R.string.added, Toast.LENGTH_SHORT).show();					
				} else {
					Toast.makeText(getApplicationContext(), R.string.duplicate, Toast.LENGTH_SHORT).show();
				}
				cursor.close();
			} else {
				Toast.makeText(getApplicationContext(), R.string.thread, Toast.LENGTH_SHORT).show();
			}
			favHelper.close();
	    }
    }
    
    // Add to My History
    private class AddHis extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
    		try {
    			Document doc = Jsoup.connect(params[0]).get();
    	        //Elements  
    			String title = doc.body().getElementsByClass("ViewTitle").text();  
    			return title;
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			return null;  
    		}
			
		}
    	
		protected void onPostExecute(String title) {
			SQLiteDatabase db = hisHelper.getWritableDatabase(); 
			Cursor cursor = db.query(TABLE2_NAME, new String[] { ID,
                    URL, TITLE }, TITLE + " = '" + title + "'" , null, null, null, null);   
			if (myBrowser.getUrl().contains("view.aspx?message")) {
				if(cursor.getCount() == 0) {					
			        ContentValues values = new ContentValues(); 
			        values.put(TITLE, title); 
			        values.put(URL, myBrowser.getUrl());   
			        db.insertOrThrow(TABLE2_NAME, null, values);
				} else {
					// Do nothing
				}			
			} else {
				// Do nothing
			}
			hisHelper.close();			
	    }
    }
    
    protected void onDestroy() {
        hisHelper.close();  
        favHelper.close();  
	    super.onDestroy();   
    }  
    
    // Clear Cache
    public static boolean deleteDir(File dir) { 
    	if (dir != null && dir.isDirectory()) { 
    		String[] children = dir.list(); 
    		for (int i = 0; i < children.length; i++) { 
    			boolean success = deleteDir(new File(dir, children[i])); 
    			if (!success) { 
    				return false; 
    			} 
    		} 
    	} 
    	// The directory is now empty so delete it 
    	return dir.delete(); 
    } 
    
    public static void trimCache(Context context) { 
    	try { 

    	File dir = context.getCacheDir(); 
    		if (dir != null && dir.isDirectory()) { 
    			deleteDir(dir); 
    		} 
    	} catch (Exception e) { 
    	// TODO: handle exception 
    	} 
    } 

    // Check Mobile Network & MHKG Connections
    public static void isNetworkAvailable(final Handler handler, final int timeout) {
        // ask fo message '0' (not connected) or '1' (connected) on 'handler'
        // the answer must be send before before within the 'timeout' (in milliseconds)
        new Thread() {
            private boolean responded = false;
            @Override
            public void run() {
                // set 'responded' to TRUE if is able to connect with MHKG (responds fast)
                new Thread() {
                    @Override
                    public void run() {
                        HttpGet requestForTest = new HttpGet("http://m.hkgolden.com");
                        try {
                            new DefaultHttpClient().execute(requestForTest); // can last...
                            responded = true;
                        } catch (Exception e) {}
                    }
                }.start();
                try {
                    int waited = 0;
                    while(!responded && (waited < timeout)) {
                        sleep(100);
                        if(!responded ) { 
                            waited += 100;
                        }
                    }
                } 
                catch(InterruptedException e) {} // do nothing 
                finally { 
                    if (!responded) { handler.sendEmptyMessage(0); } 
                    else { handler.sendEmptyMessage(1); }
                }
            }
        }.start();
    }

    Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what != 1) { 
            	// code if not connected
            	Toast.makeText(getApplicationContext(), R.string.offline, Toast.LENGTH_SHORT).show();
            	myBrowser.stopLoading();
            } else { 
            	// code if connected 
            	Toast.makeText(getApplicationContext(), R.string.loaded, Toast.LENGTH_SHORT).show();
            }
            if (initProgress.isShowing()) {
                initProgress.dismiss();                             	 		 
            }
        }
    };
    
    // Exit MHKG+
    @SuppressWarnings("deprecation")
	private boolean exithkg(){
    	final CheckBox checkBox = new CheckBox(this);
    	checkBox.setText(R.string.clear_cache);
    	LinearLayout linearLayout = new LinearLayout(this);
    	linearLayout.setLayoutParams( new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
    	                        LinearLayout.LayoutParams.FILL_PARENT));
    	linearLayout.setOrientation(1);     
    	linearLayout.addView(checkBox);

    	new AlertDialog .Builder(this) 
		.setTitle(R.string.exit) 
		.setView(linearLayout)
		.setMessage(R.string.exithkg) 
    	.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {  
    		public void onClick(DialogInterface arg0, int arg1) {     
    			if (checkBox.isChecked()) {       			
        			trimCache(MainActivity.this);
    	        }
    			finish();
    			Toast.makeText(getApplicationContext(), R.string.exited, Toast.LENGTH_SHORT).show();
    		} 
    	})
    	.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {  
    		public void onClick(DialogInterface arg0, int arg1){
    			// Do nothing
    		}
    	})
    	.show();    	
        return true;	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    //  View My Favourite List
    public void viewFav(){   	
    	Intent intent = new Intent(MainActivity.this, MyFavourite.class);
    	MainActivity.this.startActivity(intent);
    }
        
    //  View My History List
    public void viewHis(){
        Intent intent = new Intent(MainActivity.this, MyHistory.class);
        MainActivity.this.startActivity(intent);
    }
    
    // View Copied URL
    public void viewUrl(){
    	final EditText input = new EditText(this);
    	new AlertDialog.Builder(this)
    	.setTitle("")
    	.setMessage(R.string.input)
    	.setView(input)
    	.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int whichButton) {
    			 Editable value = input.getText(); 
    			 myBrowser.loadUrl(value.toString());
    		}
    	}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int whichButton) {
    			// Do nothing
    		}
    	})
    	.show();
    }
    
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.reload:
            	// Refresh MHKG+ Content                
                myBrowser.reload();
                return true;
            case R.id.copyurl:
            	// Copy Current Page's URL      
    			android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE); 
    		    android.content.ClipData clip = android.content.ClipData.newPlainText("Current Page's URL", myBrowser.getUrl());
    		    clipboard.setPrimaryClip(clip);
    		    Toast.makeText(getApplicationContext(), R.string.copied, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.addfav:
            	// Add to my Favourite 
            	AddFav addfav = new AddFav();
            	addfav.execute(myBrowser.getUrl());
                return true;
            case R.id.fav:
            	// Browse My Favourite
            	viewFav();
            	return true;
            case R.id.his:
            	// Browse My History
            	viewHis();
            	return true;
            case R.id.view:
            	// Browse My History
            	viewUrl();
            	return true; 	
            case R.id.settings: 
            	// Go to Settings           	
            	Intent intent1 = new Intent(MainActivity.this, SettingsActivity.class);
            	MainActivity.this.startActivity(intent1);
    			return true;
            case R.id.exit:
            	// Exit MHKG+
            	exithkg();           	
            	return true;
            case R.id.c1:
            	// Go to different categories
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=BW");  
            	return true;
            case R.id.c2:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=ET");  
            	return true;
            case R.id.c3:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=CA");  
            	return true;
            case R.id.c4:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=FN");  
            	return true;
            case R.id.c5:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=GM");  
            	return true;
            case R.id.c6:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=HW");  
            	return true;
            case R.id.c7:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=IN");  
            	return true;
            case R.id.c8:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=SW");  
            	return true;
            case R.id.c9:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=MP");  
            	return true;
            case R.id.c10:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=SP");  
            	return true;
            case R.id.c11:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=LV");  
            	return true;
            case R.id.c12:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=SY");  
            	return true;
            case R.id.c13:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=ED");  
            	return true;
            case R.id.c14:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=TR");  
            	return true;
            case R.id.c15:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=CO");  
            	return true;
            case R.id.c16:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=AN");  
            	return true;
            case R.id.c17:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=TO");  
            	return true;
            case R.id.c18:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=MU");  
            	return true;
            case R.id.c19:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=VI");  
            	return true;
            case R.id.c20:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=DC");  
            	return true;
            case R.id.c21:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=ST");  
            	return true;
            case R.id.c22:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=TS");  
            	return true;
            case R.id.c23:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=RA");  
            	return true;
            case R.id.c24:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=MB");  
            	return true;
            case R.id.c25:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=AC");  
            	return true;
            case R.id.c26:
            	myBrowser.loadUrl("http://m.hkgolden.com/topics.aspx?type=EP");  
            	return true;    
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myBrowser.canGoBack()) {
        	myBrowser.goBack();
            return true;
        }
        
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)        
        else if(keyCode == KeyEvent.KEYCODE_BACK)  {
        	// Exit MHKG+        
        	exithkg();
        }
		return false;
	}
}

