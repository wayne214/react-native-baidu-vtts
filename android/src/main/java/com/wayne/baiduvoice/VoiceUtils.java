package com.wayne.baiduvoice;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.wayne.baiduvoice.control.InitConfig;
import com.wayne.baiduvoice.control.MySyntherizer;
import com.wayne.baiduvoice.control.NonBlockSyntherizer;
import com.wayne.baiduvoice.listener.UiMessageListener;
import com.wayne.baiduvoice.util.AutoCheck;
import com.wayne.baiduvoice.util.OfflineResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VoiceUtils {
    // APPID
    private String APPID = "";
    //ApiKey
    private String APIKEY = "";
    //SecretKey
    private String SECRETKEY = "";

    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    protected TtsMode ttsMode = TtsMode.MIX;

    // 离线发音选择，VOICE_FEMALE即为离线女声发音。
    // assets目录下bd_etts_common_speech_m15_mand_eng_high_am-mix_v3.0.0_20170505.dat为离线男声模型；
    // assets目录下bd_etts_common_speech_f7_mand_eng_high_am-mix_v3.0.0_20170512.dat为离线女声模型
    protected String offlineVoice = OfflineResource.VOICE_MALE;

    // ===============初始化参数设置完毕，更多合成参数请至getParams()方法中设置 =================

    // 主控制类，所有合成控制方法从这个类开始
    private MySyntherizer synthesizer;

    /**
     * 初始化引擎，需要的参数均在InitConfig类里
     * <p>
     * DEMO中提供了3个SpeechSynthesizerListener的实现
     * MessageListener 仅仅用log.i记录日志，在logcat中可以看见
     * UiMessageListener 在MessageListener的基础上，对handler发送消息，实现UI的文字更新
     * FileSaveListener 在UiMessageListener的基础上，使用 onSynthesizeDataArrived回调，获取音频流
     */
    // 获取解析器
    public void speak(String text) {
        if (TextUtils.isEmpty(text)) {

        }

        synthesizer.speak(text);
    }


    protected void initialTts(Context context) {
        LoggerProxy.printable(true); // 日志打印在logcat中
        // 设置初始化参数
        // 此处可以改为 含有您业务逻辑的SpeechSynthesizerListener的实现类
        SpeechSynthesizerListener listener = new MyListnener();

        Map<String, String> params = getParams(context);


        // appId appKey secretKey 网站上您申请的应用获取。注意使用离线合成功能的话，需要应用中填写您app的包名。包名在build.gradle中获取。
        InitConfig initConfig = new InitConfig(APPID, APIKEY, SECRETKEY, ttsMode, params, listener);

        // 如果您集成中出错，请将下面一段代码放在和demo中相同的位置，并复制InitConfig 和 AutoCheck到您的项目中
        // 上线时请删除AutoCheck的调用
        AutoCheck.getInstance(context).check(initConfig, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck autoCheck = (AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainDebugMessage();
//                        toPrint(message); // 可以用下面一行替代，在logcat中查看代码
                         Log.w("AutoCheckMessage", message);
                    }
                }
            }

        });
        synthesizer = new MySyntherizer(context, initConfig, null); // 此处可以改为MySyntherizer 了解调用过程
    }



    //合成监听器
    class MyListnener implements SpeechSynthesizerListener {
        @Override
        public void onSynthesizeStart(String s) {
            //合成准备工作
        }

        @Override
        public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
            //合成数据和进度的回调接口，分多次回调
        }

        @Override
        public void onSynthesizeFinish(String s) {
            //合成正常结束，每句合成正常结束都会回调，如果过程中出错，则回调onError，不再回调此接口
        }

        @Override
        public void onSpeechStart(String s) {
            //播放开始，每句播放开始都会回调
        }

        @Override
        public void onSpeechProgressChanged(String s, int i) {
            //播放进度回调接口，分多次回调
        }

        @Override
        public void onSpeechFinish(String s) {
            // 播放正常结束，每句播放正常结束都会回调，如果过程中出错，则回调onError,不再回调此接口
        }

        @Override
        public void onError(String s, SpeechError speechError) {
            //当合成或者播放过程中出错时回调此接口
        }
    }

    /**
     * 合成的参数，可以初始化时填写，也可以在合成前设置。
     *
     * @return
     */
    protected Map<String, String> getParams(Context context) {
        Map<String, String> params = new HashMap<String, String>();
        // 以下参数均为选填
        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_VOLUME, "9");
        // 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, "5");
        // 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, "5");

        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

        // 离线资源文件， 从assets目录中复制到临时目录，需要在initTTs方法前完成
        OfflineResource offlineResource = createOfflineResource(context, offlineVoice);
        // 声学模型文件路径 (离线引擎使用), 请确认下面两个文件存在
        params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, offlineResource.getTextFilename());
        params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE,
                offlineResource.getModelFilename());
        return params;
    }

    protected OfflineResource createOfflineResource(Context context, String voiceType) {
        OfflineResource offlineResource = null;
        try {
            offlineResource = new OfflineResource(context, voiceType);
        } catch (IOException e) {
            // IO 错误自行处理
            e.printStackTrace();
//            toPrint("【error】:copy files from assets failed." + e.getMessage());
        }
        return offlineResource;
    }

    // 释放资源
    public void release() {
        synthesizer.release();
    }

}
