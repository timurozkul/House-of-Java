package Todo;

import java.util.Date;

public class TodoEntry {
	private int id;
	private String title;
	private Date dueDate;
	private Boolean completed;

	public TodoEntry() {};
	
	public TodoEntry(int id, String title, Date dueDate) {
		super();
		this.id = id;
		this.title = title;
		this.dueDate = dueDate;
		this.completed = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	


}
