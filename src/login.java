import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;


public class login extends JFrame implements ActionListener{
	
	JTextField  user_id,user_bb;
	static String cid,cpw,cbb;
	Label      lid, lpw, lbb;
	JButton     bok, breset, badd;

	public login() {
		//title
		super("로그인하기");
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
		pid.add(user_id = new JTextField(10));
		
		center.add(pid);
		
		//활성 버튼 창
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(2,2));
		bottom.setBackground(new Color(76,76,76));
		//정보를 확인하고 다음 페이지로 넘겨주는 버튼
		bottom.add(bok = new JButton("로그인"));
		bok.setForeground(new Color(255,224,140));
		bok.setBackground(new Color(76,76,76));
		bok.addActionListener(this);
		
		bottom.add(badd = new JButton("추가하기"));
		badd.setForeground(new Color(255,224,140));
		badd.setBackground(new Color(76,76,76));
		badd.addActionListener(this);

		//reset버튼
		bottom.add(breset = new JButton("다시 작성"));
		breset.setForeground(new Color(255,224,140));
		breset.setBackground(new Color(76,76,76));
		breset.addActionListener(this);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				dispose();
				System.exit(0);
			}
		});
		add("Center", center);
		add("South", bottom);
		setSize(400, 300);
		setVisible(true);
	}

	public void setEnable() {
		bok.setEnabled(true);
		badd.setEnabled(true);
		breset.setEnabled(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		ResultSet rs = null;//초기화
		Component c = (Component) e.getSource();//버튼 값 받기
		try {
			if(c == bok) {//로그인 버튼을 눌렀을 때
				cid = user_id.getText();
				//입력하지 않았을 때 
				if(cid == null || cid.length() == 0 ){
					JOptionPane.showMessageDialog(getParent(),"모든 정보를 입력해주세요");
					return ;
				}
				else if(cid.equals("guest")){
					dispose();
					new Student_List();
				}
				else if(cid.equals("professor")){
					dispose();
					new CheckPassword();
				}else
					JOptionPane.showMessageDialog(getParent(),"등록되지 않은 정보 입니다.");
				//잘못 입력했을 경우 모든 것을 초기화
				setEnable();
				user_id.setText("");
				user_id.setEditable(true);
			} else if(c == badd){
				dispose();
				new User_Add();
			} else if(c == breset){
				//reset에 되게 설정
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
