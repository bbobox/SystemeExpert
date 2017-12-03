package Moteur;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * 
 * Le lecteur de fichiers permet lire et remplir la base de connaissances
 * @author etudiant
 *
 */
public class LecteurFichier {
	
	public LecteurFichier(){
	}
	
	
	/**
	 * Remplisssage de la base de fait 
	 * 	@param le fichier de description des regles 
	 */
	
	public void  fillBaseRegle( ArrayList<Regle> lRegles, String filePath){
		BufferedReader Lecteur = null;
		String ligne;
		
		try {
			Lecteur = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Regle r = null;
		
		try {
			while ((ligne = Lecteur.readLine()) != null){
				if (!ligne.startsWith("#")){
					String s = ligne.split(":")[1]; 
					String condition, resultat;
					condition = s.split("->")[0]; 
					resultat = s.split("->")[1]; 
					
					String[] premisses_before = condition.split("&"); // premisses_before = {vision=mauvais_loin}
					String[] premisses_after = resultat.split("&");
					ArrayList<Fait> premisses, consequences;
					premisses = new ArrayList<Fait>();
					consequences = new ArrayList<Fait>();
					
					// remplissage Premisses
					for (int i=0; i<premisses_before.length; i++){
						String[] cds = premisses_before[i].split("="); // cds = {vision, mauvais_loin}
						if (cds.length == 1)
							premisses.add(new Fait(cds[0], ""));
						else
							premisses.add(new Fait(cds[0], cds[1]));
					}
					
					// remplissage Consequences
					for (int i=0; i<premisses_after.length; i++){
						String[] cds= premisses_after[i].split("=");
						if(cds.length ==1){
							consequences.add(new Fait(cds[0], ""));
						}
						else{
							consequences.add(new Fait(cds[0],cds[0]));
						}
						
					lRegles.add(new Regle(premisses, consequences));
					}
				
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 *  Remplissage de la base de fait
	 *  @param le fichier de description des faits
	 */
	
	public void fillBaseFait(ArrayList<Fait> lFaits, String filePath){
		BufferedReader Lecteur = null;
		String ligne;
		
		try {
			Lecteur = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while ((ligne = Lecteur.readLine()) != null) {
				if (!ligne.startsWith("#")){
					String[] fait = ligne.split("=");
					if (fait.length>1)
						lFaits.add(new Fait(fait[0], fait[1]));
					else
						lFaits.add(new Fait(fait[0], ""));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
