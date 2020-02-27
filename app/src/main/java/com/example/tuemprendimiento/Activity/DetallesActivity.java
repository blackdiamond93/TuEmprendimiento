package com.example.tuemprendimiento.Activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import com.example.tuemprendimiento.Adapters.AdapterRestaurante;
import com.example.tuemprendimiento.Adapters.ViewPagerAdapter;
import com.example.tuemprendimiento.Model.Imagenes;
import com.example.tuemprendimiento.Model.Rating;
import com.example.tuemprendimiento.Model.Restaurantes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuemprendimiento.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DetallesActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        ratingBar = findViewById(R.id.ratingBar);
        setTaskBarColored(this);
        inicializarFirebase();
        Bundle bundle = getIntent().getExtras();
        final String Instancia = bundle.getString("Instancia");
        final int position = bundle.getInt("position");
        final String Nombre = bundle.getString("Nombre");
        final int NumImagenes = bundle.getInt("NumImagenes");
        getMotel(Instancia,position);






    }
   /*
    private  void CountImagenes(final String instancia , final String nombre){

        databaseReference.child(instancia).child(nombre).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    int count = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        String n = ds.getValue(Imagenes.class).getImagen();
                        count++;
                    }
                    Map<String,Object>Imagenes = new HashMap<>();
                    Imagenes.put("NumImagenes",count);
                    databaseReference.child(instancia).child(nombre).updateChildren(Imagenes);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    */

    private void CalcularRating(final String instancia, final String nombre,final int position) {

        databaseReference.child("Rating").child(instancia).child(nombre).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String p = String.valueOf(position);
                int total = 0;
                int count = 0;
                int average = 0;
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        int Rating = ds.getValue(com.example.tuemprendimiento.Model.Rating.class).getRateRating();
                        total = total + Rating;
                        count = count + 1;
                        average = total / count;

                    }

                    Map<String, Object> Raring = new HashMap<>();

                    Raring.put("Rating" , average);
                    databaseReference.child(instancia).child(p).updateChildren(Raring);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void rating(float rating, String instancia,String Nombre,String id) {
        Map<String, Object> Raring = new HashMap<>();
        Raring.put("id",id);
        Raring.put("RateRating" , rating);
        Raring.put("Nombre",Nombre);

        databaseReference.child("Rating").child(instancia).child(Nombre).child(id()).setValue(Raring);
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


    private  void getMotel(String instancia,int p){
        final RatingBar valoracion = findViewById(R.id.ratingBar);
        final TextView Titulo = findViewById(R.id.textViewTituloDealles);
        final TextView detalles = findViewById(R.id.textViewDealles);
        final ImageView Imagen = findViewById(R.id.imageViewDetalles);
        final String position =String.valueOf(p);
        databaseReference.child(instancia).child(position).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Titulo.setText(dataSnapshot.getValue(Restaurantes.class).getNombre());
                Picasso.get().load(dataSnapshot.getValue(Restaurantes.class).getImagen()).fit().into(Imagen);
                detalles.setText(dataSnapshot.getValue(Restaurantes.class).getUbicacion());
                int v = dataSnapshot.getValue(Restaurantes.class).getRating();
                valoracion.setRating(v);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        final String Instancia = bundle.getString("Instancia");
        final int position = bundle.getInt("position");
        final String Nombre = bundle.getString("Nombre");
        final int NumImagenes = bundle.getInt("NumImagenes");


        //   CountImagenes(Instancia,Nombre);

        ImagenesDetalles(Instancia,Nombre,NumImagenes);
        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Este es el id : "+NumImagenes, Toast.LENGTH_SHORT);
        toast1.show();


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                rating(rating, Instancia,Nombre,id());

            }
        });


        CalcularRating(Instancia, Nombre,position);




    }
    private void ImagenesDetalles(String Instancia, String Nombre, final int N) {


        databaseReference.child("Imagenes").child(Instancia).child(Nombre).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    int coun=0;
                    ArrayList<String> Images = new ArrayList<>();
                    String [] I = new String[N];
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String url =ds.getValue(Imagenes.class).getImagen();
                        Images.add(url);
                        I[coun]= url;
                        coun++;
                    }




                    ViewPager viewPager = findViewById(R.id.viewpager);
                    ViewPagerAdapter adapter = new ViewPagerAdapter(DetallesActivity.this, I);

                    viewPager.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String id(){
        String id = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return id;
    }

}
