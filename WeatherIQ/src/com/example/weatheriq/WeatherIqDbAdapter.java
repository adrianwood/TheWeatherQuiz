package com.example.weatheriq;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * @author MetO weather quiz app team
 * 
 * <blockquote>
 * This provides access to database questions and allows 
 * you to filter questions in terms of theme and difficulty
 * </blockquote> 
 */
public class WeatherIqDbAdapter {

	
	public class DbQuestion
	{

		private String mQuestion, mPrologue, mAnswerA, mAnswerB,
			mAnswerC, mAnswerD, mActualAnswer;
		private int mQuestionId, mDifficulty, mTheme, mQuestionType;
		
		public String getmActualAnswer() {
			return mActualAnswer;
		}
		public void setmActualAnswer(String mActualAnswer) {
			this.mActualAnswer = mActualAnswer;
		}
		public String getmQuestion() {
			return mQuestion;
		}
		public void setmQuestion(String mQuestion) {
			this.mQuestion = mQuestion;
		}
		public int getmTheme() {
			return mTheme;
		}
		public void setmTheme(int mTheme) {
			this.mTheme = mTheme;
		}
		public String getmPrologue() {
			return mPrologue;
		}
		public void setmPrologue(String mPrologue) {
			this.mPrologue = mPrologue;
		}
		public String getmAnswerA() {
			return mAnswerA;
		}
		public void setmAnswerA(String mAnswerA) {
			this.mAnswerA = mAnswerA;
		}
		public String getmAnswerB() {
			return mAnswerB;
		}
		public void setmAnswerB(String mAnswerB) {
			this.mAnswerB = mAnswerB;
		}
		public String getmAnswerC() {
			return mAnswerC;
		}
		public void setmAnswerC(String mAnswerC) {
			this.mAnswerC = mAnswerC;
		}
		public String getmAnswerD() {
			return mAnswerD;
		}
		public void setmAnswerD(String mAnswerD) {
			this.mAnswerD = mAnswerD;
		}
		public int getmQuestionId() {
			return mQuestionId;
		}
		public void setmQuestionId(int mQuestionId) {
			this.mQuestionId = mQuestionId;
		}
		public int getmDifficulty() {
			return mDifficulty;
		}
		public void setmDifficulty(int mDifficulty) {
			this.mDifficulty = mDifficulty;
		}
		public int getmQuestionType() {
			return mQuestionType;
		}
		public void setmQuestionType(int mQuestionType) {
			this.mQuestionType = mQuestionType;
		} 
		
	}
	
	private static final String TAG = "weatherIQ_dbAdapater";
    private static final String DATABASE_NAME = "weatherIQ_quiz.db";
    private static final int DATABASE_VERSION = 2;
    private static final String QUESTIONS_TABLE_NAME = "weather_questions";
    
    /**
     * column names in database
     */
    private static final String QUESTION_ID = "Q_ID", QUESTION = "QUESTION",
    		THEME = "THEME", DIFFICULTY = "DIFFICULTY", 
    		PROLOGUE = "VOLUME", ANSWER_A = "ANS_A", ANSWER_B = "ANS_B", ANSWER_C = "ANS_C", 
    		ANSWER_D = "ANS_D", ACT_ANS="ACT_ANS", Q_TYPE = "Q_TYPE";
    
    /**
     *  column indexes in database
     */
	private static final int  QUESTION_ID_INDEX = 1, QUESTION_INDEX = 2,
    		THEME_INDEX = 3, DIFFICULTY_INDEX = 4, 
    		PROLOGUE_INDEX = 11, ANSWER_A_INDEX = 5, ANSWER_B_INDEX = 6, ANSWER_C_INDEX = 7, 
    		ANSWER_D_INDEX = 8, ACT_ANS_INDEX=9, Q_TYPE_INDEX = 10;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

