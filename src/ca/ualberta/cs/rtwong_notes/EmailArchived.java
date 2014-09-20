package ca.ualberta.cs.rtwong_notes;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// controller for Archive view for emailing
public class EmailArchived extends ActionBarActivity {

	private DataManager dataManager;
	
	private ToDoList todoList;
	
	private ArrayAdapter<ToDo> archiveViewAdapter;
	
	private ListView archiveListView;
	
	private ArrayList<ToDo> selectedList;
	
	private String emailInput[];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		emailInput = intent.getStringArrayExtra("EXTRA_MESSAGE");
		setContentView(R.layout.activity_email_archived);
		archiveListView = (ListView) findViewById(R.id.emailfromarchivelist);
	}
	
    @Override
    protected void onStart() {
    	super.onStart();
    	dataManager = new DataManager(getApplicationContext());
    	todoList = this.loadToDoList();
    	archiveViewAdapter = new ArrayAdapter<ToDo>(this, R.layout.list_item, todoList.getArchived());
    	archiveListView.setAdapter(archiveViewAdapter);
    	selectedList = new ArrayList<ToDo>();
    	// listener waiting for clicks
        archiveListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
				ToDo todo = archiveViewAdapter.getItem(adapter.getPositionForView(v));
				// uses a new arraylist to track if the selected item has been selected before
				// if not, copy to selected list and highlight
				// if already selected, delete from selected list and unhighlight
				if (selectedList.contains(todo)) {
					selectedList.remove(todo);
					v.setBackgroundColor(Color.TRANSPARENT);
				}
				else {
					selectedList.add(todo);
					v.setBackgroundColor(Color.LTGRAY);
				}
			}
        });
    }
    
    // send the intent to email, same structure as before
    public void sendSelected(View v) {
    	String emailTo[] = emailInput;
    	if (!(emailTo[0].isEmpty())) {
    		Intent i = new Intent(Intent.ACTION_SEND);
    		i.setType("plain/text");
    		i.putExtra(android.content.Intent.EXTRA_EMAIL, emailTo);
    		i.putExtra(android.content.Intent.EXTRA_SUBJECT, "rtwong-notes ToDo's");
    		String emailbody = "Archived To Do's:\n";
    		String temp = "";
    		for (int looptemp = 0; looptemp < (selectedList.size()); looptemp++) {
    			temp = selectedList.get(looptemp).toString();
    			temp += "\n";
    			emailbody += temp;
    		}
    		i.putExtra(android.content.Intent.EXTRA_TEXT, emailbody);
    		startActivity(Intent.createChooser(i, "Send"));
    	}
    }
	public ToDoList loadToDoList() {
		return dataManager.load();
	}
}
