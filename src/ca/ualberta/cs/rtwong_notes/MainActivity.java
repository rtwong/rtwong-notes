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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends Activity {
		
	private static final String FILENAME = "rtwongfile.sav";
	
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
    	
    	todoList = this.loadToDoList();
    	todoViewAdapter = new ArrayAdapter<ToDo>(this, R.layout.list_item, todoList.getToDos() ) {
		};
    	todoListView.setAdapter(todoViewAdapter);
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
        	Intent intent = new Intent(this, ArchiveActivity.class);
        	intent.putExtra("EXTRA_DATA", todoList);
        	startActivity(intent);
        	return true;
        
        default:
        	return super.onOptionsItemSelected(item);
        }
    }
   
	public ToDoList loadToDoList() {
		ToDoList todoList = new ToDoList();

		try {
			FileInputStream fis = new FileInputStream(FILENAME);
			ObjectInputStream ois = new ObjectInputStream(fis);

			todoList = (ToDoList) ois.readObject();
			fis.close();
			ois.close();

		} catch (Exception e) {
			Log.i("rtwong-notes", "Error casting");
			e.printStackTrace();
		} 

		return todoList;
	}
	
	public void saveToDoList(ToDoList todoList) {
		try {
			FileOutputStream fos = new FileOutputStream(FILENAME);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(todoList);
			fos.close();
			oos.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

    
    
    
    
    public void addToDo(View v) {
    	
    	String text = bodyText.getText().toString();
    	ToDo todo = new ToDo(text);
    	todoList.addToDo(todo);
    	todoViewAdapter.notifyDataSetChanged();
    	bodyText.setText("");
    
    }
    
    
}
