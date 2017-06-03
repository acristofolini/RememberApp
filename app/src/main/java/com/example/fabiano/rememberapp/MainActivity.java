package com.example.fabiano.rememberapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.fabiano.rememberapp.dao.GroupDAO;
import com.example.fabiano.rememberapp.data.Group;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView groupsList;

    //carrega a tela activity_main.xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addAdctions();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_add_folder);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, FolderActivity.class);
                // passa parâmetro se está editando ou criando um novo registro
                it.putExtra("action", "save");
                startActivity(it);

                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // carrega dados já cadastrados
    @Override
    protected void onResume() {
        super.onResume();
        fetchGroups();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // busca a lista de groups a serem apresentados em tela
    private void fetchGroups() {
        GroupDAO dao = new GroupDAO(this);
        List<Group> groups = dao.fetchAll();
        dao.close();
        ArrayAdapter<Group> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, groups);
        groupsList.setAdapter(adapter);
    }


    private void addAdctions() {
        groupsList = (ListView) findViewById(R.id.groups_list);

        groupsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View item, int position, long id) {
                Group group = (Group) groupsList.getItemAtPosition(position);
                Intent intentGoToEdit = new Intent(MainActivity.this, FolderActivity.class);

                // passa parâmetro se está editando ou criando um novo registro
                intentGoToEdit.putExtra("action", "edit");
                // passa parämetro da informa;áo que está sendo editada
                intentGoToEdit.putExtra(Group.TABLE_NAME, group);
                startActivity(intentGoToEdit);
            }
        });
    }

}

