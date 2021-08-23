/**
 * Copyright (c) 2011 john Selmys.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
**/
package ca.lotuspond.unixfortune;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class UnixFortuneActivity extends Activity implements OnClickListener, OnSharedPreferenceChangeListener {
	
	private static final int MENU_ID = Menu.FIRST;
	public static int BgColour = 0xfffaebd7;
	public static String DefTheme = "all";
	private UnixFortuneHelper doh;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
     // Set up database if necessary
        doh = new UnixFortuneHelper(this);
		try {
			doh.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
        
     // Set up preferences
        SharedPreferences allPrefs = PreferenceManager.getDefaultSharedPreferences(this);
	    allPrefs.registerOnSharedPreferenceChangeListener(this);
		String arg1 = "Oops";
		arg1 = allPrefs.getString("listPref", arg1);
		System.out.println("arg1 is "+arg1);
		if (!arg1.equals("Oops")) {
			BgColour = Integer.valueOf(arg1, 16).intValue();
			BgColour = BgColour | 0xff000000;
			findViewById(R.id.main).setBackgroundColor(BgColour);
		}
		arg1 = "Oops";
		arg1 = allPrefs.getString("themePref", arg1);
		System.out.println("arg1 is "+arg1);
		if (!arg1.equals("Oops")) {
			DefTheme = arg1.toString();
		}
		System.out.println("BgColour = "+BgColour+" DefTheme = "+DefTheme);
		findViewById(R.id.main).setBackgroundColor(BgColour);
		
     // Set up click listeners for all the buttons
        View randomButton = findViewById(R.id.random_button);
        randomButton.setOnClickListener(this);
        View aboutButton = findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        View exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
    }
    
    public void onClick(View v) {
		Intent i;
		 switch (v.getId()) {
		  case R.id.random_button:
	    	  i = new Intent(this, RandomFortune.class);
	    	  startActivity(i);
	    	  break;
	      case R.id.about_button:
	         i = new Intent(this, About.class);
	         startActivity(i);
	         break;
	        
	      case R.id.exit_button:
	         finish();
	         break;
	      }	
	}
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        super.onCreateOptionsMenu(menu);

	        menu.add(0, MENU_ID, 0, "Colour").setIcon(android.R.drawable.btn_star);
	        menu.add(0, MENU_ID+1, 0, "Theme").setIcon(android.R.drawable.ic_dialog_info);
	        return true;
	    } 
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
       case MENU_ID:
          startActivity(new Intent(this, Colours.class));
          return true;
       case MENU_ID+1:
          startActivity(new Intent(this, Themes.class));
          return true; 
       // More items go here (if any) ...
       }
       return false;
    }

	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		System.out.println("Preferences changed! .....");
		System.out.println("arg0 is "+arg0+" arg1 is "+arg1);
		String arg2="Oops";
		System.out.println("getString is "+arg0.getString(arg1, arg2));
		if(arg1.equals("listPref")) {
				BgColour = Integer.valueOf(arg0.getString(arg1, arg2),16).intValue();
				BgColour = BgColour | 0xff000000;
				findViewById(R.id.main).setBackgroundColor(BgColour);
		}
		else if(arg1.equals("themePref")){
			DefTheme = arg0.getString(arg1, arg2);
			System.out.println("Inside else if DefTheme = "+ DefTheme);
		}
	}
}
