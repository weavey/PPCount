package com.weavey.msc;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.weavey.presenter.EditNotesPresenter;
import com.weavey.utils.LogUtils;
import com.weavey.utils.ToastUtils;

import java.util.List;

/**
 * create by Weavey
 * on date 2016-01-22
 * TODO
 */
public class XunFeiVoiceAction {

    private static XunFeiVoiceAction instance = null;
    private Context context;

    private InitListener mInitListener = null;  //初始化监听器。
    //    public static SpeechUnderstander mSpeechUnderstander = null;
//    public static SpeechUnderstanderListener mSpeechUnderstanderListener =
// null;
    private SpeechSynthesizer mSpeaker;// 语音合成对象
    private SynthesizerListener speakerListener;
    private int mPercentForBuffering = 0; // 缓冲进度
    private int mPercentForPlaying = 0; // 播放进度
    private String voicer = "xiaoyan";// 默认发音人
    private String emot = "";// 情感
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private Gson gson;

    //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
    private SpeechRecognizer mIat;
    private RecognizerListener mRecoListener;
    private String recogniserString;
    private EditNotesPresenter presenter;


    public static XunFeiVoiceAction getInstance() {

        if (instance == null) {

            synchronized (XunFeiVoiceAction.class) {

                if (instance == null)
                    instance = new XunFeiVoiceAction();

            }

        }
        return instance;
    }

    public void initXunFeiAction(Context mcontext){
        this.context = mcontext;
        this.gson = new Gson();
        mInitListener = new InitListener() {

            @Override
            public void onInit(int code) {
                if (code != ErrorCode.SUCCESS) {
                }
            }
        };
//        initSemanticAnalysis();
        initSpeaker();
        initVoiceListener();
    }

    public void initXunFeiAction(Context mcontext,EditNotesPresenter prestener) {

        this.context = mcontext;
        this.presenter = prestener;
        this.gson = new Gson();
        mInitListener = new InitListener() {

            @Override
            public void onInit(int code) {
                if (code != ErrorCode.SUCCESS) {
                }
            }
        };
//        initSemanticAnalysis();
        initSpeaker();
        initVoiceListener();
    }

    //语义分析 初始化
//    private void initSemanticAnalysis() {
//
//
//        // 语义理解对象（语音到语义）。
//        mSpeechUnderstander = SpeechUnderstander.createUnderstander
// (context, mInitListener);
//        mSpeechUnderstanderListener = new SpeechUnderstanderListener() {
//
//            @Override
//            public void onResult(final UnderstanderResult result) {
//
//                Log.i("mytag", "语义分析结果：" + result.getResultString());
//                if (null != result) {
//
//                    String text = result.getResultString();
//                    final SemanticAnalysisResult bean = gson.fromJson(text,
// SemanticAnalysisResult.class);
//                    if (bean.getOperation() != null) {
//                        if (!bean.getOperation().equals("")) {
//                            speaking("正在执行");
//                           new Handler().postDelayed(new Runnable() {
//                               @Override
//                               public void run() {
//                                   SemanticActionDeal.getInstance(context)
// .standardAction(bean.getOperation());
//                               }
//                           },1000);
//
//                        }
//                    }else if(SemanticActionDeal.getInstance(context)
// .defineAction(bean.getText())){
//
//
//                    }
//                    else if(bean.getText()==null||bean.getText().equals("")){
//
//                        speaking("您好像没有说话，别逗我好吗");
//
//                    }
//                    else{
//                        speaking("无法识别您的意图，请重试，谢谢。");
//                    }
//                    if (!TextUtils.isEmpty(text)) {
//                        String s = JsonParser.parseIatResult(result
// .getResultString());
//                    }
//                } else {
////                    showTip("识别结果不正确。");
//                }
//            }
//
//            @Override
//            public void onVolumeChanged(int volume, byte[] data) {
////                showTip("当前正在说话，音量大小：" + volume);
////                microphoneView.setScale(volume);
//            }
//
//            @Override
//            public void onEndOfSpeech() {
//                // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
////                MicrophoneRecordService.setChange();
////                microphoneView.setScale(0);
////                microphoneView.goToEdge();
////                microphoneView.isRecording = false;
//                ToastUtils.showShort("结束说话");
////
//            }
//
//            @Override
//            public void onBeginOfSpeech() {
//                // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
////                showTip("开始录音,再按一次停止录音！");
//                ToastUtils.showShort("开始说话");
//            }
//
//            @Override
//            public void onError(SpeechError error) {
////                showTip(error.getPlainDescription(true));
//                ToastUtils.showShort(error.getPlainDescription(true));
//            }
//
//            @Override
//            public void onEvent(int eventType, int arg1, int arg2, Bundle
// obj) {
//                // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
////                if (SpeechEvent.EVENT_SESSION_ID == eventType) {
////                    String sid = obj.getString(SpeechEvent
// .KEY_EVENT_SESSION_ID);
////                }
//            }
//        };
//
//
//        //测试环境``````
////        mSpeechUnderstander.setParameter("server_url", "http://bj
// .voicecloud.cn/index.htm");
////        mSpeechUnderstander.setParameter("domain", "jdsearch");
////        mSpeechUnderstander.setParameter("lang", "jdsearch");
////        mSpeechUnderstander.setParameter("acous", "jdsearch");
////        mSpeechUnderstander.setParameter("rate", "16000");
//        //``````````````
//
//        // 设置语言
//        mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
//        // 设置语言区域
//        mSpeechUnderstander.setParameter(SpeechConstant.ACCENT, "mandarin");
//        //[1000, 10000] 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
//        mSpeechUnderstander.setParameter(SpeechConstant.VAD_BOS, "3000");
//
//        // 值范围：[0, 10000] 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
//        mSpeechUnderstander.setParameter(SpeechConstant.VAD_EOS, "2000");
//
//        // 设置标点符号，默认：1（有标点）
//        mSpeechUnderstander.setParameter(SpeechConstant.ASR_PTT, "1");
//
//        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
//        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
//        mSpeechUnderstander.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
//        mSpeechUnderstander.setParameter(SpeechConstant.ASR_AUDIO_PATH,
// Environment.getExternalStorageDirectory() + "/msc/sud.wav");
//
//    }

