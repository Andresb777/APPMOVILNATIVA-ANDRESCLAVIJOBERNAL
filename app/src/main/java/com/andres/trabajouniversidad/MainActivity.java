package com.andres.trabajouniversidad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //declaro mi variable de tipo control
    Button btnIngresar,btnregistrar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnIngresar=findViewById(R.id.btningresar);
        btnregistrar=findViewById(R.id.btnregistrar);

         btnIngresar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(MainActivity.this,"Ingresaste",Toast.LENGTH_SHORT).show();
             }
         });

        btnregistrar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Registrar.class));
             }
         });


    }
}