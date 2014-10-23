package com.mobeyosoft.addlistsample;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity{
	ListView listView;
	DBHelper mydb;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initComponents();
		showlist();
	}

	private void initComponents(){
		listView = (ListView) findViewById(R.id.listView);
		mydb = new DBHelper(this);
	}

	private void showlist() {
		ArrayList< HashMap<String, Object> >  list_to_do = mydb.selectToDoList();

		MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(MainActivity.this, list_to_do);
		listView.setAdapter(adapter);
	}

	public class MySimpleArrayAdapter extends ArrayAdapter<String> {
		private final Context context;
		ArrayList<HashMap<String, Object>> array_list;

		public MySimpleArrayAdapter(Context context, ArrayList<HashMap<String, Object>> array_list) {
			super(context, R.layout.list_row_item);
			this.context = context;
			this.array_list = array_list;
		}

		@Override
		public int getCount() {
			return 	array_list.size();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView;
			rowView = inflater.inflate(R.layout.list_row_item, parent, false);
			TextView txtTitle = (TextView)rowView.findViewById(R.id.txtTitle);
			TextView txtDescription = (TextView)rowView.findViewById(R.id.txtDescription);
			Button btnEdit = (Button)rowView.findViewById((R.id.btnEdit));
			Button btnDelete = (Button)rowView.findViewById((R.id.btnDelete));

			HashMap<String, Object> map = array_list.get(position);
			String title = (String) map.get("title"); 
			String description = (String) map.get("description"); 
			txtTitle.setText(title);
			txtDescription.setText(description);

			btnEdit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					HashMap<String, Object> map = array_list.get(position);
					String title = (String) map.get("title"); 
					String description = (String) map.get("description");
					int rowId = (Integer) map.get("id");

					Intent i= new Intent(MainActivity.this, EditActivity.class);
					i.putExtra("id", rowId);
					i.putExtra("title", title);
					i.putExtra("description", description);
					startActivityForResult(i, 1);	

				}
			});

			btnDelete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					HashMap<String, Object> map = array_list.get(position);

					int rowId = (Integer) map.get("id");
					deleteValues(rowId);
				}
			});

			return rowView;
		}
	} 

	private void deleteValues(int rowId){
		mydb.deleteToDoValues( rowId);
		showlist();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId() == R.id.action_add){
			Intent i= new Intent(MainActivity.this, AddToDoActivity.class);
			startActivityForResult(i, 1);	
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode==1 && resultCode==2){
			showlist();
		}
	}
}
