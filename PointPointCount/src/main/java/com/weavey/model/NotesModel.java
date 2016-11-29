package com.weavey.model;

import com.weavey.litepal.Notes;
import com.weavey.model.interfac.NotesInfc;
import com.weavey.utils.LogUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * create by Weavey
 * on date 2016-03-30
 */
public class NotesModel implements NotesInfc {


    @Override
    public void saveNotes(String title, String content, String type,String date, String
            address, String weather) {

        Notes notes = new Notes();
        notes.setTitle(title);
        notes.setContent(content);
        notes.setNotesType(type);
        notes.setPosition(address);
        notes.setTime(date);
        notes.setWeather(weather);
        notes.save();

    }

    @Override
    public List<Notes> queryBy(String query) {


        List<Notes> result = DataSupport.where("notestype=?",query).find(Notes.class);
        return result;
    }

    @Override
    public void deleteNotes(int id) {
        DataSupport.delete(Notes.class,id);
    }

    @Override
    public void updateNotes(Notes notes, int id) {

        notes.update(id);

    }
}
