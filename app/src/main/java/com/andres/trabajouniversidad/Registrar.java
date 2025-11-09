package com.andres.trabajouniversidad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registrar extends AppCompatActivity {

    EditText etNombreR,etApellidoR,etCorreoR,etContrasenaR;

    Button btnResgistrarU;
    TextView tvIrLogin;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    String nombres="",apellidos="",correo="",contrasena="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar);

        btnResgistrarU=findViewById(R.id.btnResgistrarU);
        tvIrLogin=findViewById(R.id.tvirLogin);
        etNombreR=findViewById(R.id.etNombreR);
        etApellidoR=findViewById(R.id.etApellidoR);
        etCorreoR=findViewById(R.id.etCorreoR);
        etContrasenaR=findViewById(R.id.etContrasenaR);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(Registrar.this);
        progressDialog.setTitle("Espere por favor...");
        progressDialog.setCanceledOnTouchOutside(false);


        btnResgistrarU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Registrar.this,"Te Registraste",Toast.LENGTH_SHORT).show();
                validarDatos();
            }
        });

        tvIrLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });


    }
    private void validarDatos(){
        nombres= etNombreR.getText().toString().trim();
        apellidos=etApellidoR.getText().toString().trim();
        correo=etCorreoR.getText().toString().trim();
        contrasena=etContrasenaR.getText().toString().trim();

        if(TextUtils.isEmpty(nombres)){
            Toast.makeText(this,"El campo nombre esta vacio",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(apellidos)){
            Toast.makeText(this,"El campo apellido esta vacio",Toast.LENGTH_SHORT).show();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this,"Ingrese correo valido",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(contrasena) || contrasena.length()<6){
            Toast.makeText(this,"Ingrese contraseña minimo 6 caracteres",Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(this,"Registrado",Toast.LENGTH_SHORT).show();
            registrar();
        }
    }

    private void registrar() {
        progressDialog.setMessage("Registrando usuario....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(correo,contrasena)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        guardarUsuario();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Registrar.this,"Ocurrio un problema revisa los campos",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void guardarUsuario() {

        progressDialog.setMessage("Guardando Usuario");
        progressDialog.show();
        String uid=firebaseAuth.getUid();
        HashMap<String,String>datosUsuario= new HashMap<>();
        datosUsuario.put("uid",uid);
        datosUsuario.put("nombre_usuario",nombres);
        datosUsuario.put("apellido_usuario",apellidos);
        datosUsuario.put("correo_usuario",correo);
        datosUsuario.put("contrasena_usuario",contrasena);
        datosUsuario.put("estado","1");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        assert uid != null;
        databaseReference.child(uid).setValue(datosUsuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(Registrar.this,"Usuario registrado correctamente",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(Registrar.this,DashBoard.class));
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Registrar.this,"Ocurrio un problema al guardar los datos",Toast.LENGTH_SHORT).show();
            }
        });





    }
}