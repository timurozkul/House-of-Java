package Todo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class TodoList {
	public static HashMap<Integer, TodoEntry> todoListHM = new HashMap<Integer, TodoEntry>();
	
	public int getsize() {
		return todoListHM.size();
	}
	
	public void addEntry(TodoEntry todoEntry) {
		todoListHM.put(todoEntry.getId(), todoEntry);
	}
	
	public Map getTodoList() {
		return todoListHM;
	}
	
	public TodoEntry getById(int key) {
		return todoListHM.get(key);
	}
	
	public void removeEntry(int id) {
		todoListHM.remove(id);
	}
	
	public List<TodoEntry> listOfUncompleteTodos() {
		Collection<TodoEntry> c = todoListHM.values();
		return c.stream()
				.filter(t -> t.getCompleted() == false)
				.collect(Collectors.toList());
	}
	
	public List<TodoEntry> listOfTodosByDate() {
		Collection<TodoEntry> c = todoListHM.values();
		
		return c.stream()
				.filter(t -> t.getCompleted() == false)
				.sorted((t1, t2) -> t1.getDueDate().compareTo(t2.getDueDate()))
				.collect(Collectors.toList());
	}
	
	public void completeTodoEntry(int id) {
		 TodoEntry todoEntry = todoListHM.get(id);
		 todoEntry.setCompleted(true);
	}
	
	
	public List<TodoEntry> ListofTodos() {
		 Collection<TodoEntry> c = todoListHM.values();
		 
		 return c.stream()
				 .sorted((t1, t2) -> t1.getCompleted().compareTo(t2.getCompleted()))
				 .collect(Collectors.toList());
	}
	
	
	public int getNewId() {
		Iterator<Entry<Integer, TodoEntry>> it = todoListHM.entrySet().iterator();	
		int count = 1;
		
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        int currId = todoListHM.get(pair.getKey()).getId();
	        if(currId >= 1) {
	        	count = currId;
	        }	       
	    }
	    count++;
	    return count;
	}
}


