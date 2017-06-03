package com.example.fabiano.rememberapp.helper;

import android.widget.EditText;

import com.example.fabiano.rememberapp.FolderActivity;
import com.example.fabiano.rememberapp.HistoryActivity;
import com.example.fabiano.rememberapp.R;
import com.example.fabiano.rememberapp.data.Group;
import com.example.fabiano.rememberapp.data.History;

/**
 * Created by Fabiano on 6/1/2017.
 */

public class HistoryHelper {

    private EditText title;
    private EditText groupFolder;
    private EditText details;
    private EditText dataHistory;

    private History history;

    public HistoryHelper(HistoryActivity activity) {
        groupFolder = (EditText) activity.findViewById(R.id.group_folder);
        title = (EditText) activity.findViewById(R.id.title_history);
        details = (EditText) activity.findViewById(R.id.details_history);
        dataHistory = (EditText) activity.findViewById(R.id.date_history);

        history = new History();
    }

    public History getHistory() {
        history.setTitle(title.getText().toString());
        return history;
    }

    public void fillForm(History history) {
        title.setText(history.getTitle());
    }

}
