package fapra.magenta.database;

import android.provider.BaseColumns;

public final class LinesContract {
	public LinesContract() {
	}

	public static abstract class LinesEntry implements BaseColumns {
		public static final String TABLE_NAME = "lines";
		public static final String COLUMN_NAME_JSON = "json";
	}
	
	private static final String TEXT_TYPE = " TEXT";
	@SuppressWarnings("unused")
	private static final String COMMA_SEP = ",";
	public static final String SQL_CREATE_LINES =
	    "CREATE TABLE " + LinesEntry.TABLE_NAME + " (" +
	    LinesEntry._ID + " INTEGER PRIMARY KEY," +
	    LinesEntry.COLUMN_NAME_JSON + TEXT_TYPE + 
	    " )";

	public static final String SQL_DELETE_LINES =
	    "DROP TABLE IF EXISTS " + LinesEntry.TABLE_NAME;
}
