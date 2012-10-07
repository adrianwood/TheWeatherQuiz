package com.example.weatheriq;

import java.util.ArrayList;
import java.util.List;

import com.example.weatheriq.WeatherIqDbAdapter.DbQuestion;

/** 
 * @author MetO weather quiz app team
 * Use to create a quiz 
 */
public class QuizFactory {

	public static final Integer WEATHER_PHENOM = 1, WORLD_CLIMATE = 2, WEATHER_FORECASTING = 3, 
			CLIMATE_CHANGE = 4, WEATHER_IMPACTS = 5;
	
	public static final int DIFFICULTY_EASY = 1, DIFFICULTY_MEDIUM = 2, DIFFICULTY_HARD = 3;
	
	/**
	 * @author christopher evi-parker
	 * This class contains the actual quiz 
	 * to be taken
	 */
	public class Quiz 
	{

		public Quiz(List<Question> questions, List<Integer> themes,
				int diffulty) 
		{
			Questions = questions;
			Themes = themes;
			Difficulty = diffulty;
		}
		
		
		public List<Question> getQuestions() {
			return Questions;
		}
		public List<Integer> getThemes() {
			return Themes;
		}
		public int getDifficulty() {
			return Difficulty;
		}

		private List<Question> Questions;
		private List<Integer> Themes;
		private int Difficulty;
		
	}
	abstract public class Question 
	{
		public Question(WeatherIqDbAdapter.DbQuestion dbQuestion) {
			mDbQuestion = dbQuestion;
		}
		
		private WeatherIqDbAdapter.DbQuestion mDbQuestion;

		public WeatherIqDbAdapter.DbQuestion getDbQuestion() {
			return mDbQuestion;
		}
	}
	
	public class PictorialQuestion extends Question 
	{
		public PictorialQuestion(WeatherIqDbAdapter.DbQuestion dbQuestion) 
		{ 
			super(dbQuestion); 
		
		}	
	}
	
	public class TextualQuestion extends Question
	{
		public TextualQuestion(WeatherIqDbAdapter.DbQuestion dbQuestion) 
		{
			super(dbQuestion);
		}
	}
	
	private WeatherIqDbAdapter mWeatherDb;
	
	public QuizFactory(WeatherIqDbAdapter db) 
	{
		mWeatherDb = db;
	}
	
	/**
	 * generates a random quiz based on difficulty and themes requested
	 * @param themes The themes to consider in generating question
	 * @param difficulty the difficulty level of questions
	 * @param numQuestions the number question to create for quiz (partitioned equally among chosen themes)
	 * @return the created quiz
	 */
	public Quiz QuizFactoryMethod(List<Integer> themes, int difficulty, int numQuestions)
	{
		
		if(numQuestions < themes.size())
			return null;
		
		int nParts = numQuestions/themes.size();
		
		int[] nP = new int[themes.size()];
		
		for(int i = 0; i<themes.size(); i++)
		{
			nP[i] = nParts;
		}
		
		// add the remainder
		nP[themes.size()-1] += numQuestions - (nParts*themes.size());
		
		
		List<Question> q = new ArrayList<QuizFactory.Question>();
		for (Integer theme : themes) {
			int i = 0;
			int j = 0;
			for (DbQuestion dbq : mWeatherDb.getQuestions(theme, difficulty))
			{
				
				// if gotten partition
				if(j>nP[i])
					break;
				
				q.add(new TextualQuestion(dbq));
				j++;
			}
			i++;
		}
		
		Quiz quiz = new Quiz(q, themes, difficulty);
		 
		return quiz;	
	}

	
	
}
