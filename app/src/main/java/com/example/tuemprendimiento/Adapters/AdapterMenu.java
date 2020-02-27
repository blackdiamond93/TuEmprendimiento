package com.example.tuemprendimiento.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tuemprendimiento.Model.Menu;
import com.example.tuemprendimiento.Model.Restaurantes;
import com.example.tuemprendimiento.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMenu  extends RecyclerView.Adapter<AdapterMenu.ViewHolder>  {
    private List<Menu> menus;
    private int layout;
    private AdapterMenu.OnItemClickListener itemClickListener;

    private Context context;


    public AdapterMenu(List<Menu> menus, int layout, AdapterMenu.OnItemClickListener listener) {
        this.menus = menus;
        this.layout = layout;
        this.itemClickListener = listener;
    }



    @Override
    public AdapterMenu.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_menu, parent, false);
        context = parent.getContext();
        AdapterMenu.ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterMenu.ViewHolder holder, int position) {

        holder.bind(menus.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitulo;
        public ImageView imageViewPoster;

        public ViewHolder(View itemView) {

            super(itemView);
            textViewTitulo =  itemView.findViewById(R.id.textViewTituloM);
            imageViewPoster =  itemView.findViewById(R.id.imageViewPortadaM);

        }
        public void bind(final Menu menus, final AdapterMenu.OnItemClickListener listener) {

            textViewTitulo.setText(menus.getNombre());
            Picasso.get().load(menus.getImagen()).fit().into(imageViewPoster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(menus, getAdapterPosition());
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Menu menu, int position);
    }


}
