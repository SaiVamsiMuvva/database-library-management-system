import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class BookSearchAndAvailability extends JPanel {
	ResultSet myRs = null;

	private JFrame frame;
	private JTable table;
	private JTextField ISBN_text;
	private JTextField Title_text;
	private JTextField Authors_text;
	private JLabel lblTitle;
	private JLabel lblAuthors;
	private LMSproject lms1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookSearchAndAvailability window = new BookSearchAndAvailability();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BookSearchAndAvailability() {
		try {
			lms1 = new LMSproject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		JPanel topPanel = new JPanel();
		frame = new JFrame();
		frame.setBounds(100, 100, 524, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton Search_Button = new JButton("Search");
		Search_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame = new JFrame();
				frame.setTitle("Book Search");
				frame.setSize(300, 200);
				frame.setBackground(Color.gray);
				
				try{
					String isbn = ISBN_text.getText();
					String title = Title_text.getText();
					String authors = Authors_text.getText();
					myRs = lms1.BookSearchAndAvailability(isbn, title, authors);
					table.setModel(DbUtils.resultSetToTableModel(myRs));					
					topPanel.setLayout(new BorderLayout());
					frame.getContentPane().add(topPanel);
					
					topPanel.setVisible(true);
				}catch(Exception e){
			
				}
			}
		});
		Search_Button.setBounds(379, 12, 89, 23);
		frame.getContentPane().add(Search_Button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 70, 508, 180);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		ISBN_text = new JTextField();
		ISBN_text.setBounds(68, 12, 86, 20);
		frame.getContentPane().add(ISBN_text);
		ISBN_text.setColumns(10);
		
		Title_text = new JTextField();
		Title_text.setBounds(68, 39, 86, 20);
		frame.getContentPane().add(Title_text);
		Title_text.setColumns(10);
		
		Authors_text = new JTextField();
		Authors_text.setBounds(246, 12, 86, 20);
		frame.getContentPane().add(Authors_text);
		Authors_text.setColumns(10);
		
		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setBounds(10, 15, 46, 14);
		frame.getContentPane().add(lblIsbn);
		
		lblTitle = new JLabel("Title");
		lblTitle.setBounds(10, 45, 46, 14);
		frame.getContentPane().add(lblTitle);
		
		lblAuthors = new JLabel("Authors");
		lblAuthors.setBounds(181, 15, 67, 14);
		frame.getContentPane().add(lblAuthors);
		
	}
	
}
