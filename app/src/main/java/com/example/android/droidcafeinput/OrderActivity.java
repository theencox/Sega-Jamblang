/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.droidcafeinput;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity handles radio buttons for choosing a delivery method for an
 * order, a spinner for setting the label for a phone number, and EditText input
 * controls.
 */
public class OrderActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener{

    /**
     * Sets the content view to activity_order, and gets the intent and its
     * data. Also creates an array adapter and layout for a spinner.
     *
     * @param savedInstanceState Saved instance state bundle.
     */

    Integer harga;
    Integer valuejumlah=1;
    Integer valuetotalharga=0;
    Integer valueharga=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Get the intent and its data.
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        int dataharga = intent.getIntExtra("harga", 0);
        TextView textView = findViewById(R.id.order_textview);
        final EditText namas = findViewById(R.id.name_text);
        final EditText alamat = findViewById(R.id.address_text);
        final EditText hps = findViewById(R.id.phone_text);
        TextView dharga = findViewById(R.id.order_harga);
        Button btn_plus = findViewById(R.id.btn_plus);
        Button btn_min = findViewById(R.id.btn_min);
        Button submit = findViewById(R.id.submit);
        final TextView total = findViewById(R.id.total_value_all);
        final TextView value = findViewById(R.id.total_value);

        harga = dataharga;
        total.setText(harga.toString());

        valueharga = dataharga;
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valuejumlah += 1;
                value.setText(valuejumlah.toString());

                valuetotalharga=valueharga * valuejumlah;
                total.setText(valuetotalharga.toString());
            }
        });

        btn_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valuejumlah>1){
                    valuejumlah -= 1;
                    value.setText(valuejumlah.toString());
                }

                valuetotalharga=valueharga * valuejumlah;
                total.setText(valuetotalharga.toString());
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String snama = namas.getText().toString();
                String salamat = alamat.getText().toString();
                String shp = hps.getText().toString();
                String sjumlah = value.getText().toString();
                String stotal = total.getText().toString();


                if (snama.equals("")){
                    namas.setError("Nama harus diisi");
                    namas.requestFocus();
                }
                else if (salamat.equals("")){
                    alamat.setError("Alamat harus diisi ");
                    alamat.requestFocus();
                }
                else if (shp.equals("")){
                    hps.setError("No. HP harus diisi");
                    hps.requestFocus();
                }
                else {
                    Intent intent1 = new Intent(OrderActivity.this,HasilActivity.class);
                    intent1.putExtra("nama", snama);
                    intent1.putExtra("alamat", salamat);
                    intent1.putExtra("hp", shp);
                    intent1.putExtra("jumlah", sjumlah);
                    intent1.putExtra("total", stotal);
                    startActivity(intent1);
                }



            }
        });

        textView.setText(message);
        dharga.setText("Harga : " + harga.toString());


    }



    /**
     * Displays the actual message in a toast message.
     *
     * @param message Message to display.
     */
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    // Interface callback for when any spinner item is selected.
    @Override
    public void onItemSelected(AdapterView<?> adapterView,
                               View view, int i, long l) {
        String spinnerLabel = adapterView.getItemAtPosition(i).toString();
        displayToast(spinnerLabel);
    }

    // Interface callback for when no spinner item is selected.
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Do nothing.
    }
}
