package extension.videoview;

#if android
import lime.system.JNI;
#end
	
class VideoView  
{
	public static var onError:Void -> Void = null;
	public static var onCompletion:Void -> Void = null;
	public static var onPrepared:Void -> Void = null;

	public static function playVideo(path:String = null):Void 
        {
                #if android
		_callbackFunc(new CallBack());
		_playVideo(path);
                #end
        }

	public static function pauseVideo():Void 
        {
                #if android
		_pauseVideo();
                #end
        }

	public static function resumeVideo():Void 
        {
                #if android
		_resumeVideo();
                #end
        }

	public static function seekVideoTo(msec:Int = 0):Void 
        {
                #if android
		_seekVideoTo(msec);
                #end
        }
    
	#if android
	private static var _playVideo = JNI.createStaticMethod("extensions/videoview/VideoViewExtension", "playVideo", "(Ljava/lang/String;)V");
	private static var _pauseVideo = JNI.createStaticMethod("extensions/videoview/VideoViewExtension", "pauseVideo", "()V");
	private static var _resumeVideo = JNI.createStaticMethod("extensions/videoview/VideoViewExtension", "resumeVideo", "()V");
	private static var _seekVideoTo = JNI.createStaticMethod("extensions/videoview/VideoViewExtension", "seekVideoTo", "(I)V");
	private static var _callbackFunc = JNI.createStaticMethod("extensions/videoview/VideoViewExtension", "setCallback", "(Lorg/haxe/lime/HaxeObject;)V");
	#end
}

class CallBack {
	public function new() {

	}

	public function onError() {
		if (VideoView.onError != null) {
			VideoView.onError();
		}
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
seeI
