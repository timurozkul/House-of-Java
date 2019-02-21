package Todo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class TodoDAO {
	public static final String FILENAME = "todolist.txt";
	public static final String DELIMITER = "::";
	TodoList todoList = new TodoList();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public void loadTodo() {
		Scanner read;
		try {		
			read = new Scanner(new BufferedReader(new FileReader(FILENAME)));
			
			while(read.hasNext()) {
				String recordLine = read.nextLine();
				String[] recordProperties = recordLine.split(DELIMITER);
					
				if(recordProperties.length == 4){
					TodoEntry todoEntry = new TodoEntry();
					
					todoEntry.setId(Integer.parseInt(recordProperties[0]));
					todoEntry.setTitle(recordProperties[1]);
					Date date = df.parse(recordProperties[2]);
					todoEntry.setDueDate(date);
					todoEntry.setCompleted(Boolean.parseBoolean(recordProperties[3]));
					todoList.addEntry(todoEntry);
				}
			}
		
		} catch (FileNotFoundException e) {
			System.out.println("Data file not found, no user input yet " + e);
		} catch (ParseException e) {
			System.out.println("Data seems to be corrupt " + e);
		}
	}
	
	public void writeTodo() {
		try {
			PrintWriter write = new PrintWriter(new FileWriter(FILENAME));
			TodoList todoList = new TodoList();
			Map todoListHM = todoList.getTodoList();
	
			for(Object key: todoList.getTodoList().keySet()) {
				TodoEntry currEntry = (TodoEntry) todoListHM.get(key);
	
				write.println(currEntry.getId() + DELIMITER
						+ currEntry.getTitle() + DELIMITER
						+ df.format(currEntry.getDueDate()) + DELIMITER
						+ Boolean.toString(currEntry.getCompleted())
						);	
			}
			write.flush();
			write.close();
			
		} catch(IOException exception) {
			System.out.println("Something went wrong");
		}
	}
}
