import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.jdbc.Statement;


class Login extends JFrame implements ActionListener{
	JPanel panel_top;
	JPanel panel_center;
	JPanel panel_bottom;
	JPanel last;
	JLabel username;
	JTextField username_txt;
	JLabel userpwd;
	JPasswordField pwd;
	JButton ok;
	JButton cancel;
	JLabel result;
	Login(String name){
		super(name);
		setBounds(700,500,240,200);
		setVisible(true);
		setResizable(false);
		Container con = this.getContentPane();
		con.setLayout(new GridLayout(4,0));
		panel_top=new JPanel();
		panel_center = new JPanel();
		panel_bottom = new JPanel();
		last= new JPanel();
		username = new JLabel("用户名",10);
		username.setBounds(10,10,30,30);
		username_txt= new JTextField("请输入您的用户名");
		username_txt.setColumns(10);
		username_txt.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				username_txt.setBackground(Color.LIGHT_GRAY);
			}
			public void mouseClicked(MouseEvent e){
				username_txt.setText("");
				result.setText("");
				username_txt.setBackground(Color.WHITE);
			}
			public void mouseExited(MouseEvent e){
				username_txt.setBackground(Color.WHITE);
			}
		});
		username_txt.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					pwd.requestFocusInWindow();
				}
			}
		});
		userpwd=new JLabel("密码");
		userpwd.setBounds(10, 20, 30, 30);
		pwd = new JPasswordField();
		pwd.setColumns(10);
		pwd.setEchoChar('*');
		
		pwd.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					ok.requestFocusInWindow();
				}
			}
		});
		
		ok = new JButton("确定");
		ok.addActionListener(this);
		cancel = new JButton("取消");
		cancel.addActionListener(this);
		result=new JLabel();
		panel_top.add(username);
		panel_top.add(username_txt);
		panel_center.add(userpwd);
		panel_center.add(pwd);
		panel_bottom.add(ok);
		panel_bottom.add(cancel);
		last.add(result);
		con.add(panel_top);
		con.add(panel_center);
		con.add(panel_bottom);
		con.add(last);
		con.validate();
		validate();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==ok){
			result.setText("正在验证登陆");
			String name=username_txt.getText().trim();
			String password=pwd.getText().trim();
			Connection con=null;
			String sql=null;
			ResultSet rs=null;
			java.sql.Statement st=null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				try {
					con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user", "root", "000000");
					st=con.createStatement();
					sql="select * from login where username='"+name+"'";
					result.setText("运行到这里 ok");
					rs = st.executeQuery(sql);
					if(rs.next()){
						if(password.equals(rs.getString("userpwd"))){
							result.setText("验证通过");
						}else{
							result.setText("密码错误，请重新登陆！");
							pwd.setText("");
						}
					}else{
						result.setText("用户名错误，请重新登陆！");
						username_txt.setText("");
						pwd.setText("");
					}
						rs.close();
						con.close();
					}
				 catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(e.getSource()==cancel){
			System.exit(0);
		}//note the position of else
	}
	}


public class TestLogin {
	public static void main(String[] args){
		System.out.println("欢迎！");
		new Login("登陆");
	}
}
