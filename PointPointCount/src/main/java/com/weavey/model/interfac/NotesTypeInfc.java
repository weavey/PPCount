package com.weavey.model.interfac;

import com.weavey.litepal.NotesType;

import java.util.List;

/**
 * create by Weavey
 * on date 2016-03-25
 */
public interface NotesTypeInfc {

    public List<NotesType> getNotesType();

    public void updateNotesTypeOrder(int type,int id);

}
