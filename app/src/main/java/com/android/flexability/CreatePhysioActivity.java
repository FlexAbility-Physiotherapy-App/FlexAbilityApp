package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreatePhysioActivity extends AppCompatActivity {
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_physio);

        Button btnBack = findViewById(R.id.backButton);
        Button btnCancel = findViewById(R.id.btnCancel);
        Button btnCreate = findViewById(R.id.btnCreate);

        EditText editTextPhysioName = findViewById(R.id.editTextPhysioName);
        EditText editTextPhysioAddress = findViewById(R.id.editTextPhysioAddress);
        EditText editTextPhysioPhone = findViewById(R.id.editTextPhysioPhone);
        EditText editTextPhysioSSN = findViewById(R.id.editTextPhysioSSN);

        btnBack.setOnClickListener(view -> finish());

        btnCancel.setOnClickListener(view -> finish());

        btnCreate.setOnClickListener(view -> {
            // Check if all fields are filled
            String name = editTextPhysioName.getText().toString();
            String address = editTextPhysioAddress.getText().toString();
            String phone = editTextPhysioPhone.getText().toString();
            String ssn = editTextPhysioSSN.getText().toString();

            if (name.length() > 0
                && address.length() > 0
                && phone.length() > 0
                && ssn.length() > 0) {

                try {
                    OkHttpHandler okHttpHandler = new OkHttpHandler();
                    okHttpHandler.Physio(counter, name, address, phone, ssn);
                    Toast.makeText(getApplicationContext(), "Δημιουργήθηκε νέο φυσιοθεραπευτήριο",
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }
        });

        editTextPhysioName.addTextChangedListener(new TextWatcher() {
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

                if (editTextPhysioName.getText().toString().length() == 0) {
                    editTextPhysioName.setError("Εισάγετε όνομα φυσιοθεραπευτηρίου");
                } else {
                    editTextPhysioName.setError(null);
                }
            }
        });

        editTextPhysioAddress.addTextChangedListener(new TextWatcher() {
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

                if (editTextPhysioAddress.getText().toString().length() == 0) {
                    editTextPhysioAddress.setError("Εισάγετε διεύθυνση");
                } else {
                    editTextPhysioAddress.setError(null);
                }
            }
        });

        editTextPhysioPhone.addTextChangedListener(new TextWatcher() {
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

                if (editTextPhysioPhone.getText().toString().length() == 0) {
                    editTextPhysioPhone.setError("Εισάγετε αριθμό τηλεφώνου");
                } else {
                    editTextPhysioPhone.setError(null);
                }
            }
        });

        editTextPhysioSSN.addTextChangedListener(new TextWatcher() {
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

                if (editTextPhysioSSN.getText().toString().length() == 0) {
                    editTextPhysioSSN.setError("Εισάγετε ΑΦΜ");
                } else {
                    editTextPhysioSSN.setError(null);
                }
            }
        });
    }
}