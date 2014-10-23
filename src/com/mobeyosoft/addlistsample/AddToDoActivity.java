package com.mobeyosoft.addlistsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddToDoActivity extends Activity{

	EditText edtTitle;
	EditText edtDescription;
	DBHelper mydb;
	String title;
	String description;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_add);
		initComponents();
		
	}
	
	private void initComponents(){
		edtTitle = (EditText)findViewById(R.id.edtTitle);
		edtDescription = (EditText)findViewById(R.id.edtDescription);
		mydb = new DBHelper(this);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Add To Do");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu_save, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
			
		case R.id.action_save:
			mydb.addToDoList( edtTitle.getText().toString(), edtDescription.getText().toString() );
			Toast.makeText(AddToDoActivity.this,"save successfully", Toast.LENGTH_SHORT).show();	
			setResult(2);
			finish();
			break;
		
		}
		return false;
	}
}

