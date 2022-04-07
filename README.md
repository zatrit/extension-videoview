# Extension-Videoview
Native Video Support For Android in Haxe Flixel 

## Credits:
* Saw (M.A. JIGSAW) me - doing the hole code for the extension

## If You want to use it credit me, thank you

## A code Exemple to be able to use it

```haxe
#if android
import extension.videoview.VideoView;
#end
import flixel.FlxBasic;

class VideoPlayer extends FlxBasic {
	public var finishCallback:Void->Void = null;

	public function new(name:String) {
		super();

	        #if android
                VideoView.playVideo('file:///android_asset/assets/video.mp4');// the video can be in any format webm mkv any
                VideoView.onCompletion = function(){
		        if (finishCallback != null){
			        finishCallback();
		        }
                }
		#end
	}
}
```
