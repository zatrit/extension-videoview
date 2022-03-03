package extensions.videoview;

import android.util.Log;
import android.content.Intent;
import org.haxe.extension.Extension;
import org.haxe.lime.HaxeObject;

public class VideoViewExtension extends Extension {

	public static final String EXTRA_VIDEOPATH = "extensions.webviewex.EXTRA_URL";
	public static boolean active = false;

	public static HaxeObject callback;

	public static void open(String videoPath) {
		try {
			Intent intent = new Intent(mainActivity, WebViewActivity.class);
			intent.putExtra(EXTRA_VIDEOPATH, videoPath);
			mainActivity.startActivity(intent);
			active = true;

		} catch (JSONException e) {
			Log.d("VideoViewExtension", e.toString());
		}
	}

	public static boolean isActive() {
		return active;
	}

	public static void setCallback(final HaxeObject _callback) {
		callback = _callback;
	}
}
