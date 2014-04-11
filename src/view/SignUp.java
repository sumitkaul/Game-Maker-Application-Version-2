package view;

import gameServer.UserInfo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.naming.ServiceUnavailableException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import utility.Encrypt;

public class SignUp {
	
	private JFrame signupFrame;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	
	private JLabel emailLabel;
	private JLabel nameLabel;
	
	private JTextField usernameText;
	private JPasswordField passwordText;
	
	private JTextField emailText;
	private JTextField nameText;

	
	private JButton signupButton;
	private JPanel contentPane;

	/*
	public static void main(String[] args) {
		new SignUp();
	}*/

	
	public SignUp() {
		create();
		signupFrame.setVisible(true);
	}

	private void create() {
		signupFrame = new JFrame();
		usernameLabel = new JLabel();
		passwordLabel = new JLabel(); 
		
		//emailLabel = new JLabel();
		//nameLabel = new JLabel();
		
		usernameText = new JTextField();
		passwordText = new JPasswordField();
		
		//emailText = new JTextField();
		//nameText = new JTextField();
		
		signupButton = new JButton();
		contentPane = new JPanel();

		usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		usernameLabel.setForeground(new Color(0, 0, 255));
		usernameLabel.setText("Username:");
		passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		passwordLabel.setForeground(new Color(0, 0, 255));
		passwordLabel.setText("Password:");							
		

		/*emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
		emailLabel.setForeground(new Color(0, 0, 255));
		emailLabel.setText("Email:");
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		nameLabel.setForeground(new Color(0, 0, 255));
		nameLabel.setText("Name:");*/
		
		usernameText.setForeground(new Color(0, 0, 255));
		usernameText.setSelectedTextColor(new Color(0, 0, 255));
		usernameText.setToolTipText("Enter your username");
		usernameText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==10) {
					
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			@Override
			public void keyTyped(KeyEvent arg0) {				
			}
		});
		
				
		passwordText.setForeground(new Color(0, 0, 255));
		passwordText.setToolTipText("Enter your password");
		passwordText.addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(KeyEvent arg0) {
					if(arg0.getKeyCode()==10) {

					}
				}
				@Override
				public void keyReleased(KeyEvent arg0) {
				}
				@Override
				public void keyTyped(KeyEvent arg0) {				
				}
			});
		
		
		/*emailText.setForeground(new Color(0, 0, 255));
		emailText.setSelectedTextColor(new Color(0, 0, 255));
		emailText.setToolTipText("Enter your email");
		emailText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==10) {

				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			@Override
			public void keyTyped(KeyEvent arg0) {				
			}
		});
		
		nameText.setForeground(new Color(0, 0, 255));
		nameText.setSelectedTextColor(new Color(0, 0, 255));
		nameText.setToolTipText("Enter your name");
		nameText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==10) {

				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			@Override
			public void keyTyped(KeyEvent arg0) {				
			}
		});*/
		
		
		signupButton.setBackground(new Color(204, 204, 204));
		signupButton.setForeground(new Color(0, 0, 255));
		signupButton.setText("Signup");
		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserInfo userInfo = new UserInfo();

				//if(!(usernameText.getText().equalsIgnoreCase("") && passwordText.getText().equalsIgnoreCase("") && emailText.getText().equalsIgnoreCase("") && nameText.getText().equalsIgnoreCase(""))){
				if(!(usernameText.getText().equalsIgnoreCase("") && passwordText.getText().equalsIgnoreCase(""))){	
				
					userInfo.setUsername(usernameText.getText());
					String encryptPassword="";
					try {
						encryptPassword = Encrypt.encrypt(passwordText.getText());
					} catch (ServiceUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					userInfo.setPassword(encryptPassword);
					
					Configuration configuration = new Configuration();
				    configuration.configure();
				    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry(); 
				    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(serviceRegistry);
					Session session = sessionFactory.openSession(); 
					session.beginTransaction();
					session.save(userInfo);
					session.getTransaction().commit();
					
					signupFrame.setVisible(false);
					MainLogin.getLoginFrame().setVisible(false);
					Design.getInstance();
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter all the values required in order to sign up !");
				} 
			}
		});

		contentPane.setLayout(null);
		contentPane.setBorder(BorderFactory.createEtchedBorder());
		contentPane.setBackground(new Color(204, 204, 204));
		addComponent(contentPane, usernameLabel, 5, 10, 106, 18);
		addComponent(contentPane, passwordLabel, 5, 47, 97, 18);
		
		/*addComponent(contentPane, emailLabel, 5, 84, 106, 18);
		addComponent(contentPane, nameLabel, 5, 121, 97, 18);
		*/
		addComponent(contentPane, usernameText, 110, 10, 183, 22);
		addComponent(contentPane, passwordText, 110, 45, 183, 22);
		
		/*addComponent(contentPane, emailText, 110, 80, 183, 22);
		addComponent(contentPane, nameText, 110, 115, 183, 22);*/
		
		//addComponent(contentPane, loginButton, 50, 165, 83, 28);
		addComponent(contentPane, signupButton, 150, 165, 83, 28);

		signupFrame.setTitle("Sign Up Page");
		signupFrame.setLocation(new Point(500, 300));
		signupFrame.setSize(new Dimension(350, 300));
		signupFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		signupFrame.setResizable(false);
		signupFrame.setContentPane(contentPane);
	}

	/** Add Component Without a Layout Manager (Absolute Positioning) */
	private void addComponent(JPanel contentPane, Component c, int x, int y, int width, int height) {
		c.setBounds(x, y, width, height);
		contentPane.add(c);
	}

}