package Moteur;

import java.util.ArrayList;


/**
 * Le systeme Expert se sert de la base de connaissance ( base et  faits et base de regle ), et utilise son moteur d'inferences
 * 	(chainage avant, chainage arrière) realiser une deduction . 
 * @author etudiant
 *
 */

public class Systeme {
	private ArrayList<Regle> baseRegle; // Base deregle
	private ArrayList<Fait> baseFait; // base de fait
	private LecteurFichier filler; // Lecteur de fichier descripteurs et remplisseur de bases 
	
	private String textTrace;
	private ArrayList<ArrayList<Fait>> niveauxProfondeur; 
	private ArrayList<ArrayList<Fait>> niveauxLargeur; 
	private String traceChainageArrière;
	
	public Systeme( ArrayList<Regle> regles, ArrayList<Fait> faits) {
		baseFait =faits;
		baseRegle = regles;
	}
	
	public Systeme() {
		baseFait = new ArrayList<Fait>();
		baseRegle =new  ArrayList<Regle>();
		filler= new LecteurFichier();
		textTrace=new String();
		niveauxProfondeur=new ArrayList<ArrayList<Fait>>();
		niveauxLargeur=new ArrayList<ArrayList<Fait>>();
		
		traceChainageArrière= new String();
	}
	
	
	/**
	 * Remplissage de la base de Regle
	 * @param filePath
	 */
	public void fillRegle(String filePath) {
		filler.fillBaseRegle(baseRegle, filePath);
	}
	
	
	public String   recuPNiveauxProfondeur(){
		String res= "";
		for(int i=0; i<niveauxProfondeur.size();i++){
			String nivI= "NIVEAU "+String.valueOf(i)+": \n";
			res=res+nivI;
			for(int j=0; j<niveauxProfondeur.get(i).size();j++){
				Fait fJ=niveauxProfondeur.get(i).get(j);
				if(fJ.getValeur().equals(new String(""))==true){
					res=res+"\t -"+fJ.getAttribut()+"\n";
				}
				else{
					res=res+"\t -"+fJ.getAttribut()+"="+fJ.getValeur()+"\n";
				}
			}
		}
		
		return res;
	}
	
	public String recupTraceChainageArriere(){
		
		return traceChainageArrière;
	}
	
	/**
	 * Remplissage de la base de fait
	 * @param filePath
	 */
	public void fillFait(String filePath) {
		filler.fillBaseFait(baseFait, filePath);
		
		
	}
	
	/**
	 * Affichage de la base de regles
	 */

	public void affichebaseRegle() {
		for (Regle r : baseRegle)
			r.afficheRegle();
	}
	
	/**
	 * Fonctipn d'affiche d'une liste de regle
	 */

	public void afficheListRegle(ArrayList<Regle> lR) {
		for (Regle r : lR)
			r.afficheRegle();
	}

	
	/**
	 * Affichage de la base de faits
	 */

	public void affichebaseFait() {
		for (Fait f : baseFait)
			f.afficheFait();
	}
	
	/**
	 * Recuperation de la base de fait en fichier 
	 * @return : Une chaine de caractère comportant tous les elements de la base de faits
	 */
	public String stringBaseFait(){
		String ret = "";
		for (Fait f : baseFait)
			ret += f.toString()+"\n";
		return ret;
	}
	
	/**
	 * On vide la base de connaissance  
	 */
	public void clearLists(){
		baseRegle.clear();
		baseFait.clear();
	}
	
	
	/**
	 * Fonction de remplissage de la base de fait en profondeur:
	 * 
	 */

