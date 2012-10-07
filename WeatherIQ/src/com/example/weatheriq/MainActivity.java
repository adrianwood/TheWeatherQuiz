package com.example.weatheriq;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.weatheriq.QuizFactory.Question;
import com.example.weatheriq.QuizFactory.Quiz;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {

	WeatherIqDbAdapter mWeatherDb;
	Button endQuizButton;
	Question theQ; 
    RadioButton rbA ;
    RadioButton rbA1;
    RadioButton rbA2;
    RadioButton rbA3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Context ctx = getApplicationContext();
        mWeatherDb = new WeatherIqDbAdapter(ctx);
        mWeatherDb.clearAllEntries();
        endQuizButton = (Button) findViewById(R.id.button1);
        endQuizButton.setOnClickListener(this);
        
        // The InputStream opens the resourceId and sends it to the buffer
        InputStream is = this.getResources().openRawResource(R.raw.sample_q2);
        mWeatherDb.addContent(is);
        
        QuizFactory qFactory = new QuizFactory(mWeatherDb);
        
        List<Integer> themes = new ArrayList<Integer>();
        themes.add(QuizFactory.WEATHER_IMPACTS);
        
        Quiz quiz = qFactory.QuizFactoryMethod(themes, QuizFactory.DIFFICULTY_HARD, 9);
        
        int QuestionNumber = 0;
        List<Question> qs = quiz.getQuestions();
        
        theQ = qs.get(QuestionNumber);
        
        TextView qt = (TextView) findViewById(R.id.theme);
        TextView tv = (TextView) findViewById(R.id.hello_tv);
        TextView df = (TextView) findViewById(R.id.difficulty);
       
        rbA = (RadioButton) findViewById(R.id.question1);
        rbA1 = (RadioButton) findViewById(R.id.question2);
        rbA2 = (RadioButton) findViewById(R.id.question3);
        rbA3 = (RadioButton) findViewById(R.id.question4);
        
//        qt.setText (qs.get(QuestionNumber).getDbQuestion().getmTheme());
        tv.setText (qs.get(QuestionNumber).getDbQuestion().getmQuestion());
       // df.setText (qs.get(QuestionNumber).getDbQuestion().getmDifficulty());
        rbA.setText(qs.get(QuestionNumber).getDbQuestion().getmAnswerA());
        rbA1.setText(qs.get(QuestionNumber).getDbQuestion().getmAnswerB());
        rbA2.setText(qs.get(QuestionNumber).getDbQuestion().getmAnswerC());
        rbA3.setText(qs.get(QuestionNumber).getDbQuestion().getmAnswerD());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	
	public void onClick(View v)
	{
		switch(v.getId())
		{
		
			case R.id.button1:
			{
				boolean correct = false;
				if(rbA.isChecked())
				{
					
					if(theQ.getDbQuestion().getmActualAnswer().equals("A")) correct  = true;

				}
				if(rbA1.isChecked())
				{
					
					if(theQ.getDbQuestion().getmActualAnswer().equals("B")) correct  = true;

				}
				if(rbA2.isChecked())
				{
					
					if(theQ.getDbQuestion().getmActualAnswer().equals("C")) correct  = true;

				}
				if(rbA3.isChecked())
				{
					
					if(theQ.getDbQuestion().getmActualAnswer().equals("D")) correct  = true;

				}
				
				// first check if they got question right
				Bundle b = new Bundle();
				b.putBoolean("correct", correct);
				Intent i = new Intent(getBaseContext(), Success.class);
				startActivity(i);
			}
		}
	}
}
