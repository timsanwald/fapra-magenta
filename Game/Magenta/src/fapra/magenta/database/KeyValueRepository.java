package fapra.magenta.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fapra.magenta.database.KeyValueContract.KeyValue;

public class KeyValueRepository {
	private SQLiteDatabase readDb;
	private SQLiteDatabase writeDb;

	public KeyValueRepository(Context context) {
		KeyValueDbHelper dbHelper = new KeyValueDbHelper(context);
		this.readDb = dbHelper.getReadableDatabase();
		this.writeDb = dbHelper.getWritableDatabase();
	}

	public String getValue(String key) throws Exception {
		String[] projection = { KeyValue.COLUMN_NAME_VALUE };
		String selection = KeyValue.COLUMN_NAME_KEY + " = ?";
		String[] selectionArgs = { key };

		Cursor c = readDb.query(KeyValue.TABLE_NAME, projection, selection,
				selectionArgs, null, null, null);

		if(c.moveToFirst()) {
			do {
				return c.getString(c.getColumnIndex(KeyValue.COLUMN_NAME_VALUE));
			} while(c.moveToNext());
		}
		
		throw new Exception("Key: " + key + " not found");
	}
	
	public void setValue(String key, String value) {
		ContentValues values = new ContentValues();
		values.put(KeyValue.COLUMN_NAME_KEY, key);
		values.put(KeyValue.COLUMN_NAME_VALUE, value);
		
		try {
			this.getValue(key);			
			//key found --> update
			
			String where = KeyValue.COLUMN_NAME_KEY + " = ?";
			String[] whereArgs = { key };
			
			writeDb.update(KeyValue.TABLE_NAME, values, where, whereArgs);
			
		} catch (Exception e) {
			// key not found --> insert
			this.writeDb.insert(KeyValue.TABLE_NAME, null, values);
		}
	}
}
