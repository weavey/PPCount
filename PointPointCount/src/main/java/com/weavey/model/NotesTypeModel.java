package com.weavey.model;

import com.weavey.litepal.NotesType;
import com.weavey.model.interfac.NotesTypeInfc;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * create by Weavey
 * on date 2016-03-25
 */
public class NotesTypeModel implements NotesTypeInfc {


    @Override
    public List<NotesType> getNotesType() {

        List<NotesType>list =  DataSupport.findAll(NotesType.class);

        return list;
    }

    public void delete(int id){

        DataSupport.delete(NotesType.class,id);

    }

    @Override
    public void updateNotesTypeOrder(int type,int id) {

        NotesType notesType = new NotesType();
        notesType.setOrderType(type);
        notesType.update(id);

    }
}
