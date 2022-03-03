package extension.webview;

#if android
import lime.system.JNI;
#end
	
class VideoView  
{
	public static var onError:Void -> Void = null;
	public static var onCompletion:Void -> Void = null;

	public static function playVideo(path:String = null):Void 
        {
                #if android
		_callbackFunc(new CallBack());
		_playVideo(path);
                #end
        }
    
	#if android
	private static var _playVideo = JNI.createStaticMethod("extensions/videoview/VideoViewExtension", "playVideo", "(Ljava/lang/String;)V");
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
}
