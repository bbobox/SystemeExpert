package interface_utilisateur;


import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/**
 * 
 * @author thibaut
 * Ma classe window hérité de JFrame
 *
 */
public class Window extends JFrame {
	
	private static final long serialVersionUID = 8799814155423117081L;
	private BorderLayout layout;
	private CentralWidget cw;
	
	private Window() throws IOException
    {
		layout = new BorderLayout();
		cw = new CentralWidget();
        initUI();
    }
	private void initUI(){
		this.setLayout(layout);
		this.setTitle("System Expert Moteur O/O+");
	    // Taille de la frame
	    this.setSize(1024, 768);
	    // Placer au centre de l'ecran
	    this.setLocationRelativeTo(null);
	    // Resizable ou non
	    this.setResizable(true);
        this.add(cw, BorderLayout.CENTER);
        // Action a la fermeture (croix)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        this.setVisible(true);
	}

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
					new Window();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }

}
