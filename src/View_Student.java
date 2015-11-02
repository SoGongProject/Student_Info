import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;


public class View_Student extends JFrame implements ActionListener{
	
	JTextField  Stu_id;
	static String confirm_id;
	Label      lid;
	JButton     bok,bcancel;
	Connection conn;
	Statement  stmt;

	public View_Student() {
		//title
		super("학생 정보 찾기");
		setLayout(new BorderLayout());
		//정보를 검색할 창
		JPanel center = new JPanel();
		center.setBackground(new Color(153,153,153));
		center.setLayout(new GridLayout(3,1));
		//아이디 입력
		JPanel pid = new JPanel();
		pid.setBackground(new Color(153,153,153));
		JLabel lid = new JLabel("ID");
		lid.setFont(new Font("HY헤드라인M",Font.BOLD,20));
		lid.setForeground(new Color(255,224,140));
		lid.setBackground(new Color(153,153,153));
		pid.add(lid);
		pid.add(Stu_id = new JTextField(10));
				
		center.add(pid);

		JPanel bottom = new JPanel();
	    bottom.setBackground(new Color(76,76,76));
	    bottom.setLayout(new GridLayout(1,2));
	      
	    JPanel pok = new JPanel();
	    pok.setBackground(new Color(76,76,76));
	    pok.add(bok = new JButton("찾기"));
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
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				destroy();
				setVisible(false);
				dispose();
				System.exit(0);
			}
		});
		add("Center", center);
		add("South", bottom);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 300);
		setVisible(true);
		
		DBcon();//DB 연결
	}
	//데이터베이스를 연결해줄 함수	
	private void DBcon() {
		try {
			//DB연결하기 위한 url 저장
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/Student_Management";
			//계정 연결
			conn = DriverManager.getConnection(url, "sogong", "1234");
			//Statement 객체를 얻음
			stmt = conn.createStatement();
			Stu_id.setEditable(true);
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
			if(c == bok) {//로그인 버튼을 눌렀을 때
				confirm_id = Stu_id.getText().trim();
									
				//입력하지 않았을 때 
				if(confirm_id == null){
					JOptionPane.showMessageDialog(getParent(),"모든 정보를 입력해주세요");
					return ;
				}
				//동일한 사용자ID가 있는지 확인
				ResultSet rs2=stmt.executeQuery("select ID from Student" +
						" where ID="+confirm_id);
				if(!rs2.next()) {
					//등록되지 않은 사용자
					JOptionPane.showMessageDialog(getParent(),"등록되지 않은 정보 입니다.");											
				}
				else{
					dispose();
					new View_Info();
				}		
			}
			else if(c==bcancel){
	        	 dispose();
	        	 new Student_List();
	         }
		}catch(Exception ex) {
			ex.printStackTrace(System.out);
		}

		return ;
	}
	public static void main(String args[]) {
		new View_Student();
	}
	
}

