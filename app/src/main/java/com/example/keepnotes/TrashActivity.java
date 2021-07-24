package com.example.keepnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.keepnotes.dbhelper.DbHandler;
import com.example.keepnotes.models.Notes;

import java.util.ArrayList;
import java.util.List;

public class TrashActivity extends AppCompatActivity {

    private RecyclerView trashNotesRecView;

    private DbHandler dbHandler;

//    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initialize();

        fillRecyclerView();

    }

    private boolean emptyTrash() {

        if (dbHandler.deleteAllTrashNotes()) {
            dbHandler.close();
            return true;
        }

        return false;
    }

    private void fillRecyclerView() {

        ArrayList<Notes> notes = new ArrayList<>();
        List<Notes> dbNotes = dbHandler.getAllTrashNotes();

        for (Notes note : dbNotes) {
            notes.add(note);
        }

        NotesRecViewAdapter adapter = new NotesRecViewAdapter(this, true);
        adapter.setNotes(notes);

        trashNotesRecView.setAdapter(adapter);
        trashNotesRecView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initialize() {

        trashNotesRecView = findViewById(R.id.trashNotesRecView);

        dbHandler = new DbHandler(TrashActivity.this);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AllActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
//        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        getMenuInflater().inflate(R.menu.delete_all, menu);

        menu.add("Empty Bin");
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getTitle().equals("Empty Bin")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(TrashActivity.this);
            builder.setMessage("Are you sure you want to delete all notes permanently?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (emptyTrash()) {
                        fillRecyclerView();
                        Toast.makeText(TrashActivity.this, "Trash Cleared", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TrashActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.create().show();

        }

//        return super.onOptionsItemSelected(item);
        return true;
    }

}