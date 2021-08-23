/**
 * Copyright (c) 2011 john Selmys.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
**/
package ca.lotuspond.unixfortune;

import android.provider.BaseColumns;

public interface Constants extends BaseColumns {
	public static final int DATABASE_VERSION = 3;
	public static final String TABLE_NAME = "all";
	public static final String DATABASE_NAME = "fortune.jpg";
	public static final String DB_PATH = "/data/data/ca.lotuspond.unixfortune/databases/";
}
