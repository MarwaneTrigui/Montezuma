package PkgClasse;

import static PkgClasse.Piece.getLettre;

public class Planche {

    private char[][] planche; //la matrice qui represente la planche de jeu


    public char[][] getPlanche() {
        return planche;
    }

    /**
     * Ce constructeur permet d initialiser l objet planche qui est fait dans la classe niveau
     */
    public Planche() {

        this.planche = planche;
    }

    /**Ce constructeur permet d initialiser la matrice de planche
     * @param ligne represente le nombre de ligne de la matrice de planche
     * @param colonne represente le nombre de colonne de la matrice de planche
     * @param remplissage represente le contenu de toutes les cases de la matrice de planche
     */
    public Planche(int ligne, int colonne, String remplissage) { //Permet d initialiser la planche
        int contenu = 0; //permet de donner le contenu du tableau
        this.planche = new char[ligne][colonne];

        for (ligne = 0; ligne < this.planche.length; ligne++) {

            for (colonne = 0; colonne < this.planche[ligne].length; colonne++) {

                this.planche[ligne][colonne] = remplissage.charAt(contenu);
                contenu++;

                if (this.planche[ligne][colonne] == '0') {

                    this.planche[ligne][colonne] = '_';

                } else {

                    this.planche[ligne][colonne] = '█';

                }
            }

        }
    }



}