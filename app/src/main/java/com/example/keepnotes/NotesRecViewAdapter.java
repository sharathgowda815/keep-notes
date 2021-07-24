package com.example.keepnotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepnotes.dbhelper.DbHandler;
import com.example.keepnotes.models.Notes;
import com.example.keepnotes.params.Params;

import java.util.ArrayList;

import static android.os.SystemClock.sleep;

public class NotesRecViewAdapter extends RecyclerView.Adapter<NotesRecViewAdapter.ViewHolder> {

    private ArrayList<Notes> notes = new ArrayList<>();
    private Context context;
    private boolean isTrash;

    private AdapterView.OnItemLongClickListener onItemLongClickListener;

    public NotesRecViewAdapter(Context context, boolean isTrash) {
        this.context = context;
        this.isTrash = isTrash;
    }

//    public void setOnItemLongClickListener (AdapterView.OnItemLongClickListener onItemLongClickListener) {
//        this.onItemLongClickListener = onItemLongClickListener;
//    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull NotesRecViewAdapter.ViewHolder holder, int position) {
        holder.txtNote.setText(notes.get(position).getText());

//        Clicked
        holder.cardParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isTrash) {
//                    Toast.makeText(context, notes.get(position).getText() + " Selected, ID: " + notes.get(position).getId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, AddNewNoteActivity.class);
                    intent.putExtra(Params.KEY_ID, notes.get(position).getId());
                    context.startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to delete PERMANENTLY" + notes.get(position).getId() + "?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DbHandler dbHandler = new DbHandler(context);

                            if (1 == dbHandler.deleteTrashNotes(notes.get(position).getId())) {
                                int p = position;
                                Toast.makeText(context, "Note Deleted PERMANENTLY!", Toast.LENGTH_SHORT).show();
                                notifyItemRemoved(p);
                                dbHandler.close();
                                Intent intent = new Intent(context, TrashActivity.class);
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, "Error Deleting from TRASH!", Toast.LENGTH_SHORT).show();
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

            }
        });

//        Long Pressed
        holder.cardParent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (!isTrash) {
//                    Toast.makeText(context, notes.get(position).getText() + " Selected Long Pressed, ID: " + notes.get(position).getId(), Toast.LENGTH_SHORT).show();

//                    System.out.println("Position: " + position);

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to delete " + notes.get(position).getId() + "?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DbHandler dbHandler = new DbHandler(context);

                            if (-1 != dbHandler.addNoteToTrashNotes(new Notes(notes.get(position).getId(), notes.get(position).getText()))) {
                                if (1 == dbHandler.deleteCurNotes(notes.get(position).getId())) {
                                    int p = position;
                                    Toast.makeText(context, "Note Deleted!", Toast.LENGTH_SHORT).show();
                                    notifyItemRemoved(p);
                                    dbHandler.close();
                                    Intent intent = new Intent(context, AllActivity.class);
                                    context.startActivity(intent);
                                }
                            }

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.create().show();
                    return true;
                } else {
                    // move note back to all notes

//                    Toast.makeText(context, notes.get(position).getText() + " Selected Long Pressed, ID: " + notes.get(position).getId(), Toast.LENGTH_SHORT).show();

//                    System.out.println("Position: " + position);

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to restore this note " + notes.get(position).getId() + "?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DbHandler dbHandler = new DbHandler(context);

                            if (-1 != dbHandler.addNoteToCurNotes(new Notes(notes.get(position).getId(), notes.get(position).getText()))) {
                                if (1 == dbHandler.deleteTrashNotes(notes.get(position).getId())) {
                                    int p = position;
                                    Toast.makeText(context, "Note Restored!", Toast.LENGTH_SHORT).show();
                                    notifyItemRemoved(p);
                                    dbHandler.close();
                                    Intent intent = new Intent(context, TrashActivity.class);
                                    context.startActivity(intent);
                                }
                            }

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.create().show();
                    return true;

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(ArrayList<Notes> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNote;
        private CardView cardParent;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            txtNote = itemView.findViewById(R.id.txtNote);
            cardParent = itemView.findViewById(R.id.cardParent);
        }
    }

}
