package com.example.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MoodActivity extends AppCompatActivity {

    private RadioGroup question1Group, question2Group, question3Group;
    private TextView resultMood, resultQuote;
    private ImageView moodImage;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        // Initialize views
        question1Group = findViewById(R.id.question1Group);
        question2Group = findViewById(R.id.question2Group);
        question3Group = findViewById(R.id.question3Group);
        resultMood = findViewById(R.id.resultMood);
        resultQuote = findViewById(R.id.resultQuote);
        moodImage = findViewById(R.id.moodImage);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateMood();
            }
        });
    }

    private void evaluateMood() {
        int moodScore = 0;

        // Calculate mood score based on selected answers
        moodScore += getScoreFromQuestion(question1Group);
        moodScore += getScoreFromQuestion(question2Group);
        moodScore += getScoreFromQuestion(question3Group);









































































        
        // Determine mood and set quote based on score
        String mood;
        String quote;
        int imageResId;

        if (moodScore >= 7) {
            mood = "Happy";
            quote = "Keep spreading the happiness! Life is beautiful when you're happy.";
            imageResId = R.drawable.happy_image;
        } else if (moodScore >= 5) {
            mood = "Motivated";
            quote = "Keep pushing forward! You’re on the path to greatness.";
            imageResId = R.drawable.motivated_image;
        } else if (moodScore >= 3) {
            mood = "Stressed";
            quote = "Take a deep breath. You are stronger than you think. Relax and keep going.";
            imageResId = R.drawable.stressed_image;
        } else if (moodScore == 2) {
            mood = "Sad";
            quote = "This too shall pass. Every day is a fresh start; brighter days are ahead.";
            imageResId = R.drawable.sad_image;
        } else {
            mood = "Anxious";
            quote = "Calm your mind. Focus on what you can control, and let go of what you can’t.";
            imageResId = R.drawable.anxious_image;
        }

        // Display mood, quote, and image
        resultMood.setText("Your Mood: " + mood);
        resultQuote.setText(quote);
        moodImage.setImageResource(imageResId);
        moodImage.setVisibility(View.VISIBLE); // Make the image visible
    }

    private int getScoreFromQuestion(RadioGroup questionGroup) {
        int selectedId = questionGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            return 0; // No answer selected
        }

        RadioButton selectedButton = findViewById(selectedId);
        String answer = selectedButton.getText().toString();

        // Assign scores to answers
        switch (answer) {
            case "Good":
            case "Yes":
            case "High":
                return 3;
            case "Okay":
            case "Moderate":
                return 2;
            case "Not great":
            case "No":
            case "Low":
                return 1;
            default:
                return 0;
        }
    }
}
