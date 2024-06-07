package com.example.laboratorum1_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class GradesActivity extends AppCompatActivity {

    private TextView tvGrades, tvAverage;
    private LinearLayout gradesLayout;
    private int gradesCount;
    private static final String KEY_GRADES_SELECTION = "KEY_GRADES_SELECTION";
    private int[] selectedGrades;

    private String[] subjectNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvGrades = findViewById(R.id.tvGrades);
        tvAverage = findViewById(R.id.tvAverage);
        gradesLayout = findViewById(R.id.gradesLayout);

        gradesCount = getIntent().getIntExtra("gradesCount", 5);
        subjectNames = getIntent().getStringArrayExtra("subjectNames");
        selectedGrades = new int[gradesCount];

        if (savedInstanceState != null) {
            selectedGrades = savedInstanceState.getIntArray(KEY_GRADES_SELECTION);
        }

        tvGrades.setText(getString(R.string.lizba_ocen) + gradesCount);

        createGradeInputs(gradesCount);

        Button btnCalculate = findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(v -> {
            if (validateGrades()) {
                calculateAverage();
            }
        });
    }

    private void createGradeInputs(int count) {
        for (int i = 0; i < count; i++) {
            RadioGroup radioGroup = new RadioGroup(this);
            radioGroup.setOrientation(RadioGroup.HORIZONTAL);
            radioGroup.setId(View.generateViewId());

            TextView subjectName = new TextView(this);
            subjectName.setText(subjectNames[i]);
            radioGroup.addView(subjectName);

            RadioButton rb2 = new RadioButton(this);
            rb2.setText("2");
            rb2.setId(View.generateViewId());

            RadioButton rb3 = new RadioButton(this);
            rb3.setText("3");
            rb3.setId(View.generateViewId());

            RadioButton rb4 = new RadioButton(this);
            rb4.setText("4");
            rb4.setId(View.generateViewId());

            RadioButton rb5 = new RadioButton(this);
            rb5.setText("5");
            rb5.setId(View.generateViewId());

            radioGroup.addView(rb2);
            radioGroup.addView(rb3);
            radioGroup.addView(rb4);
            radioGroup.addView(rb5);

            gradesLayout.addView(radioGroup);

            if (selectedGrades[i] != 0) {
                RadioButton selectedButton = radioGroup.findViewById(selectedGrades[i]);
                if (selectedButton != null) {
                    selectedButton.setChecked(true);
                }
            }

            int finalI = i;
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                selectedGrades[finalI] = checkedId;
            });
        }
    }

    private boolean validateGrades() {
        for (int i = 0; i < gradesLayout.getChildCount(); i++) {
            RadioGroup radioGroup = (RadioGroup) gradesLayout.getChildAt(i);
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Wybierz ocenę dla każdej pozycji", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void calculateAverage() {
        int total = 0;
        for (int i = 0; i < gradesLayout.getChildCount(); i++) {
            RadioGroup radioGroup = (RadioGroup) gradesLayout.getChildAt(i);
            RadioButton selectedButton = findViewById(radioGroup.getCheckedRadioButtonId());
            total += Integer.parseInt(selectedButton.getText().toString());
        }
        double average = (double) total / gradesCount;
        tvAverage.setText(getString(R.string.average_label) + average);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("average", average);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(KEY_GRADES_SELECTION, selectedGrades);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
