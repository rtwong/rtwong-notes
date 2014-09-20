package ca.ualberta.cs.rtwong_notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

// email activity controller
public class EmailActivity extends Activity {

	private EditText sendAddress;
	
	private ToDoList todoList;
	
	private DataManager dataManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email);
		sendAddress = (EditText) findViewById(R.id.emailsendto);
	}

    @Override
    protected void onStart() {
    	super.onStart();
    	dataManager = new DataManager(getApplicationContext());
    	todoList = this.loadToDoList();
    }
	
	public ToDoList loadToDoList() {
		return dataManager.load();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.email, menu);
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
        case R.id.switchSummary:
        	Intent summaryIntent = new Intent(this, SummaryActivity.class);
        	startActivity(summaryIntent);
        	return true;
        default:
        	return super.onOptionsItemSelected(item);
        }
	}
	
	// NOTE: Email functions only work if the app has already authenticated an email account for use
	
	// Send all current and archived ToDos, some string manipulation and loops to format email body
    public void sendAll(View v) {
    	String emailTo[] = { sendAddress.getText().toString() };
    	if (!(emailTo[0].isEmpty())) {
    		Intent i = new Intent(Intent.ACTION_SEND);
    		i.setType("plain/text");
    		i.putExtra(android.content.Intent.EXTRA_EMAIL, emailTo);
    		i.putExtra(android.content.Intent.EXTRA_SUBJECT, "rtwong-notes ToDo's");
    		String emailbody = "To Do's:\n";
    		String temp = "";
    		for (int looptemp = 0; looptemp < (todoList.getToDos().size()); looptemp++) {
    			temp = todoList.getToDos().get(looptemp).toString();
    			temp += "\n";
    			emailbody += temp;
    		}
    		emailbody += "Archived To Do's:\n";
    		for (int looptemp = 0; looptemp < (todoList.getArchived().size()); looptemp++) {
    			temp = todoList.getArchived().get(looptemp).toString();
    			temp += "\n";
    			emailbody += temp;
    		}
    		i.putExtra(android.content.Intent.EXTRA_TEXT, emailbody);
    		sendAddress.setText("");
    		startActivity(Intent.createChooser(i, "Send"));
    	}
    }
	// Send all current ToDos, similar to above
    public void sendCurrent(View v) {
    	String emailTo[] = { sendAddress.getText().toString() };
    	if (!(emailTo[0].isEmpty())) {
    		Intent i = new Intent(Intent.ACTION_SEND);
    		i.setType("plain/text");
    		i.putExtra(android.content.Intent.EXTRA_EMAIL, emailTo);
    		i.putExtra(android.content.Intent.EXTRA_SUBJECT, "rtwong-notes ToDo's");
    		String emailbody = "To Do's:\n";
    		String temp = "";
    		for (int looptemp = 0; looptemp < (todoList.getToDos().size()); looptemp++) {
    			temp = todoList.getToDos().get(looptemp).toString();
    			temp += "\n";
    			emailbody += temp;
    		}
    		i.putExtra(android.content.Intent.EXTRA_TEXT, emailbody);
    		sendAddress.setText("");
    		startActivity(Intent.createChooser(i, "Send"));
    	}
    }
    
    // Starts a new activity to display all current ToDos to select from, also sends the entered e-mail
    // since the next activity has to send the intent to email apps
	public void selectCurrent(View v) {
    	String emailTo[] = { sendAddress.getText().toString() };
    	if (!(emailTo[0].isEmpty())) {
    		Intent i = new Intent(this, EmailCurrent.class);
    		i.putExtra("EXTRA_MESSAGE", emailTo);
    		startActivity(i);
    	}
	}
	// same as above, but for archived items
	public void selectArchived(View v) {
    	String emailTo[] = { sendAddress.getText().toString() };
    	if (!(emailTo[0].isEmpty())) {
    		Intent i = new Intent(this, EmailArchived.class);
    		i.putExtra("EXTRA_MESSAGE", emailTo);
    		startActivity(i);
    	}
	}
	
    
    
    
    
	
	
}
