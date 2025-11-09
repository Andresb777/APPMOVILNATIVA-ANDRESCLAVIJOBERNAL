package com.andres.trabajouniversidad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //declaro mi variable de tipo control
    Button btnIngresar, btnregistrar;
    EditText etUsuario, etclave;

    //instancia de FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //inicializo Firebase
        mAuth = FirebaseAuth.getInstance();

        btnIngresar = findViewById(R.id.btningresar);
        btnregistrar = findViewById(R.id.btnregistrar);
        etUsuario = findViewById(R.id.etUsuario);
        etclave = findViewById(R.id.etclave);

        eventos();

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registrar.class));
            }
        });
    }

    private void eventos() {
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etUsuario.getText().toString().trim();
                String password = etclave.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(email, password);
                }
            }

            private void loginUser(String email, String password) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this,DashBoard.class));
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this,
                                        "Error: " + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}
