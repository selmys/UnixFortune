/**
 * Copyright (c) 2011 john Selmys.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
**/
package ca.lotuspond.unixfortune;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static ca.lotuspond.unixfortune.Constants.*;

public class RandomFortune extends Activity {
	private UnixFortuneHelper doh;
	private String tableName = TABLE_NAME;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(UnixFortuneActivity.DefTheme.equalsIgnoreCase("all"))
        		this.setTitle("Theme is ALL");
        
        doh = new UnixFortuneHelper(this);
		try {
			doh.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			Cursor cursor = getRandomFortune();
			showRandomFortune(cursor);
			cursor.close();
			doh.close();
		} finally {
			doh.close();
		}
    }
    private Cursor getRandomFortune() {
		doh.close();
		doh.openDataBase();
		SQLiteDatabase db = doh.getReadableDatabase();
		Random rand = new Random();
		
		String[] listOfTables = getResources().getStringArray(R.array.TablesValues);
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
		Cursor cursor = db.query(tableName, null, null, null, null,
				null, null);
		int numRows = cursor.getCount();
		int randomRow = rand.nextInt(numRows);
		if(randomRow < 1)randomRow = 1;
		cursor.move(randomRow);
		return cursor;
	}

	private void showRandomFortune(final Cursor cursor) {
		String theFortune = cursor.getString(1);
		setContentView(R.layout.cookie);
		findViewById(R.id.onecookie_root).setBackgroundColor(
				UnixFortuneActivity.BgColour);
		
		TextView tableView = (TextView) findViewById(R.id.cookie_table);
		tableView.append(tableName.toUpperCase());
		
		TextView cookieView = (TextView) findViewById(R.id.one_cookie);
		cookieView.append(theFortune);
		
	}
}
