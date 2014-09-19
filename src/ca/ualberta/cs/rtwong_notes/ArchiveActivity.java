package ca.ualberta.cs.rtwong_notes;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ArchiveActivity extends ActionBarActivity {
	
	private ToDoList todoList;
	
	private ArrayAdapter<ToDo> archiveViewAdapter;
	
	private ListView archiveListView;
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_archive);
		
        archiveListView = (ListView) findViewById(R.id.ArchivedTodoList);
		Intent i = getIntent();
		todoList = (ToDoList)i.getSerializableExtra("EXTRA_DATA");
	}
	
    @Override
    protected void onStart() {
    	super.onStart();
    	archiveViewAdapter = new ArrayAdapter<ToDo>(this, R.layout.list_item, todoList.getArchived()) {
		};
    	archiveListView.setAdapter(archiveViewAdapter);
    }
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.archive, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}
}
