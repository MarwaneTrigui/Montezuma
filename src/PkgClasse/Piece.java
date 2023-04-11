package PkgClasse;

import java.util.Arrays;

public class Piece {
    private char[][] matricePiece; //la matrice qui represente la piece
    private static char lettre; //le caractere identifie les pieces

    private char lettrechacun;

    /**Ce constructeur permet d initialiser le contenu d une matrice d une piece
     * @param ligne elle represente le nombre de ligne de la matrice d une piece
     * @param colonne elle represente le nombre de colonne de la matrice une piece
     * @param remplissage elle represente le contenu de la matrice de piece donc la lettre en question
     */
    public Piece(int ligne, int colonne, String remplissage) {

        int indicechainecaractere = 0;
        matricePiece = new char[ligne][colonne];
        lettre = (char) (lettre - 1);

        this.lettrechacun = lettre;

        for (ligne = 0; ligne < this.matricePiece.length; ligne++) {

            for (colonne = 0; colonne < this.matricePiece[ligne].length; colonne++) {


                if (remplissage.charAt(indicechainecaractere) == '0') {

                    matricePiece[ligne][colonne] = '?';

                } else {

                    matricePiece[ligne][colonne] = this.lettrechacun;
                }
                indicechainecaractere++;
            }
        }

    }

    public static void setLettre(char lettre) {

        Piece.lettre = lettre;
    }

    public static char getLettre() {

        return lettre;
    }

    public char[][] getMatricePiece() {
        return matricePiece;
    }

    public char getLettrechacun() {
        return lettrechacun;
    }
}
