package ca.ualberta.cs.rtwong_notes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


public class ToDoList implements Serializable{
	
	protected ArrayList<ToDo> todoList;
	protected ArrayList<ToDo> archivedList;
	protected int[] summaryList;
	
	
	
	// summaryList is populated with the summary stats in the following order:
	// ToDos Checked
	// ToDos Unchecked
	// total Archived
	// Archived Checked
	// Archived Unchecked
	public ToDoList() {
		todoList = new ArrayList<ToDo>();
		archivedList = new ArrayList<ToDo>();
		summaryList = new int[5];
		int[] summaryList = {0,0,0,0,0};
	}
	
	// returns summary stats in order of: ToDoChecked, ToDoUnchecked, totalArchived, archivedChecked, archivedUnchecked
	public int[] getSummary() {
		return summaryList;
	}
	
	// returns all current ToDos
	public ArrayList<ToDo> getToDos() {
		return todoList;
	}
	
	// returns all archived ToDos
	public ArrayList<ToDo> getArchived() {
		return archivedList;
	}
	
	// adds a new ToDo to current ToDos
	public void addToDo (ToDo testToDo) {
		todoList.add(testToDo);
		// increment Unchecked ToDos
		summaryList[1] += 1;
	}
	
	// removes ToDos from current
	public void removeToDo (ToDo testToDo) {
		todoList.remove(testToDo);
		if (testToDo.getChecked()) {
			// if checked, decrement todoChecked counter
			summaryList[0] -= 1;
		}
		else {
			// if unchecked, decrement todoUnchecked counter
			summaryList[1] -= 1;
		}
	}
	
	// removes ToDos from archived
	public void removeArchived (ToDo testToDo) {
		archivedList.remove(testToDo);
		// decrement totalArchived counter
		summaryList[2] -= 1;
		if (testToDo.getChecked()) {
			// if checked, decrement archivedChecked counter
			summaryList[3] -= 1;
		}
		else {
			// if unchecked, decrement archivedUnchecked counter
			summaryList[4] -= 1;
		}
	}
	
	// moves ToDo from current to archived
	public void archiveToDo (ToDo testToDo) {
		todoList.remove(testToDo);
		archivedList.add(testToDo);
		// increment totalArchived counter
		summaryList[2] += 1;
		if (testToDo.getChecked()) {
			// if checked, decrement todoChecked and increment archivedChecked
			summaryList[0] -= 1;
			summaryList[3] += 1;
		}
		else {
			// if unchecked, decrement todoUnchecked and increment archivedUnchecked
			summaryList[1] -= 1;
			summaryList[4] += 1;
		}
	}
	
	// moves ToDo from archived to current
	public void unarchiveToDo (ToDo testToDo) {
		archivedList.remove(testToDo);
		todoList.add(testToDo);
		// decrement totalArchived counter
		summaryList[2] -= 1;
		if (testToDo.getChecked()) {
			// if checked, increment todoChecked and decrement archivedChecked
			summaryList[0] += 1;
			summaryList[3] -= 1;
		}
		else {
			// if unchecked, increment todoUnchecked and decrement archivedUnchecked
			summaryList[1] += 1;
			summaryList[4] -= 1;
		}
	}
	
	public void toggleCheckToDo(ToDo testToDo) {
		testToDo.toggleChecked();
		if (testToDo.getChecked()) {
			// it is already checked, we are unchecking it, decrement todoChecked and increment todoUnchecked
			summaryList[0] -= 1;
			summaryList[1] += 1;
		}
		else {
			// it is unchecked, we are checking it, increment todoChecked and decrement todoUnchecked
			summaryList[0] += 1;
			summaryList[1] -= 1;
		}
		
	}
	
	
	
}
