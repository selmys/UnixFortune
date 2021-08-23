/**
 * Copyright (c) 2011 john Selmys.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
**/
package ca.lotuspond.unixfortune;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

public class Colours extends PreferenceActivity implements
			OnSharedPreferenceChangeListener {
	
	public static final String KEY_LIST_PREFERENCE = "listPref";

	private ListPreference mListPreference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.colourprefs);

		// Get a reference to the preferences
		mListPreference = (ListPreference) getPreferenceScreen()
				.findPreference(KEY_LIST_PREFERENCE);
		mListPreference.setDefaultValue("00faebd7");
		mListPreference.setSummary("Current colour is "
				+ mListPreference.getEntry().toString());
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Setup the initial values but first time I have none !!!
		mListPreference.setSummary("Current colour is "
				+ mListPreference.getValue());
		mListPreference.setSummary("Current colour is "
				+ mListPreference.getEntry().toString());

		// Set up a listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Unregister the listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// Set new summary, when a preference value changes
		if (key.equals(KEY_LIST_PREFERENCE)) {
			mListPreference.setSummary("Current colour is "
					+ mListPreference.getEntry().toString());
		}
	}
}
