package com.example.tuemprendimiento.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.tuemprendimiento.Adapters.AdapterMenu;
import com.example.tuemprendimiento.Model.Menu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.tuemprendimiento.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private AdapterMenu adapterRestaurante;
    private RecyclerView mRecyclerView;
    private ArrayList<Menu> restaurantesArrayList = new ArrayList<>();
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        cardView = findViewById(R.id.cardView);

        inicializarFirebase();
        mRecyclerView = findViewById(R.id.recyclerViewMenu);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setTaskBarColored(this);

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);

    }
    private void getMoteles(){

        databaseReference.child("Categorias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                restaurantesArrayList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        String Nombre = ds.getValue(Menu.class).getNombre();
                        String Imagen = ds.getValue(Menu.class).getImagen();
                        restaurantesArrayList.add(new Menu(Nombre,Imagen));
                    }
                    adapterRestaurante.notifyDataSetChanged();
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        getMoteles();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setHasFixedSize(true);
        adapterRestaurante = new AdapterMenu(restaurantesArrayList, R.layout.recycler_view_item_menu, new AdapterMenu.OnItemClickListener() {
            @Override
            public void onItemClick(Menu menu, int position) {

                Intent intent = new Intent(MenuActivity.this,EmprendimientoActivity.class);
                String Instancia = menu.getNombre();
                intent.putExtra("Instancia",Instancia);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(adapterRestaurante);


    }
    public static void setTaskBarColored(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window w = context.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View view = new View(context);
            view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

}
