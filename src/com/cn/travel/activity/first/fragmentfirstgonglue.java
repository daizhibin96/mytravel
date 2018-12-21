package com.cn.travel.activity.first;


import com.cn.travle.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class fragmentfirstgonglue extends Activity {
	private WebView webView;
	private ProgressBar progressBar;
	WebSettings mWebSettings;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
       
       getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.activity_first_gonglue);
		progressBar=(ProgressBar) findViewById(R.id.progressbar);
		webView=(WebView) findViewById(R.id.webview);
		mWebSettings=webView.getSettings();
		webView.loadUrl("http://you.ctrip.com/article/detail/1080877.html");
		webView.setWebViewClient(new WebViewClient(){
			
			 public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                view.loadUrl(url);
	                return true;
	            }
			
		});
		
	}
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
	        	webView.goBack();
	            return true;
	        }

	        return super.onKeyDown(keyCode, event);
	    }
	 protected void onDestroy() {
	        if (webView != null) {
	        	webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
	        	webView.clearHistory();

	            ((ViewGroup) webView.getParent()).removeView(webView);
	            webView.destroy();
	            webView = null;
	        }
	        super.onDestroy();
	    }

}
