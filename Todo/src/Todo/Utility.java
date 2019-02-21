package Todo;

import java.awt.Image;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;

public class Utility {
	TodoList todoList = new TodoList();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public void addTodoEntry(int id, String title, String dueDate) {
		
		Date formattedDate = DateValidator(dueDate);
		if(dueDate == null) {
			return;
		}

		TodoEntry todoEntry = new TodoEntry(id, title, formattedDate);
		todoList.addEntry(todoEntry);
	}

	
	public boolean validateTextFields(String title, String dueDate) {
		if(title.length() > 0 && DateValidator(dueDate) != null) {
			return true;
		}
	
		return false;
	}
	
	public Date DateValidator(String dateToValidate) {
		Date date = null;
		if (dateToValidate != null) {
			dateFormat.setLenient(false);

			try {
				// if not valid, it will throw ParseException
				date = dateFormat.parse(dateToValidate);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
	
	public static ImageIcon scaleImage(ImageIcon originalImage) {
		Image image = originalImage.getImage();
		Image iconCancelScaled = image.getScaledInstance(15, 15, Image.SCALE_DEFAULT);
		return new ImageIcon(iconCancelScaled);
	}
}
