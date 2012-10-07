package com.example.weatheriq;



import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class Success extends Activity implements OnClickListener{
	

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);
        
        TextView tv = (TextView) findViewById(R.id.ansText);
        boolean correct = true; 
        //= savedInstanceState.getBoolean("correct");
        
        if(correct)
        {
        	tv.setText("Correct");
        }else
        {
        	tv.setText("Wrong");
        }
        
        
        
        Button shareButton = (Button) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(this);  
        
        Button learnButton = (Button) findViewById(R.id.learnButton);
        learnButton.setOnClickListener(this);
        
        Button quizButton = (Button) findViewById(R.id.quizButton);
        quizButton.setOnClickListener(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
        
        
    }

	public void onClick(View arg0) {
		if (arg0.getId() == R.id.shareButton)
		{
			Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			String shareBody = "Join me on WeatherIQ";
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			startActivity(Intent.createChooser(sharingIntent, "Share via"));
		}
		
		else if(arg0.getId() == R.id.learnButton)
		{
			Intent learn = new Intent(Intent.ACTION_VIEW, Uri.parse("http://weatherquiz.wordpress.com/"));
			startActivity(learn);
		}
		
		else if(arg0.getId() == R.id.quizButton)
		{
			Intent quiz = new Intent(Intent.ACTION_VIEW, Uri.parse("http://weatherquiz.wordpress.com/question-entry/"));
			startActivity(quiz);		
		}
		
		else
		{
			
		}
		
	}

	
	 
	 public void onBackPressed()
	 {
		 super.onBackPressed();
		 Intent i =  new Intent(Success.this, LoginActivity.class);
		 startActivity(i);
	 }

}
