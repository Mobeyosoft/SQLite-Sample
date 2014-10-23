package com.mobeyosoft.addlistsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends Activity {
	EditText edtTitle;
	EditText edtDescription;
	DBHelper mydb;
	String title;
	String description;
	int no = 0;
	int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_edit);
		initComponents();
		setValues();
	}

	private void initComponents(){
		edtTitle = (EditText)findViewById(R.id.edtTitle);
		edtDescription = (EditText)findViewById(R.id.edtDescription);
		mydb = new DBHelper(this);
		id = getIntent().getIntExtra("id", 0);
		title = getIntent().getStringExtra("title");
		description = getIntent().getStringExtra("description");
	}

	private void setValues(){
		edtTitle.setText(title);
		edtDescription.setText(description);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Edit");
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
			mydb.updateList(edtTitle.getText().toString(), edtDescription.getText().toString(), id);
			Toast.makeText(EditActivity.this,"updated successfully", Toast.LENGTH_SHORT).show();	
			setResult(2);
			finish();
			break;
		}
		return false;
	}

}
