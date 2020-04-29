package cn.bloomad.module;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.linkin.videosdk.DrawVideoFragment;
import com.linkin.videosdk.VideoConfig;
import com.linkin.videosdk.VideoSdk;

import java.util.Map;

import cn.bloomad.BuildConfig;

public class VideoModule extends EventModule {
    private final int NEWS_FRAGMENT_ID = 8888;
    private DrawVideoFragment mDrawVideoFragment;
    // private FrameLayout newsContainer;
    private InitModule initModule;

    public VideoModule(final ReactApplicationContext reactContext, final Activity activity, String name) {
        super(reactContext, activity, name);
        initModule = InitModule.getInstance();
    }

    @SuppressLint("ResourceType")
    @Override
    public void threadAction(Activity mActivity, Map params) {
        Log.d(TAG, "VideoModule threadAction");
        final ViewGroup viewGroup = (ViewGroup) params.get("viewGroup");
        final String appId = (String) params.get("appId");
        AppCompatActivity activity = (AppCompatActivity) mActivity;
        FragmentManager fm = activity.getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(NEWS_FRAGMENT_ID);
        Log.d(TAG, "VideoModule threadAction:" + (null == fragment) + ":" + String.valueOf(viewGroup.getId()));
        if (null == fragment) {

            initModule.init(mActivity, appId);

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            FrameLayout newsContainer = new FrameLayout(activity);
            newsContainer.setId(NEWS_FRAGMENT_ID);

            viewGroup.addView(newsContainer, layoutParams);
            fragment = DrawVideoFragment.newInstance();

            fm.beginTransaction().add(NEWS_FRAGMENT_ID, fragment).commitAllowingStateLoss();

            mDrawVideoFragment = (DrawVideoFragment) fragment;
            mDrawVideoFragment.setVideoListener(new VideoSdk.VideoListener() {
                @Override
                public void onVideoStart(String id) { // 播放开始
                    Log.d(TAG, "VideoModule onVideoStart");
                    sendStatus("onVideoStart", id);
                }

                @Override
                public void onVideoPause(String id) { // 播放暂停
                    Log.d(TAG, "VideoModule onVideoPause");
                    sendStatus("onVideoPause", id);
                }

                @Override
                public void onVideoResume(String id) { // 播放恢复
                    Log.d(TAG, "VideoModule onVideoResume");
                    sendStatus("onVideoResume", id);
                }

                @Override
                public void onVideoComplete(String id) { // 播放完成
                    Log.d(TAG, "VideoModule onVideoComplete");
                    sendStatus("onVideoComplete", id);
                }

                @Override
                public void onVideoError(String id) { // 播放出错
                    Log.d(TAG, "VideoModule onVideoError");
                    sendStatus("onVideoError", id);
                }
            });

            mDrawVideoFragment.setOnLikeClickListener(new VideoSdk.OnLikeClickListener() {
                @Override
                public void onLikeClick(String id, boolean like) { // 点赞或取消点赞
                    Log.d(TAG, "VideoModule onLikeClick");
                    WritableMap params = Arguments.createMap();
                    params.putString("type", "onLikeClick");
                    params.putString("id", id);
                    params.putString("like", String.valueOf(like));
                    sendEvent(params);
                }
            });
        } else {
            fm.beginTransaction().show(fragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void sendEvent(WritableMap event) {
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(Integer.parseInt(eventName), "onNativeChange",
                event);
    }
}
