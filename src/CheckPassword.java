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
		super("비밀번호 입력창");
		get_id = login.cid;
		getContentPane().setLayout(new BorderLayout());

		//정보를 검색할 창
		JPanel center = new JPanel();
		center.setBackground(new Color(153,153,153));
		center.setLayout(new GridLayout(3,1));
		//아이디 입력
		JPanel ppw = new JPanel();
		ppw.setBackground(new Color(153,153,153));
		JLabel lpw = new JLabel("Password");
		lpw.setFont(new Font("HY헤드라인M",Font.BOLD,20));
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
	    pok.add(bok = new JButton("확인"));
	    bok.setBackground(new Color(76,76,76));
	    bok.setForeground(new Color(255,224,140));
	    bok.setFont(new Font("HY헤드라인M",Font.PLAIN,15));
	    bok.addActionListener(this);
	    	      
	    JPanel pcancel = new JPanel();
	    pcancel.setBackground(new Color(76,76,76));
	    pcancel.add(bcancel = new JButton("돌아가기"));
	    bcancel.setBackground(new Color(76,76,76));
	    bcancel.setForeground(new Color(255,224,140));
	    bcancel.setFont(new Font("HY헤드라인M",Font.PLAIN,15));
	    bcancel.addActionListener(this);
		
	    bottom.add(pok);
	    bottom.add(pcancel);
	    
	    add("Center", center);
		add("South", bottom);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 300);
		setVisible(true);
		
		DBcon();//DB 연결
		
	}
	private void DBcon() {
		try {
			//DB연결하기 위한 url 저장
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/Student_Management";
			//계정 연결
			conn = DriverManager.getConnection(url, "sogong", "1234");
			//Statement 객체를 얻음
			stmt = (Statement) conn.createStatement();
			System.out.println("DB가 연결되었습니다.");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
	//연결된 데이터베이스를 끊어주는 함수
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
		ResultSet rs = null;//초기화
		Component c = (Component) e.getSource();//버튼 값 받기
		try {
			if(c == bok) {//확인 버튼
				confirm_pw = pw.getText().trim();
				
				if(confirm_pw == null){
					JOptionPane.showMessageDialog(getParent(),"비밀번호를 입력해주세요");
					return ;
				}
				//동일한 사용자ID가 있는지 확인
				ResultSet rs2=stmt.executeQuery("select User_ID from User" +
						" where User_ID='"+get_id+"'and Password='"+confirm_pw+"'");
				if(!rs2.next()) {
					//등록되지 않은 사용자
					JOptionPane.showMessageDialog(getParent(),"등록되지 않은 정보 입니다.");											
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
