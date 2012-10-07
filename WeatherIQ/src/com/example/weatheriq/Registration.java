package com.example.weatheriq;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.EditText;




public class Registration extends Activity implements OnClickListener, OnItemSelectedListener {
	
	private Button btnSubmit;
	private Button btnCancel;
	
	private EditText txtBoxRegUser;
	private EditText txtBoxRegEmail;
	
	private DBHelper DB  = null;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		btnSubmit = (Button)findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(this);
		
		btnCancel = (Button)findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
		
		txtBoxRegUser = (EditText)findViewById(R.id.txtBoxRegUser);
		txtBoxRegEmail = (EditText)findViewById(R.id.txtBoxRegEmail);
		
		DB = new DBHelper(getApplicationContext());
				
	}


	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {

		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
	
		
	}

	public void onClick(View v) {
		
		switch(v.getId())
		{
		case R.id.btnCancel:
			Intent i = new Intent(getBaseContext(), LoginActivity.class);
			startActivity(i);
			//finish();
			break;
			
		case R.id.btnSubmit:
			String username = txtBoxRegUser.getText().toString();
			String email = txtBoxRegEmail.getText().toString();
			
			boolean invalid = false;
			
			if (username.equals(""))
			{
				invalid = true;
				Toast.makeText(getApplicationContext(), "Missing username or email", Toast.LENGTH_SHORT).show();		
			}
			
			else if (email.equals(""))
			{
				invalid = true;
				Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
			}
			
			else 
			{
				invalid = false;
				addEntry(username, email);
				startActivity(new Intent(Registration.this, LoginActivity.class));
				//Intent i_register = new Intent(Registration.this, LoginActivity.class);
				//startActivity(i_register);
				//finish();
				
			}
		
			break;
		}

		
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		DB.close();
	}

	private void addEntry(String username, String email) {
		SQLiteDatabase db = DB.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("username", username);
		values.put("email", email);
		
		try
		{
			db.insert(DBHelper.DATABASE_TABLE_NAME, null, values);
			
			Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();		
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	
}