		@Override
		public void onCreate(SQLiteDatabase db) {
			
            db.execSQL("CREATE TABLE " + QUESTIONS_TABLE_NAME + " ("
                    + QUESTION_ID + " TEXT PRIMARY KEY,"
                    + QUESTION + " TEXT,"
                    + THEME + " TEXT,"
                    + DIFFICULTY + " TEXT,"
                    + ANSWER_A + " TEXT," + ANSWER_B + " TEXT,"
                    + ANSWER_C + " TEXT," + ANSWER_D + " TEXT," + 
                    ACT_ANS + " TEXT,"+ Q_TYPE + " TEXT,"
                    + PROLOGUE + " TEXT"
                    + ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+ QUESTIONS_TABLE_NAME);
            onCreate(db);
		}
		
	}
	
	public void closeDb()
	{
		mWeatherDbHelper.close();
	}
	
    public void clearAllEntries(){
    	SQLiteDatabase db = mWeatherDbHelper.getWritableDatabase();
    	db.delete(QUESTIONS_TABLE_NAME, "1", null);
    }
	private DatabaseHelper mWeatherDbHelper;
	
	
	public WeatherIqDbAdapter(Context ctx)
	{
		mWeatherDbHelper = new DatabaseHelper(ctx);
	}
		
	
	public boolean addContent(InputStream is) 
	{

		// take data from input stream and create table
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is), 20000);
	    
	    boolean ok = true;
	    try {

	        String line;
	        SQLiteDatabase db  = mWeatherDbHelper.getWritableDatabase();
	        
            String q_id;
            String question;
            String theme;
            String difficulty;
            String prologue;
            String answer_a;
            String answer_b;
            String answer_c;
            String answer_d;
            String actual_ans;
            String question_type;
            
	        while ((line = reader.readLine()) != null) {
	             String[] RowData = line.split(",");
	             
	             q_id = RowData[QUESTION_ID_INDEX-1];
	             question = RowData[QUESTION_INDEX-1];
	             theme = RowData[THEME_INDEX-1];
	             difficulty = RowData[DIFFICULTY_INDEX-1];
	             prologue = RowData[PROLOGUE_INDEX-1];
	             answer_a = RowData[ANSWER_A_INDEX-1];
	             answer_b = RowData[ANSWER_B_INDEX-1];
	             answer_c = RowData[ANSWER_C_INDEX-1];
	             answer_d = RowData[ANSWER_D_INDEX-1];
	             actual_ans = RowData[ACT_ANS_INDEX-1];
	             question_type = RowData[Q_TYPE_INDEX-1];
	             
	             
	             // store in content value object for db
	             ContentValues cv = new ContentValues();
	             cv.put(QUESTION_ID, q_id);
	             cv.put(QUESTION, question);
	             cv.put(THEME, theme);
	             cv.put(DIFFICULTY, difficulty);
	             cv.put(PROLOGUE, prologue);
	             cv.put(ANSWER_A, answer_a);
	             cv.put(ANSWER_B, answer_b);
	             cv.put(ANSWER_C, answer_c);
	             cv.put(ANSWER_D, answer_d);
	             cv.put(ACT_ANS, actual_ans);
	             cv.put(Q_TYPE, question_type);
	             
	             // now store in db
	             ok &= db.insert(QUESTIONS_TABLE_NAME, null, cv) != -1;
	        }
	    }
	    catch (IOException ex) {
	        // handle exception
	    	System.out.print(ex.toString());
	    }
	    finally {
	        try {
	    	    reader.close();
	    	    is.close();
	        }
	        catch (IOException e) {
	            // handle exception
	        	System.out.print(e.toString());
	        }
	    }
	    
	    return ok;
	}
	
	DbQuestion[] getQuestions(int theme, int difficulty)
	{
		SQLiteDatabase db = mWeatherDbHelper.getReadableDatabase();
		Cursor c = db.query(QUESTIONS_TABLE_NAME, null, 
				THEME + "='" + Integer.toString(theme) + "'"+ 
				" AND " + DIFFICULTY+ "='" +Integer.toString(difficulty) + "'", 
				null, null, null, null);
		// THEME + "='" + Integer.toString(theme) + "'"
		// 
		
    	if(c == null || !c.moveToFirst()){
    		
    		if(c!= null){
    			c.close();
    		}    		
    		return null;	
    	}
    	
    	
    	int count = c.getCount();
    	
    	DbQuestion[] questions = new DbQuestion[c.getCount()];
    	
    	for(int i=0; i<count; i++){
    	
    		questions[i] = getQuestionFromCursor(c);
    		c.moveToNext();
    	}
    	
    	// close cursor
    	c.close();
 
    	return questions;
	}
	
	private DbQuestion getQuestionFromCursor(Cursor c)
	{
		DbQuestion dbq = new DbQuestion();
		
		Integer q_id = Integer.valueOf(c.getString(QUESTION_ID_INDEX-1));	
        String question = c.getString(QUESTION_INDEX-1);
        Integer theme = Integer.valueOf(c.getString(THEME_INDEX-1));
        Integer difficulty = Integer.valueOf(c.getString(DIFFICULTY_INDEX-1));
        String prologue = c.getString(PROLOGUE_INDEX-1);
        String answer_a = c.getString(ANSWER_A_INDEX-1);
        String answer_b = c.getString(ANSWER_B_INDEX-1);
        String answer_c = c.getString(ANSWER_C_INDEX-1);
        String answer_d = c.getString(ANSWER_D_INDEX-1);
        String actual_ans = c.getString(ACT_ANS_INDEX-1);
        Integer question_type = Integer.valueOf(c.getString(Q_TYPE_INDEX-1));
        
        dbq.setmAnswerA(answer_a);
        dbq.setmAnswerB(answer_b);
        dbq.setmAnswerC(answer_c);
        dbq.setmAnswerD(answer_d);
        
        dbq.setmDifficulty(difficulty.intValue());
        dbq.setmPrologue(prologue);
        dbq.setmQuestion(question);
        dbq.setmTheme(theme.intValue());
        dbq.setmQuestionId(q_id.intValue());
        dbq.setmQuestionType(question_type.intValue());
        dbq.setmActualAnswer(actual_ans);
		
        return dbq;
	}

}
