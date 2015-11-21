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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

public class CheckPassword extends JFrame implements ActionListener{

	String get_id;
	static String confirm_pw;
	JButton bok, bcancel, badd;
	JTextField pw;
	Connection conn;
	Statement  stmt;
	
	public CheckPassword(){
		super("��й�ȣ �Է�â");
		get_id = login.cid;
		getContentPane().setLayout(new BorderLayout());

		//������ �˻��� â
		JPanel center = new JPanel();
		center.setBackground(new Color(153,153,153));
		center.setLayout(new GridLayout(3,1));
		//���̵� �Է�
		JPanel ppw = new JPanel();
		ppw.setBackground(new Color(153,153,153));
		JLabel lpw = new JLabel("Password");
		lpw.setFont(new Font("HY������M",Font.BOLD,20));
		lpw.setForeground(new Color(255,224,140));
		lpw.setBackground(new Color(153,153,153));
		ppw.add(lpw);
		ppw.add(pw = new JTextField(10));
				
		center.add(ppw);
		
		JPanel bottom = new JPanel();
	    bottom.setBackground(new Color(76,76,76));
	    bottom.setLayout(new GridLayout(1,3));
	      
	    JPanel pok = new JPanel();
	    pok.setBackground(new Color(76,76,76));
	    pok.add(bok = new JButton("Ȯ��"));
	    bok.setBackground(new Color(76,76,76));
	    bok.setForeground(new Color(255,224,140));
	    bok.setFont(new Font("HY������M",Font.PLAIN,15));
	    bok.addActionListener(this);
	    	      
	    JPanel pcancel = new JPanel();
	    pcancel.setBackground(new Color(76,76,76));
	    pcancel.add(bcancel = new JButton("���ư���"));
	    bcancel.setBackground(new Color(76,76,76));
	    bcancel.setForeground(new Color(255,224,140));
	    bcancel.setFont(new Font("HY������M",Font.PLAIN,15));
	    bcancel.addActionListener(this);
		
	    bottom.add(pok);
	    bottom.add(pcancel);
	    
	    add("Center", center);
		add("South", bottom);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 300);
		setVisible(true);
		
		DBcon();//DB ����
		
	}
	private void DBcon() {
		try {
			//DB�����ϱ� ���� url ����
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/Student_Management";
			//���� ����
			conn = DriverManager.getConnection(url, "sogong", "1234");
			//Statement ��ü�� ����
			stmt = (Statement) conn.createStatement();
			System.out.println("DB�� ����Ǿ����ϴ�.");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
	//����� �����ͺ��̽��� �����ִ� �Լ�
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
		ResultSet rs = null;//�ʱ�ȭ
		Component c = (Component) e.getSource();//��ư �� �ޱ�
		try {
			if(c == bok) {//Ȯ�� ��ư
				confirm_pw = pw.getText().trim();
				
				if(confirm_pw == null){
					JOptionPane.showMessageDialog(getParent(),"��й�ȣ�� �Է����ּ���");
					return ;
				}
				//������ �����ID�� �ִ��� Ȯ��
				ResultSet rs2=stmt.executeQuery("select User_ID from User" +
						" where User_ID='"+get_id+"'and Password='"+confirm_pw+"'");
				if(!rs2.next()) {
					//��ϵ��� ���� �����
					JOptionPane.showMessageDialog(getParent(),"��ϵ��� ���� ���� �Դϴ�.");											
				}
				else{
					dispose();
					new Student_List();
				}
			}else if(c == bcancel){
				dispose();
				new login();
			}
		}catch(Exception ex) {
			ex.printStackTrace(System.out);
		}

		return ;
		
	}
	
	public static void main(String[] args) {
		
	}


}
