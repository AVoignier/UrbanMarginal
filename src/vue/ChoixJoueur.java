package vue;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.Controle;
import controleur.Global;
import outils.son.Son;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class ChoixJoueur extends JFrame implements Global {

	private static final int NB_MAX_PERSO = 3;
	
	private Controle controle;
	private int idPerso;
	
	private JPanel contentPane;
	private JTextField txfPseudo;
	private JButton btnPrecedent;
	private JButton btnSuivant;
	private JButton btnGo;
	private JLabel lblPersonnage;

	private Son sonBienvenue;
	private Son sonPrecedent;
	private Son sonSuivant;
	private Son sonGo;
	
	/**
	 * Create the frame.
	 */
	public ChoixJoueur(Controle controle ) {
		this.controle = controle;
		this.idPerso = 1;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblPersonnage = new JLabel("");
		lblPersonnage.setHorizontalAlignment(SwingConstants.CENTER);
		lblPersonnage.setBounds(144, 105, 119, 125);
		affichePerso();
		contentPane.add(lblPersonnage);
		
		JLabel lblFond = new JLabel("");
		lblFond.setIcon(new ImageIcon( getClass().getResource( CHEMIN_FONDS+"fondchoix.jpg")));
		lblFond.setBounds(0, 0, 400, 261);
		contentPane.add(lblFond);
		
		txfPseudo = new JTextField();
		txfPseudo.setBounds(140, 239, 125, 20);
		contentPane.add(txfPseudo);
		txfPseudo.setColumns(10);
		
		btnPrecedent = new JButton("");
		btnPrecedent.setOpaque(false);
		btnPrecedent.setContentAreaFilled(false);
		btnPrecedent.setBorderPainted(false);
		btnPrecedent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clicGauche();
			}
		});
		btnPrecedent.setBounds(65, 138, 31, 47);
		contentPane.add(btnPrecedent);
		
		btnSuivant = new JButton("New button");
		btnSuivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clicDroite();
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
				clicGo(txfPseudo.getText());
			}
		});
		btnGo.setOpaque(false);
		btnGo.setContentAreaFilled(false);
		btnGo.setBorderPainted(false);
		btnGo.setBounds(310, 184, 68, 66);
		contentPane.add(btnGo);
		
		MouseListener mouse = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				sourisNormale();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		};
		
		btnGo.addMouseListener(mouse);
		btnPrecedent.addMouseListener(mouse);
		btnSuivant.addMouseListener(mouse);

		sonBienvenue = new Son( getClass().getClassLoader().getResource(CHEMIN_SONS+"welcome.wav") );
		sonPrecedent = new Son( getClass().getClassLoader().getResource(CHEMIN_SONS+"precedent.wav") );
		sonSuivant = new Son( getClass().getClassLoader().getResource(CHEMIN_SONS+"suivant.wav") );
		sonGo = new Son( getClass().getClassLoader().getResource(CHEMIN_SONS+"go.wav") );
		
		sonBienvenue.play();
		
	}
	
	private void clicDroite() 
	{
		System.out.println("Suivant");
		
		idPerso ++;
		
		if(idPerso > NB_MAX_PERSO)
			idPerso = 1;
		
		affichePerso();
		
		sonSuivant.play();
		
	}
	
	private void clicGauche() 
	{
		System.out.println("Precedent");
		
		idPerso --;
		
		if(idPerso == 0)
			idPerso = NB_MAX_PERSO;
		
		affichePerso();
		
		sonPrecedent.play();
	}
	
	private void clicGo(String pseudo) 
	{
		System.out.println("GO");
		if( pseudo.length()>0 )
		{			
			String message = MOT_ECHANGE_CREATION_PERSO + SYMBOLE_SEPARATEUR +pseudo+ SYMBOLE_SEPARATEUR +idPerso;
			controle.evenementChoixJoueur(message);
			
			sonGo.play();
		}
		else 
		{
			System.out.println("pas de pseudo");
			JOptionPane.showMessageDialog(null, "La saisie du pseudo est obligatoire");
		}
	}
	
	public void affichePerso() 
	{
		lblPersonnage.setIcon( new ImageIcon( getClass().getResource(CHEMIN_PERSONNAGES+"perso" + idPerso + "marche1d1.gif" ) ) );
	}
	
	public void sourisNormale() 
	{
		contentPane.setCursor( new Cursor(Cursor.DEFAULT_CURSOR) );
	}
	
	public void sourisDoigt() 
	{
		contentPane.setCursor( new Cursor(Cursor.HAND_CURSOR) );
	}
	
}
