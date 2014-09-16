package ca.ualberta.cs.rtwong_notes;

import java.util.ArrayList;
import java.util.Collection;

public class ToDoList {
	
	protected ArrayList<ToDo> todoList;
	protected ArrayList<ToDo> archivedList;
	protected ArrayList<Integer> summaryList;
	protected Integer totalToDoChecked;
	protected Integer totalToDoUnchecked;
	protected Integer totalArchived;
	protected Integer archivedChecked;
	protected Integer archivedUnchecked;
	
	
	
	
	public ToDoList() {
		todoList = new ArrayList<ToDo>();
		archivedList = new ArrayList<ToDo>();
		summaryList = new ArrayList<Integer>();
		totalToDoChecked = 0;
		totalToDoUnchecked = 0;
		totalArchived = 0;
		archivedChecked = 0;
		archivedUnchecked = 0;
		summaryList.add(totalToDoChecked);
		summaryList.add(totalToDoUnchecked);
		summaryList.add(totalArchived);
		summaryList.add(archivedChecked);
		summaryList.add(archivedUnchecked);

	}
	
	// returns summary stats in order of: totalToDoChecked, totalToDoUnchecked, totalArchived, archivedChecked, archivedUnchecked
	public ArrayList<Integer> getSummary() {
		return summaryList;
	}
	
	// returns all current ToDos
	public Collection<ToDo> getToDos() {
		return todoList;
	}
	
	// returns all archived ToDos
	public Collection<ToDo> getArchived() {
		return archivedList;
	}
	
	// adds a new ToDo to current ToDos
	public void addToDo (ToDo testToDo) {
		todoList.add(testToDo);
	}
	
	// removes ToDos from current or archived
	public void removeToDo (ToDo testToDo, ArrayList<ToDo> List) {
		List.remove(testToDo);
	}
	
	// moves ToDo from current to archived
	public void archiveToDo (ToDo testToDo) {
		todoList.remove(testToDo);
		archivedList.add(testToDo);
	}
	
	// moves ToDo from archived to current
	public void unarchiveToDo (ToDo testToDo) {
		archivedList.remove(testToDo);
		todoList.add(testToDo);
	}
	
	public void checkToDo(ToDo testToDo) {
		testToDo.toggleChecked();
		
	}
	
	
	
}
