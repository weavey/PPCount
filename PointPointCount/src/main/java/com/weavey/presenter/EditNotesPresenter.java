package com.weavey.presenter;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.weavey.activity.view.EditNotesView;
import com.weavey.base.BaseTag;
import com.weavey.bean.NotesAction;
import com.weavey.litepal.Notes;
import com.weavey.litepal.NotesType;
import com.weavey.model.NotesModel;
import com.weavey.model.NotesTypeModel;
import com.weavey.msc.XunFeiVoiceAction;
import com.weavey.utils.LogUtils;
import com.weavey.utils.SharePreferenceUtils;
import com.weavey.utils.ToastUtils;
import com.weavey.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * create by Weavey
 * on date 2016-03-30
 */
public class EditNotesPresenter {

    private EditNotesView view;
    private NotesModel model;
    private NotesTypeModel model2;
    private List<NotesType> list;

    private XunFeiVoiceAction action;
    private SpeechSynthesizer mSpeaker;// 语音合成对象
    private SpeechRecognizer recognizer;
    private RecognizerListener mRecoListener;
    private boolean isSpeaking;

    public EditNotesPresenter(EditNotesView view) {

        this.view = view;
        model = new NotesModel();
        model2 = new NotesTypeModel();
        initVoice();

    }

    public void initViewData() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String time = sdf.format(new Date());
        view.setTime(time);

        view.setAddress(SharePreferenceUtils.getInstance().get(BaseTag.CITY,
                BaseTag.UNKNOWN_CITY));
        view.setWeather(SharePreferenceUtils.getInstance().get(BaseTag
                .WEATHER, BaseTag.UNKNOWN_WEATHER));
    }

    public void showDialog() {

        if (!view.getMyTitle().trim().equals("") || !view.getContent().trim()
                .equals("")) {
            view.showDialog();
        } else {

            view.finishActivity();

        }

    }

    public void setSpinnerList() {

        list = model2.getNotesType();
        ArrayList<String> type = new ArrayList<>();
        for (NotesType t : list) {
            type.add(t.getNotesType());
        }
        view.initSpinner(type);

    }

    public void initViewDataWithBean(Notes bean) {

        view.setTitle(bean.getTitle());
        view.setContent(bean.getContent());
        view.setTime(bean.getTime());
        view.setWeather(bean.getWeather());
        view.setAddress(bean.getPosition());

    }

    public void saveNotes() {

        model.saveNotes(view.getMyTitle(), view.getContent(), view.getType(),
                view.getTime(), view.getAddress(), view.getWeather());
        view.finishActivity();
        NotesAction action = new NotesAction();
        action.setSelectFragment(view.getSpinnerSelectedItemId());
        action.setTag(list.get(view.getSpinnerSelectedItemId()).getId());
        EventBus.getDefault().post(action);
    }

    public void saveSecretNotes() {

        model.saveNotes(view.getMyTitle(), view.getContent(), "秘密",
                view.getTime(), view.getAddress(), view.getWeather());
        NotesAction action = new NotesAction();
        action.setTag(-100);
        action.setSelectFragment(-1);
        EventBus.getDefault().post(action);
        view.finishActivity();
    }

    public void updateNotes(int id) {

        Notes bean = new Notes();
        bean.setId(id);
        bean.setTitle(view.getMyTitle());
        bean.setContent(view.getContent());
        bean.setWeather(view.getWeather());
        bean.setPosition(view.getAddress());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String time = sdf.format(new Date());
        bean.setTime(time);
        model.updateNotes(bean, id);
        EventBus.getDefault().post(bean);  //更新详情里的信息
        view.finishActivity();
    }

    private void initVoice() {

        isSpeaking = false;
        action = XunFeiVoiceAction.getInstance();
        action.initXunFeiAction(ViewUtils.getContext(view),this);
        recognizer = action.getRecognizer();
        mRecoListener = action.getRrecoginzerListener();
        mSpeaker = action.getSpeaker();

    }

    public void dealMsc(){

        if (isSpeaking) {
            isSpeaking = false;
            recognizer.stopListening();
            view.setMscImg(1);
            ToastUtils.showShort("正在为您转换，请稍等···");
            view.setMscText("点击开始录音");

        } else {
            view.setMscImg(2);
            view.setMscText("正在录音，再次点击停止录音···");
            if (recognizer.isListening()) {
                recognizer.stopListening();
            } else {
                recognizer.startListening(mRecoListener);
            }
            isSpeaking = true;
        }

    }

    public void setMscResult(String result){

        if(view.getTitleView().isFocused()){
            view.getTitleView().setText(view.getMyTitle()+result);
            view.getTitleView().setSelection(view.getMyTitle().length());

        }else
        {
            view.getContentView().setText(view.getContent()+result);
            if(view.getContentView().isFocused()){
                view.getContentView().setSelection(view.getContent().length());
            }
        }

    }

    public void destoryMsc(){

        recognizer.cancel();
        recognizer.destroy();
        mSpeaker.stopSpeaking();// 退出时释放连接
        mSpeaker.destroy();

    }




}
