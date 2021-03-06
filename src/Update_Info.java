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
import javax.swing.JTextField;
 

public class Update_Info extends JFrame implements ActionListener{
    Connection conn;
    Statement stmt;
    String get_id;
    JTextField  Stu_id;
    JTextField  Stu_name;
    JTextField  Stu_dept;
    JTextField  Stu_phone;
    JButton     mod;
    String idData;
    String nameData;
    String deptData;
    String phoneData;
    
    public Update_Info(){
    	
    	super("학생 정보 수정");
    	
    	get_id = Update_Student.confirm_id;
    	System.out.println(get_id);
    	
    	DBcon();//DB 연결
    	
    	JPanel top = new JPanel();
		top.setBackground(new Color(76,76,76));
		JLabel ltop = new JLabel(get_id + " 학생 정보 수정");
		ltop.setOpaque(true);
		ltop.setBackground(new Color(76,76,76));
		ltop.setForeground(new Color(255,224,140));
		ltop.setFont(new Font("HY헤드라인M",Font.BOLD,20));
		top.add(ltop);
		
		setLayout(new BorderLayout());
		JPanel center = new JPanel();
		center.setBackground(new Color(153,153,153));
		center.setLayout(new GridLayout(4,1));
		
		JPanel pid = new JPanel();
		pid.setBackground(new Color(153,153,153));
		JLabel lid = new JLabel("ID");
		lid.setFont(new Font("HY헤드라인M",Font.BOLD,10));
		lid.setForeground(new Color(255,224,140));
		lid.setBackground(new Color(153,153,153));
		pid.add(lid);
		pid.add(Stu_id = new JTextField(10));
		try {
			ResultSet indata = stmt.executeQuery("select ID from Student" +
					" where ID="+get_id);
			indata.first();
			idData = indata.getString("ID");
			Stu_id.setText(idData);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
				
		JPanel pname = new JPanel();
		pname.setBackground(new Color(153,153,153));
		JLabel lname = new JLabel("Name");
		lname.setFont(new Font("HY헤드라인M",Font.BOLD,10));
		lname.setForeground(new Color(255,224,140));
		lname.setBackground(new Color(153,153,153));
		pname.add(lname);
		pname.add(Stu_name = new JTextField(10));
		try {
			ResultSet indata = stmt.executeQuery("select Name from Student" +
					" where ID="+get_id);
			indata.first();
			nameData = indata.getString("Name");
			Stu_name.setText(nameData);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		JPanel pdept = new JPanel();
		pdept.setBackground(new Color(153,153,153));
		JLabel ldept = new JLabel("Department");
		ldept.setFont(new Font("HY헤드라인M",Font.BOLD,10));
		ldept.setForeground(new Color(255,224,140));
		ldept.setBackground(new Color(153,153,153));
		pdept.add(ldept);
		pdept.add(Stu_dept = new JTextField(10));
		try {
			ResultSet indata = stmt.executeQuery("select Department from Student" +
					" where ID="+get_id);
			indata.first();
			deptData = indata.getString("Department");
			Stu_dept.setText(deptData);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		JPanel pphone = new JPanel();
		pphone.setBackground(new Color(153,153,153));
		JLabel lphone = new JLabel("Phone number");
		lphone.setFont(new Font("HY헤드라인M",Font.BOLD,10));
		lphone.setForeground(new Color(255,224,140));
		lphone.setBackground(new Color(153,153,153));
		pphone.add(lphone);
		pphone.add(Stu_phone = new JTextField(10));
		try {
			ResultSet indata = stmt.executeQuery("select Phone_Number from Student" +
					" where ID="+get_id);
			indata.first();
			phoneData = indata.getString("Phone_Number");
			Stu_phone.setText(phoneData);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
				
		center.add(pid);
		center.add(pname);
		center.add(pdept);
		center.add(pphone);
		
		Stu_id.setEnabled(false);
		Stu_name.setEnabled(false);
		Stu_dept.setEnabled(false);
		Stu_phone.setEditable(true);

		//활성 버튼 창
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(1,2));
		bottom.setBackground(new Color(76,76,76));
		//수정시키는 버튼
		bottom.add(mod = new JButton("수정"));
		mod.setForeground(new Color(255,224,140));
		mod.setBackground(new Color(76,76,76));
		mod.addActionListener(this);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				destroy();
				setVisible(false);
				dispose();
				System.exit(0);
			}
		});
		add("North",top);
		add("Center", center);
		add("South", bottom);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 300);
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

    
    public void actionPerformed(ActionEvent e) {
    	ResultSet rs = null;//초기화
		Component c = (Component) e.getSource();//버튼 값 받기
		
		try {
			if(c == mod) {//검색 버튼을 눌렀을 때
				String mphone = Stu_phone.getText().trim();
									
				//입력하지 않았을 때 
				if(mphone == null){
					JOptionPane.showMessageDialog(getParent(),"휴대폰 번호를 입력해주세요");
					return ;
				}
				else{
					stmt.executeUpdate("update Student set Phone_Number='"+ mphone +"' where ID="+ get_id);
					JOptionPane.showMessageDialog(getParent(),"정보 수정 완료");
					dispose();
					new Student_List();
				}		
			}
		}catch(Exception ex) {
			ex.printStackTrace(System.out);
		}

		return ;
	}
    public static void main(String args[]) {
		new Update_Info();
	}
 
}