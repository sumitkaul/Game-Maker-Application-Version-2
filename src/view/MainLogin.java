package view;

import gameMaker.Main;
import gameServer.UserInfo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.naming.ServiceUnavailableException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;


import testClient.GameClient;
import utility.Constants;
import utility.Encrypt;


public class MainLogin {

	private static JFrame loginFrame;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField usernameText;
	private JPasswordField passwordText;
	private JButton loginButton;
	private JButton signupButton;
	private JPanel contentPane;
	private String username;
	private String password;
	JRadioButton playerButton;
	JRadioButton makerButton;
	private ButtonGroup group;
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(MainLogin.class);

	/*public static void main(String[] args) {
		new MainLogin();
	}*/

	public MainLogin() {
		create();
		loginFrame.setVisible(true);
	}

	public static JFrame getLoginFrame(){
		return loginFrame;
	}

	private void create() {
		loginFrame = new JFrame();
		playerButton  = new JRadioButton("Play Game"  , true);
		playerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Constants.isGameMaker = false;

			}
		});
		makerButton  = new JRadioButton("Make Game"   , false);
		makerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Constants.isGameMaker = true;
				
			}
		});
		group = new ButtonGroup();
		group.add(makerButton);
		group.add(playerButton);
		makerButton.setSelected(true);
		
		usernameLabel = new JLabel();
		passwordLabel = new JLabel();
		usernameText = new JTextField();
		passwordText = new JPasswordField();
		loginButton = new JButton();
		signupButton = new JButton();
		contentPane = new JPanel();

		usernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		usernameLabel.setForeground(new Color(0, 0, 255));
		usernameLabel.setText("Username:");
		passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		passwordLabel.setForeground(new Color(0, 0, 255));
		passwordLabel.setText("Password:");

		usernameText.setForeground(new Color(0, 0, 255));
		usernameText.setSelectedTextColor(new Color(0, 0, 255));
		usernameText.setToolTipText("Enter your username");
		usernameText.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==10) {
					login_check();
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
					login_check();
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			@Override
			public void keyTyped(KeyEvent arg0) {				
			}
		});

		loginButton.setBackground(new Color(204, 204, 204));
		loginButton.setForeground(new Color(0, 0, 255));
		loginButton.setText("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login_check();
			}
		});

		signupButton.setBackground(new Color(204, 204, 204));
		signupButton.setForeground(new Color(0, 0, 255));
		signupButton.setText("Signup");
		signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginFrame.setVisible(false);
				new SignUp();
			}
		});

		contentPane.setLayout(new GridLayout(4,2,20,10));
		contentPane.setBorder(BorderFactory.createEtchedBorder());

		contentPane.add( makerButton );
		
		contentPane.add( playerButton );
		contentPane.add( usernameLabel );
		contentPane.add( usernameText );
		contentPane.add( passwordLabel );
		contentPane.add( passwordText );
		contentPane.add(loginButton);
		contentPane.add(signupButton);

		loginFrame.setTitle("Login");
		loginFrame.setLocation(new Point(500, 300));
		loginFrame.setSize(new Dimension(400, 150));
		loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		loginFrame.setResizable(false);
		loginFrame.setContentPane(contentPane);
	}

	/** Add Component Without a Layout Manager (Absolute Positioning) */
	private void addComponent(JPanel contentPane, Component c, int x, int y, int width, int height) {
		c.setBounds(x, y, width, height);
		contentPane.add(c);
	}

	private void login_check() {
		this.username = new String(usernameText.getText());
		this.password = new String(passwordText.getPassword());
		String encryptPassword="";
		UserInfo userInfo = new UserInfo();
		try {
			encryptPassword = Encrypt.encrypt(password);
		} catch (ServiceUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (username.equals("") || password.equals("")) {
			loginButton.setEnabled(false);
			JLabel errorMsg = new JLabel(
					"Please enter a username and password to login");
			JOptionPane.showMessageDialog(null, errorMsg);
			usernameText.setText("");
			passwordText.setText("");
			loginButton.setEnabled(true);
			loginFrame.setVisible(true);
		} else {

			/**/
			GameClient gameClient = new GameClient();
			gameClient.setUsername(username);
			gameClient.setPassword(encryptPassword);
			LOG.debug("checking login");
			boolean ret = gameClient.checkLogin();
			LOG.debug("login returned " + ret);
			/**/

			if(gameClient.checkLogin()) {
				loginFrame.setVisible(false);
				Design.getInstance();
			}
			else {
				loginButton.setEnabled(false);
				JLabel errorMsg = new JLabel(
						"Invalid username/password combination");
				JOptionPane.showMessageDialog(null, errorMsg);
				usernameText.setText("");
				passwordText.setText("");
				loginButton.setEnabled(true);
				loginFrame.setVisible(true);
			}		
		}
	}
}
