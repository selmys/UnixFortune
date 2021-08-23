/**
 * Copyright (c) 2011 john Selmys.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
**/
package ca.lotuspond.unixfortune;

import java.util.Random;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static ca.lotuspond.unixfortune.Constants.*;

public class FortuneWidgetProvider extends AppWidgetProvider implements OnSharedPreferenceChangeListener {
	private SQLiteDatabase myDataBase;
	private String tableName = TABLE_NAME;
	private String myPath = DB_PATH+DATABASE_NAME;
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
	    super.onUpdate(context, appWidgetManager, appWidgetIds);
	    // Get the preference
	    SharedPreferences allPrefs = PreferenceManager.getDefaultSharedPreferences(context);
	    allPrefs.registerOnSharedPreferenceChangeListener(this);
	    String arg1 = "Oops";
		arg1 = allPrefs.getString("listPref", arg1);
		System.out.println("arg1 is "+arg1);
		if (!arg1.equals("Oops")) {
			UnixFortuneActivity.BgColour = Integer.valueOf(arg1, 16).intValue();
			UnixFortuneActivity.BgColour = UnixFortuneActivity.BgColour | 0xff000000;
		}
	    arg1 = "Oops";
		arg1 = allPrefs.getString("themePref", arg1);
		System.out.println("arg1 is "+arg1);
		if (!arg1.equals("Oops")) {
			UnixFortuneActivity.DefTheme = arg1.toString();
		}
	    // Get a random fortune
	    Random rand = new Random();
		String[] listOfTables = context.getResources().getStringArray(R.array.TablesValues);
		System.out.println("BgColour is "+UnixFortuneActivity.BgColour);
		System.out.println("DefTheme is "+UnixFortuneActivity.DefTheme);
		if(UnixFortuneActivity.DefTheme.equals("all")) {
			int N = listOfTables.length;
			int randomTable = rand.nextInt(N);
			System.out.println("N = "+N+" randomTable = "+randomTable);
			if(randomTable<2)randomTable=2;
			tableName = listOfTables[randomTable];
		} else {
			tableName = UnixFortuneActivity.DefTheme;
		}
		tableName="shorts";
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY|SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		Cursor cursor = myDataBase.query(tableName, null, null, null, null,
				null, null);
		int numRows = cursor.getCount();
		int randomRow = rand.nextInt(numRows);
		if(randomRow < 1)randomRow = 1;
		cursor.move(randomRow);
		String theFortune = cursor.getString(1);
		
		for (int i : appWidgetIds) {
			System.out.println("i is "+i);
			System.out.println("size is "+appWidgetIds.length);
			if(appWidgetIds.length==1)i=0;
			int widgetId = appWidgetIds[i];

			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.widget);
			views.setInt(R.id.TextView01, "setBackgroundColor", 0x7fffffff & (UnixFortuneActivity.BgColour));
			views.setTextViewText(R.id.TextView01, "Unix Fortune"+"\n"+theFortune);
			appWidgetManager.updateAppWidget(widgetId, views);
		}
	}
	
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
}