	public boolean remplissagebaseFaitProfondeur() {
		boolean ret = false;
		int regleSize = baseRegle.size(); // taille de la base de regle;
		Regle regle;
		ArrayList<Fait> listPremisse;
		int i = 0;
		while (i < regleSize && !ret) {
			regle = baseRegle.get(i); // Recuperation de la regle d'indice i
			listPremisse = regle.recupPremisses(); // Recuperation de la liste de premisse de la regle d'indice i
			int nbPremisse = listPremisse.size(); // t coorespond à la taille de liste de premisse de la regle d'indice i
			int j = 0;
			boolean ok = true;
			while (j < nbPremisse && ok) {
				// On parcours la liste de premisse de la regle
				Fait faitJ = listPremisse.get(j); // Premisse d'indice j dans la liste de premisse
				if (!baseFait.contains(faitJ)) {
					// Si le Premisse n'es est pas dans la base fait (conditions d'arret)
					ok = false;
				}
				j++;
			}

			if (ok) { // Si une regle est verifé
				ArrayList<Fait> deducFait = regle.recupConsequences(); // Recuperation deduction
				int cp = 0;
				for (int k = 0; k < deducFait.size(); k++) {
					Fait faitK = deducFait.get(k);
					if (!baseFait.contains(faitK)) {
						baseFait.add(faitK); // Ajout du premise deduit dans la Base de fait s'il n'y est pas
						cp++;
					}
				}
				if (cp > 0) { // Si la liste d'Premisse ajouté dans la base de fait est superieure à Zero
					ret = true;
				} else
					ret = false;
			}
			i++;
		}
		return ret;
	}
	
	/**
	 * 1eère strategie: Chainage en avant: profondeur
	 */

	public boolean chainAvantProfondeur(Fait fait) {
		// 1-On verifie d'abord si le fait à deduire n'est pas deja dans la base  de fait
		boolean ret = true; // variabe boleenne qui renvoi le resulat de la liste;
		niveauxProfondeur.clear();
		ArrayList<Fait> NivZero= new ArrayList<Fait>();
		for(int i=0;i<baseFait.size();i++){
			NivZero.add(baseFait.get(i));
		}
		niveauxProfondeur.add(NivZero);

		if (!baseFait.contains(fait)) { // Si le fait n'est pas dans la base de fait
			ret = false;
		}
		// 2-Remplissage de la base faits
		int t = 0;
		int nbRegle = baseRegle.size(); // nombre de regle contenu dans la base de regles
		int niv = 1;
		int tbfInit = baseFait.size();
		while (!ret && t < nbRegle) {
			// On remplit la base de fait grace à la fonction de remplissage
			boolean testAjout = remplissagebaseFaitProfondeur(); // test prend la valeur booleean retournée par la fonction de remplissage

			// affichage des premisses ajoutés dans la base de fait
			if (testAjout) {
				System.out.print(" Au Niveau" + niv + " : Les faits declenclés sont  :");
				int tbf = baseFait.size();
				ArrayList<Fait> faitsAjout=new ArrayList<Fait>();
				for (int i = tbfInit; i < tbf; i++) {
					baseFait.get(i).afficheFait();
					faitsAjout.add(baseFait.get(i));
				}
				niveauxProfondeur.add(faitsAjout);
				
				System.out.println("");
				niv++;
				tbfInit = tbf;
			}
			/*
			 * Arreter le remplissage de la base de fait une fois que le but est
			 * atteint
			 */
			if (!baseFait.contains(fait)) {
				ret = false;
			} else {
				ret = true;
			}
			t++;
		}
		return ret;
	}

	
	/**
	 * Methode de remplissage de la base de fait dans le chainage Avant en
	 * largeur
	 */
	public ArrayList<Fait> remplissageBaseFaitLargeur() {
		int nbRegle = baseRegle.size(); // taille de la base de regle;
		Regle regle;
		ArrayList<Fait> listPremisse;
		int i = 0;
		/* On parcours la base de regle */
		int cpt = 0;

		ArrayList<Fait> listFait = new ArrayList<Fait>(); // List dans laquelle on stock les fait declenchés

		while (i < nbRegle) {
			regle = baseRegle.get(i); // Recuperation de la regle d'indice i
			listPremisse = regle.recupPremisses(); // Recuperation de la liste de premisse de la regle d'indice i
			int nbPremisse = listPremisse.size(); // t coorespond à la taille de liste de premisse de la regle 4
			int j = 0;
			boolean ok = true;

			while (j < nbPremisse && ok) {
				// On parcours la liste de premisse de la regle
				Fait faitJ = listPremisse.get(j); // Premisse d'indice j dans la liste de premisse
				if (!baseFait.contains(faitJ)) {
					// Si le Premisse est dans la base fait (conditions d'arret)
					ok = false;
				}
				j++;
			}

			if (ok) { // Si une regle est verifé
				ArrayList<Fait> deducFait = regle.recupConsequences(); // Recuperation deduction
				int cp = 0;
				for (int k = 0; k < deducFait.size(); k++) {
					Fait faitK = deducFait.get(k);
					if (!baseFait.contains(faitK)) {
						// baseFait.add(PremisseK); // Ajout du fait declenchés
						// deduit dans la Base de fait s'il n'y est pas
						listFait.add(faitK); // Ajout du fait declenché aussi dans la liste dans la liste
						cp++;
					}
				}
				if (cp > 0) { // Si la liste d'Premisse ajouté dans la base de fait est superieure à Zero
					cpt++;
				}

			}
			i++;
		}
		return listFait;
	}
	
	
	/**
	 * Chaiage avant en largeur
	 */

