package com.example.weatheriq;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{

	 Button btnLogin;
     Button btnRegister;
     
     EditText txtBoxUserName;
     EditText txtBoxEmail;

     private DBHelper DB =null;
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       btnRegister = (Button)findViewById(R.id.btnRegister);
       btnRegister.setOnClickListener(this);
       
       btnLogin = (Button)findViewById(R.id.btnLogin);
       btnLogin.setOnClickListener(this);

       txtBoxUserName = (EditText)findViewById(R.id.txtBoxUserName);
	   txtBoxEmail = (EditText)findViewById(R.id.txtBoxEmail);
	   
	   
	   
	   DB = new DBHelper(getApplicationContext());
               
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }



	public void onClick(View v) {
		switch(v.getId())
		{
		
		case R.id.btnRegister:
			//setContentView(R.layout.activity_register);
		    break;
		    
		case R.id.btnLogin:
			String username = txtBoxUserName.getText().toString();
			String email = txtBoxEmail.getText().toString();
			
			
			if(username.equals("") || username == null)
			{
				Toast.makeText(getApplicationContext(), "Please enter your username", 
						Toast.LENGTH_SHORT).show();				
			}
			
			else if (email.equals("") || email == null)
			{
				Toast.makeText(getApplicationContext(), "Please enter your email", 
						Toast.LENGTH_SHORT).show();
			}
			
			else
			{
				boolean validLogin = true;//validateLogin(username, email, getBaseContext());
				if (validLogin)
				{
					//System.out.println("valid");
					Intent in = new Intent(getBaseContext(), MainActivity.class);
					in.putExtra("userName", txtBoxUserName.getText().toString());
					startActivity(in);
					//finish();					
				}
										
			}
			default: 
			{
				System.out.print("default");
			}
			
			break;

		}
		
	}


	private boolean validateLogin(String username, String email, Context baseContext) {
		
		
		SQLiteDatabase db = DB.getReadableDatabase();
		
		String[] columns = {"_id"};
		
		String selection = "username=? AND email =?";
		String selectionArgs[] = {username, email};
		
		Cursor cursor = null;
		
		cursor = db.query(DBHelper.DATABASE_TABLE_NAME, columns, selection, selectionArgs, 
				null, null, null);
		startManagingCursor(cursor);

		
		int numberOfRows = cursor.getCount();
		
		if(numberOfRows <= 0)
		{
			Toast.makeText(getApplicationContext(), "Wrong login details", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getBaseContext(), LoginActivity.class);
			startActivity(intent);
			return false;
			
		}
		
		return true;
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		DB.close();
		
	}
	
	

	
}
