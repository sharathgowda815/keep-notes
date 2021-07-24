package com.example.keepnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.keepnotes.dbhelper.DbHandler;
import com.example.keepnotes.models.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AllActivity extends AppCompatActivity {

    private RecyclerView allNotesRecView;
    private FloatingActionButton fabAddNew, fabTrash;
    private Intent intent, intentTrash;

    private DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        initialize();

        ArrayList<Notes> notes = new ArrayList<>();
//        notes.add(new Notes(1, "Text 1 111111111111111111111111111111111111111111111111" +
//                "1111111111111111111111111111111111111111111111111111111" +
//                "1111111111111111111111111111111111111111111111111111" +
//                "111111111111111111111111 2222222222222222222222222222222222222222222 3333" +
//                "3333333333333333333333333333333333333333333333333333333333333333" +
//                "4444444444444444444444444444444444444444444444444444444444444"));
//        notes.add(new Notes(2, "Text 2"));
//        notes.add(new Notes(3, "Text 3"));
//        notes.add(new Notes(1, "Text 1"));
//        notes.add(new Notes(2, "Text 2"));
//        notes.add(new Notes(3, "Text 3"));
//        notes.add(new Notes(1, "Text 1"));
//        notes.add(new Notes(2, "Text 2"));
//        notes.add(new Notes(3, "Text 3"));
//        notes.add(new Notes(1, "Text 1"));
//        notes.add(new Notes(2, "Text 2"));
//        notes.add(new Notes(3, "Text 3"));
//        notes.add(new Notes(3, "Text 3"));
//        notes.add(new Notes(1, "Text 1"));
//        notes.add(new Notes(2, "Text 2"));
//        notes.add(new Notes(3, "Text 3"));
//        notes.add(new Notes(1, "Text 1"));
//        notes.add(new Notes(2, "Text 2"));
//        notes.add(new Notes(3, "Text 3"));

        List<Notes> dbNotes = dbHandler.getAllCurNotes();

        for (Notes note : dbNotes) {
            notes.add(note);
        }

        NotesRecViewAdapter adapter = new NotesRecViewAdapter(this, false);
        adapter.notifyDataSetChanged();
        adapter.setNotes(notes);

        allNotesRecView.setAdapter(adapter);
//        contactsRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        contactsRecView.setLayoutManager(new GridLayoutManager(this, 2));
        allNotesRecView.setLayoutManager(new LinearLayoutManager(this));

        fabAddNewClicked();
        fabTrashClicked();

    }

    private void fabTrashClicked() {

        fabTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentTrash);
            }
        });

    }

    private void fabAddNewClicked() {

        fabAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }

    private void initialize() {

        allNotesRecView = findViewById(R.id.allNotesRecView);

        fabAddNew = findViewById(R.id.fabAddNew);
        fabTrash = findViewById(R.id.fabTrash);

        intent = new Intent(getApplicationContext(), AddNewNoteActivity.class);

        intentTrash = new Intent(getApplicationContext(), TrashActivity.class);

        dbHandler = new DbHandler(AllActivity.this);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
//        super.onBackPressed();
    }
}