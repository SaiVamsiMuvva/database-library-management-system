
//import com.mysql.jdbc.Connection;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class LMSproject {
	
	private Connection myConn;
	static Statement myStmt = null;
	static ResultSet myRs = null;
	static JFrame frame;

	public LMSproject() throws Exception {
		String url1 = "jdbc:mysql://localhost:3306/library";
		String user = "root";
		String password ="password";
		Connection myConn = DriverManager.getConnection(url1, user, password);
		myStmt = myConn.createStatement();
		initialize();
	}


	public ResultSet BookSearchAndAvailability(String ISBN, String Title , String Authors) throws Exception {
		try {
			 
			if((!ISBN.isEmpty() &&  !Title.isEmpty() &&  (Authors.isEmpty() || !Authors.isEmpty()))){ //all three available or only author empty(isbn and title)
				
				return myRs = myStmt.executeQuery("select Book.ISBN,Title,Authors,BOOK_COPIES.Branch_id,Branch_name,No_of_copies,Available_copies from BOOK,BOOK_COPIES,LIBRARY_BRANCH where BOOK.isbn=BOOK_COPIES.Book_id AND BOOK_COPIES.Branch_id=LIBRARY_BRANCH.Branch_id AND BOOK.isbn LIKE '%"+ ISBN +"%' AND BOOK.Title LIKE '%"+ Title +"%' AND BOOK.Authors LIKE '%"+ Authors + "%' ");
				
				}
				

				else if(!ISBN.isEmpty() && Title.isEmpty() && (Authors.isEmpty() || !Authors.isEmpty())){ // if only ISBN available or isbn and author
				return myRs = myStmt.executeQuery("select Book.ISBN,Title,Authors,BOOK_COPIES.Branch_id,Branch_name,No_of_copies,Available_copies from BOOK,BOOK_COPIES,LIBRARY_BRANCH where BOOK.isbn=BOOK_COPIES.Book_id AND BOOK_COPIES.Branch_id=LIBRARY_BRANCH.Branch_id AND BOOK.isbn like '%"+ ISBN  + "%' ");

				}
				
				
				else if(ISBN.isEmpty() && !Title.isEmpty() && Authors.isEmpty()){ // only Title available 
				return	myRs = myStmt.executeQuery("select Book.ISBN,Title,Authors,BOOK_COPIES.Branch_id,Branch_name,No_of_copies,Available_copies from BOOK,BOOK_COPIES,LIBRARY_BRANCH where BOOK.isbn=BOOK_COPIES.Book_id AND BOOK_COPIES.Branch_id=LIBRARY_BRANCH.Branch_id AND BOOK.Title LIKE '%" +Title + "%' ");
					
			 }
				else if(ISBN.isEmpty() && !Title.isEmpty() && !Authors.isEmpty()){ // title and author
					return	myRs = myStmt.executeQuery("select Book.ISBN,Title,Authors,BOOK_COPIES.Branch_id,Branch_name,No_of_copies,Available_copies from BOOK,BOOK_COPIES,LIBRARY_BRANCH where BOOK.isbn=BOOK_COPIES.Book_id AND BOOK_COPIES.Branch_id=LIBRARY_BRANCH.Branch_id AND BOOK.Title LIKE '%" +Title + "%' AND BOOK.Authors LIKE '%" +Authors + "%'");
						
				 }
				
				else if( ISBN.isEmpty() && Title.isEmpty() && !Authors.isEmpty()){ //only Author Available 
				return	myRs = myStmt.executeQuery("select Book.ISBN,Title,Authors,BOOK_COPIES.Branch_id,Branch_name,No_of_copies,Available_copies from BOOK,BOOK_COPIES,LIBRARY_BRANCH where BOOK.isbn=BOOK_COPIES.Book_id AND BOOK_COPIES.Branch_id=LIBRARY_BRANCH.Branch_id AND BOOK.Authors LIKE '%" + Authors + "%' ");
					
				 }
				else if( ISBN.isEmpty() && Title.isEmpty() && Authors.isEmpty()){  //ISBN, AUTHOR AND TITLE ALL EMPTY (ON CLICKING THE SEARCH BUTTON WITHOUT ENTERING ANYTHING)
					 int reply = JOptionPane.showConfirmDialog(null, "Nothing Entered, Showing All Books", "" , JOptionPane.OK_CANCEL_OPTION);
					 if(reply == 0){
							myRs = myStmt.executeQuery("select Book.ISBN,Title,Authors,BOOK_COPIES.Branch_id,Branch_name,No_of_copies,Available_copies from BOOK,BOOK_COPIES,LIBRARY_BRANCH where BOOK.isbn=BOOK_COPIES.Book_id AND BOOK_COPIES.Branch_id=LIBRARY_BRANCH.Branch_id");
					 }
				}			
			
		} finally {
			 //close(myStmt, myRs);
		}
		if(!myRs.next()){
			JOptionPane.showMessageDialog(null, "No results found");
		}
		return myRs;
	}


	
	public static void CheckOut(String ISBN, int _Branch_id, String _Card_no) throws SQLException{
		int checker= 0;
		myRs = myStmt.executeQuery("select Check_out(\"" + ISBN +  "\" ," + _Branch_id + "," + "\"" + _Card_no + "\")"  );
		while(myRs.next()){
			checker = myRs.getInt(1);
		}
		
		if (checker == 1){
		JOptionPane.showMessageDialog(null, "Book Checked Out !!");
			
		}
		
		else if (checker == 2){
			JOptionPane.showMessageDialog(null, "Sorry , Selected book copy is Unavailable in the selected branch");
		}
		
		
		else if (checker == 3){
			JOptionPane.showMessageDialog(null, "Sorry, Checkout Failed Because already 3 loans exist");
		}
		
		else{
			JOptionPane.showMessageDialog(null, "Oops,Checkout failed ! Please make sure that all fields are entered/ entered correctly");
		}
	}
	
	public static ResultSet locateLoans(String Book_id , String Card_no , String Borrower) throws SQLException{
		if((Book_id.isEmpty() || !Book_id.isEmpty()) && !Card_no.isEmpty() && (Borrower.isEmpty() || !Borrower.isEmpty())){ // when all three parameters given or only card_no given or  book_id and Card_no given or Borrower and Card_no given
			 myRs = myStmt.executeQuery("select * from Book_loans bk join Borrower b on  bk.Card_no =  b.Card_no where bk.Date_in IS NULL and b.Card_no = \"" + Card_no + "\""); 
			 return myRs;
			}
			
			else if(!Book_id.isEmpty() && !Borrower.isEmpty() && Card_no.isEmpty()){  //only Book_id and Borrower given
			 myRs = myStmt.executeQuery("select * from Book_loans bl join (Select Card_no from Borrower b where b.Fname LIKE '%" + Borrower + "%' or b.Lname LIKE '%" + Borrower + "%' ) as s on bl.Card_no = s.Card_no and bl.Date_in is NULL");
			 return myRs;
			}
			
			else if(!Book_id.isEmpty() && Borrower.isEmpty() && Card_no.isEmpty()){  // only book_id given 
				myRs =myStmt.executeQuery("select * from Book_loans bl join Borrower b on bl.Card_no = b.Card_no where bl.ISBN = \"" +Book_id + "\"and bl.Date_in IS NULL");
				return myRs;
				}
			else if(Book_id.isEmpty() && !Borrower.isEmpty() && Card_no.isEmpty()){ // only Borrower is given 
			 myRs =myStmt.executeQuery("select * from Book_loans bl join Borrower b on bl.Card_no = b.Card_no where bl.Date_in IS NULL and b.Fname LIKE '%" + Borrower + "%' or b.Lname LIKE '%" + Borrower + "%'");
			 return myRs;
			}
			else{
				JOptionPane.showMessageDialog(null, "Enter atleast one field");
				return null;
			}
			
			}
	
	public void checkIn(int Loan_id , String Book_id , int Branch_id){
		try {
			myRs = myStmt.executeQuery("Select Check_in(" + Loan_id + ",\"" + Book_id + "\",\"" + Branch_id + "\")");
			JOptionPane.showMessageDialog(null, "Book Check in successful !!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void createBorrower(String Fname , String Lname , String SSN , String Address , String Phone) throws SQLException{
		if(Fname.isEmpty() || Lname.isEmpty() || SSN.isEmpty() || Address.isEmpty() ){
			JOptionPane.showMessageDialog(null, "Only Phone field can be empty");
			return;
		}
		myRs = myStmt.executeQuery("Select createBorrower(\"" + Fname + "\",\"" + Lname + "\",\"" + SSN + "\",\"" + Address + "\",\"" + Phone + "\")");
		int checker = 0;
		while(myRs.next()){
			checker = myRs.getInt(1);
		}
		
		if (checker == 1){
		JOptionPane.showMessageDialog(null, "New Borrower Creation Successful");
			
		}
		
		else if(checker == 0){
			JOptionPane.showMessageDialog(null, "Borrower Already exists");
		}
		else{
			JOptionPane.showMessageDialog(null, "Borrower Creation failed");
		}
	}
	
	public void refreshFines() throws SQLException{
		myRs = myStmt.executeQuery("Select refreshFines()");
		JOptionPane.showMessageDialog(null, "Fines Updated Successfully");
	}
	
	public ResultSet showFines(String Card_no) throws SQLException{
		if(!Card_no.isEmpty()){
			myRs = myStmt.executeQuery("Select f.loan_id , f.fine_amt from fines f join Book_loans bl on f.Loan_id = bl.Loan_id where f.paid = 0 and Card_no =\"" + Card_no + "\"");
			return myRs;
		}
		else{
			JOptionPane.showMessageDialog(null, "enter a card number");
			return null;
		}
		
	}
	
	public void showTotal(String Card_no) throws SQLException{
		if(!Card_no.isEmpty()){
			float checker = 0;
			myRs = myStmt.executeQuery("Select f.loan_id , sum(f.fine_amt)  from fines f join Book_loans bl on f.Loan_id = bl.Loan_id where f.paid = 0 Group by bl.Card_no having bl.Card_no =\"" + Card_no + "\"");
			while(myRs.next()){
				checker = myRs.getFloat(2);
			}
			
			JOptionPane.showMessageDialog(null, checker);

		}
		else{
			JOptionPane.showMessageDialog(null, "Enter a card number");
		}
	}
	
	public void payFines(int Loan_id) throws SQLException{
		myRs = myStmt.executeQuery("Select payFines(" + Loan_id + ")");
		int checker = 0;
		while(myRs.next()){
			checker = myRs.getInt(1);
		}
		if(checker == 1){
		JOptionPane.showMessageDialog(null, "Fine paid!!");
	}
	else if(checker == 2){
		JOptionPane.showMessageDialog(null, "Cannot pay fine before returning the book");
	}
	else{
		JOptionPane.showMessageDialog(null, "Payment not completed");
	}
	}

//	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {
//
//		if (myRs != null) {
//			myRs.close();
//		}
//
//		if (myStmt != null) {
//
//		}
//
//		if (myConn != null) {
//			myConn.close();
//		}
//	}
//
//	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
//		close(null, myStmt, myRs);
//	}
//	private void close(Statement myStmt) throws SQLException {
//		close(null, myStmt, null);		
//	}

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			try {
			LMSproject window = new LMSproject();
			window.frame.setVisible(true);
			} catch (Exception e) {
			e.printStackTrace();
			}
			}
			});
			} 
			
			

			private void initialize() {
			frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			frame.setBounds(100, 100, 450, 300);
			frame.getContentPane().setLayout(null);

			JButton btnNewButton = new JButton("Book Search");
			btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			BookSearchAndAvailability.main(null);
			}
			});
			btnNewButton.setBounds(6, 30, 117, 29);
			frame.getContentPane().add(btnNewButton);

			JButton btnNewButton_1 = new JButton("Add Borrowers");
			btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			CreateBorrower.main(null);
			}
			});
			btnNewButton_1.setBounds(148, 30, 117, 29);
			frame.getContentPane().add(btnNewButton_1);

			JButton btnNewButton_2 = new JButton("Check Out");
			btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			CheckOut.main(null);}
			});
			btnNewButton_2.setBounds(309, 30, 117, 29);
			frame.getContentPane().add(btnNewButton_2);

			JButton btnNewButton_3 = new JButton("Check In");
			btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			CheckIn.main(null);
			}
			});
			btnNewButton_3.setBounds(6, 103, 117, 29);
			frame.getContentPane().add(btnNewButton_3);

			JButton btnNewButton_4 = new JButton("Fines");
			btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				Fines.main(null);
			}
			});
			btnNewButton_4.setBounds(148, 103, 117, 29);
			frame.getContentPane().add(btnNewButton_4);

			}
		
	}


