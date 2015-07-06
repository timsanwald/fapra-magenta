package fapra.magenta.database;

import android.provider.BaseColumns;

public final class KeyValueContract {
	public KeyValueContract() {
	}

	public static abstract class KeyValue implements BaseColumns {
		public static final String TABLE_NAME = "keyValueStore";
		public static final String COLUMN_NAME_KEY = "key";
		public static final String COLUMN_NAME_VALUE = "value";
	}
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	public static final String SQL_CREATE_KEY_VALUE =
	    "CREATE TABLE " + KeyValue.TABLE_NAME + " (" +
	    KeyValue._ID + " INTEGER PRIMARY KEY," + 
	    KeyValue.COLUMN_NAME_KEY + TEXT_TYPE + COMMA_SEP + 
	    KeyValue.COLUMN_NAME_VALUE + TEXT_TYPE +
	    " )";

	public static final String SQL_DELETE_KEY_VALUE =
	    "DROP TABLE IF EXISTS " + KeyValue.TABLE_NAME;
}
