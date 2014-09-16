package ca.ualberta.cs.rtwong_notes;

public class ToDo {
	protected String task;
	protected boolean checked;
	
	public ToDo(String testtask) {
		task = testtask;
		checked = false;
	}

	// returns the description of the task
	public String getTask() {
		return task;
	}
	
	// returns True if it is Checked or False if unchecked
	public boolean getChecked() {
		return checked;
	}
	
	// toggles between checked and unchecked
	public void toggleChecked() {
		checked = !checked;
	}
	
	
}
