package vue;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChoixJoueur extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnPrecedent;
	private JButton btnSuivant;
	private JButton btnGo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChoixJoueur frame = new ChoixJoueur();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChoixJoueur() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFond = new JLabel("");
		lblFond.setIcon(new ImageIcon(ChoixJoueur.class.getResource("/fonds/fondchoix.jpg")));
		lblFond.setBounds(0, 0, 400, 261);
		contentPane.add(lblFond);
		
		textField = new JTextField();
		textField.setBounds(144, 241, 119, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnPrecedent = new JButton("");
		btnPrecedent.setOpaque(false);
		btnPrecedent.setContentAreaFilled(false);
		btnPrecedent.setBorderPainted(false);
		btnPrecedent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Précédent");
			}
		});
		btnPrecedent.setBounds(65, 138, 31, 47);
		contentPane.add(btnPrecedent);
		
		btnSuivant = new JButton("New button");
		btnSuivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Suivant");
			}
		});
		btnSuivant.setOpaque(false);
		btnSuivant.setContentAreaFilled(false);
		btnSuivant.setBorderPainted(false);
		btnSuivant.setBounds(300, 138, 31, 47);
		contentPane.add(btnSuivant);
		
		btnGo = new JButton("");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Arene.main(null);
			}
		});
		btnGo.setOpaque(false);
		btnGo.setContentAreaFilled(false);
		btnGo.setBorderPainted(false);
		btnGo.setBounds(310, 184, 68, 66);
		contentPane.add(btnGo);
	}
}
