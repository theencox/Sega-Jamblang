package com.example.android.droidcafeinput;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HasilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        TextView nama = findViewById(R.id.name_text);
        TextView alamat = findViewById(R.id.address_text);
        TextView hp = findViewById(R.id.phone_text);
        TextView jumlah = findViewById(R.id.jumlah_text);
        TextView total = findViewById(R.id.total_text);
        Button sback = findViewById(R.id.back);

        nama.setText(getIntent().getStringExtra("nama"));
        alamat.setText(getIntent().getStringExtra("alamat"));
        hp.setText(getIntent().getStringExtra("hp"));
        jumlah.setText(getIntent().getStringExtra("jumlah"));
        total.setText(getIntent().getStringExtra("total"));
        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}
