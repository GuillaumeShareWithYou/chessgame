package Model;

public enum Info {

	COUP_REUSSI("Coup réussi, changement de joueur"),
	DEPLACEMENT_IMPOSSIBLE("deplacement impossible"),
	CHANGEMENT_PIECE("Changement de pièce"),
	 COUP_ANNULE("Coup annulé"), PIECE_INTERDITE("Cet pièce n'est pas à  vous !"), FIN_DE_PARTIE("la partie est terminée"), TEMPS_ECOULE("Temps écoulé"), DEBUT_DE_PARTIE("début de partie");
	 
private String message= "";


	Info(String msg)
	{
		this.message = msg;
	}

	public String toString() {
		return this.message;
	}
}
