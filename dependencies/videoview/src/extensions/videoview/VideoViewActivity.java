package extensions.videoview;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;
import android.widget.FrameLayout;
import org.haxe.lime.HaxeObject;
import android.view.View;
import java.io.File;

public class VideoViewActivity extends Activity {
    public static VideoView videoView;
    public static HaxeObject callback;
    public static String videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
	Bundle extras = getIntent().getExtras();
	videoPath = extras.getString(VideoViewExtension.EXTRA_VIDEOPATH);
	callback = VideoViewExtension.callback;

	if(android.os.Build.VERSION.SDK_INT >= 19) 
	{
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
	}
	else if(android.os.Build.VERSION.SDK_INT >= 16) 
	{
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
	}
	                  
        initUI();
    }
    
    protected void initUI()
    {
	setContentView(R.layout.videoview_activity);

        Uri uri = Uri.parse(videoPath);
        videoView = (VideoView) findViewById(R.id.simpleVideoView);
        videoView.setVideoURI(uri);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //a callback and something to remove 
                callback.call("onCompletion", new Object[] {});
                finish();
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                //a callback and something to remove it
                callback.call("onError", new Object[] {});
                finish();
                return false;//idk why is need this
            }
        });        
        videoView.start();
    }
	
    @Override
    public void onBackPressed() {
	callback.call("onCompletion", new Object[] {});
	finish();
    }
	
    @Override
    public void finish(){
	super.finish();
        videoView.stopPlayback();
	VideoViewExtension.active = false;
    }
}
