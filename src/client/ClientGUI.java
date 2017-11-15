package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class ClientGUI extends JFrame {
	ButtonsAdapter buttonsAdapter;
	Client client;
	JButton submitButton;
	JTextField inputField;
	JLabel outputField;
	
	public ClientGUI(Client client) {
		super("Chinese Checkers");
		this.client = client;
		buttonsAdapter = new ButtonsAdapter(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		setSize(600, 600);
		
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		JPanel p2 = new JPanel();
		
		inputField = new JTextField();
		inputField.setPreferredSize(new Dimension(200, 20));
		submitButton = new JButton("Send");
		submitButton.addActionListener(buttonsAdapter);
		
		outputField = new JLabel();
		
		p1.add(inputField);
		p1.add(submitButton);
		p2.add(outputField);
		this.add(p1);
		this.add(p2);
		
		setVisible(true);
	}
	
	String sendString() {
		String s = inputField.getText();
		return s;
	}
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				new ClientGUI();
//			}
//		});
//	}
}
