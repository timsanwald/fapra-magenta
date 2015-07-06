package fapra.magenta.database;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fapra.magenta.database.LinesContract.LinesEntry;

public class LinesRepository {
	private SQLiteDatabase readDb;
	private SQLiteDatabase writeDb;

	public LinesRepository(Context context) {
		LinesDbHelper dbHelper = new LinesDbHelper(context);
		this.readDb = dbHelper.getReadableDatabase();
		this.writeDb = dbHelper.getWritableDatabase();
	}

	public void insertLine(String json) {
		ContentValues values = new ContentValues();
		values.put(LinesEntry.COLUMN_NAME_JSON, json);

		writeDb.insert(LinesEntry.TABLE_NAME, null, values);
	}

	public ArrayList<HashMap<String, String>> getLines(String id) {
		String[] projection = { LinesEntry.COLUMN_NAME_JSON, LinesEntry._ID };
		String selection = LinesEntry._ID + " > ?";
		String[] selectionArgs = { id };

		Cursor c = readDb.query(LinesEntry.TABLE_NAME, projection, selection,
				selectionArgs, null, null, LinesEntry._ID + " ASC");
		
		ArrayList<HashMap<String, String>> lines = new ArrayList<HashMap<String,String>>();
		if (c.moveToFirst()) {
			do {
				HashMap<String, String> line = new HashMap<String, String>();
				line.put("id", c.getString(c.getColumnIndex(LinesEntry._ID)));
				line.put("json", c.getString(c.getColumnIndex(LinesEntry.COLUMN_NAME_JSON)));
				lines.add(line);
			} while (c.moveToNext());
		}

		return lines;
	}
}
