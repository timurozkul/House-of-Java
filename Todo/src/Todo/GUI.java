package Todo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {
	private JLabel title, labelTitle, labelDate, misc;
	private JTextField fieldNameTitle, fieldNameDate;

	JPanel mainPanel = new JPanel();
	JPanel northPanel = new JPanel(new FlowLayout());
	JPanel centerPanel = new JPanel();
	JPanel todoListPanel = new JPanel();
	Utility utility = new Utility();
	JScrollPane scrollPanel;
	TodoList todoList = new TodoList();

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	ImageIcon iconCancel = Utility.scaleImage(new ImageIcon(this.getClass().getResource("/images/cancel.png")));
	ImageIcon iconCheck = Utility.scaleImage(new ImageIcon(this.getClass().getResource("/images/checked.png")));

	public GUI() {
		createView();

		setTitle("Todo List Omatron");
		setSize(430, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void createView() {
		mainPanel.setLayout(new BorderLayout());
		getContentPane().add(mainPanel);
		scrollPanel = new JScrollPane(todoListPanel);
		scrollPanel.setBorder(BorderFactory.createMatteBorder(30, 0, 0, 0, UIManager.getColor("Panel.background")));

		todoListPanel.setPreferredSize(new Dimension(400, 380));
		todoListPanel.setLayout(new BoxLayout(todoListPanel, BoxLayout.Y_AXIS));
		northPanel.setPreferredSize(new Dimension(100, 90));

		mainPanel.add(northPanel, BorderLayout.NORTH);
		title = new JLabel("<html><h2>Todo List Omatron</h2></html>");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setPreferredSize(new Dimension(400, 50));

		northPanel.add(title, BorderLayout.NORTH);

		labelTitle = new JLabel("Title");

		northPanel.add(labelTitle);

		fieldNameTitle = new JTextField();
		fieldNameTitle.setPreferredSize(new Dimension(100, 30));

		northPanel.add(fieldNameTitle);

		labelDate = new JLabel("Due Date");
		northPanel.add(labelDate);

		fieldNameDate = new JTextField();
		fieldNameDate.setPreferredSize(new Dimension(100, 30));
		fieldNameDate.setForeground(Color.GRAY);
		fieldNameDate.setText("yyyy-mm-dd");
		fieldNameDate.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (fieldNameDate.getText().equals("yyyy-mm-dd")) {
					fieldNameDate.setText("");
					fieldNameDate.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (fieldNameDate.getText().isEmpty()) {
					fieldNameDate.setForeground(Color.GRAY);
					fieldNameDate.setText("yyyy-mm-dd");
				}
			}
		});

		northPanel.add(fieldNameDate);

		JButton button = new JButton("Add todo");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = fieldNameTitle.getText();
				String dueDate = fieldNameDate.getText();

				if (utility.validateTextFields(title, dueDate)) {
					int id = todoList.getNewId();
					utility.addTodoEntry(id, title, dueDate);

					fieldNameTitle.setText("");
					fieldNameDate.setText("");

					printNewTodo(id);
				}
			}
		});
		northPanel.add(button);

		printTodos();
	}

	public void printNewTodo(int id) {
		TodoEntry lastEntry = todoList.getById(id);
		todoListPanel.add(setTodoEntryPanel(lastEntry));
		scrollPanel.revalidate();
	}

	public void printTodos() {
		List<TodoEntry> todoEntries = todoList.ListofTodos();

		for (TodoEntry t : todoEntries) {
			todoListPanel.add(setTodoEntryPanel(t));
		}

		mainPanel.add(scrollPanel);
	}

	public JPanel setTodoEntryPanel(TodoEntry todoEntry) {
		JPanel todoEntryPanel = new JPanel();

		todoEntryPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		todoEntryPanel.setMaximumSize(new Dimension(400, 45));
		todoEntryPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

		title = new JLabel(todoEntry.getTitle());
		title.setFont(new Font("Sans", Font.BOLD, 18));

		misc = new JLabel(
				dateFormat.format(todoEntry.getDueDate()) + "  Completed: " + String.valueOf(todoEntry.getCompleted()),
				SwingConstants.CENTER);
		misc.setFont(new Font("Sans", Font.PLAIN, 11));
		misc.setPreferredSize(new Dimension(380, 20));

		JLabel cancelLabel = new JLabel(iconCancel);
		JLabel checkLabel = new JLabel(iconCheck);
		checkLabel.setBorder(new EmptyBorder(3, 0, 0, 8));
		cancelLabel.setBorder(new EmptyBorder(3, 8, 0, 0));

		todoEntryPanel.setName(String.valueOf(todoEntry.getId()));

		checkLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int id = getId(e);

				todoList.completeTodoEntry(id);

				reprintTodo();
			}
		});

		cancelLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int id = getId(e);

				todoList.removeEntry(id);

				reprintTodo();
			}
		});

		todoEntryPanel.add(checkLabel);
		todoEntryPanel.add(title);
		todoEntryPanel.add(cancelLabel);
		todoEntryPanel.add(misc);
		return todoEntryPanel;
	}

	public void reprintTodo() {
		todoListPanel.removeAll();
		printTodos();
		todoListPanel.validate();
		todoListPanel.repaint();
	}

	public int getId(MouseEvent e) {
		JLabel panelx = (JLabel) e.getSource();
		JPanel parent = (JPanel) panelx.getParent();

		return Integer.parseInt(parent.getName());
	}

	public void init() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new GUI().setVisible(true);
			}
		});
	}
}
