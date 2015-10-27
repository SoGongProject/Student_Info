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
		super("�л� ���� ����");
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
		pid.add(Stu_id = new JTextField(10));
				
		center.add(pid);

		//Ȱ�� ��ư â
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(1,2));
		bottom.setBackground(new Color(76,76,76));
		//������ Ȯ���ϰ� ���� �������� �Ѱ��ִ� ��ư
		bottom.add(bok = new JButton("�˻�"));
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
		
		DBcon();//DB ����
	}
	//�����ͺ��̽��� �������� �Լ�	
	private void DBcon() {
		try {
			//DB�����ϱ� ���� url ����
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/student_management";
			//���� ����
			con = DriverManager.getConnection(url, "sogong", "1234");
			//Statement ��ü�� ����
			stmt = con.createStatement();
			Stu_id.setEditable(true);
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
			if(con != null) {
				con.close();
			}
		} catch(Exception ex) { }
	}
	
	public void actionPerformed(ActionEvent e) {
		ResultSet rs = null;//�ʱ�ȭ
		Component c = (Component) e.getSource();//��ư �� �ޱ�
		try {
			if(c == bok) {//�˻� ��ư�� ������ ��
				confirm_id = Stu_id.getText().trim();
									
				//�Է����� �ʾ��� �� 
				if(confirm_id == null){
					JOptionPane.showMessageDialog(getParent(),"���̵� �Է����ּ���");
					return ;
				}
				//������ �����ID�� �ִ��� Ȯ��
				ResultSet rs2=stmt.executeQuery("select ID from Student" +
						" where ID="+confirm_id);
				if(!rs2.next()) {
					//��ϵ��� ���� �����
					JOptionPane.showMessageDialog(getParent(),"��ϵ��� ���� ���� �Դϴ�.");											
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
