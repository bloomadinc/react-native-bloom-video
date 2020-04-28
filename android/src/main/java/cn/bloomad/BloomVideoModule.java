package cn.bloomad;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.linkin.adsdk.AdConfig;
import com.linkin.adsdk.AdSdk;
import com.linkin.videosdk.VideoConfig;
import com.linkin.videosdk.VideoSdk;

import cn.bloomad.module.ModuleManager;

public class BloomVideoModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private ModuleManager moduleManager;

    public BloomVideoModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

        moduleManager = ModuleManager.getInstance();

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
            // AdSdk 在 VideoSdk 之前初始化，视频流中才能展现广告
            AdSdk.getInstance().init(reactContext,
                    new AdConfig.Builder()
                            .appId(appId)
                            // .userId("uid") // 未登录可不设置 userId，登录时再设置
                            .multiProcess(false)
                            .debug(BuildConfig.DEBUG)
                            .build(),
                    null);

            VideoSdk.getInstance().init(reactContext,
                    new VideoConfig.Builder()
                            .appId(appId)
                            // .userId("uid") // 未登录可不设置 userId，登录时再设置
                            .debug(BuildConfig.DEBUG)
                            .build(),
                    null);

            promise.resolve(appId);
        } catch (IllegalViewOperationException e) {
            promise.reject(e);
        }

    }

    @ReactMethod
    public void setUserId(String userId, Promise promise) {
        try {
            if (userId.length() > 0) {
                AdSdk.getInstance().setUserId(userId);
                VideoSdk.getInstance().setUserId(userId);
            } else {
                AdSdk.getInstance().setUserId(null);
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
