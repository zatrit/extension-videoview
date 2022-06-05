package extension.videoview;

#if android
import lime.system.JNI;
#end

/**
 * author: Saw (M.A. Jigsaw)
 */
	
class VideoView
{
	public static var onCompletion:Void -> Void = null;
	public static var onPrepared:Void -> Void = null;

	public static function playVideo(path:String = null):Void 
	{
		#if android
		var _callbackFunc = JNI.createStaticMethod("extensions/videoview/VideoViewExtension", "setCallback", "(Lorg/haxe/lime/HaxeObject;)V");
		var _playVideo = JNI.createStaticMethod("extensions/videoview/VideoViewExtension", "playVideo", "(Ljava/lang/String;)V");

		_callbackFunc(new CallBack());
		_playVideo(path);
		#end
	}
}

class CallBack {
	public function new() {

	}

	public function onCompletion() {
		if (VideoView.onCompletion != null) {
			VideoView.onCompletion();
		}
	}

	public function onPrepared() {
		if (VideoView.onPrepared != null) {
			VideoView.onPrepared();
		}
	}
}