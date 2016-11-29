package com.weavey.model.interfac;

import com.weavey.litepal.Notes;

import java.util.List;

/**
 * create by Weavey
 * on date 2016-03-30
 */
public interface NotesInfc {

    public void saveNotes(String title, String content,String type, String date, String
            address, String weather);


    public List<Notes> queryBy(String query);

    public void deleteNotes(int id);

    public void updateNotes(Notes notes,int id);

}
