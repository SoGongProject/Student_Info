import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
 

public class View_Info extends JFrame implements ActionListener{
    Connection conn;
    Statement stmt;
    static String get_id;
    JPanel panel;
    JTable table;
    JButton bcancel;
    
    public View_Info(){
    	
    	get_id = View_Student.confirm_id;
    	System.out.println(get_id);
		
    	JPanel top = new JPanel();
    	top.setBackground(new Color(76,76,76));
		JLabel ltop = new JLabel(get_id+"학생 정보");
		ltop.setOpaque(true);
		ltop.setBackground(new Color(76,76,76));
		ltop.setForeground(new Color(255,224,140));
		ltop.setFont(new Font("HY헤드라인M",Font.BOLD,20));
		top.add(ltop);
		
		JPanel bottom = new JPanel();
	    bottom.setBackground(new Color(76,76,76));
	    bottom.setLayout(new GridLayout(1,2));
		
		JPanel pcancel = new JPanel();
	    pcancel.setBackground(new Color(76,76,76));
	    pcancel.add(bcancel = new JButton("돌아가기"));
	    bcancel.setBackground(new Color(76,76,76));
	    bcancel.setForeground(new Color(255,224,140));
	    bcancel.setFont(new Font("HY헤드라인M",Font.PLAIN,15));
	    bcancel.addActionListener(this);
	    
	    bottom.add(pcancel);
	    add("South", bottom);
	    
    	DBcon();
    	selectAll();

        setSize(600, 400);
        setVisible(true);
    }
    
    private void DBcon() {
		try {
			//DB연결하기 위한 url 저장
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/Student_Management";
			//계정 연결
			conn = DriverManager.getConnection(url, "sogong", "1234");
			//Statement 객체를 얻음
			stmt = conn.createStatement();
			System.out.println("DB가 연결되었습니다.");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
    
    private void destroy() {
		try {
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		} catch(Exception ex) { }
	}

    public void selectAll() {
       String[] header = {"ID", "Name","Department","Phone_Number"};
       String[][] context = null;
       int i = 0;
     
       try {
    	   String cid = get_id;
    	   ResultSet rs2=stmt.executeQuery("select ID from Student where ID="+cid);
   		if(!rs2.next()) { 	
   			JOptionPane.showMessageDialog(getParent(), "아이디에 맞는 데이터가 없습니다.");
   				return ;
   		}
           ResultSet rs = stmt.executeQuery("select ID,Name,Department,Phone_Number from Student where ID="+cid);
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
    
    public void actionPerformed(ActionEvent e) {
    	
    	Component c = (Component) e.getSource();//버튼 값 받기
		try {
			if(c == bcancel) {//검색 버튼을 눌렀을 때				
				dispose();
	        	new Student_List();
			}
		}catch(Exception ex) {
			ex.printStackTrace(System.out);
		}

		return ;
	}
    
    public static void main(String args[]) {
		new View_Info();
	}
 
}