    private void initVoiceListener() {

        mIat = SpeechRecognizer.createRecognizer(context, null);
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
        mIat.setParameter(SpeechConstant.VAD_EOS, "2000"); //尾超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "2000");//头超时处理


        //听写监听器
        mRecoListener = new RecognizerListener() {
            @Override
            public void onVolumeChanged(int i, byte[] bytes) {

            }

            @Override
            public void onBeginOfSpeech() {
//                ToastUtils.showShort("开始说话");
                recogniserString = "";
            }

            @Override
            public void onEndOfSpeech() {
                ToastUtils.showShort("结束说话");
                presenter.dealMsc();
            }

            @Override
            public void onResult(RecognizerResult result, boolean b) {

                RecognizeResult temp = gson.fromJson(result
                        .getResultString(), RecognizeResult
                        .class);
                List<RecognizeResult.WsBean> wsbean = temp.getWs();

                for(RecognizeResult.WsBean bean : wsbean){


                    List<RecognizeResult.WsBean.CwBean>cwbean = bean.getCw();

                    for(RecognizeResult.WsBean.CwBean bean2:cwbean){
                        recogniserString += bean2.getW();

                    }
                }
                if(temp.getLs()){
                    presenter.setMscResult(recogniserString);
                }

            }

            @Override
            public void onError(SpeechError speechError) {
                ToastUtils.showShort(speechError.getErrorDescription());
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        };


    }

    //语音合成初始化
    private void initSpeaker() {

        mSpeaker = SpeechSynthesizer.createSynthesizer(context, mInitListener);
        speakerListener = new SynthesizerListener() {

            @Override
            public void onSpeakBegin() {
//                showTip("开始播放");
            }

            @Override
            public void onSpeakPaused() {
//                showTip("暂停播放");
            }

            @Override
            public void onSpeakResumed() {
//                showTip("继续播放");
            }

            @Override
            public void onBufferProgress(int percent, int beginPos, int endPos,
                                         String info) {
                // 合成进度
                mPercentForBuffering = percent;
//            showTip(String.format(getString(R.string.tts_toast_format),
//                    mPercentForBuffering, mPercentForPlaying));
            }

            @Override
            public void onSpeakProgress(int percent, int beginPos, int endPos) {
                // 播放进度
                mPercentForPlaying = percent;
//            showTip(String.format(getString(R.string.tts_toast_format),
//                    mPercentForBuffering, mPercentForPlaying));
            }

            @Override
            public void onCompleted(SpeechError error) {
                if (error == null) {
//                    showTip("播放完成");
                } else if (error != null) {
//                    showTip(error.getPlainDescription(true));
                }
            }

            @Override
            public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
                // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
                // 若使用本地能力，会话id为null
                //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                //		String sid = obj.getString(SpeechEvent
                // .KEY_EVENT_SESSION_ID);
                //		Log.d(TAG, "session id =" + sid);
                //	}
            }
        };

        // 清空参数
        mSpeaker.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {

            mSpeaker.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant
                    .TYPE_CLOUD);
            // 设置在线合成发音人
            mSpeaker.setParameter(SpeechConstant.VOICE_NAME, voicer);
            if (!"neutral".equals(emot)) {
                // 当前仅发音人“小艾”支持设置情感
                mSpeaker.setParameter(SpeechConstant.EMOT, emot);
            }
            //设置合成语速
            mSpeaker.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mSpeaker.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mSpeaker.setParameter(SpeechConstant.VOLUME, "50");
        }
//            else {
//                mSpeaker.setParameter(SpeechConstant.ENGINE_TYPE,
// SpeechConstant.TYPE_LOCAL);
//                // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
//                mSpeaker.setParameter(SpeechConstant.VOICE_NAME, "");
//                /**
//                 * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
//                 * 开发者如需自定义参数，请参考在线合成参数设置
//                 */
//            }
        //设置播放器音频流类型
        mSpeaker.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mSpeaker.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mSpeaker.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mSpeaker.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment
                .getExternalStorageDirectory() + "/msc/tts.wav");

    }

    //在线语音播报
    public void speaking(String words) {

        int code = mSpeaker.startSpeaking(words, speakerListener);
        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //未安装则跳转到提示安装页面
//                mInstaller.install();
            } else {
//                showTip("语音合成失败,错误码: " + code);
            }

        }
    }

    public void startListening() {

        //3.开始听写
        mIat.startListening(mRecoListener);

    }
    //返回语义分析者
//    public SpeechUnderstander getSpeechUnderstander() {
//
//        return mSpeechUnderstander;
//    }

    //返回语义监听器
//    public SpeechUnderstanderListener getSpeechUnderstanderListener() {
//
//        return mSpeechUnderstanderListener;
//    }

    public SpeechRecognizer getRecognizer() {

        return mIat;

    }

    public RecognizerListener getRrecoginzerListener() {

        return mRecoListener;
    }

    //返回语音播报器
    public SpeechSynthesizer getSpeaker() {

        return mSpeaker;
    }
}
