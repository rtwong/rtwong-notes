package ca.ualberta.cs.rtwong_notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// prints to a list the current stats of the app
public class SummaryActivity extends Activity {

	private DataManager dataManager;
	
	private ToDoList todoList;
	
	private ArrayAdapter<String> summaryViewAdapter;
	
	private ListView summaryListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_summary);
		summaryListView = (ListView) findViewById(R.id.summaryListView);
	}

    @Override
    protected void onStart() {
    	super.onStart();
    	dataManager = new DataManager(getApplicationContext());
    	todoList = this.loadToDoList();
    	summaryViewAdapter = new ArrayAdapter<String> (this, R.layout.list_item, todoList.getSummary());
    	summaryListView.setAdapter(summaryViewAdapter);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.summary, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();    
        switch (id) {
        case R.id.switchArchive:
        	Intent archiveIntent = new Intent(this, ArchiveActivity.class);
        	startActivity(archiveIntent);
        	return true;
        case R.id.switchToDo:
        	Intent todoIntent = new Intent(this, MainActivity.class);
        	startActivity(todoIntent);
        	return true;
        case R.id.switchEmail:
        	Intent emailIntent = new Intent(this, EmailActivity.class);
        	startActivity(emailIntent);
        	return true;
        default:
        	return super.onOptionsItemSelected(item);
        }
	}
	
	public ToDoList loadToDoList() {
		return dataManager.load();
	}
}
