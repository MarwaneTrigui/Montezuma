package PkgClasse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Niveau {


    private int nbPiece = 0; //calcul le nombre total de piece
    private static int nbrColonneP = 0; //calcul le nombre de colonne de Piece pour la grande matrice

    private String[][] grille = new String[0][0]; //la grande grille d affichage qui permet d affoicher la planches, les pieces, les bordure, etc.

    private Planche planche1 = new Planche(); //objet planche a mettre dans la grande grille
    private ArrayList<Piece> piecesStock = new ArrayList<>(); //permet de stocker les pieces
    private String[] coordoneesPiecesPlacees = new String[0]; //permet de stocker dans un tableau les coordonnes des pieces
    private ArrayList<Integer> colonnedepart = new ArrayList<>(); //permet de stocker les nombre de colonne a partir de la premiere pieces


    /**Le constructeur niveau prend les donnees des fichiers niveau pour ensuite configurer les matrices de pieces et la matrice de planche
     * @param fiche fiche ce parametre represente le nom du fichier niveau la valeur de cette variable a ete assigne dans le main
     * @param numeroniveau cette valeur permet de donner a la methode qui affiche la grille d affichage le niveau auquelle l utilisateur est rendu
     */
    public Niveau(String fiche, int numeroniveau) {

        try {

            BufferedReader fichier = new BufferedReader(new FileReader(fiche));
            String ligne;

            //Cette boucle sert a la validation du fichier
            boolean continu = true;
            int nbPlateaux = 0;

            while ((ligne = fichier.readLine()) != null && continu) {
                 //permet de ne pas dire qu il manque de piece si le fichier commence a montrer la grille au lieu des pieces
                if (ligne.charAt(0) == 'P') {
                    nbPiece++;
                }
                if (ligne.charAt(0) == 'G') {
                    nbPlateaux++;
                }

                if (ligne.charAt(0) != 'P' && ligne.charAt(0) != 'G') {
                    continu = false;
                }
                if (nbPlateaux > 1) {
                    System.out.println("Le fichier contient plus qu une grille");
                    continu = false;
                }

            }

            if(nbPlateaux == 0 && continu) {
                System.out.println("Le fichier n a pas de grille");
            }
            coordoneesPiecesPlacees = new String[nbPiece];

            int longueurPlanche=0;
            int largeurPlanche=0;
            String contenuPlanche="";
            if (continu) {
                    fichier = new BufferedReader(new FileReader(fiche)); //relecture du fichier

                    while ((ligne = fichier.readLine()) != null && continu) { //on peut creer des objets dans la classe niveau, on fait ligne split et on les identifie dans par le lettre

                        if (ligne.charAt(0) == 'P') {

                            String[] proprieteObjet;
                            proprieteObjet = ResoudreDonneFichier(ligne);

                            int longueurPiece = Integer.parseInt(proprieteObjet[0]);
                            int largeurPiece =  Integer.parseInt(proprieteObjet[1]);
                            String contenuPiece = proprieteObjet[2];
                            int aire = Integer.parseInt(String.valueOf(proprieteObjet[3]));

                            if(aire == longueurPiece * largeurPiece) {

                                nbrColonneP += largeurPiece + 2; //le nombre total de colonne de toutes les pieces
                                piecesStock.add(new Piece(longueurPiece, largeurPiece, contenuPiece)); //permet d utiliser les proprietes de l objet a l exterieur du if
                                //on met des objets de piece dans ce array
                            } else {

                                System.out.println("Les dimensions de la piece ne sont pas bonnes");
                                continu = false;
                            }


                        }
                        if (ligne.charAt(0) == 'G') {


                            String[] proprieteObjet;
                            proprieteObjet = ResoudreDonneFichier(ligne);

                            longueurPlanche = Integer.parseInt(proprieteObjet[0]);
                            largeurPlanche =  Integer.parseInt(proprieteObjet[1]);
                            contenuPlanche = proprieteObjet[2];
                            int aire = Integer.parseInt(String.valueOf(proprieteObjet[3]));

                            if(aire == longueurPlanche * largeurPlanche) {

                                planche1 = new Planche(longueurPlanche, largeurPlanche, contenuPlanche); //l objet planche

                            } else {
                                System.out.println("Les dimensions de la planche ne sont pas correct");
                                continu = false;
                            }

                            //verifie si le fichier contient un bon nombre de piece
                            if (nbPiece == 0) {
                                System.out.println("Le fichier n a pas de piece");
                            }

                        }


                    }
                }


            InitialiserGrilleConsole(longueurPlanche, largeurPlanche, planche1, piecesStock, numeroniveau);

            } catch (FileNotFoundException ee) {

                System.out.println("Le fichier est inexistant");
                System.exit(0);

            } catch (NumberFormatException ee){

                System.out.println("IL y a une erreur dans le contenu du fichier");
                System.exit(0);

             } catch (IOException e) {

                System.out.println("Il y a eu une erreur d acces au fichier");
                System.exit(0);

            }  catch (Exception eee) {

                System.out.println("Il y a une erreur dans le fichier");
                System.exit(0);
        }
    }

    /**Cette methode est appeler dans le constructeur niveau elle sert a prendre les donnees du fichier niveau pour donner les dimensiosn de la matrice des pieces et de celle de la planche
     * @param ligne ce parametre represente une variable de chaine de caracterer qui parcours le fichier niveau en mode lecture et lorsuqu elle est affecte a une ligne du fichier elle passe dans cette methode pour donner les dimensions de la matrice des pieces et de la planche
     * @return Cette methode retourne un tableau de String qui stock les dimensions de la matrice de piece et de planche et l aire de ces derniers pour valider le contenu du fichier
     */
        public String[] ResoudreDonneFichier(String ligne) {

            String[] contenu = ligne.split("\\|");
            String[] dimension = contenu[1].split(",");

            String interieur = "";

            interieur = contenu[2];
            int aire=0;
            for (int i=0; i<interieur.length(); i++) {
                int verifier = Integer.parseInt(String.valueOf(interieur.charAt(i))); //verifie s il n y a que des chiffres dans le interieur de la piece
                aire++;
            }
            String[] parametreobjet = new String [4];
            parametreobjet[0] = dimension[0];

            parametreobjet[1] = dimension[1];
            parametreobjet[2] = interieur;
            parametreobjet[3] = String.valueOf(aire);

            return parametreobjet;
        }


    /** Cette methode permet d initialiser la grande matric ou on place la matrice de la planche, les matrices des pieces, les bordures, les nom des colonnes et des lignes de la planche
     * @param longueurPlanche ce parametre represente la longueur de la planche, elle est necessair pour configurer la longueur de la grande matrice d affichage
     * @param largeurPlanche ce parametre represente la largeur de la planche, elle est necessair pour configurer la largeur de la grande matrice d affichage
     * @param planche ce parametre represente l objet planche, elle est neccessaie pour stocker le contenu de sa matrice dans la grande grille d affichage
     * @param pieceStock ce array de piece contient toutes les pieces, elle est necessaire pour que toutes les pieces soient stocker dans la matrice
     * @param numeroniveau cette valeur permet de donner a la methode qui affiche la grille d affichage le niveau auquelle l utilisateur est rendu
     */
    public void InitialiserGrilleConsole(int longueurPlanche, int largeurPlanche, Planche planche, ArrayList<Piece> pieceStock, int numeroniveau) { //initialise la grille de jeu

        int nbrColonneG = nbrColonneP + (largeurPlanche + 1) + 1;
        this.grille = new String[longueurPlanche + 3][nbrColonneG];

        //Cette boucle sert a placer le contenu de la planche
        for (int i = 0; i < planche.getPlanche().length; i++) {
            grille[i + 2][0] = String.valueOf(i + 1);
            for (int j = 0; j < planche.getPlanche()[0].length; j++) {
                grille[i + 2][j + 1] = "|" + planche.getPlanche()[i][j] + "|";
            }
        }

        //Cette boucle sert a identifie les ranges par des lettres
        char rang = 'A';
        for (int j = 0; j < planche.getPlanche()[0].length; j++) {
            grille[0][j + 1] = "|" + rang++ + "|";
        }

        //Cette boucle sert a placer les pieces dans la matrice
        int commencecolumn = planche.getPlanche()[0].length + 2;
        for (Piece p : pieceStock) {
            PlacerPiece(grille, p, commencecolumn);
            colonnedepart.add(commencecolumn);
            commencecolumn += p.getMatricePiece()[0].length + 2;
        }

        //Cette boucle sert au bordure
        for (int i = 1; i <= planche.getPlanche()[0].length; i++) {
            grille[1][i] = "***";
            grille[grille.length - 1][i] = "***";
        }

        //Cette case en particulier de la grille est pour identifier l endroit des pieces placees
        grille[1][planche.getPlanche()[0].length + 2] = "Piece a placer: ";

        AfficherGrilleConsole(numeroniveau);


    }

    /**C est une sous methode appeler dans la methode GrilleConsole qui permet de placer toutes les pieces du array de pieces dans la grande matrice d affichage
     * @param grille elle represente la grande matrice d affichage ou tous le contenu des matrices des pieces sont stockes
     * @param p elle represente l objet piece ou tout le contenu de sa matrice sera stocke dans la grande matrice d affichage
     * @param departcolonne elle represente la colonne ou on commence a mettre la piece, elle est incrementer pour placer les autres pieces a cote
     */
    private void PlacerPiece(String[][] grille, Piece p, int departcolonne) {

        for (int i = 0; i < p.getMatricePiece().length; i++) {

            for (int j = 0; j < p.getMatricePiece()[0].length; j++) {
                grille[i + 2][j + departcolonne] = String.valueOf(p.getMatricePiece()[i][j]);
            }
        }
    }

    /**Cette methode sert a afficher le contenu de la grande matrice d affichage
     * @param numeroniveau cette valeur represente le niveau auquelle l utilisateur est rendu
     */
    public void AfficherGrilleConsole(int numeroniveau) { //affiche la grille de jeu
        System.out.println("~~~ Montezuma+ ~~~ N" + (numeroniveau + 1));
        int largeur = this.grille[0].length;
        for (String[] strings : this.grille) {

            for (int c = 0; c < largeur; c++) {
                System.out.print(strings[c] != null ? strings[c] : " ");
            }

            System.out.println();
        }
    }

    /**Cette sert a retirer la pieces de la planche de jeu si l utilisateur ou un fichier de commande tient a enlever un piece qui a deja ete place dans la planche
     * @param pieceAEnlever elle represente la piece qui veut etre enlever
     * @param manipulationPiece elle represente une valeur entiere qui diminue de 1 une fois qu une piece a ete enlever, ceci permettra de verifier si le niveau est complete
     * @param numeroniveau cette valeur permet de donner a la methode qui affiche la grille d affichage le niveau auquelle l utilisateur est rendu
     * @return elle retourne la valeur de manipulationPiece pour ensuite verifier si le niveau est complete ou non
     */
    public int RetirerPiece(String pieceAEnlever, int manipulationPiece, int numeroniveau) {

        char piecechoisi = pieceAEnlever.toUpperCase().charAt(0);
        int indice = -1;
        Piece piece = null;
        manipulationPiece--;
        boolean continuer = true;

        //on egalise l objet piece a la piece en question qui se trouve dans l objet piece
        for (int i = 0; i < piecesStock.size() && continuer; i++) {

            if (piecesStock.get(i).getLettrechacun() == piecechoisi) {
                piece = piecesStock.get(i);
                indice = i; //permet de savoir dans quelle indice du array se trouve la piece
                continuer = false;
            }
        }

        if (piece != null) {

            String coordonne = coordoneesPiecesPlacees[indice];

            char colonnechoisi = coordonne.toUpperCase().charAt(1);
            int lignechoisi = Character.getNumericValue(coordonne.charAt(2));
            int colonnechoix = (int) colonnechoisi - 64;

            coordoneesPiecesPlacees[indice] = "";

            for (int l = 0; l < piece.getMatricePiece().length; l++) {

                for (int c = 0; c < piece.getMatricePiece()[0].length; c++) {

                    this.grille[l + 2][colonnedepart.get(indice) + c] = String.valueOf(piece.getMatricePiece()[l][c]); //remet la piece a sa place

                    if (piece.getMatricePiece()[l][c] != '?') {
                        this.grille[lignechoisi + l + 1][colonnechoix + c] = "|_|"; //fait disparaitre la piece de la planche
                    }
                }
            }

            AfficherGrilleConsole(numeroniveau);

        } else {

            System.out.println("La commande entree n est pas valide");
        }

        return manipulationPiece;
    }

    /**Cette methode sert a placer une piece dans la planche
     * @param coordonne elle represente la piece, la colonne et la ligne auxquelles l utilisateur ou le fichier de commande veut mettre la piece dans la planche
     * @param manipulationPiece elle represente une valeur entiere s incremente une fois que la piece est place, ceci permettra de verifier si le niveau est complete
     * @param numeroniveau cette valeur permet de donner a la methode qui affiche la grille d affichage le niveau auquelle l utilisateur est rendu
     * @return elle retourne la valeur de manipulationPiece pour ensuite verifier si le niveau est complete ou non
     */
    public int PlacerPiece(String coordonne, int manipulationPiece, int numeroniveau) {

        char pieceChoisie = coordonne.toUpperCase().charAt(0);
        char colonnechoisi = coordonne.toUpperCase().charAt(1);
        int lignechoisi = Character.getNumericValue(coordonne.charAt(2));

        int indice = -1; //permet de montrer l indice ou se trouve l objet piece en question dans le array de piecestock
        Piece piece = null;
        boolean continu=true;

        //on egalise l objet piece a la piece en question qui se trouve dans l objet piece
        for (int i = 0; i < piecesStock.size(); i++) {

            if (piecesStock.get(i).getLettrechacun() == pieceChoisie && continu) {

                piece = piecesStock.get(i);
                indice = i;
                continu = false;

            }
        }

        int colonnechoix = (int) colonnechoisi - 64;//-64 pour le code ASCII

        if (piece != null && colonnechoix <= this.planche1.getPlanche()[0].length && colonnechoix > 0 && lignechoisi <= this.planche1.getPlanche().length && lignechoisi > 0) {

            boolean permis = true;
            boolean continuerboucle = true;

            ///verifier si on peut mettre la piece
            for (int l = 0; l < piece.getMatricePiece().length; l++) {

                for (int c = 0; c < piece.getMatricePiece()[0].length && continuerboucle; c++) {

                    if (this.grille[lignechoisi + l + 1][colonnechoix + c] != null) {

                        if (piece.getMatricePiece()[l][c] == '?' || this.grille[lignechoisi + l + 1][colonnechoix + c].equals("|_|")) {

                            continuerboucle = true;

                        } else {

                            continuerboucle = false;
                            permis = false;

                        }
                    } else {

                        permis = false;
                    }
                }
            }

            if (permis) {

                //on met la piece
                manipulationPiece++;
                coordoneesPiecesPlacees[indice] = coordonne;

                for (int l = 0; l < piece.getMatricePiece().length; l++) {

                    for (int c = 0; c < piece.getMatricePiece()[0].length ; c++) {

                        this.grille[l + 2][colonnedepart.get(indice) + c] = " "; //on disparait la piece

                        if (piece.getMatricePiece()[l][c] != '?') { //on n affioche pas les ?
                            this.grille[lignechoisi + l + 1][colonnechoix + c] = "|" + pieceChoisie + "|";
                        }
                    }
                }

                AfficherGrilleConsole(numeroniveau);

            } else {

                System.out.println("Tu ne peux pas mettre la piece " + pieceChoisie + " a l endroit " + coordonne);

            }

        } else {

                System.out.println("La commande " + coordonne + " n est pas valide");
            }

        return manipulationPiece;
        }


    /**Cette methode verifie si le niveau est complete, si c est le cas le programme passera au niveau suivant
     * @param manipulationPiece cette methode prend en parametre la valeur de manipulationPiece comme elle se fait incrementer lorsqu on met une piece et desincrementer lorsqu on en enleve une, au moment ou manipulaitonPiece aura la meme valeur que le nombre de piece ceci signifiera que le niveau est complete
     * @return la methode retourne un boolean pour verifier si la valeur est true le programme pourra sortir de la boucle (do while), dans le main, pour ensuite passe au niveau suivant sinon le programme continu la boucle (do while) pour recevoir les prochaines commandes pour le meme niveau
     */
    public boolean VerifierNiveauComplete(int manipulationPiece) {

    boolean changerniveau=false;

    if (manipulationPiece == nbPiece) {

           changerniveau=true;

       }

        return changerniveau;
    }

    /**Cette methode interprete les donnes rentrer par l utilisateur ou le fichier de commande dependemment des donnees rentrer cette methode va appeler la methode pour placer une piece, celle pour l enlever, arrete le programme si l utilisateur veut arreter son programme et dit aussi, si c est le cas, que la donnee entree n est pas valide
     * @param manipulationPiece elle represente une valeur entiere qui est envoye en parametre a la methode PlacerPiece ou RetirerPiece et VerifierNiveauComplete pour ensuite voir si un niveau est complete ou pas
     * @param niveau represente l objet niveau[i] qui vient du main, sa represente l objet du niveau que l utilisateur est rendu, on appelle les methodes PlacerPiece et RetirerPiece a partir de cet objet
     * @param entree represente la chaine de caractere que l utilisateur a rentre dans la console ou le commande d une ligne d un fichier de commande
     * @param mode permet de verifier si la commande entree a ete fait en mode manuel par l utilisateur ou si la commande entree represente une commande d un fichier de commande
     * @param numeroniveau represente le niveau ou l utilisateur est rendu, on l envoit en parametre aux methode PlacerPiece et RetirerPiece qui eux l envoie en parametre a la methode AfficherGrilleConsole pour dire a l utilisateur il est a quel niveau
     * @return elle retourne la valeur de la variable manipulationPiece pour verifier si le niveau est complete ou pas
     */
    public int JouerNiveau (int manipulationPiece, Niveau niveau, String entree, int mode, int numeroniveau) {

        if (entree.equals("!") && mode == 1) {

            System.out.println("On arrete le jeu ");
            System.exit(0);

        } else if (entree.length() == 3) {

            manipulationPiece = niveau.PlacerPiece(entree, manipulationPiece, numeroniveau);

        } else if (entree.length() == 1 && !entree.equals("<") && !entree.equals("!")) {

            manipulationPiece = niveau.RetirerPiece(entree, manipulationPiece, numeroniveau);

        } else {

            System.out.println("La commande " + entree + " n est pas valide");

        }

        return manipulationPiece;
    }
}








