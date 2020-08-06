package com.example.android.droidcafeinput;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText usernames = findViewById(R.id.username);
        final EditText passwords = findViewById(R.id.password);
        TextView daftars = findViewById(R.id.daftar);
        TextView logins = findViewById(R.id.login);
        daftars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DaftarActivity.class));
            }
        });
        logins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernames.getText().toString();
                final String password = passwords.getText().toString();
                if(username.equals("")){
                    usernames.setError("Harus diisi!!!");
                    usernames.requestFocus();
                }
                else if(password.equals("")){
                    passwords.setError("Harus diisi!!!");
                    passwords.requestFocus();
                }
                else {
                    reference = FirebaseDatabase.getInstance().getReference().child("users").child(username);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // ambil data password dari firebase
                                String passFirebase = dataSnapshot.child("password").getValue().toString();

                                //validasi password dengan password yang ada di firebase
                                if (password.equals(passFirebase)) {
                                    Preferences.setRegisteredUser(getBaseContext(),username);
                                    Preferences.setLoggedInUser(getBaseContext(),Preferences.getRegisteredUser(getBaseContext()));
                                    Preferences.setLoggedInStatus(getBaseContext(),true);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);

                                } else {
                                    passwords.setError("Password salah!");
                                    passwords.requestFocus();
                                }
                            } else {
                                usernames.setError("Username ini belum terdaftar!");
                                usernames.requestFocus();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    /** ke MainActivity jika data Status Login dari Data Preferences bernilai true */
    @Override
    protected void onStart() {
        super.onStart();
        if (Preferences.getLoggedInStatus(getBaseContext())){
            startActivity(new Intent(getBaseContext(),MainActivity.class));
            finish();
        }
    }
}
