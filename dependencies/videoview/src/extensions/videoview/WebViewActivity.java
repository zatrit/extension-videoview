package extensions.webview;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import org.haxe.lime.HaxeObject;
import java.util.regex.PatternSyntaxException;

public class WebViewActivity extends Activity {
	
	private static final String TAG = "WebViewActivity";	
	protected FrameLayout webViewPlaceholder;
	protected WebView webView;	
	protected String url;
	protected String html;
	protected String[] urlWhitelist;
	protected String[] urlBlacklist;
	protected boolean hideui;
	protected boolean useWideViewPort;
	protected HaxeObject callback;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Load parameters from intent
		Bundle extras = getIntent().getExtras();
		url = extras.getString(WebViewExtension.EXTRA_URL, "about:blank");
		html = extras.getString(WebViewExtension.EXTRA_HTML, "null");
		urlWhitelist = extras.getStringArray(WebViewExtension.EXTRA_URL_WHITELIST);
		urlBlacklist = extras.getStringArray(WebViewExtension.EXTRA_URL_BLACKLIST);
		hideui = extras.getBoolean(WebViewExtension.EXTRA_USE_HIDE_UI);
		useWideViewPort = extras.getBoolean(WebViewExtension.EXTRA_USE_WIDE_PORT);
		callback = WebViewExtension.callback;

                if (hideui)
                {
	                if(android.os.Build.VERSION.SDK_INT >= 19 ) 
                        {
	                        getWindow().getDecorView().setSystemUiVisibility(
				        View.SYSTEM_UI_FLAG_FULLSCREEN |
				        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
				        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
				        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
			                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
				        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
	                        );
	                 }
                         else if(android.os.Build.VERSION.SDK_INT >= 16 ) 
                         {
            		        getWindow().getDecorView().setSystemUiVisibility(
				        View.SYSTEM_UI_FLAG_FULLSCREEN |
				        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
				        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
				        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
				        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
			        );
	                  }
                }
                else
                {
		        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                }
		
		// Initialize the UI
		initUI();
	}

	protected void initUI()
	{
		// Load layout from resources
		setContentView(R.layout.webview_activity);
		
		// Retrieve UI elements
		webViewPlaceholder = ((FrameLayout)findViewById(R.id.webViewPlaceholder));

		// Initialize the WebView if necessary
		if (webView == null)
		{
			// Create the webview
			webView = new WebView(this);
			WebSettings webSettings = webView.getSettings();
			webSettings.setMediaPlaybackRequiresUserGesture(false);
			webSettings.setJavaScriptEnabled(true);
			webSettings.setDomStorageEnabled(true);
			webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
			webView.setScrollbarFadingEnabled(true);
			webSettings.setLoadsImagesAutomatically(true);
			webSettings.setUseWideViewPort(useWideViewPort);
			
			// Add the callback to handle new page loads
			webView.setWebViewClient(new WebViewClient() {					
				@Override
				public boolean shouldOverrideUrlLoading (WebView view, String url) {
						
				Log.d(TAG, "shouldOverrideUrlLoading(): url = " + url);

				callback.call("onURLChanging", new Object[] {url});
						
				if (WebViewActivity.this.urlWhitelist == null) {							
					Log.d(TAG, "urlWhitelist is null");							
				} else if (WebViewActivity.this.urlWhitelist.length == 0) {
					Log.d(TAG, "urlWhitelist is empty");
				} else {							
					boolean whitelisted = false;
							
					for (String whitelistedUrl : WebViewActivity.this.urlWhitelist) {							
						try {	
						        if (url.matches(whitelistedUrl)) {					
							        Log.d(TAG, "URL matches with whitelist entry: '" + whitelistedUrl + "'.");
							        whitelisted = true;				
						        }											
					        } catch (PatternSyntaxException ex) {				
						        Log.e(TAG, "Regular expression '" + whitelistedUrl + "' is not valid.");								
					        }			
					}			
					if (!whitelisted) {	
						Log.d(TAG, "URL is not whitelisted. Closing view...");
						// call onClose( with args ) ???
						finish();
						return true;
					}			
				}
						
				if (WebViewActivity.this.urlBlacklist == null) {							
					Log.d(TAG, "urlBlacklist is null");							
				} else for (String blacklistedUrl : WebViewActivity.this.urlBlacklist) {							
					try {	
						if (url.matches(blacklistedUrl)) {									
							Log.d(TAG, "URL matches with blacklist entry: '" + blacklistedUrl + "'. Closing view...");
							// call onClose( with args ) ???
							finish();
							return true;
									
						}		
					} catch (PatternSyntaxException ex) {	
						Log.e(TAG, "Regular expression '" + blacklistedUrl + "' is not valid.");		
					}							
				}						
				return false;
				}				
			        }				
			);

			// Load the page
			callback.call("onURLChanging", new Object[] {url});
			if(url=="about:blank" && html!="null")
                            webView.loadData(html, "text/html", null);
			else 
                            webView.loadUrl(url);
		}

		// Attach the WebView to its placeholder
		webViewPlaceholder.addView(webView);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		Log.d(TAG, "onConfigurationChanged (newConfig = " + newConfig.toString() + ")");
		
		if (webView != null)
		{
			// Remove the WebView from the old placeholder
			webViewPlaceholder.removeView(webView);
		}

		super.onConfigurationChanged(newConfig);

		initUI();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		webView.saveState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		webView.restoreState(savedInstanceState);
	}

	@Override
	public void onBackPressed() {
                if (webView.canGoBack()) {
                         webView.goBack();
                } else {
		         callback.call("onClose", new Object[] {});
		         finish();
                         //Will do the same thing like on close
                }
	}

	public void onClosePressed(View view) {
		callback.call("onClose", new Object[] {});
		finish();
	}

	@Override
	public void finish(){
		super.finish();
		WebViewExtension.active=false;
		webView.clearHistory();
		webView.loadUrl("about:blank");
		webViewPlaceholder.removeView(webView);
	}
}
