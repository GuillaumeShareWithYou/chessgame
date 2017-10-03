/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package View;
import java.util.Observable;
import java.util.Observer;

import Controller.Controller;
import Model.Affichage;
import Model.Model;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class View extends Application implements Observer{
    private   final int LARGEUR_F = 1000;
    private   final int HAUTEUR_F = 700;
    private   Echiquier echiquier;
    private   Model model;
    private    Scene scene;
    private   Stage stage;
    // TextField en "global" pour conserver les noms des personnages écris pour la prochaine partie
    private    TextField username = new TextField();
    private   TextField username2 = new TextField();
    
    private   Button btnNP1J = new Button("Nouvelle Partie 1 Joueur");
    private   Button btnNP2J = new Button("Nouvelle Partie 2 Joueurs");
    private   Button btnQ = new Button("Quitter");
    private Button btnI = new Button("Se pr�senter");
    private   Button btnContinuer = new Button("Continuer");
    private   Button btnRetour = new Button("Retour au Menu");
    private    LabelInfo info;
    private    String style = "-fx-background-color : black;"
            + "-fx-text-fill: white;"
            + "-fx-border-color : lightblue;"
            +" -fx-border-width : 5;";
    
    private    MinuteurView minuteur;
    
    public View(){
        this.model = new Model();
        this.echiquier =  new Echiquier(model);
        this.info = new LabelInfo(model);
        new Controller(this, model);
    }
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        stage.setMinWidth(LARGEUR_F);
        stage.setMinHeight(HAUTEUR_F);
        stage.setTitle("Jeu d'echec");
        stage.setResizable(false);// sinon ca peut être vraiment horrible
        stage.setOnCloseRequest((WindowEvent e)->System.exit(0));
        scene = new Scene(new Group());
        setScene();
        stage.setScene(this.getScene());
        stage.show();
    }
    
    public Scene getScene()
    {
        return this.scene;
    }
    public Echiquier getEchiquier()
    {
        return this.echiquier;
    }
    public final void setScene()
    {
        
        
        if(model.getAffichage()==Affichage.MENU)
        {   VBox vbox = new VBox();
        Label MenuTitle = new Label("Simulation jeu d'echec");
        MenuTitle.setStyle("-fx-text-fill : black; ");
        MenuTitle.setFont(Font.font("System", FontWeight.BOLD, 50));
        
        int largeur = 180;
        int longueur = 50;
        btnNP1J.setPrefSize(largeur, longueur);
        btnNP2J.setPrefSize(largeur, longueur);
        btnQ.setPrefSize(largeur, longueur);
        btnNP1J.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID,new CornerRadii(0),
                new BorderWidths(5))));
        btnNP2J.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID,new CornerRadii(0),
                new BorderWidths(5))));
        btnQ.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID,new CornerRadii(0),
                new BorderWidths(5))));
        
        btnNP1J.setStyle(style);
        btnNP2J.setStyle(style);
        btnQ.setStyle(style);
        
        btnNP1J.setCursor(Cursor.HAND);
        btnNP2J.setCursor(Cursor.HAND);
        btnQ.setCursor(Cursor.HAND);
        btnQ.setCancelButton(true);
        btnNP1J.setDefaultButton(true);
        
        vbox.getChildren().add(MenuTitle);
        vbox.getChildren().add(btnNP1J);
        vbox.getChildren().add(btnNP2J);
        vbox.getChildren().add(btnQ);
        
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(30);
        
        
        
        scene.setRoot(vbox);
        }else if(model.getAffichage()==Affichage.IDENTIFICATION)
        {
            VBox vboxI = new VBox();
            GridPane form = new GridPane();
            Label lb = new Label("Qui êtes vous ?");
            lb.setStyle("-fx-text-fill : blue;"+"-fx-padding : 30;");
            lb.setFont(Font.font("Constantia", FontWeight.EXTRA_BOLD, 50));
            
            vboxI.getChildren().add(lb);
            
            
            
            Label luser1 = new Label("Joueur 1 :");
            
            luser1.setFont(Font.font("Constantia", FontWeight.BOLD, 35));
            
            username.setMaxWidth(250);
            username.setBorder(new Border(new BorderStroke(Color.BLUE,
                    BorderStrokeStyle.SOLID,new CornerRadii(0),
                    new BorderWidths(2))));
            btnI.setPrefSize(180,50);
            
            
            btnI.setStyle("-fx-background-color : black;"
                    +" -fx-text-fill: white;"
                    
                    + " -fx-border-color : lightblue;"
                    + " -fx-border-width : 5;"
            );
            
            form.add(luser1, 0, 0);
            form.add(username, 1,0);
            
            
            btnI.setCursor(Cursor.HAND);
            btnI.setDefaultButton(true);
            
            vboxI.setAlignment(Pos.CENTER);
            
            
            // Ce n'est qu'ici qu'on différencie le cas 1 ou 2 joueurs
            if(model.getNbJoueurs()==2)
            {
                HBox user2 = new HBox(15);
                Label luser2 = new Label("Joueur 2 :");
                luser2.setFont(Font.font("Constantia", FontWeight.BOLD, 35));
                username2.setMaxWidth(250);
                username2.setBorder(new Border(new BorderStroke(Color.BLUE,
                        BorderStrokeStyle.SOLID,new CornerRadii(0),
                        new BorderWidths(2))));
                
                user2.getChildren().add(luser2);
                user2.getChildren().add(username2);
                
                user2.setAlignment(Pos.BASELINE_CENTER);
                
                form.add(luser2, 0, 1);
                form.add(username2, 1, 1);
                
            }
            
            vboxI.getChildren().add(btnI);
            
            btnRetour.setStyle(style);
            btnRetour.setCursor(Cursor.HAND);
            btnRetour.setPrefSize(180, 50);
            btnRetour.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    model.setAffichage(Affichage.MENU);
                }
                
            });
            
            form.add(btnI,1,2);
            form.add(btnRetour,1,3);
            
            form.setStyle("-fx-alignment : center;");
            form.setHgap(40);
            form.setVgap(30);
            vboxI.setSpacing(20);
            
            vboxI.setAlignment(Pos.CENTER);
            
            vboxI.getChildren().add(form);
            
            
            scene.setRoot(vboxI);
        }else if(model.getAffichage()==Affichage.JEU)
        {
            setGame();
            
        }else if(model.getAffichage() == Affichage.FIN_DE_PARTIE)
        {
            BorderPane bpFin = new BorderPane();
            Label gagnant = new Label(model.getNomJoueurCourant()+" a gagn� avec un score de "+model.getJoueurCourant().getScore()+" points");
            gagnant.setFont(Font.font("Constantia", FontWeight.BOLD, 35));
            btnContinuer.setStyle(style);
            btnContinuer.setDefaultButton(true);
            btnContinuer.setCursor(Cursor.HAND);
            btnContinuer.setPrefSize(180, 50);
            btnContinuer.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID,new CornerRadii(0),
                    new BorderWidths(5))));
            VBox p = new VBox(25);
            p.getChildren().add(gagnant);
            p.getChildren().add(btnContinuer);
            p.setStyle("-fx-alignment: center;");
            bpFin.setCenter(p);
            BorderPane.setAlignment(p, Pos.CENTER);
            scene.setRoot(bpFin);
        }
        
        
    }
    /**
     * Permet d'initialiser la scene au début de la partie
     */
    private void setGame()
    {
        BorderPane partie = new BorderPane();
        info.setFont(Font.font("Constantia", FontWeight.BOLD, 20));
        info.setPadding(new Insets(15));
        partie.setPadding(new Insets(10));
        //Les noms des joueurs
        VBox vJ1 = new VBox(10);
        Label ljoueur = new Label(model.getNomJoueur1());
        ljoueur.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        ljoueur.setTextFill(Color.rgb(0,43,126));
        Label score1 = new Label("Score = "+model.getJoueur1().getScore());
        score1.setFont(Font.font("System", FontWeight.BOLD, 20));
        Label couleur1 = new Label("�quipe blanche");
        couleur1.setFont(Font.font("System", FontWeight.BOLD, 20));
        vJ1.getChildren().add(ljoueur);
        vJ1.getChildren().add(score1);
        vJ1.getChildren().add(couleur1);
        
        VBox vJ2 = new VBox(10);
        Label ljoueur2 = new Label(model.getNomJoueur2());
        ljoueur2.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        ljoueur2.setTextFill(Color.rgb(0,43,126));
        Label score2 = new Label("Score = "+model.getJoueur2().getScore());
        score2.setFont(Font.font("System", FontWeight.BOLD, 20));
        Label couleur2 = new Label("�quipe noire");
        couleur2.setFont(Font.font("System", FontWeight.BOLD, 20));
        
        vJ2.getChildren().add(ljoueur2);
        vJ2.getChildren().add(score2);
        vJ2.getChildren().add(couleur2);
        // le message qui indique qui joue
        Label votreTour = new Label("Jouez !");
        votreTour.setFont(Font.font("Constantia", FontWeight.BOLD, 25));
        votreTour.setStyle("-fx-text-fill : red");
        if(this.model.getJoueurCourant() == this.model.getJoueur1())
        {
            vJ1.getChildren().add(votreTour);
        }else{
            vJ2.getChildren().add(votreTour);
        }
        
        partie.setLeft(vJ1);
        partie.setRight(vJ2);
        
        //en-tête
        this.minuteur = new MinuteurView(model);
        BorderPane.setAlignment(this.minuteur, Pos.CENTER);
        
        BorderPane.setAlignment(echiquier, Pos.CENTER);
        BorderPane.setAlignment(info, Pos.CENTER);
        BorderPane.setAlignment(vJ1, Pos.CENTER_LEFT);
        BorderPane.setAlignment(vJ2, Pos.CENTER_RIGHT);
        partie.setTop(this.minuteur);
        
        //echiquier
        echiquier.setStyle("-fx-alignment: center;");
        
        //  BorderPane.setAlignment(echiquier, Pos.CENTER); fonctionne pas
        partie.setCenter(echiquier);
        
        
        partie.setBottom(info);
        
        scene.setRoot(partie);
        
    }
    
    /**
     * retourne le champ texte saisi par le joueur
     * @return
     */
    public String getUsername()
    {
        return username.getText();
    }
    public String getUsername2()
    {
        return username2.getText();
    }
    /**
     * Bouton lié au commencement d'une nouvelle partie
     * @return
     */
    public Button getBtnNP1J()
    {
        return this.btnNP1J;
    }
    public Button getBtnNP2J()
    {
        return this.btnNP2J;
    }
    /**
     * Bouton permettant de quitter l'application
     * @return
     */
    public Button getBtnQ()
    {
        return this.btnQ;
    }
    /**
     * Bouton permettant au joueur de donner son nom
     */
    public Button getBtnI()
    {
        return btnI;
    }
    public Button getBtnContinuer()
    {
        return this.btnContinuer;
    }
    public Label getLabelInfo()
    {
        return this.info;
    }
    
    @Override
    public void update(Observable o, Object o1) {
        
//        Platform.runLater(new Runnable(){  @Override
//        public void run() {
//            setScene();
//        }
//        });
        
        Platform.runLater(()->setScene());
    }
    
    
    
    
    
    
    
}

