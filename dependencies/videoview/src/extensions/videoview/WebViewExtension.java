package extensions.webview;

import android.util.Log;
import android.content.Intent;
import org.haxe.extension.Extension;
import org.haxe.lime.HaxeObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebViewExtension extends Extension {

	public static final String EXTRA_URL = "extensions.webviewex.EXTRA_URL";
	public static final String EXTRA_HTML = "extensions.webviewex.EXTRA_HTML";
	public static final String EXTRA_URL_WHITELIST = "extensions.webviewex.EXTRA_URL_WHITELIST";
	public static final String EXTRA_URL_BLACKLIST = "extensions.webviewex.EXTRA_URL_BLACKLIST";
	public static final String EXTRA_USE_HIDE_UI = "extensions.webviewex.EXTRA_USE_HIDE_UI";
	public static final String EXTRA_USE_WIDE_PORT = "extensions.webviewex.EXTRA_USE_WIDE_PORT";
	public static boolean active = false;

	public static HaxeObject callback;

	public static void open(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			String url = obj.getString("url");

			JSONArray jsonUrlWhitelist = obj.getJSONArray("urlWhitelist");
			String[] urlWhitelist = new String[jsonUrlWhitelist.length()];
			for (int i=0; i<jsonUrlWhitelist.length(); ++i) {
				urlWhitelist[i] = jsonUrlWhitelist.getString(i);
			}

			JSONArray jsonUrlBlacklist = obj.getJSONArray("urlBlacklist");
			String[] urlBlacklist = new String[jsonUrlBlacklist.length()];
			for (int i=0; i<jsonUrlBlacklist.length(); ++i) {
				urlBlacklist[i] = jsonUrlBlacklist.getString(i);
			}

			boolean hideui = obj.getBoolean("hideui");
			boolean useWideViewPort = obj.getBoolean("useWideViewPort");

			Intent intent = new Intent(mainActivity, WebViewActivity.class);
			intent.putExtra(EXTRA_URL, url);
			intent.putExtra(EXTRA_URL_WHITELIST, urlWhitelist);
			intent.putExtra(EXTRA_URL_BLACKLIST, urlBlacklist);
			intent.putExtra(EXTRA_USE_HIDE_UI, hideui);
			intent.putExtra(EXTRA_USE_WIDE_PORT, useWideViewPort);

			mainActivity.startActivity(intent);
			active = true;

		} catch (JSONException e) {
			Log.d("JSONException", e.toString());
		}
	}

	public static void openHtml(String json){
		try {
			JSONObject obj = new JSONObject(json);
			String html = obj.getString("html");

			boolean hideui = obj.getBoolean("hideui");
			boolean useWideViewPort = obj.getBoolean("useWideViewPort");

			Intent intent = new Intent(mainActivity, WebViewActivity.class);
			intent.putExtra(EXTRA_HTML, html);
			intent.putExtra(EXTRA_USE_HIDE_UI, hideui);
			intent.putExtra(EXTRA_USE_WIDE_PORT, useWideViewPort);

			mainActivity.startActivity(intent);
			active = true;

		} catch (JSONException e) {
			Log.d("JSONException", e.toString());
		}
	}

	public static boolean isActive() {
		return active;
	}

	public static void setCallback(final HaxeObject _callback) {
		callback = _callback;
	}
}
