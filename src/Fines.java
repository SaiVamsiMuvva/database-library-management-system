import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Fines {
	
	ResultSet myRs = null;

	private JFrame frame;
	private JTable table;
	private JTextField CardNo_text;
	private LMSproject lmsFines;
	private JTextField selLoanId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fines window = new Fines();
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
	public Fines() {
		try {
			lmsFines = new LMSproject();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 579, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton RefreshFines_btn = new JButton("Refresh Fines");
		RefreshFines_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					lmsFines.refreshFines();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		RefreshFines_btn.setBounds(367, 7, 125, 23);
		frame.getContentPane().add(RefreshFines_btn);
		
		JButton Payfine_btn = new JButton("Pay Fine");
		Payfine_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row == -1){
					JOptionPane.showMessageDialog(null, "No selection made");
					return;
				}
				int Loan_id = Integer.parseInt(selLoanId.getText());
				try {
					lmsFines.payFines(Loan_id);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		Payfine_btn.setBounds(464, 227, 89, 23);
		frame.getContentPane().add(Payfine_btn);
		
		JButton Showfines_btn = new JButton("Show Fines");
		Showfines_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame = new JFrame();
				frame.setTitle("Fines");
				frame.setSize(300, 200);
				frame.setBackground(Color.gray);
				
				String card_no = CardNo_text.getText();
				if(card_no.isEmpty()){
					JOptionPane.showMessageDialog(null, "enter a card number");
					return;
				}
				try {
					myRs = lmsFines.showFines(card_no);
					table.setModel(DbUtils.resultSetToTableModel(myRs));
					JPanel topPanel = new JPanel();
					topPanel.setLayout(new BorderLayout());
					frame.getContentPane().add(topPanel);
					topPanel.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		Showfines_btn.setBounds(10, 227, 108, 23);
		frame.getContentPane().add(Showfines_btn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 37, 543, 183);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.getSelectedRow();
				String Loan_id = (table.getModel().getValueAt(row, 0)).toString();
				selLoanId.setText(Loan_id);
			}
		});
		scrollPane.setViewportView(table);
		
		JButton ShowTotal_btn = new JButton("Total fine");
		ShowTotal_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String card_no = CardNo_text.getText();
				try {
					lmsFines.showTotal(card_no);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		ShowTotal_btn.setBounds(128, 227, 110, 23);
		frame.getContentPane().add(ShowTotal_btn);
		
		CardNo_text = new JTextField();
		CardNo_text.setBounds(79, 8, 115, 20);
		frame.getContentPane().add(CardNo_text);
		CardNo_text.setColumns(10);
		
		JLabel lblCardNo = new JLabel("Card No");
		lblCardNo.setBounds(23, 11, 46, 14);
		frame.getContentPane().add(lblCardNo);
		
		selLoanId = new JTextField();
		selLoanId.setEditable(false);
		selLoanId.setBounds(368, 228, 86, 20);
		frame.getContentPane().add(selLoanId);
		selLoanId.setColumns(10);
		
		JLabel lblLoanidSelected = new JLabel("Loan_id Selected");
		lblLoanidSelected.setBounds(248, 231, 110, 14);
		frame.getContentPane().add(lblLoanidSelected);
	}

}
