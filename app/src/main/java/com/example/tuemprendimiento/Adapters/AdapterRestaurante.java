package com.example.tuemprendimiento.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tuemprendimiento.Model.Restaurantes;
import com.example.tuemprendimiento.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterRestaurante extends RecyclerView.Adapter<AdapterRestaurante.ViewHolder> {

    private List<Restaurantes> restaurantes;
    private int layout;
    private OnItemClickListener itemClickListener;

    private Context context;


    public AdapterRestaurante(List<Restaurantes> restaurantes, int layout, OnItemClickListener listener) {
        this.restaurantes = restaurantes;
        this.layout = layout;
        this.itemClickListener = listener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.bind(restaurantes.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return restaurantes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitulo,textViewUbicacion;
        public ImageView imageViewPoster;

        public ViewHolder(View itemView) {

            super(itemView);
            textViewTitulo =  itemView.findViewById(R.id.textViewTitulo);
            imageViewPoster =  itemView.findViewById(R.id.imageViewPortada);
            textViewUbicacion =itemView.findViewById(R.id.textViewUbicacion);
        }
        public void bind(final Restaurantes restaurantes, final OnItemClickListener listener) {

            textViewTitulo.setText(restaurantes.getNombre());
            textViewUbicacion.setText(restaurantes.getUbicacion());
            Picasso.get().load(restaurantes.getImagen()).fit().into(imageViewPoster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(restaurantes, getAdapterPosition());
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Restaurantes restaurantes, int position);
    }



}
