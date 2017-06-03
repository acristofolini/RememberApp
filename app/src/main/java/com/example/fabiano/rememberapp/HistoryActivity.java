package com.example.fabiano.rememberapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.fabiano.rememberapp.dao.GroupDAO;
import com.example.fabiano.rememberapp.data.Group;
import com.example.fabiano.rememberapp.data.History;
import com.example.fabiano.rememberapp.dao.HistoryDAO;
import com.example.fabiano.rememberapp.helper.HistoryHelper;

public class HistoryActivity extends AppCompatActivity {

    private HistoryHelper historyHelper;
    private ListView historyList;
    private History history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_history);

        EditText campo_group = (EditText) findViewById(R.id.group_history);
        FloatingActionButton bt_del_history  = (FloatingActionButton) findViewById(R.id.btn_del_history);
        FloatingActionButton bt_save_history = (FloatingActionButton) findViewById(R.id.btn_save_history);
        FloatingActionButton bt_edit_history = (FloatingActionButton) findViewById(R.id.btn_edit_history);

        historyHelper = new HistoryHelper(HistoryActivity.this);


        //recupera os parämetros passados na chamada da tela FolderActivity
        Intent intent = getIntent();
        // recupera o campo action passado por parâmetro para identificar se bloqueia ou não o editText
        String action = intent.getStringExtra("action");
        Group group = (Group) intent.getSerializableExtra("group");

        //Toast.makeText(HistoryActivity.this, "" + group.getGroup(), Toast.LENGTH_SHORT).show();

        campo_group.setText(group.getGroup());
        campo_group.setEnabled(false);

        if (action.equals("save")) {
            // se aberta a tela através do item selecionado, desabilita o item group_folder
            bt_edit_history.hide();
            bt_del_history.hide();
            bt_save_history.show();
        } else {
            bt_edit_history.show();
            bt_del_history.show();
            bt_save_history.hide();
        }

        history = (History) intent.getSerializableExtra(History.TABLE_NAME);
        if (history != null) {
            historyHelper.fillForm(history);
        }


        // Clique no botão de deletar um Histórico
        bt_del_history.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(HistoryActivity.this)
                        .setTitle("Deletar Histórico")
                        .setMessage("Você confirma a eliminação deste histórico?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                /* Elimina o histórico  */
                                HistoryDAO dao = new HistoryDAO(HistoryActivity.this);
                                dao.remove(history);
                                dao.close();
                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        // Clique no botão de salvar um Histórico
        bt_save_history.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* Salva o cadastro do Histórico  */
                HistoryDAO dao = new HistoryDAO(HistoryActivity.this);
                if (history == null) {
                    history = historyHelper.getHistory();
                    dao.persist(history);
                } else {
                    History localHistory = historyHelper.getHistory();
                    localHistory.setId(history.getId());
                    dao.update(localHistory);
                    history = localHistory;
                }
                dao.close();
                finish();
            }
        });

        bt_edit_history.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(HistoryActivity.this, FolderActivity.class);
                startActivity(it);
            }
        });
    }
}
