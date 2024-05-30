package com.example.laboratorum1_2;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etGrades;
    private Button btnSubmit;

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
                validateFields();
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
    }

    private void validateFields() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String gradesStr = etGrades.getText().toString().trim();

        boolean isFirstNameValid = !firstName.isEmpty();
        boolean isLastNameValid = !lastName.isEmpty();
        boolean isGradesValid = validateGrades();

        if (!isFirstNameValid) {
            etFirstName.setError("Imię nie może być puste");
        }

        if (!isLastNameValid) {
            etLastName.setError("Nazwisko nie może być puste");
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
            etGrades.setError("Proszę podać liczbę ocen");
            return false;
        }

        int grades;
        try {
            grades = Integer.parseInt(gradesStr);
        } catch (NumberFormatException e) {
            etGrades.setError("Błędna wartość");
            return false;
        }

        if (grades < 5 || grades > 15) {
            etGrades.setError("Liczba ocen musi być z przedzialu 5-15");
            Toast.makeText(MainActivity.this, "Liczba ocen musi być z przedzialu 5-15", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
