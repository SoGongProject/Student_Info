import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;


public class login extends JFrame implements ActionListener{
	
	JTextField  user_id,user_bb;
	JPasswordField password;
	static String cid,cpw,cbb;
	Label      lid, lpw, lbb;
	JButton     bok, breset, bfind, bbank;
	Connection conn;
	Statement  stat;

	public login() {
		//title
		super("�α����ϱ�");
		setLayout(new BorderLayout());
		//������ �˻��� â
		JPanel center = new JPanel();
		center.setBackground(new Color(153,153,153));
		center.setLayout(new GridLayout(3,1));
		//���̵� �Է�
		JPanel pid = new JPanel();
		pid.setBackground(new Color(153,153,153));
		JLabel lid = new JLabel("ID");
		lid.setFont(new Font("HY������M",Font.BOLD,20));
		lid.setForeground(new Color(255,224,140));
		lid.setBackground(new Color(153,153,153));
		pid.add(lid);
		pid.add(user_id = new JTextField(10));
		
		center.add(pid);
		
		//Ȱ�� ��ư â
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(2,2));
		bottom.setBackground(new Color(76,76,76));
		//������ Ȯ���ϰ� ���� �������� �Ѱ��ִ� ��ư
		bottom.add(bok = new JButton("�α���"));
		bok.setForeground(new Color(255,224,140));
		bok.setBackground(new Color(76,76,76));
		bok.addActionListener(this);
		//reset��ư
		bottom.add(breset = new JButton("�ٽ� �ۼ�"));
		breset.setForeground(new Color(255,224,140));
		breset.setBackground(new Color(76,76,76));
		breset.addActionListener(this);

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
		DBcon();//DB ����
		
		setSize(400, 300);
		setVisible(true);
	}
	//�����ͺ��̽��� �������� �Լ�	
	private void DBcon() {
		try {
			//DB�����ϱ� ���� url ����
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/Student_Management";
			//���� ����
			conn = DriverManager.getConnection(url, "sogong", "1234");
			//Statement ��ü�� ����
			stat = conn.createStatement();
			user_id.setEditable(true);
			System.out.println("DB�� ����Ǿ����ϴ�.");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
	//����� �����ͺ��̽��� �����ִ� �Լ�
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
	//��� ��ư�� Ȱ����Ű�� �Լ�
	public void setEnable() {
		bok.setEnabled(true);
		breset.setEnabled(true);
	}

	
	public void actionPerformed(ActionEvent e) {
		ResultSet rs = null;//�ʱ�ȭ
		Component c = (Component) e.getSource();//��ư �� �ޱ�
		try {
			if(c == bok) {//�α��� ��ư�� ������ ��
					cid = user_id.getText().trim();
					
					//�Է����� �ʾ��� �� 
					if(cid == null || cid.length() == 0 ){
						JOptionPane.showMessageDialog(getParent(),"��� ������ �Է����ּ���");
						return ;
					}
					else if(cid == "guest"){ //����
						dispose();
						new Student_List();
					}
					else if(cid == "professor"){ // ����
						dispose();
						new Student_List();
					}else
						JOptionPane.showMessageDialog(getParent(),"��ϵ��� ���� ���� �Դϴ�.");
					//�߸� �Է����� ��� ��� ���� �ʱ�ȭ
					setEnable();
					user_id.setText("");
					user_id.setEditable(true);
			}else if(c == breset){
				//reset�� �ǰ� ����
				setEnable();
				user_id.setText("");
				user_id.setEditable(true);
			}
		}catch(Exception ex) {
			ex.printStackTrace(System.out);
		}

		return ;
	}
	
	public static void main(String args[]) {
		new login();
	}
}
