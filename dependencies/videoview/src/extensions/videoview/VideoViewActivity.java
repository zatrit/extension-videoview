package extensions.videoview;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;
import org.haxe.lime.HaxeObject;

/**
 * author: Saw (M.A. Jigsaw)
 */

public class VideoViewActivity extends Activity {
	public static VideoView videoView;
	public static HaxeObject callback;
	public static String videoPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			Bundle extras = getIntent().getExtras();
			videoPath = extras.getString(VideoViewExtension.EXTRA_VIDEOPATH);
			callback = VideoViewExtension.callback;

			if(Build.VERSION.SDK_INT >= 19) 
			{
				getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			}
			else if(Build.VERSION.SDK_INT >= 16) 
			{
				getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			}

			setContentView(R.layout.videoview_activity);

			videoView = (VideoView) findViewById(R.id.simpleVideoView);
		} catch (Exception e) {
			Log.d("VideoViewActivity", e.toString());
		}
	}

	@Override
	protected void onStart() {
		try {
			videoView.setVideoURI(uri);

			videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) { 
					callback.call("onCompletion", new Object[] {});
					finish();
				}
			});
			videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					callback.call("onError", new Object[] {});
					finish();
					return false;
				}
			});
			videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) { 
					callback.call("onPrepared", new Object[] {});
				}
			});

			videoView.start();
		} catch (Exception e) {
			Log.d("VideoViewActivity", e.toString());
		}
	}

	@Override
	protected void onDestroy() {
		try {
			videoView.stopPlayback();
		} catch (Exception e) {
			Log.d("VideoViewActivity", e.toString());
		}
	}

	@Override
	protected void onPause() {
		try {
			VideoViewActivity.videoView.pause();
		} catch (Exception e) {
			Log.d("VideoViewActivity", e.toString());
		}
	}

	@Override
	protected void onResume() {
		try {
			VideoViewActivity.videoView.resume();
		} catch (Exception e) {
			Log.d("VideoViewActivity", e.toString());
		}
	}

	@Override
	protected void onStop() {
		try {
			videoView.stopPlayback();
		} catch (Exception e) {
			Log.d("VideoViewActivity", e.toString());
		}
	}

	@Override
	public void onBackPressed() {
		try {
			callback.call("onCompletion", new Object[] {});
			finish();
		} catch (Exception e) {
			Log.d("VideoViewActivity", e.toString());
		}
	}

	@Override
	public void finish(){
		super.finish();
		try {
			videoView.stopPlayback();
		} catch (Exception e) {
			Log.d("VideoViewActivity", e.toString());
		}
	}
}
