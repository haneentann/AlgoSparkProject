package com.example.algosparkproject.mobile.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.algosparkproject.R;

import androidx.appcompat.app.AppCompatActivity;

import com.example.algosparkproject.mobile.utils.GeminiCallback;
import com.example.algosparkproject.mobile.utils.GeminiManager;

public class ExerciseActivity extends AppCompatActivity {

    private TextView exerciseTitleTextView;
    private TextView exerciseDescriptionTextView;
    private EditText codeEditText;
    private Button submitButton;
    private TextView feedbackTextView;
    private Button hintButton;
    private Button showAnswerButton;
    private Spinner subjectSpinner;

    private String correctAnswer = "";
    private String currentQuestion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        exerciseTitleTextView = findViewById(R.id.exerciseTitleTextView);
        exerciseDescriptionTextView = findViewById(R.id.exerciseDescriptionTextView);
        codeEditText = findViewById(R.id.codeEditText);
        submitButton = findViewById(R.id.submitButton);
        feedbackTextView = findViewById(R.id.feedbackTextView);
        hintButton = findViewById(R.id.hintButton);
        showAnswerButton = findViewById(R.id.showAnswerButton);
        subjectSpinner = findViewById(R.id.subjectSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.subjects_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(adapter);

        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                generateExercise(); // Generate exercise when subject is selected
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCode();
            }
        });

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestHint();
            }
        });

        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswerDialog();
            }
        });
    }

    private void generateExercise() {
        String selectedSubject = subjectSpinner.getSelectedItem().toString();
        String prompt = "Generate a Java programming exercise on the topic of with short description"
                + selectedSubject +
                ", suitable for a high school student. Include a problem description, a code template, and the correct answer.";

        Log.d("ExerciseActivityHaneen", "Generating exercise for subject: " + selectedSubject);
        GeminiManager.getInstance().sendMessage(prompt, new GeminiCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("ExerciseActivityHaneen", "Gemini API response: " + result);
                if (result == null || result.trim().isEmpty()) {
                    Log.e("ExerciseActivityHaneen", "Received empty response from API.");
                    Toast.makeText(ExerciseActivity.this, "Received empty response.", Toast.LENGTH_SHORT).show();
                    return;
                }
                parseExercise(result);
            }

            @Override
            public void onError(Throwable error) {
                Log.e("ExerciseActivityHaneen", "Failed to generate exercise: " + error.getMessage());
                Toast.makeText(ExerciseActivity.this, "Failed to generate exercise: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseExercise(String result) {
        Log.d("ExerciseActivityHaneen", "Parsing response: " + result);
        try {
            int startDescription = result.indexOf("Problem Description:") + "Problem Description:".length();
            int endDescription = result.indexOf("Code Template:");
            int startTemplate = result.indexOf("Code Template:") + "Code Template:".length();
            int startAnswer = result.indexOf("Correct Answer:") + "Correct Answer:".length();

            if (startDescription == -1 || endDescription == -1 || startTemplate == -1 || startAnswer == -1) {
                Log.e("ExerciseActivityHaneen", "Response format is incorrect.");
                Toast.makeText(ExerciseActivity.this, "Failed to parse response format.", Toast.LENGTH_SHORT).show();
                return;
            }

            final String description = result.substring(startDescription, endDescription).trim();
            final String codeTemplate = result.substring(startTemplate, result.indexOf("Correct Answer:")).trim();
            correctAnswer = result.substring(startAnswer).trim();
            currentQuestion = description;
            final String title = subjectSpinner.getSelectedItem().toString() + " Exercise"; // Generate dynamic title

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    exerciseDescriptionTextView.setText(description);
                    //codeEditText.setText(codeTemplate);
                    exerciseTitleTextView.setText(title); // Set dynamic title
                    feedbackTextView.setText("");
                }
            });

        } catch (StringIndexOutOfBoundsException e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ExerciseActivity.this, "Failed to parse exercise.", Toast.LENGTH_SHORT).show();
                }
            });
            e.printStackTrace();
        }
    }

    private void submitCode() {
        String userAnswer = codeEditText.getText().toString();
        GeminiManager.getInstance().sendMessage(
                "Compare the user's code: " + userAnswer + " with the correct answer: " + correctAnswer + ". The problem is: " + currentQuestion,
                new GeminiCallback() {
                    @Override
                    public void onSuccess(String result) {
                        feedbackTextView.setText(result);
                    }

                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(ExerciseActivity.this, "Gemini API error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void requestHint() {
        GeminiManager.getInstance().sendMessage(
                "Provide a hint for the following question: " + currentQuestion,
                new GeminiCallback() {
                    @Override
                    public void onSuccess(String result) {
                        feedbackTextView.setText("Hint: " + result);
                    }

                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(ExerciseActivity.this, "Failed to get hint: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void showAnswerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Correct Answer")
                .setMessage(correctAnswer)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}