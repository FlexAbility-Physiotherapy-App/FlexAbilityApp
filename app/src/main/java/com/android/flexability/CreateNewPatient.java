package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class CreateNewPatient extends AppCompatActivity {

    ImageFilterView backButton;

    EditText fullNameBox;
    EditText addressBox;
    EditText phoneBox;
    EditText amkaBox;

    RadioGroup genderRG;

    ConstraintLayout createButton;
    ConstraintLayout cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_patient);

        backButton = findViewById(R.id.backButton);

        fullNameBox = findViewById(R.id.fullNameBox);
        addressBox = findViewById(R.id.addressBox);
        phoneBox = findViewById(R.id.phoneBox);
        amkaBox = findViewById(R.id.amkaBox);

        genderRG = findViewById(R.id.genderRG);

        createButton = findViewById(R.id.createButton);
        createButton.setEnabled(false);
        cancelButton = findViewById(R.id.cancelButton);

        // Sets back and cancel button to return to the last screen.
        backButton.setOnClickListener(v -> finish());
        cancelButton.setOnClickListener(v -> finish());


        // Initialized just to change button color to grayed out.
        checkButtonEnabled();


        // Checks weather all EditText boxes are non-empty and that a gender is selected.
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkButtonEnabled();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };


        // Adds TextWatcher listener to EditText boxes.
        fullNameBox.addTextChangedListener(textWatcher);
        addressBox.addTextChangedListener(textWatcher);
        phoneBox.addTextChangedListener(textWatcher);
        amkaBox.addTextChangedListener(textWatcher);

        genderRG.setOnCheckedChangeListener((group, checkedId) -> {
            checkButtonEnabled();
        });


        // Creates a new User and assigns it to a Patient.
        createButton.setOnClickListener(v -> {
            new OkHttpHandler().createPatient(fullNameBox.getText().toString().split(" ")[0],
                                            fullNameBox.getText().toString().split(" ")[1],
                                            addressBox.getText().toString(),
                                            phoneBox.getText().toString(),
                                            amkaBox.getText().toString(),
                                            ((RadioButton) findViewById(genderRG.getCheckedRadioButtonId())).getText().toString());

            finish();
        });
    }

    private void checkButtonEnabled() {
        boolean isFullNameFilled = !fullNameBox.getText().toString().isEmpty();
        boolean isAddressFilled = !addressBox.getText().toString().isEmpty();
        boolean isPhoneFilled = !phoneBox.getText().toString().isEmpty();
        boolean isAmkaFilled = !amkaBox.getText().toString().isEmpty();
        boolean isRadioButtonSelected = genderRG.getCheckedRadioButtonId() != -1;

        boolean isButtonEnabled = isFullNameFilled && isAddressFilled && isPhoneFilled && isAmkaFilled && isRadioButtonSelected;
        createButton.setEnabled(isButtonEnabled);


        // Changes button color depending on its state.
        if(isButtonEnabled)
            createButton.getBackground().setColorFilter(null);
        else
            createButton.getBackground().setColorFilter(ContextCompat.getColor(this, com.google.android.material.R.color.material_on_background_disabled), PorterDuff.Mode.MULTIPLY);
    }
}
