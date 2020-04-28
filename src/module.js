import { NativeModules } from "react-native";
import { setInit, checkInit, getInit } from "./utils";

const { BloomVideo } = NativeModules;

export default {
  init(appId) {
    return new Promise((resolve, reject) => {
      let isInit = getInit();
      if (!isInit && typeof appId === "string" && appId.length > 0) {
        // setInit(true);
        BloomVideo.init(appId)
          .then(() => {
            setInit(true);
            resolve(appId);
          })
          .catch(reject);
      } else if (isInit) {
        resolve(appId);
      } else {
        console.error("appid not null");
        reject(new Error("appid not null"));
      }
    });
  },
  setUserId(userId) {
    if (checkInit()) {
      BloomVideo.setUserId(userId);
    }
  },
};
