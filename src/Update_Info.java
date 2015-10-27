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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
 

public class Update_Info extends JFrame implements ActionListener{
    Connection con;
    Statement stmt;
    String get_id;
    JTextField  Stu_id;
    JTextField  Stu_name;
    JTextField  Stu_dept;
    JTextField  Stu_phone;
    JButton     mod;
    
    public Update_Info(){
    	
    	super("�л� ���� ����");
    	
    	get_id = Update_Student.confirm_id;
    	System.out.println(get_id);
    	
    	JPanel top = new JPanel();
		top.setBackground(new Color(76,76,76));
		JLabel ltop = new JLabel(get_id + " �л� ���� ����");
		ltop.setOpaque(true);
		ltop.setBackground(new Color(76,76,76));
		ltop.setForeground(new Color(255,224,140));
		ltop.setFont(new Font("HY������M",Font.BOLD,20));
		top.add(ltop);
		
		setLayout(new BorderLayout());
		JPanel center = new JPanel();
		center.setBackground(new Color(153,153,153));
		center.setLayout(new GridLayout(4,1));
		
		JPanel pid = new JPanel();
		pid.setBackground(new Color(153,153,153));
		JLabel lid = new JLabel("ID");
		lid.setFont(new Font("HY������M",Font.BOLD,10));
		lid.setForeground(new Color(255,224,140));
		lid.setBackground(new Color(153,153,153));
		pid.add(lid);
		pid.add(Stu_id = new JTextField(10));
		
		JPanel pname = new JPanel();
		pname.setBackground(new Color(153,153,153));
		JLabel lname = new JLabel("Name");
		lname.setFont(new Font("HY������M",Font.BOLD,10));
		lname.setForeground(new Color(255,224,140));
		lname.setBackground(new Color(153,153,153));
		pname.add(lname);
		pname.add(Stu_name = new JTextField(10));
		
		JPanel pdept = new JPanel();
		pdept.setBackground(new Color(153,153,153));
		JLabel ldept = new JLabel("Department");
		ldept.setFont(new Font("HY������M",Font.BOLD,10));
		ldept.setForeground(new Color(255,224,140));
		ldept.setBackground(new Color(153,153,153));
		pdept.add(ldept);
		pdept.add(Stu_dept = new JTextField(10));
		
		JPanel pphone = new JPanel();
		pphone.setBackground(new Color(153,153,153));
		JLabel lphone = new JLabel("Phone number");
		lphone.setFont(new Font("HY������M",Font.BOLD,10));
		lphone.setForeground(new Color(255,224,140));
		lphone.setBackground(new Color(153,153,153));
		pphone.add(lphone);
		pphone.add(Stu_phone = new JTextField(10));
				
		center.add(pid);
		center.add(pname);
		center.add(pdept);
		center.add(pphone);
		
		Stu_id.setEnabled(false);
		Stu_name.setEnabled(false);
		Stu_dept.setEnabled(false);

		//Ȱ�� ��ư â
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(1,2));
		bottom.setBackground(new Color(76,76,76));
		//������Ű�� ��ư
		bottom.add(mod = new JButton("����"));
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
		
		DBcon();//DB ����

        setSize(600, 400);
        setVisible(true);
    }
    
    private void DBcon() {
		try {
			//DB�����ϱ� ���� url ����
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/student_management";
			//���� ����
			con = DriverManager.getConnection(url, "sogong", "1234");
			//Statement ��ü�� ����
			stmt = con.createStatement();
			System.out.println("DB�� ����Ǿ����ϴ�.");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
    
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
			if(c == mod) {//�˻� ��ư�� ������ ��
				String mphone = Stu_phone.getText().trim();
									
				//�Է����� �ʾ��� �� 
				if(mphone == null){
					JOptionPane.showMessageDialog(getParent(),"�޴��� ��ȣ�� �Է����ּ���");
					return ;
				}
				else{
					stmt.executeUpdate("update Student set Phone_Number='"+ mphone +"' where ID="+ get_id);
					JOptionPane.showMessageDialog(getParent(),"���� ���� �Ϸ�");
					dispose();
					new Student_List();
				}		
			}
		}catch(Exception ex) {
			ex.printStackTrace(System.out);
		}

		return ;

	}
     
 
    public static void main(String[] args) {
       
    	new Update_Info();
    }
 
}