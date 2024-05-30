package com.example.laboratorum1_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class GradesActivity extends AppCompatActivity {

    private TextView tvGrades;
    private LinearLayout gradesLayout;
    private int gradesCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        tvGrades = findViewById(R.id.tvGrades);
        gradesLayout = findViewById(R.id.gradesLayout);

        gradesCount = getIntent().getIntExtra("gradesCount", 5);

        tvGrades.setText(getString(R.string.lizba_ocen) + gradesCount);

        createGradeInputs(gradesCount);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            if (validateGrades()) {
                // Save the grades or proceed as needed
                Toast.makeText(this, "Oceny zapisane", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createGradeInputs(int count) {
        for (int i = 0; i < count; i++) {
            RadioGroup radioGroup = new RadioGroup(this);
            radioGroup.setOrientation(RadioGroup.HORIZONTAL);

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
}
