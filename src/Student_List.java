import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Student_List extends JFrame implements ActionListener {

	Vector fields,contents;
	JTable table;
	JScrollPane scroll;
	JLabel      ltop;
	JButton     add, update, delete, view;
	Connection conn;
	DefaultTableModel dtm;
	Statement  stat;
	int        cmd;
	ResultSet rs;
	ResultSetMetaData rsmd;
	

	public Student_List() {
		super("�л� ���� ����Ʈ ����");
		getContentPane().setLayout(new BorderLayout());
		
		JPanel top = new JPanel();
		top.setBackground(new Color(76,76,76));
		ltop = new JLabel("�л� ��ü ����");
		ltop.setOpaque(true);
		ltop.setBackground(new Color(76,76,76));
		ltop.setForeground(new Color(255,224,140));
		ltop.setFont(new Font("HY������M",Font.BOLD,20));
		top.add(ltop);
		
		//������ ���� vector�� table�� ����
		dtm = new DefaultTableModel(contents = new Vector(),fields = new Vector());
		table = new JTable(dtm);
		scroll = new JScrollPane(table);
				
		JPanel bottom = new JPanel();
		bottom.setBackground(new Color(76,76,76));
		bottom.setLayout(new GridLayout(1,4));
		bottom.add(add = new JButton("���"));
		add.setForeground(new Color(255,224,140));
		add.setBackground(new Color(76,76,76));
		add.addActionListener(this);
		bottom.add(update = new JButton("����"));
		update.setForeground(new Color(255,224,140));
		update.setBackground(new Color(76,76,76));
		update.addActionListener(this);
		bottom.add(delete = new JButton("����"));
		delete.setForeground(new Color(255,224,140));
		delete.setBackground(new Color(76,76,76));
		delete.addActionListener(this);
		bottom.add(view = new JButton("�˻�"));
		view.setForeground(new Color(255,224,140));
		view.setBackground(new Color(76,76,76));
		view.addActionListener(this);

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
		DBcon();
		TotalView();
		
		setSize(800, 500);
		setVisible(true);
	}

	private void DBcon() {
		try {
			//DB�����ϱ� ���� url ����
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/student_management";
			//���� ����
			conn = DriverManager.getConnection(url, "sogong", "1234");
			//Statement ��ü�� ����
			stat = conn.createStatement();
			System.out.println("��񿬰�!!");
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

	public void actionPerformed(ActionEvent e) {
		ResultSet rs = null;
		Component com = (Component) e.getSource();
		try {
			if(com == add) {
				new Add_Student();
			}else if(com == delete) {
				new Delete_Student();
			}else if(com == update) {
				new Update_Student();
			}else if(com == view) {
				new View_Student();
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
			//vecotr�� ������ ������ ���ǹ�
			rs = stat.executeQuery("select * from Student order by ID");
			//���� �ӽ�����
			rsmd = rs.getMetaData();
			//��Ʈ����Ʈ�� �̸� ���
			for(int i=1;i<=rsmd.getColumnCount();++i){
				fields.add(rsmd.getColumnName(i));
			}
			Vector oneRow;
			while(rs.next()){
				//��� ���� ���ʴ�� �� �� �� �� ���
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