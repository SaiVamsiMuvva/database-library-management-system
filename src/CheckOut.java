
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CheckOut {

	private JFrame frame;
	private JTextField ISBN_text;
	private JTextField Branch_id_text;
	private JTextField Card_no_text;
	private LMSproject lmsCheckout;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckOut window = new CheckOut();
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
	public CheckOut() {
		try {
			lmsCheckout = new LMSproject();
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

		JLabel lblNewLabel = new JLabel("Enter ISBN");
		lblNewLabel.setBounds(25, 28, 71, 14);
		frame.getContentPane().add(lblNewLabel);

		ISBN_text = new JTextField();
		ISBN_text.setBounds(174, 25, 129, 17);
		frame.getContentPane().add(ISBN_text);
		ISBN_text.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Branch_id");
		lblNewLabel_1.setBounds(25, 65, 71, 14);
		frame.getContentPane().add(lblNewLabel_1);

		Branch_id_text = new JTextField();
		Branch_id_text.setBounds(174, 56, 129, 17);
		frame.getContentPane().add(Branch_id_text);
		Branch_id_text.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Card_no");
		lblNewLabel_2.setBounds(25, 120, 71, 14);
		frame.getContentPane().add(lblNewLabel_2);

		Card_no_text = new JTextField();
		Card_no_text.setBounds(174, 117, 129, 17);
		frame.getContentPane().add(Card_no_text);
		Card_no_text.setColumns(10);

		JButton Check_out_button = new JButton("Check Out");
		Check_out_button.addActionListener(new ActionListener() {
			String isbn, card_no;
			int branch_id;

			public void actionPerformed(ActionEvent arg0) {

				try {
					isbn = ISBN_text.getText();
					card_no = Card_no_text.getText();
					if(!Branch_id_text.getText().isEmpty()){
					branch_id = Integer.parseInt(Branch_id_text.getText());
					}
					lmsCheckout.CheckOut(isbn, branch_id, card_no);
					
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				}

			}
		});
		Check_out_button.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 11));
		Check_out_button.setBounds(298, 189, 101, 23);
		frame.getContentPane().add(Check_out_button);

		JButton Clear_button = new JButton("Clear");
		Clear_button.setBounds(89, 189, 89, 23);
		frame.getContentPane().add(Clear_button);
	}

}
