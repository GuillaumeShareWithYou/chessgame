package Model;

public enum Info {

	COUP_REUSSI("Coup r�ussi, changement de joueur"),
	DEPLACEMENT_IMPOSSIBLE("deplacement impossible"),
	CHANGEMENT_PIECE("Changement de pi�ce"),
	 COUP_ANNULE("Coup annul�"), PIECE_INTERDITE("Cet pi�ce n'est pas � vous !"), FIN_DE_PARTIE("la partie est termin�e"), TEMPS_ECOULE("Temps �coul�"), DEBUT_DE_PARTIE("d�but de partie");
	 
private String message= "";


	Info(String msg)
	{
		this.message = msg;
	}

	public String toString() {
		return this.message;
	}
}
