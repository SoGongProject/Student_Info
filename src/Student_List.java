import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Student_List extends JFrame implements ActionListener {
	public static final int  NONE   = 0;
	public static final int  ADD    = 1;
	public static final int  UPDATE = 2;
	public static final int  DELETE = 3;
	public static final int  VIEW  = 4;
	
	Vector fields,contents;
	JTable table;
	JScrollPane scroll;
	JLabel      ltop;
	JButton     add, update, delete, view, cancel;
	Connection conn;
	DefaultTableModel dtm;
	Statement  stat;
	int        cmd;
	ResultSet rs;
	ResultSetMetaData rsmd;
	

	public Student_List() {
		super("학생 정보 리스트 보기");
		getContentPane().setLayout(new BorderLayout());
		
		JPanel top = new JPanel();
		top.setBackground(new Color(76,76,76));
		ltop = new JLabel("학생 전체 보기");
		ltop.setOpaque(true);
		ltop.setBackground(new Color(76,76,76));
		ltop.setForeground(new Color(255,224,140));
		ltop.setFont(new Font("HY헤드라인M",Font.BOLD,20));
		top.add(ltop);
		
		//정보를 넣은 vector를 table에 삽입
		dtm = new DefaultTableModel(contents = new Vector(),fields = new Vector());
		table = new JTable(dtm);
		scroll = new JScrollPane(table);
				
		JPanel bottom = new JPanel();
		bottom.setBackground(new Color(76,76,76));
		bottom.setLayout(new GridLayout(1,4));
		bottom.add(add = new JButton("등록"));
		add.setForeground(new Color(255,224,140));
		add.setBackground(new Color(76,76,76));
		add.addActionListener(this);
		bottom.add(update = new JButton("수정"));
		update.setForeground(new Color(255,224,140));
		update.setBackground(new Color(76,76,76));
		update.addActionListener(this);
		bottom.add(delete = new JButton("삭제"));
		delete.setForeground(new Color(255,224,140));
		delete.setBackground(new Color(76,76,76));
		delete.addActionListener(this);
		bottom.add(view = new JButton("검색"));
		view.setForeground(new Color(255,224,140));
		view.setBackground(new Color(76,76,76));
		view.addActionListener(this);
		bottom.add(cancel = new JButton("취소"));
		cancel.setForeground(new Color(255,224,140));
		cancel.setBackground(new Color(76,76,76));
		cancel.addActionListener(this);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				destroy();
				setVisible(false);
				dispose();
				System.exit(0);
			}
		});
		add("North",top);
		add("Center", scroll);
		add("South", bottom);
		cmd = NONE;
		DBcon();
		TotalView();
		
		setSize(800, 500);
		setVisible(true);
	}

	private void DBcon() {
		try {
			//DB연결하기 위한 url 저장
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/student_management";
			//계정 연결
			conn = DriverManager.getConnection(url, "sogong", "1234");
			//Statement 객체를 얻음
			stat = conn.createStatement();
			System.out.println("디비연결!!");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	private void destroy() {
		try {
			if(stat != null) {
				stat.close();
			}
			if(conn != null) {
				conn.close();
			}
		} catch(Exception ex) { }
	}

	public void setEnable(int n) {
		add.setEnabled(false);
		update.setEnabled(false);
		delete.setEnabled(false);
		view.setEnabled(false);
		switch(n) {
			case ADD:
				add.setEnabled(true);
				cmd = ADD;
				break;
			case UPDATE:
				update.setEnabled(true);
				cmd = UPDATE;
				break;
			case DELETE:
				delete.setEnabled(true);
				cmd = DELETE;
				break;
			case VIEW:
				view.setEnabled(true);
				cmd = VIEW;
				break;
			case NONE:
				add.setEnabled(true);
				update.setEnabled(true);
				delete.setEnabled(true);
				view.setEnabled(true);
				break;
		}
	}

	public void actionPerformed(ActionEvent e) {
		ResultSet rs = null;
		Component com = (Component) e.getSource();
		try {
			if(com == add) {
				if(cmd != ADD)
					new Add_Student();
					setEnable(ADD);
			}else if(com == delete) {
				if(cmd != DELETE)
					new Delete_Student();
					setEnable(DELETE);
			}else if(com == update) {
				if(cmd != UPDATE)
					new Update_Student();
					setEnable(UPDATE);
			}else if(com == view) {
				if(cmd != VIEW)
					new View_Student();
					setEnable(VIEW);
			} else if(com == cancel) {
				new Student_List();
				cmd = NONE;
			} 
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return ;
	}
	
	public void TotalView() {
		fields = new Vector();
		contents = new Vector();
		try{
			//vecotr에 저장할 내용의 질의문
			rs = stat.executeQuery("select * from Student order by ID");
			//정보 임시저장
			rsmd = rs.getMetaData();
			//애트리뷰트의 이름 출력
			for(int i=1;i<=rsmd.getColumnCount();++i){
				fields.add(rsmd.getColumnName(i));
			}
			Vector oneRow;
			while(rs.next()){
				//모든 값을 차례대로 한 줄 한 줄 출력
				oneRow = new Vector();
				for(int i=0;i<fields.size();i++){
					oneRow.add(rs.getString(i+1));
				}
				contents.add(oneRow);
			}
			dtm.setDataVector(contents,fields);
			table = new JTable(dtm);
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}

		return ;
		
	}
	
	public static void main(String args[]) {
		new Student_List();
	}
}