	public boolean chainAvantLargeur(Fait fait) {
		// 1-On verifie d'abord si le fait à deduire n'est pas deja dans la base de fait
		boolean ret = true; // variabe boleenne qui renvoi le resulat de la liste;
		if (!baseFait.contains(fait)) { // Si le fait n'est pas dans la base de fait
			ret = false;
		}
		// 2-Remplissage de la base faits
		int t = 0;
		int nbRegle = baseRegle.size(); // nombre de regle contenu dans la base de regles
		while (!ret && t < nbRegle) {
			// On remplit la base de fait grace à la fonction de remplissage
			ArrayList<Fait> listPremisse = remplissageBaseFaitLargeur(); // Renvoie la liste des faits ajoutés
			// affichage de ces faits si le nb de faits est superieur à 0
			int nbPremisse = listPremisse.size(); // ta taile de la liste des faits ajoutés
			if (nbPremisse > 0) {
				System.out.print(" Au Niveau" + (t + 1)	+ " : Les faits declenclés sont  :");
				for (int i = 0; i < nbPremisse; i++) {
					listPremisse.get(i).afficheFait();
					if (!baseFait.contains(listPremisse.get(i)))
						baseFait.add(listPremisse.get(i));
				}
				System.out.println("");
			}

			if (!baseFait.contains(fait)) {
				// Si le fait n'est pas dans la base de fait
				ret = false;
			} else {
				ret = true;
			}
			t++;
		}
		return ret;
	}
	
	/*
	 * Chainage arrière
	 */
	

