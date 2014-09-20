/*
rtwong-notes: Simple ToDo list app with emailing
Copyright (C) 2014 Randy Wong
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package ca.ualberta.cs.rtwong_notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

// home activity controller, each new layout page is a new activity for simplicity
// simple functions are copied because it is easier to copy than to do an abstraction
// on a project of this scope.
public class MainActivity extends Activity {
		
	private DataManager dataManager;
	
	private ToDoList todoList;
	
	private ArrayAdapter<ToDo> todoViewAdapter;
	
	private ListView todoListView;
	
	private EditText bodyText;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.activity_main);
        bodyText = (EditText) findViewById(R.id.AddToDoText);
        todoListView = (ListView) findViewById(R.id.todoListView);
    }

    @Override
    protected void onStart() {
    	super.onStart();
    	dataManager = new DataManager(getApplicationContext());
    	todoList = this.loadToDoList();
    	todoViewAdapter = new ArrayAdapter<ToDo>(this, R.layout.list_item, todoList.getToDos()); 
    	todoListView.setAdapter(todoViewAdapter);
    	registerForContextMenu(todoListView);
    	// create listener to allow for clicks to check/uncheck lines
    	todoListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapter, View v,int position, long id) {
				ToDo todo = todoViewAdapter.getItem(adapter.getPositionForView(v));
		    	todoList.toggleCheckToDo(todo);
		    	todoViewAdapter.notifyDataSetChanged();
			}
    	});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // option menu to move to different activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
        case R.id.switchArchive:
        	Intent archiveIntent = new Intent(this, ArchiveActivity.class);
        	saveToDoList(todoList);
        	startActivity(archiveIntent);
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
   
    // Longclicks create context menus that allow user to archive or remove using GUI
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);	
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.todopopupmenu, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	switch(item.getItemId()) {
    	case R.id.sendtoarchive:
    		archiveToDo(info);
    		return true;
    	case R.id.todopopupremove:
    		deleteToDo(info);
    		return true;
    	default:
    		return super.onContextItemSelected(item);
    	}
    }
   
    // archive todo and refresh UI
    public void archiveToDo(AdapterContextMenuInfo id) {
    	ToDo todo = todoViewAdapter.getItem(id.position);
    	todoList.archiveToDo(todo);
    	this.saveToDoList(todoList);
    	todoViewAdapter.notifyDataSetChanged();
    }
    
    // delete todo and refresh UI
    public void deleteToDo(AdapterContextMenuInfo id) {
    	ToDo todo = (ToDo) todoListView.getItemAtPosition(id.position);
    	todoList.removeToDo(todo);
    	this.saveToDoList(todoList);
    	todoViewAdapter.notifyDataSetChanged();
    }
    
    // load the ToDoList interface
	public ToDoList loadToDoList() {
		return dataManager.load();
	}
	
	// save the ToDoList interface
	public void saveToDoList(ToDoList todoList) {
		dataManager.save(todoList);
	}

    
    
    
    
    public void addToDo(View v) {
    	
    	String text = bodyText.getText().toString();
    	if (!text.isEmpty()) {
        	ToDo todo = new ToDo(text);
        	todoList.addToDo(todo);
        	todoViewAdapter.notifyDataSetChanged();
        	bodyText.setText("");
        	this.saveToDoList(todoList);
    	}
    }
    
    
}
