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
import android.view.View;

public class About extends Activity {
	@Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.about);
	      View mlayout = findViewById(R.id.about_content);
	      mlayout.setBackgroundResource(R.color.background);
	      mlayout.setBackgroundColor(UnixFortuneActivity.BgColour);
	   }
}