	public boolean chainageArriere(Fait fait) {
		// Utilisation de br
		//ArrayList<Regle> br = baseReglecopie(baseRegle, baseFait);
		ArrayList<Regle> br = new ArrayList<Regle>(baseRegle);
		
		System.out.print("chainage arrière sur: ");
		fait.afficheFait();
		System.out.println();
		
		if(fait.getValeur().equals(new String(""))==true){
			traceChainageArrière=traceChainageArrière+"chainage arrière sur: "+fait.getAttribut()+"\n";
		}
		else{
			traceChainageArrière=traceChainageArrière+"chainage arrière sur: "+fait.getAttribut()+"="+fait.getValeur()+"\n";
		}
		
		boolean ret = true;
		if (baseFait.contains(fait)) {
			ret = true;
		} else {
			/*
			 * Parcours de la base de regle & construction de ensemble des
			 * regles
			 */
			ArrayList<Regle> eRegle = new ArrayList<Regle>(); // Ensemble de regles
			ArrayList<Integer> eRegleTest = new ArrayList<Integer>();
			// ArrayList<boolean> eRtest= new ArrayList<Regle>();
			int nbRegle = br.size(); // la taille de la baseR de regle
			for (int i = 0; i < nbRegle; i++) {
				Regle regle = br.get(i);
				ArrayList<Fait> deductions = regle.recupConsequences();
				if (deductions.contains(fait)) { // Si est appartient à la conclusion de la regle
					eRegle.add(regle);
				}
			}

			// affiche des regles qui declenche le fait à verififier
			System.out.print("affichage des regles qui declenchent : "); fait.afficheFait(); System.out.println();
			
			if(fait.getValeur().equals(new String(""))==true){
				traceChainageArrière=traceChainageArrière+"affichage des regles qui declenchent : "+fait.getAttribut()+"\n";
			}
			else{
				traceChainageArrière=traceChainageArrière+"affichage des regles qui declenchent : "+fait.getAttribut()+"="+fait.getValeur()+"\n";
			}

			afficheListRegle(eRegle);
			//recuperation de la list des Regle
			for(int i=0;i<eRegle.size();i++){
				// recuperation des premisses
				ArrayList<Fait> P= eRegle.get(i).recupPremisses();
				traceChainageArrière=traceChainageArrière+"\t -";
				for(int j=0;j<P.size();j++){
					if(P.get(j).getValeur().equals(new String(""))==true){
						traceChainageArrière=traceChainageArrière+P.get(j).getAttribut()+" & ";
					}
					else{
						traceChainageArrière=traceChainageArrière+P.get(j).getAttribut()+"="+P.get(j).getValeur()+" & ";
					}
				}
				traceChainageArrière=traceChainageArrière+"		alors	";
				
				ArrayList<Fait> CQ= eRegle.get(i).recupConsequences();
				
				for(int j=0;j<CQ.size();j++){
					if(CQ.get(j).getValeur().equals(new String(""))==true){
						traceChainageArrière=traceChainageArrière+CQ.get(j).getAttribut()+" & ";
					}
					else{
						traceChainageArrière=traceChainageArrière+CQ.get(j).getAttribut()+"="+CQ.get(j).getValeur()+" & ";
					}
				}
				traceChainageArrière=traceChainageArrière+"\n";
			}

			if (eRegle.isEmpty()) {
				ret = false;
			} else {

				while (!eRegle.isEmpty()) {
					Regle regle = eRegle.remove(0); // On enleve la première Regle de ER						
					ArrayList<Fait> premisses = regle.recupPremisses();
					boolean testPremisse = true;
					int j = 0;
					while (testPremisse == true && j < premisses.size()) {
						Fait premisseJ = premisses.get(j); // premisse d'indice i
						testPremisse = chainageArriere(premisseJ);
						j++;
					}
					if (testPremisse == true) {
						eRegleTest.add(1);
					}
				}

				if (eRegleTest.contains(1)) {
					ret = true;
				} else {
					ret = false;
				}
			}
		}

		if (ret) {
			System.out.print("chainage arrière  de: ");
			fait.afficheFait();
			System.out.println("est reussi ");
			
			if(fait.getValeur().equals(new String(""))==true){
				traceChainageArrière=traceChainageArrière+"chainage arrière  de: "+fait.getAttribut()+" est reussi \n";
			}
			else{
				traceChainageArrière=traceChainageArrière+"chainage arrière  de:  "+fait.getAttribut()+"="+fait.getValeur()+" est reussi \n";
			}

		}
		return ret;
	}
	
/*	
	public static void main(String[] args) {
		Systeme s = new Systeme();
		Fait f= new Fait("bonjour","bonsoir");
		//f.afficheFait();
		//System.err.println("Apres chainage -> "
		//		+ s.chainAvantLargeur(new Premisse("lunettes", "")));
		//s.affichebaseFait(); 
		/// Remplissage de la base de connaissance 
	   s.fillFait("faits.txt");
	   s.fillRegle("regles.txt");
		//Fait f=s.baseFait.get(1);
	//	boolean ca=s.chainageArriere(new Fait("lunettes", ""));
		boolean caprof=s.chainageArriere(new Fait("lunettes", ""));
		System.out.println(s.recupTraceChainageArriere());
		
		//recuPNiveauxProfondeur()
	}
*/
}
