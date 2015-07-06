package fapra.magenta.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KeyValueDbHelper extends SQLiteOpenHelper{
	    public static final int DATABASE_VERSION = 1;
	    public static final String DATABASE_NAME = "magenta2.db";

	    public KeyValueDbHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	    
	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(KeyValueContract.SQL_CREATE_KEY_VALUE);
	    }
	    
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // delete table and recreate it...
	        db.execSQL(KeyValueContract.SQL_DELETE_KEY_VALUE);
	        onCreate(db);
	    }
	    
	    @Override
	    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        onUpgrade(db, oldVersion, newVersion);
	    }
	}
