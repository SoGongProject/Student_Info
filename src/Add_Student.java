import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

public class Add_Student extends JFrame implements ActionListener {
   public static final int  NONE   = 0;
   public static final int  ADD    = 1;
   public static final int  UPDATE = 2;
   public static final int  DELETE = 3;
   public static final int  TOTAL = 4;
   
   JTable table;
   JTextField  Stu_id,Stu_name,Stu_dept,Stu_phone;
   JButton     ok;
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
      ltop = new JLabel("학생 등록");
      ltop.setOpaque(true);
      ltop.setBackground(new Color(76,76,76));
      ltop.setForeground(new Color(255,224,140));
      ltop.setFont(new Font("HY헤드라인M",Font.BOLD,20));
      top.add(ltop);
      
      JPanel center = new JPanel();
      center.setBackground(new Color(153,153,153));
      center.setLayout(new GridLayout(5,1));
      
      JPanel pid = new JPanel();
      pid.setBackground(new Color(153,153,153));
      pid.add(lid = new JLabel("ID"));
      lid.setBackground(new Color(153,153,153));
      lid.setForeground(new Color(255,224,140));
      lid.setFont(new Font("HY헤드라인M",Font.PLAIN,15));
      Stu_id=new JTextField(10);
      pid.add(Stu_id);
      
      JPanel pname = new JPanel();
      pname.setBackground(new Color(153,153,153));
      pname.add(lname=new JLabel("NAME"));
      lname.setBackground(new Color(153,153,153));
      lname.setForeground(new Color(255,224,140));
      lname.setFont(new Font("HY헤드라인M",Font.PLAIN,15));
      pname.add(Stu_name = new JTextField(12));
      
      JPanel pdept = new JPanel();
      pdept.setBackground(new Color(153,153,153));
      pdept.add(ldept=new JLabel("DEPARTMENT"));
      ldept.setBackground(new Color(153,153,153));
      ldept.setForeground(new Color(255,224,140));
      ldept.setFont(new Font("HY헤드라인M",Font.PLAIN,15));
      pdept.add(Stu_dept = new JTextField(12));
      
      JPanel ppnum = new JPanel();
      ppnum.setBackground(new Color(153,153,153));
      ppnum.add(lpnum=new JLabel("PHONE NUMBER"));
      lpnum.setBackground(new Color(153,153,153));
      lpnum.setForeground(new Color(255,224,140));
      lpnum.setFont(new Font("HY헤드라인M",Font.PLAIN,15));
      ppnum.add(Stu_phone = new JTextField(12));
      
      JPanel pok = new JPanel();
      pok.setBackground(new Color(153,153,153));
      pok.add(ok = new JButton("등록"));
      ok.setBackground(new Color(76,76,76));
      ok.setForeground(new Color(255,224,140));
      ok.setFont(new Font("HY헤드라인M",Font.PLAIN,15));
      ok.addActionListener(this);
      
      center.add(pid);
      center.add(pname);
      center.add(pdept);
      center.add(ppnum);
      center.add(pok);
      
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
      
      cmd = NONE;
      DBcon();
      
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(600, 400);
      setVisible(true);
   }

   private void DBcon() {
      try {
          //JDBC 드라이버를 DriverManager에 등록
         Class.forName("com.mysql.jdbc.Driver");
         String url = "jdbc:mysql://localhost/Student_Management";
         //해당 Driver로 부터 Connection 객체 획득
         conn = DriverManager.getConnection(url, "sogong", "1234");
         //Connection 객체로 부터 Statement 객체 획득
         stmt = conn.createStatement();
         initialize();
         System.out.println("데이터베이스 연결");
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
               //모든 정보를 입력하였나 확인
               if(cid == null || cname == null || cdept == null || cpnum == null ||
                  cid.length() == 0 || cname.length() == 0 || cdept.length() == 0 || cpnum.length() == 0){
                     JOptionPane.showMessageDialog(getParent(),"모든 정보를 입력해주세요");
                     return ;
               }
      
            else{
               String query ="insert into Student (ID,Name,Department,Phone_Number) values ("+cid+","+cname+","+cdept+","+cpnum+")"; 
               stmt = conn.createStatement();
               stmt.executeUpdate(query);
               
               JOptionPane.showMessageDialog(getParent(),"학생 등록 완료");
               
               dispose();
               new Student_List();
            }
         }
      
      }catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            
            }
            return;
         }
            
   
public static void main(String args[]) {
      new Add_Student();
   }
}