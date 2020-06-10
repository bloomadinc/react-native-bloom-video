package cn.bloomad;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.linkin.videosdk.VideoSdk;

import cn.bloomad.module.InitModule;
import cn.bloomad.module.ModuleManager;

public class BloomVideoModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private ModuleManager moduleManager;

    private InitModule initModule;

    public BloomVideoModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

        moduleManager = ModuleManager.getInstance();

        initModule = InitModule.getInstance();

    }

    @Override
    public String getName() {
        return "BloomVideo";
    }

//    @ReactMethod
//    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
//        // TODO: Implement some actually useful functionality
//        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
//    }

    @ReactMethod
    public void init(String appId, Promise promise) {
        try {
            initModule.init(getCurrentActivity(), appId);
            promise.resolve(appId);
        } catch (IllegalViewOperationException e) {
            promise.reject(e);
        }

    }

    @ReactMethod
    public void setUserId(String userId, Promise promise) {
        try {
            if (userId.length() > 0) {
                VideoSdk.getInstance().setUserId(userId);
            } else {
                VideoSdk.getInstance().setUserId(null);
            }
            promise.resolve(userId);
        } catch (IllegalViewOperationException e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void destroyView(String name) {
        moduleManager.remove(name);
    }
}
