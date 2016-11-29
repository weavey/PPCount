package com.weavey.presenter;

import com.weavey.activity.view.NotesDetailView;
import com.weavey.bean.NotesAction;
import com.weavey.litepal.Notes;
import com.weavey.model.NotesModel;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by weavey on 2016-04-03.
 */
public class NotesDetailPresenter {

    private NotesDetailView view;
    private NotesModel model;

    public NotesDetailPresenter(NotesDetailView view) {

        this.view = view;
        model = new NotesModel();

    }

    public void initViewData(Notes notes) {

        if (notes != null) {

            view.setTitle(notes.getTitle());
            view.setContent(notes.getContent());
            view.setAddress(notes.getPosition());
            view.setTime(notes.getTime());
            view.setWeather(notes.getWeather());
        }
    }

    public void deleteNotes(int id) {

        model.deleteNotes(id);


    }

    public void updateFragment(int frgTag) {

        NotesAction action = new NotesAction();
        action.setTag(frgTag);
        action.setSelectFragment(-1);
        EventBus.getDefault().post(action);

    }

    public void updateSecretAct() {

        NotesAction action = new NotesAction();
        action.setTag(-100);
        action.setSelectFragment(-1);
        EventBus.getDefault().post(action);


    }

}
