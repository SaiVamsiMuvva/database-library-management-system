import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.lang.ref.PhantomReference;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class CreateBorrower {

	private JFrame frame;
	private JTextField Fname_text;
	private JTextField Lname_text;
	private JTextField SSN_text;
	private JTextField Address_text;
	private LMSproject lmsCreateBorrower;
	private JLabel lblPhone;
	private JTextField Phone_text;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateBorrower window = new CreateBorrower();
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
	public CreateBorrower() {
		try {
			lmsCreateBorrower = new LMSproject();

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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setBounds(10, 11, 96, 14);
		frame.getContentPane().add(lblNewLabel);
		
		Fname_text = new JTextField();
		Fname_text.setBounds(167, 8, 115, 20);
		frame.getContentPane().add(Fname_text);
		Fname_text.setColumns(10);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(10, 42, 96, 14);
		frame.getContentPane().add(lblLastName);
		
		Lname_text = new JTextField();
		Lname_text.setBounds(167, 39, 115, 20);
		frame.getContentPane().add(Lname_text);
		Lname_text.setColumns(10);
		
		JLabel lblSsn = new JLabel("SSN");
		lblSsn.setBounds(10, 73, 46, 14);
		frame.getContentPane().add(lblSsn);
		
		SSN_text = new JTextField();
		SSN_text.setBounds(167, 70, 115, 20);
		frame.getContentPane().add(SSN_text);
		SSN_text.setColumns(10);
		
		JLabel lblAddressLine = new JLabel("Address Line");
		lblAddressLine.setBounds(10, 104, 96, 14);
		frame.getContentPane().add(lblAddressLine);
		
		Address_text = new JTextField();
		Address_text.setBounds(167, 101, 115, 20);
		frame.getContentPane().add(Address_text);
		Address_text.setColumns(10);
		
		JButton CreateBorrower_btn = new JButton("Create Borrower");
		CreateBorrower_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fname = Fname_text.getText();
				String lname = Lname_text.getText();
				String ssn = SSN_text.getText();
				String address = Address_text.getText();
				String phone = Phone_text.getText();
				try {
					lmsCreateBorrower.createBorrower(fname, lname, ssn, address, phone);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		CreateBorrower_btn.setBounds(121, 232, 161, 23);
		frame.getContentPane().add(CreateBorrower_btn);
		
		lblPhone = new JLabel("Phone");
		lblPhone.setBounds(10, 137, 46, 14);
		frame.getContentPane().add(lblPhone);
		
		Phone_text = new JTextField();
		Phone_text.setBounds(167, 137, 115, 20);
		frame.getContentPane().add(Phone_text);
		Phone_text.setColumns(10);
	}

}

