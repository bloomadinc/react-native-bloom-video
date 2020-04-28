package cn.bloomad.view;

import android.app.Activity;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.annotations.ReactProp;
import java.util.HashMap;
import javax.annotation.Nullable;
import cn.bloomad.module.VideoModule;
import cn.bloomad.module.ModuleManager;

public class VideoManager extends BaseViewManager {
    private static final String REACT_CLASS = "VideoStreaming";

    public VideoManager(ReactApplicationContext reactContext) {
        super(reactContext, REACT_CLASS);
        mCallerContext = reactContext;
        moduleManager = ModuleManager.getInstance();
    }

    @ReactProp(name = "size")
    public void setSize(ContainerView containerView, @Nullable ReadableMap sizeReadable) {
        Activity mActivity = getActivity(mCallerContext);
        if (sizeReadable != null && mActivity != null) {
            // Log.i("Create View Instance", "setSize:" + sizeReadable.toString());
            String id = String.valueOf(containerView.getId());
             Log.i("Create View Instance", "bannerView id:" + id);
            String unique = sizeReadable.getString("unique");

            VideoModule videoModule = new VideoModule(mCallerContext, mActivity, id);
            moduleManager.add(unique, videoModule);
            HashMap<String, Object> map = sizeReadable.toHashMap();
            map.put("viewGroup", containerView);
            videoModule.action(map);
        }
    }
}
