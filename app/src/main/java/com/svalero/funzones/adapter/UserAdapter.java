package com.svalero.funzones.adapter;

import static com.svalero.funzones.db.Constants.DATABASE_NAME;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.svalero.funzones.R;
import com.svalero.funzones.db.AppDatabase;
import com.svalero.funzones.domain.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.SuperheroHolder> {
    public Context context;

    public List<User> users;
    Intent intentFrom;


    public UserAdapter(Context context, List<User> users, Intent intent) {
        this.context = context;
        this.users = users;
        this.intentFrom = intent;
    }


    public SuperheroHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new SuperheroHolder(view);
    }

    public void onBindViewHolder(SuperheroHolder holder, int position) {
        final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();

        User user = db.userDao().getById(users.get(position).getId());

        holder.username.setText(user.getUsername());
        holder.password.setText(user.getPassword());
        holder.email.setText(user.getEmail());
    }

    public int getItemCount() {
        return users.size();
    }

    public class SuperheroHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView password;
        public TextView email;
        public Button delete;
        public View parentView;

        public SuperheroHolder(View view) {
            super(view);
            parentView = view;
            username = view.findViewById(R.id.listUsername);
            password = view.findViewById(R.id.listPassword);
            email = view.findViewById(R.id.listEmail);
            delete = view.findViewById(R.id.btnDelete);


            delete.setOnClickListener(v -> deleteUser(getAdapterPosition()));
        }
    }

    public void deleteUser(int position) {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
        deleteDialog.setMessage(R.string.confirmation).setTitle(R.string.deleteMessage)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();

                    User user = users.get(position);
                    db.userDao().delete(user);
                    users.remove(user);
                    notifyItemRemoved(position);
                }).setNegativeButton(R.string.no, (dialog, id) -> {
                    dialog.dismiss();
                });
        AlertDialog dialog = deleteDialog.create();
        dialog.show();
    }
}
