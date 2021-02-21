package vue;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import controleur.Controle;

public class EntreeJeu extends JFrame {

	private JPanel contentPane;
	private JTextField txfIP;
	
	private Controle controle;
	

	public void clicConnect()
	{
		controle.evenementEntreejeu( txfIP.getText() );
	}
	
	public void clicStart()
	{
		controle.evenementEntreejeu("server");
	}
	
	public void clicExit() 
	{
		controle.evenementEntreejeu("Exit");
	}
	

	/**
	 * Create the frame.
	 */
	public EntreeJeu(Controle controle) {
		
		this.controle = controle;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblStartServer = new JLabel("Start a Server :");
		lblStartServer.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStartServer.setBounds(10, 11, 129, 31);
		contentPane.add(lblStartServer);
		
		JLabel lblExistingServer = new JLabel("Connect an existing server :");
		lblExistingServer.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblExistingServer.setBounds(10, 53, 201, 31);
		contentPane.add(lblExistingServer);
		
		JLabel lblIpServer = new JLabel("IP server :");
		lblIpServer.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblIpServer.setBounds(10, 95, 85, 31);
		contentPane.add(lblIpServer);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clicStart();
			}
		});
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnStart.setBounds(253, 15, 136, 23);
		contentPane.add(btnStart);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clicConnect();
			}
		});
		btnConnect.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnConnect.setBounds(253, 57, 136, 23);
		contentPane.add(btnConnect);
		
		txfIP = new JTextField();
		txfIP.setBounds(91, 95, 136, 27);
		contentPane.add(txfIP);
		txfIP.setColumns(10);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clicExit();
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnExit.setBounds(253, 129, 136, 23);
		contentPane.add(btnExit);
		
		txfIP.setText("127.0.0.1");
	}
}
