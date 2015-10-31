import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

public class Add_Student extends JFrame implements ActionListener {
   
   JTable table;
   JTextField  Stu_id,Stu_name,Stu_dept,Stu_phone;
   JButton     ok,cancel;
   JLabel ltop, lid, lname, ldept,lpnum;
   Connection conn;
   DefaultTableModel dtm;
   Statement  stmt;
   int        cmd;
   ResultSet rs;
   ResultSetMetaData rsmd;
   
   public Add_Student() {
      super("ADD");
      setLayout(new BorderLayout());
      
      JPanel top = new JPanel();
      top.setBackground(new Color(76,76,76));
      ltop = new JLabel("�л� ���");
      ltop.setOpaque(true);
      ltop.setBackground(new Color(76,76,76));
      ltop.setForeground(new Color(255,224,140));
      ltop.setFont(new Font("HY������M",Font.BOLD,20));
      top.add(ltop);
      
      JPanel center = new JPanel();
      center.setBackground(new Color(153,153,153));
      center.setLayout(new GridLayout(4,1));
      
      JPanel pid = new JPanel();
      pid.setBackground(new Color(153,153,153));
      pid.add(lid = new JLabel("ID"));
      lid.setBackground(new Color(153,153,153));
      lid.setForeground(new Color(255,224,140));
      lid.setFont(new Font("HY������M",Font.PLAIN,15));
      Stu_id=new JTextField(10);
      pid.add(Stu_id);
      
      JPanel pname = new JPanel();
      pname.setBackground(new Color(153,153,153));
      pname.add(lname=new JLabel("NAME"));
      lname.setBackground(new Color(153,153,153));
      lname.setForeground(new Color(255,224,140));
      lname.setFont(new Font("HY������M",Font.PLAIN,15));
      pname.add(Stu_name = new JTextField(12));
      
      JPanel pdept = new JPanel();
      pdept.setBackground(new Color(153,153,153));
      pdept.add(ldept=new JLabel("DEPARTMENT"));
      ldept.setBackground(new Color(153,153,153));
      ldept.setForeground(new Color(255,224,140));
      ldept.setFont(new Font("HY������M",Font.PLAIN,15));
      pdept.add(Stu_dept = new JTextField(12));
      
      JPanel ppnum = new JPanel();
      ppnum.setBackground(new Color(153,153,153));
      ppnum.add(lpnum=new JLabel("PHONE NUMBER"));
      lpnum.setBackground(new Color(153,153,153));
      lpnum.setForeground(new Color(255,224,140));
      lpnum.setFont(new Font("HY������M",Font.PLAIN,15));
      ppnum.add(Stu_phone = new JTextField(12));
           
      JPanel bottom = new JPanel();
      bottom.setBackground(new Color(76,76,76));
      bottom.setLayout(new GridLayout(1,2));
      
      JPanel pok = new JPanel();
      pok.setBackground(new Color(76,76,76));
      pok.add(ok = new JButton("�߰��ϱ�"));
      ok.setBackground(new Color(76,76,76));
      ok.setForeground(new Color(255,224,140));
      ok.setFont(new Font("HY������M",Font.PLAIN,15));
      ok.addActionListener(this);
      
      JPanel pcancel = new JPanel();
      pcancel.setBackground(new Color(76,76,76));
      pcancel.add(cancel = new JButton("���ư���"));
      cancel.setBackground(new Color(76,76,76));
      cancel.setForeground(new Color(255,224,140));
      cancel.setFont(new Font("HY������M",Font.PLAIN,15));
      cancel.addActionListener(this);   
      
      center.add(pid);
      center.add(pname);
      center.add(pdept);
      center.add(ppnum);
      
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
      add("North",top);
      add("Center", center);
      add("South",bottom);
     
      DBcon();
      
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(600, 400);
      setVisible(true);
   }

   private void DBcon() {
      try {
          //JDBC ����̹��� DriverManager�� ���
         Class.forName("com.mysql.jdbc.Driver");
         String url = "jdbc:mysql://localhost/Student_Management";
         //�ش� Driver�� ���� Connection ��ü ȹ��
         conn = DriverManager.getConnection(url, "sogong", "1234");
         //Connection ��ü�� ���� Statement ��ü ȹ��
         stmt = conn.createStatement();
         initialize();
         System.out.println("�����ͺ��̽� ����");
      } catch (Exception e) {
         e.printStackTrace(System.out);
      }
   }

   public void initialize() {
      Stu_id.setEditable(true);
      Stu_name.setEditable(true);
      Stu_dept.setEditable(true);
      Stu_phone.setEditable(true);
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

   public void clear() {
     Stu_id.setText("");
     Stu_name.setText("");
     Stu_dept.setText("");
     Stu_phone.setText("");
   }

   public void actionPerformed(ActionEvent e) {
      
      Component c = (Component) e.getSource();
      try {
         if(c==ok){
               String cid = Stu_id.getText().trim();
               String cname = Stu_name.getText().trim();
               String cdept = Stu_dept.getText().trim();
               String cpnum = Stu_phone.getText().trim();
               //��� ������ �Է��Ͽ��� Ȯ��
               if(cid == null || cname == null || cdept == null || cpnum == null ||
                  cid.length() == 0 || cname.length() == 0 || cdept.length() == 0 || cpnum.length() == 0){
                     JOptionPane.showMessageDialog(getParent(),"��� ������ �Է����ּ���");
                     return ;
               }
      
            else{
               String query ="insert into Student (ID,Name,Department,Phone_Number)" +
               		" values ("+cid+","+cname+","+cdept+","+cpnum+")"; 
               stmt = conn.createStatement();
               stmt.executeUpdate(query);
               
               JOptionPane.showMessageDialog(getParent(),"�л� ��� �Ϸ�");
               
               dispose();
               new Student_List();
            }
         }
         else if(c==cancel){
        	 dispose();
        	 new Student_List();
         }
      
      }catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            
            }
            return;
         }
            
}