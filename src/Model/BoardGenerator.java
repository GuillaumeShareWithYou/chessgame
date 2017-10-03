package Model;

public class BoardGenerator {
    public BoardGenerator(Plateau p){


        p.setPlateau(new Piece[8][8]);
        Piece[][] plateau = p.getPlateau();
        Joueur j1 = p.getJoueur1();
        Joueur j2 = p.getJoueur2();
        for (int i = 0; i < 8; i++) {
            plateau[i][6] = new Pion(j1);
        }
        for (int i = 0; i < 8; i++) {
            plateau[i][1] = new Pion(j2);
        }
        plateau[6][0] = new Cavalier(j2);
        p.getJoueur2().getPieces().add(plateau[6][0]);
        plateau[1][0] = new Cavalier(j2);
        p.getJoueur2().getPieces().add(plateau[1][0]);
        plateau[6][7] = new Cavalier(j1);
        p.getJoueur1().getPieces().add(plateau[6][7]);
        plateau[1][7] = new Cavalier(j1);
        p.getJoueur1().getPieces().add(plateau[1][7]);

        plateau[2][0] = new Fou(j2);
        p.getJoueur2().getPieces().add(plateau[2][0]);
        plateau[5][0] = new Fou(j2);
        p.getJoueur2().getPieces().add(plateau[5][0]);
        plateau[2][7] = new Fou(j1);
        p.getJoueur1().getPieces().add(plateau[2][7]);
        plateau[5][7] = new Fou(j1);
        p.getJoueur1().getPieces().add(plateau[5][7]);

        plateau[3][0] = new Dame(j2);
        p.getJoueur2().getPieces().add(plateau[3][0]);
        plateau[3][7] = new Dame(j1);
        p.getJoueur1().getPieces().add(plateau[3][7]);

        plateau[0][0] = new Tour(j2);
        p.getJoueur2().getPieces().add(plateau[0][0]);
        plateau[7][0] = new Tour(j2);
        p.getJoueur2().getPieces().add(plateau[7][0]);
        plateau[0][7] = new Tour(j1);
        p.getJoueur1().getPieces().add(plateau[0][7]);
        plateau[7][7] = new Tour(j1);
        p.getJoueur1().getPieces().add(plateau[7][7]);

        plateau[4][0] = new Roi(j2);
        p.getJoueur2().getPieces().add(plateau[4][0]);
        p.setPosRoi2(new Point(4, 0));
        plateau[4][7] = new Roi(j1);
        p.getJoueur1().getPieces().add(plateau[4][7]);
        p.setPosRoi1(new Point(4, 7));
    }
}