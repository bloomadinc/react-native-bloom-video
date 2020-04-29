# Bloom Video React-Native SDK 接入文档

## 概述

本文档描述了 Android 开发者如何集成 Bloom Video SDK（后面简称为 VideoSdk），通过集成 VideoSdk 为 App 引入完备的短视频服务。

如果需要在视频流中展示广告，则需集成 AdSdk。本文档默认需要展示广告，展示广告可以创造收益。

**暂时只支持 Android 客户端，ios 开发中。**

#### 术语介绍

AppId：应用程序 id，以 ba 开头的 18 位 hex 字符串，如 ba0063bfbc1a5ad878；

## SDK 集成

`$ npm install react-native-bloom-video --save`

或

`$ yarn add react-native-bloom-video --save`

## SDK 使用

```javascript
import { VideoStreaming } from "react-native-bloom-video";
```

## SDK 初始化

开发者需要初始化 AppId 后才能使用相关功能。例如：

```javascript
<VideoStreaming
  appId="ba0063bfbc1a5ad878"
  style={{
    width: width,
    height: height,
    backgroundColor: "blue",
  }}
  onChange={(params) => {
    console.log("params", params);
  }}
/>
```

## SDK 返回参数说明

params 参数说明：

| 参数    | 说明           | 类型   | 说明               |
| ------- | -------------- | ------ | ------------------ |
| type    | 广告状态       | string | -                  |
| id      | 广告 Id        | int    | -                  |
| code    | 返回的错误代码 | int    | type 为 onError 时 |
| message | 返回的错误消息 | string | type 为 onError 时 |

params.type 说明：

| type            | 说明           |
| --------------- | -------------- |
| onVideoStart    | 播放开始       |
| onVideoPause    | 播放暂停       |
| onVideoResume   | 播放恢复       |
| onVideoComplete | 播放完成       |
| onVideoError    | 播放出错       |
| onLikeClick     | 点赞或取消点赞 |
