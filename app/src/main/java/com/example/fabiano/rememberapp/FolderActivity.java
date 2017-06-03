package com.example.fabiano.rememberapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fabiano.rememberapp.data.Group;
import com.example.fabiano.rememberapp.dao.GroupDAO;
import com.example.fabiano.rememberapp.data.History;
import com.example.fabiano.rememberapp.dao.HistoryDAO;
import com.example.fabiano.rememberapp.helper.GroupHelper;

import java.util.List;

public class FolderActivity extends AppCompatActivity {

    private GroupHelper groupHelper;
    private ListView historyList;
    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_folder);
        setTitle(R.string.add_group_title);
        addAdctions();

        EditText campo_group = (EditText) findViewById(R.id.group_folder);
        FloatingActionButton bt_add_history = (FloatingActionButton) findViewById(R.id.btn_add_history);
        FloatingActionButton bt_del_group   = (FloatingActionButton) findViewById(R.id.btn_del_folder);
        FloatingActionButton bt_edit_group  = (FloatingActionButton) findViewById(R.id.btn_edit_folder);
        FloatingActionButton bt_save_group  = (FloatingActionButton) findViewById(R.id.btn_save_folder);

        groupHelper = new GroupHelper(this);

        //recupera os parämetros passados na chamada da tela FolderActivity
        Intent intent = getIntent();
        // recupera o campo action passado por parâmetro para identificar se bloqueia ou não o editText
        String action = intent.getStringExtra("action");
        if ("save".equals(action)) {
            // se aberta a tela através do item selecionado, desabilita o item group_folder
            campo_group.setEnabled(true);
            bt_add_history.hide();
            bt_edit_group.hide();
            bt_del_group.hide();
            bt_save_group.show();
        } else {
            campo_group.setEnabled(false);
            bt_add_history.show();
            bt_edit_group.show();
            bt_del_group.show();
            bt_save_group.hide();
        }

        group = (Group) intent.getSerializableExtra(Group.TABLE_NAME);
        if (group != null) {
            groupHelper.fillForm(group);
        }



        // evento de clique no botáo + da tela de detalhe do grupo, que abre a tela edi_folder.xml e permite incluir um novo grupo
        bt_add_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(FolderActivity.this, HistoryActivity.class);

                // recupera qual o group que está sendo alterado e passa por parämetro para o HistoryActivity
                //EditText campo_group = (EditText) findViewById(R.id.group_folder);

                it.putExtra("group", group);
                it.putExtra("action", "save");

                startActivity(it);
            }
        });


        // evento de clique no botão de excluir registro
        bt_del_group.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /* Confirma a eliminação do Grupo e dos histórico */
                new AlertDialog.Builder(FolderActivity.this)
                        .setTitle("Deletar Grupo")
                        .setMessage("Você confirma a eliminação deste grupo?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(FolderActivity.this, "Yaay", Toast.LENGTH_SHORT).show();
                                /* Elimina o histórico e o grupo  */
                                GroupDAO dao = new GroupDAO(FolderActivity.this);
                                dao.remove(group);
                                dao.close();
                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        // evento de clique no botão de salvar registro
        bt_save_group.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /* Salva o cadastro do grupo  */
                GroupDAO dao = new GroupDAO(FolderActivity.this);
                if (group == null) {
                    group = groupHelper.getGroup();
                    dao.persist(group);
                } else {
                    Group localGroup = groupHelper.getGroup();
                    localGroup.setId(group.getId());
                    dao.update(localGroup);
                    group = localGroup;
                }
                dao.close();
                finish();


                /* Volta para a tela principal */
                Intent it = new Intent(FolderActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

        // clique no botão editar
        bt_edit_group.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText campo_group = (EditText) findViewById(R.id.group_folder);
                FloatingActionButton bt_add_history = (FloatingActionButton) findViewById(R.id.btn_add_history);
                FloatingActionButton bt_del_group   = (FloatingActionButton) findViewById(R.id.btn_del_folder);
                FloatingActionButton bt_edit_group  = (FloatingActionButton) findViewById(R.id.btn_edit_folder);
                FloatingActionButton bt_save_group  = (FloatingActionButton) findViewById(R.id.btn_save_folder);

                campo_group.setEnabled(true);
                bt_add_history.hide();
                bt_edit_group.hide();
                bt_del_group.hide();
                bt_save_group.show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (group != null) {
            fetchHistory();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchHistory() {
        HistoryDAO dao = new HistoryDAO(this);
        List<History> historys = dao.fetchAll(group.getId());
        dao.close();
        ArrayAdapter<History> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historys);
        historyList.setAdapter(adapter);
    }

    private void addAdctions() {
        historyList = (ListView) findViewById(R.id.history_list);

        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View item, int position, long id) {
                History history = (History) historyList.getItemAtPosition(position);
                Intent intentGoToEdit = new Intent(FolderActivity.this, HistoryActivity.class);
                intentGoToEdit.putExtra("group", group);
                intentGoToEdit.putExtra(History.TABLE_NAME, history);
                startActivity(intentGoToEdit);
            }
        });
    }

}
