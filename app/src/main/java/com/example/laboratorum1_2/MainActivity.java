package com.example.laboratorum1_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etGrades;
    private Button btnSubmit;

    private static final String KEY_FIRST_NAME = "KEY_FIRST_NAME";
    private static final String KEY_LAST_NAME = "KEY_LAST_NAME";
    private static final String KEY_GRADES = "KEY_GRADES";
    private static final String KEY_BUTTON_VISIBILITY = "KEY_BUTTON_VISIBILITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etGrades = findViewById(R.id.etGrades);
        btnSubmit = findViewById(R.id.btnSubmit);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validateFields(false);
            }
        };

        etFirstName.addTextChangedListener(textWatcher);
        etLastName.addTextChangedListener(textWatcher);
        etGrades.addTextChangedListener(textWatcher);

        etGrades.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateGrades();
                }
            }
        });

        if (savedInstanceState != null) {
            etFirstName.setText(savedInstanceState.getString(KEY_FIRST_NAME));
            etLastName.setText(savedInstanceState.getString(KEY_LAST_NAME));
            etGrades.setText(savedInstanceState.getString(KEY_GRADES));
            btnSubmit.setVisibility(savedInstanceState.getInt(KEY_BUTTON_VISIBILITY));
            // Re-validate fields without showing errors
            validateFields(true);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GradesActivity.class);
                intent.putExtra("gradesCount", Integer.parseInt(etGrades.getText().toString()));
                startActivity(intent);
            }
        });
    }

    private void validateFields(boolean fromSavedState) {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String gradesStr = etGrades.getText().toString().trim();

        boolean isFirstNameValid = !firstName.isEmpty();
        boolean isLastNameValid = !lastName.isEmpty();
        boolean isGradesValid = validateGrades();

        if (!isFirstNameValid && !fromSavedState) {
            etFirstName.setError(getString(R.string.imi_nie_mo_e_by_puste));
        }

        if (!isLastNameValid && !fromSavedState) {
            etLastName.setError(getString(R.string.nazwisko_nie_mo_e_by_puste));
        }

        if (isFirstNameValid && isLastNameValid && isGradesValid) {
            btnSubmit.setVisibility(View.VISIBLE);
        } else {
            btnSubmit.setVisibility(View.GONE);
        }
    }

    private boolean validateGrades() {
        String gradesStr = etGrades.getText().toString().trim();

        if (gradesStr.isEmpty()) {
            etGrades.setError(getString(R.string.prosz_poda_liczb_ocen));
            return false;
        }

        int grades;
        try {
            grades = Integer.parseInt(gradesStr);
        } catch (NumberFormatException e) {
            etGrades.setError(getString(R.string.b_dna_warto));
            return false;
        }

        if (grades < 5 || grades > 15) {
            etGrades.setError(getString(R.string.liczba_ocen_musi_by_z_przedzialu_5_15));
            Toast.makeText(MainActivity.this, getString(R.string.liczba_ocen_musi_by_z_przedzialu_5_15), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_FIRST_NAME, etFirstName.getText().toString());
        outState.putString(KEY_LAST_NAME, etLastName.getText().toString());
        outState.putString(KEY_GRADES, etGrades.getText().toString());
        outState.putInt(KEY_BUTTON_VISIBILITY, btnSubmit.getVisibility());
    }
}
