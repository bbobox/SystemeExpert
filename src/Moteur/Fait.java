package Moteur;


/**
 * La class Fait est represée par un couple attribut-valeur
 * @author etudiant
 *
 */
public class Fait {
	
	private String attributFait;
	private String valeur;
	
	/**
	 * Constructeur 
	 * @param a:  nom/type de l'attrbut
	 * @param p:  valeur
	 */
	
	public Fait(String a, String p) {
		attributFait = a;
		valeur = p;
	}
	

	public String getAttribut(){
		return attributFait;
	}
	
	public String getValeur(){
		return valeur;
		
	}
	
	/**
	 * Affichage du fait 
	 *
	 */
	
	public void afficheFait(){
		if (valeur == "") {
			System.out.print(attributFait + " ");
		} else {
			System.out.print(attributFait+ "(" + valeur + ") ");
		}
	}
	
	
	public String toString(){
		if (valeur == "") {
			return attributFait + " ";
		} else {
			return attributFait+ "(" + valeur + ") ";
		} 
	}
	
	/**
	 *  Permet de test l'egalitté entre deux faits
	 */
	
	public boolean equals(Object arg0) {
		if (arg0 instanceof Fait){
			Fait f = (Fait) arg0;
			if (f.attributFait.equals(attributFait)){
				// si le param attend une valeur, on l'attribu et on vérifie si il y a un predicat avec
				if (valeur.contains("?")){
					String[] predicat;
					if (valeur.split(">_").length>1){
						predicat = valeur.split(">_");
						if (f.valeur.matches("-?\\d+(\\.\\d+)?")){
							if (Integer.parseInt(f.valeur)>=Integer.parseInt(predicat[1]))
								return true;
						}
						else{
							return attributFait.equals(f.attributFait) && valeur.equals(f.valeur);
						}
					}
					else if (valeur.split("<_").length>1){
						predicat = valeur.split("<_");
						if (f.valeur.matches("-?\\d+(\\.\\d+)?")){
							if (Integer.parseInt(f.valeur)<=Integer.parseInt(predicat[1]))
								return true;
						}
						else{
							return attributFait.equals(f.attributFait) && valeur.equals(f.valeur);
						}
					}
				}
				else if (f.valeur.contains("?")){
					String[] predicat;
					if (f.valeur.split(">_").length>1){
						predicat = f.valeur.split(">_");
						if (valeur.matches("-?\\d+(\\.\\d+)?")){
							if (Integer.parseInt(valeur)>=Integer.parseInt(predicat[1]))
								return true;
						}
						else{
							return attributFait.equals(f.attributFait) && valeur.equals(f.valeur);
						}
					}
					else if (f.valeur.split("<_").length>1){
						predicat = f.valeur.split("<_");
						if (valeur.matches("-?\\d+(\\.\\d+)?")){
							if (Integer.parseInt(valeur)<=Integer.parseInt(predicat[1]))
								return true;
						}
						else{
							return attributFait.equals(f.attributFait) && valeur.equals(f.valeur);
						}
					}
				}
				else{
					if (f.valeur.equals(valeur))
						return true;
				}
			}
		}
		return false;
	}
	
	


}
