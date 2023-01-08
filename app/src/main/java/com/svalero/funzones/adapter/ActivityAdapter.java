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

import com.svalero.funzones.AddActivity;
import com.svalero.funzones.R;
import com.svalero.funzones.db.AppDatabase;
import com.svalero.funzones.domain.Activity;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.SuperheroHolder> {
    public Context context;

    public List<Activity> activities;
    Intent intentFrom;


    public ActivityAdapter(Context context, List<Activity> activities, Intent intent) {
        this.context = context;
        this.activities = activities;
        this.intentFrom = intent;
    }


    public SuperheroHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        return new SuperheroHolder(view);
    }

    public void onBindViewHolder(SuperheroHolder holder, int position) {
        final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();

        Activity activity = db.activityDao().getById(activities.get(position).getId());

        holder.name.setText(activity.getName());
        holder.description.setText(activity.getDescription());
        holder.date.setText(String.valueOf(activity.getDate()));
        holder.userName.setText(db.userDao().getById(activity.getId_user()).getUsername());
        holder.placeName.setText(db.placeDao().getById(activity.getId_place()).getName());
    }

    public int getItemCount() {
        return activities.size();
    }

    public class SuperheroHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;
        public TextView date;
        public TextView userName;
        public TextView placeName;
        public Button delete;
        public Button edit;
        public View parentView;

        public SuperheroHolder(View view) {
            super(view);
            parentView = view;
            name = view.findViewById(R.id.listName);
            description = view.findViewById(R.id.listDescription);
            date = view.findViewById(R.id.listDate);
            userName = view.findViewById(R.id.listUserName);
            placeName = view.findViewById(R.id.listPlaceName);
            edit = view.findViewById(R.id.btnEdit);

            delete = view.findViewById(R.id.btnDelete);
            edit.setOnClickListener(v -> editReserve(getAdapterPosition()));

            delete.setOnClickListener(v -> deleteActivity(getAdapterPosition()));
        }
    }

    public void deleteActivity(int position) {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
        deleteDialog.setMessage(R.string.confirmation).setTitle(R.string.deleteMessage)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();

                    Activity activity = activities.get(position);
                    db.activityDao().delete(activity);
                    activities.remove(activity);
                    notifyItemRemoved(position);
                }).setNegativeButton(R.string.no, (dialog, id) -> {
                    dialog.dismiss();
                });
        AlertDialog dialog = deleteDialog.create();
        dialog.show();
    }

    public void editReserve(int position) {
        Activity activity = activities.get(position);
        Intent intent = new Intent(context, AddActivity.class);
        intent.putExtra("activity", activity);
        context.startActivity(intent);
    }
}
