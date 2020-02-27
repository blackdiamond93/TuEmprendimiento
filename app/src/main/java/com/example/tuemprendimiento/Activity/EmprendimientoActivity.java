package com.example.tuemprendimiento.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.tuemprendimiento.Adapters.AdapterRestaurante;
import com.example.tuemprendimiento.Model.Restaurantes;
import com.example.tuemprendimiento.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EmprendimientoActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private AdapterRestaurante adapterRestaurante;
    private RecyclerView  mRecyclerView;
    private ArrayList<Restaurantes> restaurantesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emprendimiento);
        inicializarFirebase();
        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setTaskBarColored(this);


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
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);

    }

    private void getMoteles(String valor){

        databaseReference.child(valor).orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                restaurantesArrayList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        String Nombre = ds.getValue(Restaurantes.class).getNombre();
                        String Ubicacion=ds.getValue(Restaurantes.class).getUbicacion();
                        String Imagen = ds.getValue(Restaurantes.class).getImagen();
                        int N = ds.getValue(Restaurantes.class).getNumImagenes();
                        restaurantesArrayList.add(new Restaurantes(Nombre,Ubicacion,Imagen,N));
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

        TextView  inicial = findViewById(R.id.saludos);
        Bundle bundle = getIntent().getExtras();
        final String Instancia = bundle.getString("Instancia");
        inicial.setText(Instancia);

        getMoteles(Instancia);
        LinearLayoutManager manager = new LinearLayoutManager(EmprendimientoActivity.this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);



        adapterRestaurante = new AdapterRestaurante(restaurantesArrayList, R.layout.recycler_view_item, new AdapterRestaurante.OnItemClickListener() {
            @Override
            public void onItemClick(Restaurantes restaurantes, int position) {

                Intent intent = new Intent(EmprendimientoActivity.this,DetallesActivity.class);
                intent.putExtra("Instancia",Instancia);
                intent.putExtra("position",position);
                intent.putExtra("Nombre",restaurantes.getNombre());
                intent.putExtra("NumImagenes",restaurantes.getNumImagenes());
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(adapterRestaurante);


    }

}
