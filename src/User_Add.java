import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class User_Add extends JFrame implements ActionListener{
	
	JTextField  id, password;
	JButton     ok, cancel;
	JLabel ltop, lid, lpw;
	Connection conn;
	Statement  stmt;
	ResultSet rs;
	
	public User_Add(){		
		super("ADD");
	    setLayout(new BorderLayout());
	    
	    JPanel top = new JPanel();
	    top.setBackground(new Color(76,76,76));
	    ltop = new JLabel("USER ���");
	    ltop.setOpaque(true);
	    ltop.setBackground(new Color(76,76,76));
	    ltop.setForeground(new Color(255,224,140));
	    ltop.setFont(new Font("HY������M",Font.BOLD,20));
	    top.add(ltop);
	    
	    JPanel center = new JPanel();
	    center.setBackground(new Color(153,153,153));
	    center.setLayout(new GridLayout(2,1));
	    
	    JPanel pid = new JPanel();
	    pid.setBackground(new Color(153,153,153));
	    pid.add(lid=new JLabel("ID"));
	    lid.setBackground(new Color(153,153,153));
	    lid.setForeground(new Color(255,224,140));
	    lid.setFont(new Font("HY������M",Font.PLAIN,15));
	    pid.add(id = new JTextField(12));
	    id.setText("professor");
	    
	    JPanel ppw = new JPanel();
	    ppw.setBackground(new Color(153,153,153));
	    ppw.add(lpw=new JLabel("PASSWORD"));
	    lpw.setBackground(new Color(153,153,153));
	    lpw.setForeground(new Color(255,224,140));
	    lpw.setFont(new Font("HY������M",Font.PLAIN,15));
	    ppw.add(password = new JTextField(12));
	    
	    center.add(pid);
	    center.add(ppw);
	    
	    JPanel bottom = new JPanel();
	      bottom.setBackground(new Color(76,76,76));
	      bottom.setLayout(new GridLayout(1,2));
	      
	    JPanel pok = new JPanel();
	      pok.setBackground(new Color(76,76,76));
	      pok.add(ok = new JButton("�߰��ϱ�"));
	      ok.setBackground(new Color(76,76,76));
	      ok.setForeground(new Color(255,224,140));
	      ok.setFont(new Font("HY������M",Font.PLAIN,15));
	      ok.addActionListener(this);
	      
	      JPanel pcancel = new JPanel();
	      pcancel.setBackground(new Color(76,76,76));
	      pcancel.add(cancel = new JButton("���ư���"));
	      cancel.setBackground(new Color(76,76,76));
	      cancel.setForeground(new Color(255,224,140));
	      cancel.setFont(new Font("HY������M",Font.PLAIN,15));
	      cancel.addActionListener(this);   
	      
	      bottom.add(pok);
	      bottom.add(pcancel);
	    
	    add("Center",center);
	    add("South",bottom);
	    
      DBcon();
      
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(600, 400);
      setVisible(true);
	    
	   }
	   private void DBcon() {
	      try {
	          //JDBC ����̹��� DriverManager�� ���
	         Class.forName("com.mysql.jdbc.Driver");
	         String url = "jdbc:mysql://localhost/Student_Management";
	         //�ش� Driver�� ���� Connection ��ü ȹ��
	         conn = DriverManager.getConnection(url, "sogong", "1234");
	         //Connection ��ü�� ���� Statement ��ü ȹ��
	         stmt = conn.createStatement();
	         System.out.println("�����ͺ��̽� ����");
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


	public void actionPerformed(ActionEvent e) {
		 Component c = (Component) e.getSource();
	      try {
	         if(c==ok){
	        	 String cid = id.getText().trim();
	               String cpw = password.getText().trim();
	               
	               if(cpw == null || cpw.length() == 0 ){
	                     JOptionPane.showMessageDialog(getParent(),"��� ������ �Է����ּ���");
	                     return ;
	               }
	               ResultSet rs2=stmt.executeQuery("select User_ID from User where User_ID='"+cid+"'");
	               if(rs2.next()) {
						JOptionPane.showMessageDialog(getParent(),"�̹� professor�� ��й�ȣ�� ��ϵǾ� �ֽ��ϴ�.");
	               }else{
		               String query ="insert into User (User_ID,Password)" +
		               		" values ('"+cid+"','"+cpw+"')";
		               stmt.executeUpdate(query);
		               
		               JOptionPane.showMessageDialog(getParent(),"����� ��� �Ϸ�");
		               
		               dispose();
		               new login();
		            }
	         }else if(c==cancel){
	        	 dispose();
	        	 new login();
	         }   	 
	      }catch(Exception ex) {
	             System.out.println(ex.getMessage());
	             ex.printStackTrace();
	             
         }
         return;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
