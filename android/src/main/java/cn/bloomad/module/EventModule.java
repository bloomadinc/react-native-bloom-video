package cn.bloomad.module;

import android.app.Activity;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Map;

import javax.annotation.Nullable;

public class EventModule {
    public String TAG = "bloomAdSdk";

    public String eventName;
    public ReactApplicationContext mReactContext;
    public Activity mActivity;

    public EventModule(final ReactApplicationContext reactContext, final Activity activity, final String name) {
        mReactContext = reactContext;
        mActivity = activity;
        eventName = name;
    }

    public void sendError(String id, int code, String message) {
        WritableMap params = Arguments.createMap();
        params.putString("type", "onError");
        params.putString("id", id);
        params.putString("code", String.valueOf(code));
        params.putString("message", message);
        Log.d(TAG, eventName + ":" + "onError");
        sendEvent(params);
    }

    public void sendStatus(String type, @Nullable String id) {
        WritableMap params = Arguments.createMap();
        params.putString("type", type);
        params.putString("id", id);
        Log.d(TAG, eventName + ":" + type);
        sendEvent(params);
    }

    public void sendEvent(WritableMap params) {
        mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    public void action(final Map params) {
        if (mActivity != null) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    threadAction(mActivity, params);
                }
            });
        } else {
            Log.d(TAG, "noActivity");
            sendStatus("noActivity", null);
        }
    }

    public void destroy() {
        Log.d(TAG, "destroy");
    }

    public void threadAction(Activity mActivity, Map params) {
        Log.d(TAG, params.toString());
    }

}
