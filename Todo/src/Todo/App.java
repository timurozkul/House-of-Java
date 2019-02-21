package Todo;

public class App {
	public static void main(String[] args) {
		TodoDAO dao = new TodoDAO();
		dao.loadTodo();
		GUI gui = new GUI();
		gui.init();
	}
}
