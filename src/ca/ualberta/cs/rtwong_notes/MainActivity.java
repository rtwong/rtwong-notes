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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


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
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    
    
    public void archiveToDo(AdapterContextMenuInfo id) {
    	ToDo todo = todoViewAdapter.getItem(id.position);
    	todoList.archiveToDo(todo);
    	todoViewAdapter.notifyDataSetChanged();
    }
    
    public void deleteToDo(AdapterContextMenuInfo id) {
    	ToDo todo = (ToDo) todoListView.getItemAtPosition(id.position);
    	todoList.removeToDo(todo);
    	todoViewAdapter.notifyDataSetChanged();
    }
    

	public ToDoList loadToDoList() {
		return dataManager.load();
	}
	
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
