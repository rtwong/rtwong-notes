package ca.ualberta.cs.rtwong_notes;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ArchiveActivity extends Activity {
	
	private ToDoList todoList;
	
	private ArrayAdapter<ToDo> archiveViewAdapter;
	
	private ListView archiveListView;
	
	private DataManager dataManager;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_archive);
		
        archiveListView = (ListView) findViewById(R.id.ArchivedTodoList);
	}
	
    @Override
    protected void onStart() {
    	super.onStart();
    	dataManager = new DataManager(getApplicationContext());
    	todoList = this.loadToDoList();
    	archiveViewAdapter = new ArrayAdapter<ToDo>(this, R.layout.list_item, todoList.getArchived());
    	archiveListView.setAdapter(archiveViewAdapter);
    	registerForContextMenu(archiveListView);
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
        
        switch (id) {
        case R.id.switchToDo:
        	Intent todoIntent = new Intent(this, MainActivity.class);
        	saveToDoList(todoList);
        	startActivity(todoIntent);
        	return true;
        	
        case R.id.switchSummary:
        	Intent summaryIntent = new Intent(this, SummaryActivity.class);
        	saveToDoList(todoList);
        	startActivity(summaryIntent);
        	return true;
    
        case R.id.switchEmail:
        	Intent emailIntent = new Intent(this, EmailActivity.class);
        	saveToDoList(todoList);
        	startActivity(emailIntent);
        	return true;
        	
        default:
        	return super.onOptionsItemSelected(item);
        }
	}
	
	
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.archivepopupmenu, menu);
    	
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	switch(item.getItemId()) {
    	case R.id.sendtotodo:
    		moveToDo(info);
    		return true;
    	case R.id.archivepopupremove:
    		deleteToDo(info);
    		return true;
    	default:
    		return super.onContextItemSelected(item);
    	}
    }
	
    public void moveToDo(AdapterContextMenuInfo id) {
    	ToDo todo = archiveViewAdapter.getItem(id.position);
    	todoList.unarchiveToDo(todo);
    	archiveViewAdapter.notifyDataSetChanged();
    }
    
    public void deleteToDo(AdapterContextMenuInfo id) {
    	ToDo todo = (ToDo) archiveListView.getItemAtPosition(id.position);
    	todoList.removeArchived(todo);
    	archiveViewAdapter.notifyDataSetChanged();
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ToDoList loadToDoList() {
		return dataManager.load();
	}
	
	public void saveToDoList(ToDoList todoList) {
		dataManager.save(todoList);
	}
	
	
	
	
	
	
	
	
}
