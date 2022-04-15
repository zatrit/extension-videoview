package extensions.videoview;

import android.util.Log;
import android.content.Intent;
import org.haxe.extension.Extension;
import org.haxe.lime.HaxeObject;

/**
* author: Saw (M.A. Jigsaw)
 z*/

public class VideoViewExtension extends Extension {

	public static final String EXTRA_VIDEOPATH = "extensions.videoviewex.EXTRA_VIDEOPATH";

	public static HaxeObject callback;

	public static void playVideo(String videoPath) {
		try {
			Intent intent = new Intent(mainActivity, VideoViewActivity.class);
			intent.putExtra(EXTRA_VIDEOPATH, videoPath);
			mainActivity.startActivity(intent);
		} catch (Exception e) {
			Log.d("VideoViewExtension", e.toString());
		}
	}

	public static void pauseVideo() {
		try {
			VideoViewActivity.videoView.pause();
		} catch (Exception e) {
			Log.d("VideoViewExtension", e.toString());
		}
	}

	public static void resumeVideo() {
		try {
			VideoViewActivity.videoView.resume();
		} catch (Exception e) {
			Log.d("VideoViewExtension", e.toString());
		}
	}

	public static void seekVideoTo(int msec) {
		try {
			VideoViewActivity.videoView.seekTo(msec);
		} catch (Exception e) {
			Log.d("VideoViewExtension", e.toString());
		}
	}

	public static void setCallback(final HaxeObject _callback) {
		callback = _callback;
	}
}