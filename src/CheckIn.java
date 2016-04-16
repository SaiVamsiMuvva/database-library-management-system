import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckIn {
	ResultSet myRs = null;

	private JFrame frame;
	private JTextField Book_Id_text;
	private JTextField Card_no_text;
	private JLabel lblBorrower;
	private JTextField Borrower_text;
	private JTable table;
	private JButton CheckIn_btn;
	private JTextField selBookID;
	private JLabel lblNewLabel;
	private JLabel lblBranchidSelected;
	private JTextField selBranchID;
	private JScrollPane scrollPane;
	private JButton Locate_btn;
	private LMSproject lmsCheckIn;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckIn window = new CheckIn();
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
	public CheckIn() {
		try {
			lmsCheckIn = new LMSproject();

		} catch (Exception e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 591, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblBookId = new JLabel("Book Id");
		lblBookId.setBounds(10, 11, 50, 14);
		frame.getContentPane().add(lblBookId);
		
		Book_Id_text = new JTextField();
		Book_Id_text.setBounds(72, 8, 86, 20);
		frame.getContentPane().add(Book_Id_text);
		Book_Id_text.setColumns(10);
		
		JLabel lblCardNo = new JLabel("Card No");
		lblCardNo.setBounds(170, 11, 57, 14);
		frame.getContentPane().add(lblCardNo);
		
		Card_no_text = new JTextField();
		Card_no_text.setBounds(229, 8, 86, 20);
		frame.getContentPane().add(Card_no_text);
		Card_no_text.setColumns(10);
		
		lblBorrower = new JLabel("Borrower");
		lblBorrower.setBounds(327, 11, 59, 14);
		frame.getContentPane().add(lblBorrower);
		
		Borrower_text = new JTextField();
		Borrower_text.setBounds(398, 8, 86, 20);
		frame.getContentPane().add(Borrower_text);
		Borrower_text.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 64, 575, 186);
		frame.getContentPane().add(scrollPane);
		
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.getSelectedRow();
				String Book_id = (table.getModel().getValueAt(row, 1)).toString();
				String Branch_id = (table.getModel().getValueAt(row, 2)).toString();
				selBookID.setText(Book_id);
				selBranchID.setText(Branch_id);
			}
		});
		
		
		CheckIn_btn = new JButton("Check In");
		CheckIn_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row == -1){
					JOptionPane.showMessageDialog(null, "No selection made");
					return;
				}
				String bookid = selBookID.getText();
				int branchid = Integer.parseInt(selBranchID.getText()) ;
				int loan_id = Integer.parseInt((table.getModel().getValueAt(row, 0)).toString());
				 lmsCheckIn.checkIn(loan_id , bookid, branchid );
			}
		});
		CheckIn_btn.setBounds(496, 29, 89, 23);
		frame.getContentPane().add(CheckIn_btn);
		
		selBookID = new JTextField();
		selBookID.setEditable(false);
		selBookID.setBounds(130, 37, 86, 20);
		frame.getContentPane().add(selBookID);
		selBookID.setColumns(10);
		
		lblNewLabel = new JLabel("Book_id Selected");
		lblNewLabel.setBounds(10, 36, 120, 14);
		frame.getContentPane().add(lblNewLabel);
		
		lblBranchidSelected = new JLabel("Branch_id Selected");
		lblBranchidSelected.setBounds(225, 38, 143, 14);
		frame.getContentPane().add(lblBranchidSelected);
		
		selBranchID = new JTextField();
		selBranchID.setEditable(false);
		selBranchID.setBounds(380, 33, 86, 20);
		frame.getContentPane().add(selBranchID);
		selBranchID.setColumns(10);
		
		Locate_btn = new JButton("Locate");
		Locate_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String bookId = Book_Id_text.getText();
				String cardNo = Card_no_text.getText();
				String borrower = Borrower_text.getText();
				
				frame = new JFrame();
				frame.setTitle("Check In");
				frame.setSize(300, 200);
				frame.setBackground(Color.gray);
				try {
					if(bookId.isEmpty() && cardNo.isEmpty() && borrower.isEmpty()){
						JOptionPane.showMessageDialog(null, "Atleast one field is required to locate loans");
						return;
					}
					else{
					myRs = lmsCheckIn.locateLoans(bookId, cardNo, borrower);
					table.setModel(DbUtils.resultSetToTableModel(myRs));
					JPanel topPanel = new JPanel();
					topPanel.setLayout(new BorderLayout());
					frame.getContentPane().add(topPanel);
					topPanel.setVisible(true);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		Locate_btn.setBounds(496, 8, 89, 23);
		frame.getContentPane().add(Locate_btn);
	}

}
