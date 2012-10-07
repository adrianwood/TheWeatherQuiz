package com.example.weatheriq;



import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;




public class DBHelper extends SQLiteOpenHelper {
	
	
	private SQLiteDatabase db;
	public static final String KEY_ROWID = "_id";
	public static final String KEY_USERNAME = "username";
	public static final String KEY_EMAIL = "email";
	
	DBHelper DB = null;
	private static final String DATABASE_NAME = "users.db";
	private static final int DATABASE_VERSION = 2;
	static final String DATABASE_TABLE_NAME = "wiq_users";
	
	private static final String DATABASE_TABLE_CREATE = "CREATE TABLE " + DATABASE_TABLE_NAME +
			"(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," + "username TEXT NOT NULL, " +
					"email TEXT NOT NULL";
	
			

	public DBHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		System.out.println("In constructor.");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try
		{
			db.execSQL(DATABASE_TABLE_CREATE);
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public Cursor rawQuery(String string, String[] strings)
	{
		return null;
	}
	
	public void open()
	{
		getWritableDatabase();
	}
	
	public Cursor getDetails(String text) throws SQLException
	{
		Cursor mCursor = db.query(true, DATABASE_TABLE_NAME, new String[] {KEY_ROWID, KEY_USERNAME, KEY_EMAIL}, 
				KEY_USERNAME + "=" + text, null, null, null, null, null);
		if(mCursor != null)
		{
			mCursor.moveToFirst();
		}
		
		return mCursor;
		
		
	}

}
