package com.weavey.presenter;

import com.weavey.activity.view.SecretNotesView;
import com.weavey.litepal.Notes;
import com.weavey.model.NotesModel;

import java.util.List;

/**
 * Created by Administrator on 2016-04-03.
 */
public class SecretNotesPresenter {

    private SecretNotesView view;
    private NotesModel model;

    public SecretNotesPresenter(SecretNotesView view){

        this.view = view;
        model = new NotesModel();

    }

    public void getListData(){

        List<Notes>list = model.queryBy("秘密");
        view.setRecycleViewData(list);

    }



}
