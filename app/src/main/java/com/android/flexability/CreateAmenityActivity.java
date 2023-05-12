package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAmenityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_amenity);

        Button btnBack = findViewById(R.id.backButton);
        Button btnCancel = findViewById(R.id.btnCancel);
        Button btnCreate = findViewById(R.id.btnCreate);

        EditText editTextAmenityName = findViewById(R.id.editTextAmenityName);
        EditText editTextAmenityCode = findViewById(R.id.editTextAmenityCode);
        EditText editTextAmenityPrice = findViewById(R.id.editTextAmenityPrice);
        EditText editTextAmenityDescription = findViewById(R.id.editTextAmenityDescription);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if all fields are filled
                String name = editTextAmenityName.getText().toString();
                String code = editTextAmenityCode.getText().toString();
                String price = editTextAmenityPrice.getText().toString();
                String description = editTextAmenityDescription.getText().toString();

                if (name.length() > 0
                    && code.length() > 0
                    && price.length() > 0
                    && description.length() > 0) {

                    try {
                        OkHttpHandler okHttpHandler = new OkHttpHandler();
                        okHttpHandler.createAmenity(name, code, price, description);
                        Toast.makeText(getApplicationContext(), "Δημιουργήθηκε νέα παροχή",
                                Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            }
        });

        editTextAmenityName.addTextChangedListener(new TextWatcher() {
            private boolean isTextChanged = false;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!isTextChanged) {
                    isTextChanged = true;
                    return;
                }

                if (editTextAmenityName.getText().toString().length() == 0) {
                    editTextAmenityName.setError("Εισάγετε όνομα παροχής");
                } else {
                    editTextAmenityName.setError(null);
                }
            }
        });

        editTextAmenityCode.addTextChangedListener(new TextWatcher() {
            private boolean isTextChanged = false;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!isTextChanged) {
                    isTextChanged = true;
                    return;
                }

                if (editTextAmenityCode.getText().toString().length() == 0) {
                    editTextAmenityCode.setError("Εισάγετε κωδικό");
                } else {
                    editTextAmenityCode.setError(null);
                }
            }
        });

        editTextAmenityPrice.addTextChangedListener(new TextWatcher() {
            private boolean isTextChanged = false;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!isTextChanged) {
                    isTextChanged = true;
                    return;
                }

                if (editTextAmenityPrice.getText().toString().length() == 0) {
                    editTextAmenityPrice.setError("Εισάγετε τιμή");
                } else {
                    editTextAmenityPrice.setError(null);
                }
            }
        });

        editTextAmenityDescription.addTextChangedListener(new TextWatcher() {
            private boolean isTextChanged = false;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!isTextChanged) {
                    isTextChanged = true;
                    return;
                }

                if (editTextAmenityDescription.getText().toString().length() == 0) {
                    editTextAmenityDescription.setError("Εισάγετε περιγραφή");
                } else {
                    editTextAmenityDescription.setError(null);
                }
            }
        });
    }
}