package com.mobeyosoft.addlistsample;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper{
	public static final String DATABASE_NAME = "AddListSample.db";
	public static final String ADDLIST_TABLE_NAME = "addlist";
	public static final String ADDLIST_COLUMN_ID = "id";
	public static final String ADDLIST_COLUMN_TITLE = "title";
	public static final String ADDLIST_COLUMN_DESCRIPTION = "description";


	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {		
		db.execSQL(
				"create table  " +ADDLIST_TABLE_NAME+
				"(" + ADDLIST_COLUMN_ID + " integer primary key autoincrement, " + ADDLIST_COLUMN_TITLE + "  text," + ADDLIST_COLUMN_DESCRIPTION + " text )"
				);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}
	
	public boolean addToDoList(String title, String description)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put(ADDLIST_COLUMN_TITLE, title);
		contentValues.put(ADDLIST_COLUMN_DESCRIPTION, description);

		db.insert(ADDLIST_TABLE_NAME, null, contentValues);
		return true;
	}
	
	public boolean updateList(String title ,String description, int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put(ADDLIST_COLUMN_TITLE, title);
		contentValues.put(ADDLIST_COLUMN_DESCRIPTION, description);

		db.update(ADDLIST_TABLE_NAME, contentValues, "id = " + id, null );
		return true;
	}
	
	public Integer deleteToDoValues (Integer id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(ADDLIST_TABLE_NAME, "id = ? ",new String[] { Integer.toString(id) });
	}
	
	public Integer dropTable ()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(ADDLIST_TABLE_NAME,null,null);
	}
	
	public int numberOfRows(){
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, ADDLIST_TABLE_NAME);
		return numRows;
	}
	
	public ArrayList< HashMap<String, Object> > selectToDoList()
	{

		ArrayList<HashMap<String, Object>> addList = new ArrayList<HashMap<String, Object>>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor res =  db.rawQuery( "select * from "+ ADDLIST_TABLE_NAME + "", null );

		res.moveToFirst();
		while(res.isAfterLast() == false){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(ADDLIST_COLUMN_ID, res.getInt(res.getColumnIndex(ADDLIST_COLUMN_ID)));
			map.put(ADDLIST_COLUMN_TITLE, res.getString(res.getColumnIndex(ADDLIST_COLUMN_TITLE)));
			map.put(ADDLIST_COLUMN_DESCRIPTION, res.getString(res.getColumnIndex(ADDLIST_COLUMN_DESCRIPTION)));
			addList.add(map);
			res.moveToNext();
		}
		return addList;
	}
}

