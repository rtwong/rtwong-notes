package ca.ualberta.cs.rtwong_notes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


public class ToDoList implements Serializable{
	
	protected ArrayList<ToDo> todoList;
	protected ArrayList<ToDo> archivedList;
	protected int[] summaryList;
	protected ArrayList<String> printList;
	
	
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
	
	// returns summary stats in order of: todoChecked, todoUnchecked, totalArchived, archivedChecked, archivedUnchecked
	public ArrayList<String> getSummary() {
		String todoChecked = "Checked off ToDo's: ";
		String todoUnchecked = "Unchecked ToDo's: ";
		String totalArchived = "Total ToDo's in Archive: ";
		String archivedChecked = "Checked off ToDo's in Archive:";
		String archivedUnchecked = "Unchecked ToDo's in Archive: ";
		printList = new ArrayList<String>();
		
		todoChecked += String.valueOf(summaryList[0]);
		printList.add(todoChecked);
		
		todoUnchecked += String.valueOf(summaryList[1]);
		printList.add(todoUnchecked);
		
		totalArchived += String.valueOf(summaryList[2]);
		printList.add(totalArchived);
		
		archivedChecked += String.valueOf(summaryList[3]);
		printList.add(archivedChecked);
		
		archivedUnchecked += String.valueOf(summaryList[4]);
		printList.add(archivedUnchecked);
		
		return printList;
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
