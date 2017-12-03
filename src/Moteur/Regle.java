package Moteur;

import java.util.ArrayList;



/**
 * Une regle est composée d'un ou plus faits( Premisses) et d'une ou plusieurs consequeces appelées Consequences 
 * @author etudiant
 *
 */

public class Regle {
	
	private ArrayList<Fait> premisses; 
	private ArrayList<Fait> consequences;
	
	public Regle(ArrayList<Fait> pr, ArrayList<Fait> cq) {
		premisses = pr;
		consequences = cq;
	}
	
	
	/**
	 * Affichage d'une regle
	 */
	public void afficheRegle() {
		int idx=0;
		for (Fait f : premisses){
			f.afficheFait();
			if (idx++ != premisses.size()-1){
				System.out.print(" & ");				
			}
		}
		System.out.print(" alors ");
		idx = 0;
		for (Fait cq : consequences){
			cq.afficheFait();
			if (idx++ != consequences.size()-1){
				System.out.print(" & ");				
			}
		}
		System.out.println(".");
	}
	
	/**
	 * Recuperation de la liste de premisse d'une Regle
	 */
	public ArrayList<Fait> recupPremisses() {
		return premisses;
	}
	
	/**
	 * Recuperation de la liste des fait deduits
	 */
	public ArrayList<Fait> recupConsequences() {
		return consequences;
	}

}
