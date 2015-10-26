import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;


public class Update_Student extends JFrame implements ActionListener{
	
	JTextField  Stu_id;
	static String confirm_id;
	Label      lid;
	JButton     bok;
	Connection con;
	Statement  stmt;

	public Update_Student() {
		//title
		super("학생 정보 수정");
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

		//활성 버튼 창
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(1,2));
		bottom.setBackground(new Color(76,76,76));
		//정보를 확인하고 다음 페이지로 넘겨주는 버튼
		bottom.add(bok = new JButton("검색"));
		bok.setForeground(new Color(255,224,140));
		bok.setBackground(new Color(76,76,76));
		bok.addActionListener(this);
		
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
			String url = "jdbc:mysql://localhost/student_management";
			//계정 연결
			con = DriverManager.getConnection(url, "sogong", "1234");
			//Statement 객체를 얻음
			stmt = con.createStatement();
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
			if(con != null) {
				con.close();
			}
		} catch(Exception ex) { }
	}
	
	public void actionPerformed(ActionEvent e) {
		ResultSet rs = null;//초기화
		Component c = (Component) e.getSource();//버튼 값 받기
		try {
			if(c == bok) {//검색 버튼을 눌렀을 때
				confirm_id = Stu_id.getText().trim();
									
				//입력하지 않았을 때 
				if(confirm_id == null){
					JOptionPane.showMessageDialog(getParent(),"아이디를 입력해주세요");
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
					new Update_Info();
				}		
			}
		}catch(Exception ex) {
			ex.printStackTrace(System.out);
		}

		return ;
	}
	
	public static void main(String args[]) {
		new Update_Student();
	}
}
