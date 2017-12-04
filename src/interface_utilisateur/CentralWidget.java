package interface_utilisateur;



import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import Moteur.Fait;
import Moteur.Systeme;

public class CentralWidget extends JPanel {
	
	private JComboBox<String> chainageChoix;
	private JButton btnGo;
	private JButton btnRegle, btnFait;
	private JLabel lblBut;
	
	private String pathRegle, pathFait;
	private Systeme se;
	
	private JPanel grandPanel;
	private JPanel zoneGauche;
	private JPanel zoneGaucheBas;
	private JPanel zoneDroit;
	private JPanel zoneDroitHaut;
	private JPanel zoneDroitBas;
	private JPanel zoneGaucheHaut;
	
	
	private Image loupe;
	
	JLabel labeLoupe;
	
	private JTextField txtField;
	//private TextAreaBackground txtArea;
	private JEditorPane txtArea;
	
	private ImageIcon diagIcon;
	private JScrollPane scrollPane;
	
	public CentralWidget() throws IOException{
		se = new Systeme();
		
		//loupe.g
		//loupe.paint(arg0)
		
		//BufferedImage wPic = ImageIO.read(this.getClass().getResource("loupe.png"));
		//JLabel wIcon = new JLabel(new ImageIcon(wPic));
		
		//URL url = getClass().getResource("loupe.png");
		//Image img = ImageIO.read(url);
		
		 
		loupe = ImageIO.read(new File("loupe.png"));
		labeLoupe = new JLabel(new ImageIcon(loupe));
		 
		JPanel panel = new JPanel(new BorderLayout());
		//panel.add(viewer)
		
		txtArea= new JEditorPane();
		txtArea.setEditable(false);
		
		btnRegle = new JButton("Choisir fichier de règles");
		btnRegle.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				chooseFile(true);
			}
		});
		btnFait = new JButton("Choisir fichier de faits");
		btnFait.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				chooseFile(false);
			}
		});
		
		btnGo = new JButton("Diagnostiquer");
		btnGo.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				boolean ret = diagnose();
				if (ret){
					/*txtArea.setText(se.recuPNiveauxProfondeur()+"\n\n \n Le but \""+txtField.getText()+"\" a été atteint");
					try {
						loupe = ImageIO.read(new File("ok.png")); labeLoupe= new JLabel(new ImageIcon(loupe));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();*/
					}
				else
					//txtArea.setText(se.recuPNiveauxProfondeur()+"\n\n \n Le but \""+txtField.getText()+"\" n'a pas été atteint");
				//txtArea.setText("\n"+se.stringBaseFait());
				se.affichebaseFait();
				//txtArea.setImage(ret ? "ok.png" : "no.png");
				txtArea.setCaretPosition(txtArea.getDocument().getLength());
			}
		});
		
		lblBut = new JLabel("Saisir But:", SwingConstants.RIGHT);
		txtField = new JTextField(30);
		Dimension dim=new Dimension();
		dim.setSize(40, 30);
		txtField.setPreferredSize(dim);
		
		chainageChoix = new JComboBox<String>();
		chainageChoix.addItem("Chainage avant (largeur)");
		chainageChoix.addItem("Chainage avant (profondeur)");
		chainageChoix.addItem("Chainage arrière");
		
		
		grandPanel= new JPanel(new GridLayout(1, 2));
		zoneGauche=new JPanel(new GridLayout(2, 1));
		zoneDroit= new JPanel(new GridLayout(2,1));

		zoneGaucheHaut= new JPanel(new GridLayout(3, 1));
		zoneGaucheHaut.add(btnRegle);
		zoneGaucheHaut.add(btnFait);
		zoneGaucheHaut.add(chainageChoix);
		
		JPanel zoneGaucheBasHaut= new JPanel(new GridLayout(2, 1));
		
		zoneGaucheBas=new JPanel(new GridLayout(2, 1));
		JPanel saisieBut= new JPanel();
		saisieBut.add(lblBut);
		saisieBut.add(txtField);
		
		
		zoneGaucheBasHaut.add(saisieBut); zoneGaucheBasHaut.add(btnGo);
		zoneGaucheBas.add(zoneGaucheBasHaut);
		
		zoneGauche.add(zoneGaucheHaut);
		zoneGauche.add(zoneGaucheBas);
	//	zoneGauche.add(btnGo);
		
	
		//saisieBut.sz

		
		zoneDroitHaut=new JPanel(new GridLayout(1, 1));
		
		zoneDroitHaut.setBackground(Color.WHITE);
		zoneDroitHaut.add(labeLoupe);
		//zoneDroitHaut.add(txtArea);
		
		
	//	zoneDroitHaut.add(saisieBut);
		
		
		//txtArea = new TextAreaBackground("interrogative_dot.png");
		/*txtArea.setEditable(false);
		scrollPane = new JScrollPane(txtArea);
		
		zoneDroitBas.add(scrollPane);*/
		
		scrollPane = new JScrollPane(txtArea);
		
		zoneDroitBas= new JPanel(new GridLayout(1, 1));
		zoneDroitBas.add(scrollPane);
		
		zoneDroit.add(zoneDroitHaut);
		zoneDroit.add(zoneDroitBas);
		
		grandPanel.add(zoneGauche);
		grandPanel.add(zoneDroit);
		setLayout(new BorderLayout());
		add(grandPanel);
		
	}
	
	private void chooseFile(boolean regleFile){
		// Ouverture du Gestionnaire de fichier
		JFileChooser choix = new JFileChooser();
		choix.setDialogTitle("Choisir le fichier .txt de " + (regleFile ? "règles" : "faits"));
		choix.addChoosableFileFilter(new FileNameExtensionFilter("Fichier txt", "txt"));
		int retour = choix.showOpenDialog(null);			 
		if (retour == JFileChooser.APPROVE_OPTION) {
			if (regleFile){
				pathRegle = choix.getSelectedFile().getAbsolutePath();
			}
			else{
				pathFait = choix.getSelectedFile().getAbsolutePath();
			}
			txtArea.setText("\nFichier de " + (regleFile ? "règles:"+pathRegle : "faits:"+pathFait));
		    choix = null;
		}
	}
	
	private boolean diagnose(){
		se.clearLists();
		se.fillRegle(pathRegle);
		se.fillFait(pathFait);
		if (chainageChoix.getItemAt(chainageChoix.getSelectedIndex()).contains("largeur")){
			return se.chainAvantLargeur(new Fait(txtField.getText(), ""));
		}
		else if (chainageChoix.getItemAt(chainageChoix.getSelectedIndex()).contains("profondeur")){
			
			boolean okprof =se.chainAvantProfondeur(new Fait(txtField.getText(), ""));
			txtArea.setText(se.recuPNiveauxProfondeur());
			return okprof;
		}
		else{
			boolean ok= se.chainageArriere(new Fait(txtField.getText(), ""));
			txtArea.setText(se.recupTraceChainageArriere());
			
			return ok;
		}
	}
	
	
    /*protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters            
    }*/
	
	
	

}
