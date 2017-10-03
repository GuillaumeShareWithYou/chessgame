
package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Plateau extends Observable {
	private Piece plateau[][];
	private Joueur j1;
	private Joueur j2;
	private Info info = Info.DEBUT_DE_PARTIE;
	private Point pointClique;
	private Model model;
	private Point posRoi1;
	private Point posRoi2;
	private boolean echec;
	Observer labelInfo; // Observer particulier pour eviter un probleme de multithread sur le plateau
	List<Point> casesAtteignables = new ArrayList<>(); 
	List<Point> casesMangeables = new ArrayList<>();
	List<Point> casesEchecEtMat = new ArrayList<>();
	public List<Point> getCasesEchecEtMat() {
		return casesEchecEtMat;
	}

	public void setCasesEchecEtMat(List<Point> casesEchecEtMat) {
		this.casesEchecEtMat = casesEchecEtMat;
	}

	public List<Point> getCasesAtteignables() {
		return casesAtteignables;
	}

	public void setCasesAtteignables(List<Point> casesAtteignables) {
		this.casesAtteignables = casesAtteignables;
	}

	public Plateau(Model m) {
		this.model = m;
		plateau = new Piece[8][8];
		this.j1 = m.getJoueur1();
		this.j2 = m.getJoueur2();

		init_plateau(this.j1, this.j2);

	}

	/**
	 * initialise le plateau, appellée dans le constructeur la premiere fois, puis
	 * par la fonction init_jeu() du model pour les parties suivantes
	 * 
	 * @param _j1
	 * @param _j2
	 */
	public void init_plateau(Joueur _j1, Joueur _j2) {
		Point pointClique = null;
		echec = false;
		this.j1 = _j1;
		this.j2 = _j2;
		new BoardGenerator(this);

		this.setChanged();
		this.notifyObservers();

	}

	public Piece getEtatPlateau(int i, int j) {
		return this.plateau[i][j];
	}

	public Piece getEtatPlateau(Point p) {
		return this.plateau[p.x][p.y];
	}

	/**
	 * fais toutes les verifications sur la possibilité de bouger une pièce
	 *
	 * @param i
	 *            abscisse du clic
	 * @param j
	 *            ordonnée du clic
	 * @param coupCourant
	 *            Le coup en cours de validation
	 * @param joueurCourant
	 *            Le joueur du tour
	 * @return true ssi piece deplacée
	 */
	public boolean clic(int i, int j, Coup coupCourant, Joueur joueurCourant) {
		boolean error = false;

		Point temp = new Point(i, j); // Position du clic
		if (coupCourant.dep == null) {
			this.viderCasesAtteignables();
			if (this.getEtatPlateau(i, j) == null) // clic sur case vide
			{
				// setInfo("Aucune pi�ce � cet endroit");
				error = true;
			} else if (this.getEtatPlateau(i, j).getJoueur() != joueurCourant) {

				setInfo(Info.PIECE_INTERDITE);
				error = true;
			}

			if (!error) {

				coupCourant.setDepart(temp);
				setPointClique(coupCourant.dep);
				this.viderCasesAtteignables();
				this.chercherCasesAtteignables();
				this.setChanged();
				this.notifyObservers();
				//montrer les coups possibles ici
				// setInfo("...");

			}

		} else if (coupCourant.dep.equals(temp)) {
			// clic sur la même case deux fois pour annuler le coup
			this.viderCasesAtteignables();
			setInfo(Info.COUP_ANNULE);
			setPointClique(null);
			coupCourant.effacer();
			
		} else {
			this.viderCasesAtteignables();
			coupCourant.setFin(temp);
			if (this.getJoueurEn(coupCourant.fin.x, coupCourant.fin.y) == joueurCourant) {
				
				coupCourant.effacer();
				coupCourant.setDepart(temp);
				setPointClique(temp);
				
				
				this.chercherCasesAtteignables();
				
				error = true;
				setInfo(Info.CHANGEMENT_PIECE);
				
			}
			if (!error && appliquerCoup(coupCourant)) {
				
				if(!echec)
					{
						setInfo(Info.COUP_REUSSI);
					}else {
						setInfo(Info.ECHEC);
					}
				coupCourant.effacer();
				setPointClique(null);
				return true;
			}

		}
		return false;

	}

	public void chercherCasesAtteignables() {
		for(int ligne = 0; ligne<8; ligne++) {
			for(int colonne=0 ; colonne<8; colonne++) {
				if(!(this.pointClique.x == ligne && this.pointClique.y == colonne) && this.getEtatPlateau(this.pointClique.x,this.pointClique.y).coupPossible(this, new Coup(this.pointClique,new Point(ligne,colonne))))
				{
					if(!(this.getJoueurEn(ligne, colonne) == this.model.getJoueurCourant()))
					{
						if(this.getJoueurEn(ligne, colonne) == this.model.getJoueurAdverse())
						{
							this.casesMangeables.add(new Point(ligne, colonne));
						}else {
							this.casesAtteignables.add(new Point(ligne,colonne));
						}
						
						
					}
				
				}
			}
		}
		this.setChanged();
		this.notifyObservers();
	}
	public List<Point> getCasesMangeables() {
		return casesMangeables;
	}

	public void setCasesMangeables(List<Point> casesMangeables) {
		this.casesMangeables = casesMangeables;
	}

	public boolean appliquerCoup(Coup c) {
		// faire les verifications sur les propriétés de déplacement intrinsèque à
		// la piece
		if (this.plateau[c.dep.x][c.dep.y].coupPossible(this, c)) {
			boolean suicide = false;
			Piece depart = this.plateau[c.dep.x][c.dep.y];
			Piece fin = this.plateau[c.fin.x][c.fin.y];
			Point roiInit1 = this.posRoi1;
			Point roiInit2 = this.posRoi2;

			this.plateau[c.fin.x][c.fin.y] = this.plateau[c.dep.x][c.dep.y];
			this.plateau[c.dep.x][c.dep.y] = null;

			if (depart.getType().equals("roi")) {
				if (model.getJoueurCourant() == j1) {
					posRoi1 = new Point(c.fin.x, c.fin.y);
				} else {
					posRoi2 = new Point(c.fin.x, c.fin.y);
				}
			}
			suicide = seMettreEchec();
			if (suicide) {
				this.plateau[c.dep.x][c.dep.y] = depart;
				this.plateau[c.fin.x][c.fin.y] = fin;
				posRoi1 = roiInit1;
				posRoi2 = roiInit2;
			} else {
				if (depart.getType().equals("pion")) {
					((Pion) depart).consommerPremierCoup();
				}

				if (fin != null) {
					// On vérifie si un roi a été mangé
					if (fin.getType().equals("roi")) {
						setInfo(Info.FIN_DE_PARTIE);
						setFinDePartie(true);
					}
					// mettre a jour le score :
					depart.manger(fin);
				}
				this.echec = this.mettreEchec();
			}
			this.setChanged();
			notifyObservers();
			if (suicide) return false; // pour rejouer
			return true;
		}
		setInfo(Info.DEPLACEMENT_IMPOSSIBLE);
		return false;

	}

	private boolean seMettreEchec() {
		boolean metEnEchec = false;
		Point posRoi = (model.getJoueurCourant() == model.getJoueur1()) ? posRoi1 : posRoi2;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (this.plateau[i][j] != null && this.plateau[i][j].getJoueur() != model.getJoueurCourant()) {
					Coup coupEchecMat = new Coup(new Point(i, j), posRoi);
					if (this.plateau[i][j].coupPossible(this, coupEchecMat)) {
						this.casesEchecEtMat.add(new Point(i,j));
						metEnEchec = true;
						this.setInfo(Info.ECHEC_SUICIDE);
					}
				}
			}
		}
		return metEnEchec;
	}

	private boolean mettreEchec() {
		boolean metEnEchec = false;
		Point posRoi = (model.getJoueurCourant() == model.getJoueur1()) ? posRoi2 : posRoi1;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (this.plateau[i][j] != null && this.plateau[i][j].getJoueur() == model.getJoueurCourant()) {
					Coup coupEchecMat = new Coup(new Point(i, j), posRoi);
					if (this.plateau[i][j].coupPossible(this, coupEchecMat)) {
						metEnEchec = true;
						this.setInfo(Info.ECHEC);
						
					}
				}
			}
		}
		return metEnEchec;
	}

	// permet de savoir à qui appartient la pièce visée sans exception
	// nullPointerException
	private Joueur getJoueurEn(int i, int j) {
		if (this.getEtatPlateau(i, j) != null) {
			return this.getEtatPlateau(i, j).getJoueur();
		}
		return null;
	}

	public String getInfo() {
		return this.info.toString();
	}

	public void setPointClique(Point p) {
		this.pointClique = p;
		this.setChanged();
		this.notifyObservers();
	}
	public void viderCasesAtteignables() {
		this.casesAtteignables = new ArrayList<>();
		this.casesMangeables = new ArrayList<>();
		this.casesEchecEtMat = new ArrayList<>();
		this.setChanged();
		this.notifyObservers();
	}
	public Point getPointClique() {
		return this.pointClique;
	}

	/**
	 * permet de changer le msg d'info et de notifier le labelInfo
	 * 
	 * @param msg
	 *            msg à envoyer
	 */
	public void setInfo(Info msg) {
		this.info = msg;
		this.labelInfo.update(this, this);
	}

	public void setFinDePartie(boolean b) {
		if (b) {
			this.model.setAffichage(Affichage.FIN_DE_PARTIE);
			this.model.terminerPartie();
		}
	}

	public Piece[][] getPlateau() {
		return plateau;
	}

	public void setPlateau(Piece[][] plateau) {
		this.plateau = plateau;
	}

	public Point getPosRoi1() {
		return posRoi1;
	}

	public void setPosRoi1(Point posRoi1) {
		this.posRoi1 = posRoi1;
	}

	public Point getPosRoi2() {
		return posRoi2;
	}

	public void setPosRoi2(Point posRoi2) {
		this.posRoi2 = posRoi2;
	}

	public Joueur getJoueur1() {
		return this.j1;
	}

	public Joueur getJoueur2() {
		return this.j2;
	}

	public void setObserverInfo(Observer obs) {
		this.labelInfo = obs;
	}
}
