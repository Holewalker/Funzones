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

import com.svalero.funzones.AddPlace;
import com.svalero.funzones.R;
import com.svalero.funzones.db.AppDatabase;
import com.svalero.funzones.domain.Place;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.SuperheroHolder> {
    public Context context;

    public List<Place> places;
    Intent intentFrom;


    public PlaceAdapter(Context context, List<Place> places, Intent intent) {
        this.context = context;
        this.places = places;
        this.intentFrom = intent;
    }


    public SuperheroHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
        return new SuperheroHolder(view);
    }

    public void onBindViewHolder(SuperheroHolder holder, int position) {
        final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();

        Place place = db.placeDao().getById(places.get(position).getId());

        holder.name.setText(place.getName());
        holder.description.setText(place.getDescription());
        holder.address.setText(place.getAddress());
        holder.latitude.setText(String.valueOf(place.getLatitude()));
        holder.longitude.setText(String.valueOf(place.getLongitude()));
    }

    public int getItemCount() {
        return places.size();
    }

    public class SuperheroHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;
        public TextView address;
        public TextView latitude;
        public TextView longitude;
        public Button delete;
        public Button edit;
        public View parentView;

        public SuperheroHolder(View view) {
            super(view);
            parentView = view;
            name = view.findViewById(R.id.listName);
            description = view.findViewById(R.id.listDescription);
            address = view.findViewById(R.id.listAddress);
            latitude = view.findViewById(R.id.listLatitude);
            longitude = view.findViewById(R.id.listLongitude);
            edit = view.findViewById(R.id.btnEdit);

            delete = view.findViewById(R.id.btnDelete);
            edit.setOnClickListener(v -> editReserve(getAdapterPosition()));

            delete.setOnClickListener(v -> deletePlace(getAdapterPosition()));
        }
    }

    public void deletePlace(int position) {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
        deleteDialog.setMessage(R.string.confirmation).setTitle(R.string.deleteMessage)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();

                    Place place = places.get(position);
                    db.placeDao().delete(place);
                    places.remove(place);
                    notifyItemRemoved(position);
                }).setNegativeButton(R.string.no, (dialog, id) -> {
                    dialog.dismiss();
                });
        AlertDialog dialog = deleteDialog.create();
        dialog.show();
    }

    public void editReserve(int position) {
        Place place = places.get(position);
        Intent intent = new Intent(context, AddPlace.class);
        intent.putExtra("place", place);
        context.startActivity(intent);
    }
}
