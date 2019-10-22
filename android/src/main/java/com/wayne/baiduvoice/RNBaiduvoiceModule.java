
package com.wayne.baiduvoice;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNBaiduvoiceModule extends ReactContextBaseJavaModule {

  private VoiceUtils utils;

  public RNBaiduvoiceModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "RNBaiduvoice";
  }

  @Override
  public void initialize() {
    super.initialize();
    utils = new VoiceUtils();
//    utils.init(getReactApplicationContext());
  }

  @ReactMethod
  public void initBaiduTTS(String appId, String appKey, String secretKey) {
    utils.initOptionParams(appId, appKey, secretKey);
    utils.init(getReactApplicationContext());
  }

  @ReactMethod
  public void speak(String msg) {
    utils.speak(msg);
  }

  @Override
  public void onCatalystInstanceDestroy() {
    utils.release();
    super.onCatalystInstanceDestroy();
  }
}