import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
 

public class View_Info extends JFrame{
    Connection con;
    Statement stmt;
    int get_id;
    JTable table;
    JPanel panel;
    
    public View_Info(){
    	
    	//get_id = View_Student.confirm_id;
		get_id = 1313777;
    	System.out.println(get_id);
		
    	JPanel top = new JPanel();
    	top.setBackground(new Color(76,76,76));
		JLabel ltop = new JLabel(get_id+"학생 정보");
		ltop.setOpaque(true);
		ltop.setBackground(new Color(76,76,76));
		ltop.setForeground(new Color(255,224,140));
		ltop.setFont(new Font("HY헤드라인M",Font.BOLD,20));
		top.add(ltop);
    	
    	DBcon();
    	selectAll();

        setSize(600, 400);
        setVisible(true);
    }
    
    private void DBcon() {
		try {
			//DB연결하기 위한 url 저장
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/sogong_db";
			//계정 연결
			con = DriverManager.getConnection(url, "sogong", "1234");
			//Statement 객체를 얻음
			stmt = con.createStatement();
			System.out.println("DB가 연결되었습니다.");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

    public void selectAll() {
       String[] header = {"ID", "Name","Department","Phone_Number"};
       String[][] context = null;
       int i = 0;
     
       try {
    	   int cid = get_id;
    	   ResultSet rs2=stmt.executeQuery("select ID from student_management where ID="+cid);
   		if(!rs2.next()) { 	
   			JOptionPane.showMessageDialog(getParent(), "아이디에 맞는 데이터가 없습니다.");
   				return ;
   		}
           ResultSet rs = stmt.executeQuery("select ID,Name,Department,Phone_number from student_management where ID="+cid);
           rs.last();
           int nRecord = rs.getRow();
           context = new String[nRecord][4];
           rs.beforeFirst();
           while (rs.next()) {
              int ID = rs.getInt("ID");
              String Name = rs.getString("Name");
              String Department = rs.getString("Department");
              String Phone_Number = rs.getString("Phone_Number");
                        

              context[i][0] = ID + "";
              context[i][1] = Name +"";
              context[i][2] = Department +"";
              context[i][3] = Phone_Number +"";
              
             i++;
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       table = new JTable(context, header);
       JScrollPane scrollPane = new JScrollPane(table);
       add(scrollPane, BorderLayout.CENTER);
    }
     
 
    public static void main(String[] args) {
       
    	new View_Info();
    }
 
}