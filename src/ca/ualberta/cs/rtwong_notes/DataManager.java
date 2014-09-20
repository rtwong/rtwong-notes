package ca.ualberta.cs.rtwong_notes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class DataManager extends MainActivity {

	private static final String FILENAME = "rtwongfile.sav";
	
	private Context context;
	
	public DataManager(Context appContext) {
		context = appContext;
	}
	
		public ToDoList load() {
			ToDoList todoList = new ToDoList();

			try {
				FileInputStream fis = context.openFileInput(FILENAME);
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
		
		public void save(ToDoList todoList) {
			try {
				FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(todoList);
				fos.close();
				oos.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}

	
	
}
