package com.weavey.presenter;

import com.weavey.fragment.view.FragmentNoteView;
import com.weavey.litepal.Notes;
import com.weavey.model.NotesModel;

import java.util.List;

/**
 * Created by weavey on 2016-04-03.
 */
public class FragmentPresenter {

    private FragmentNoteView view;
    private NotesModel model;

    public FragmentPresenter(FragmentNoteView view){

        this.view = view;
        model = new NotesModel();

    }

    public void setViewData(String query){

        List<Notes> list = model.queryBy(query);
        view.updateAdapter(list);

    }


}
