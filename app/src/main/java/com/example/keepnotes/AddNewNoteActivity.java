package com.example.keepnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.keepnotes.dbhelper.DbHandler;
import com.example.keepnotes.models.Notes;
import com.example.keepnotes.params.Params;

public class AddNewNoteActivity extends AppCompatActivity {

    private EditText editTextMultiLine;
    private Button buttonSave;
    private Intent intent;

    public static final String TAG = "Sharath";

    private boolean setNew;

    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        initialize();

        checkIntent();

        onSaveClicked();
    }

    private void setData(Notes notes) {

        editTextMultiLine.setText(notes.getText());

    }

    private void checkIntent() {

        Intent intent = getIntent();
        if (null != intent) {
            noteId = intent.getIntExtra(Params.KEY_ID, -1);
            if (noteId != -1) {
                Notes incomingNote = new DbHandler(this).getCurNoteById(noteId);
                if (null != incomingNote) {
                    setData(incomingNote);
                }
            } else if (noteId == -1) {
                setNew = true;
            }
        }

    }

    private void onSaveClicked() {

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHandler dbHandler = new DbHandler(AddNewNoteActivity.this);

                Notes notes;
                long affectedRows;

                if (!setNew) {
                    notes = new Notes(noteId, editTextMultiLine.getText().toString());

                    affectedRows = dbHandler.updateCurNote(notes);
                    Log.d(TAG, "affectedRows: " + affectedRows);

                    if (affectedRows != -1) {
                        Toast.makeText(AddNewNoteActivity.this, "Note Updated Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddNewNoteActivity.this, "Error in updating note!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                } else {
                    notes = new Notes(editTextMultiLine.getText().toString());

                    affectedRows = dbHandler.addNoteToCurNotes(notes);
                    Log.d(TAG, "affectedRows: " + affectedRows);

                    if (affectedRows != -1) {
                        Toast.makeText(AddNewNoteActivity.this, "Note Saved Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddNewNoteActivity.this, "Error in saving note!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private void initialize() {

        editTextMultiLine = findViewById(R.id.editTextMultiLine);

        buttonSave = findViewById(R.id.buttonSave);

        intent = new Intent(getApplicationContext(), AllActivity.class);

        setNew = false;

    }
}