package com.example.fabiano.rememberapp.helper;

import android.widget.EditText;
import android.widget.RatingBar;

import com.example.fabiano.rememberapp.FolderActivity;
import com.example.fabiano.rememberapp.R;
import com.example.fabiano.rememberapp.data.Group;

/**
 * Created by Ana on 6/1/2017.
 */

public class GroupHelper {

    private EditText name;

    private Group group;

    public GroupHelper(FolderActivity activity) {
        name = (EditText) activity.findViewById(R.id.group_folder);
        group = new Group();
    }

    public Group getGroup() {
        group.setGroup(name.getText().toString());
        return group;
    }

    public void fillForm(Group group) {
        name.setText(group.getGroup());
    }

}
