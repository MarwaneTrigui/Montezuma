import PkgClasse.Niveau;
import PkgClasse.Piece;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    /**Le main explique les regles du jeu, c est dans le main que le programme passe d un niveau a un autre
     * @param args
     */
    public static void main(String[] args) {

        Scanner clavier = new Scanner(System.in);
        System.out.println("Casse-tete de Montezuma de Marwane, Trigui"); //titre du jeu

        //regle du jeu
        System.out.println("Regles de jeu: ");
        System.out.println("-Pour mettre une piece dans la planche -> ecrit la lettre qui correspond a la piece suivi de la lettre qui correspond a la colonne de la grille suivi du numero qui correspond au numero de la ligne de la grille ");
        System.out.println("-Pour retirer une piece dans la planche -> ecrit la lettre qui correspond au nom de la piece que tu veux enlever sur la grille ");
        System.out.println("-Pour resoudre un niveau automatiquement -> ecrit le symbole < suivi du nom du fichier auquelle tu veux utiliser pour resoudre un niveau");

        Niveau[] niveaux = new Niveau[10];
        String entree;
        int manipulationPiece = 0; //cette variable permet de changer de niveau une fois que le niveau est completer

        boolean niveausuivant;

        System.out.println("Appuyer sur ENTRER pour debuter la partie"); //pour commencer le jeu
        clavier.nextLine();

        for (int i = 0; i < niveaux.length; i++) {


        Piece.setLettre('[');  //Permet de rinitialiser les lettres des pieces au prochain niveau

        niveaux[i] = new Niveau("niveaux\\niveau" + (i + 1) + ".txt", i);
        niveausuivant = false;
        int nbrfois=1;

        do {
            if (nbrfois > 1) { //pour reafficher cette ce message a chaque fois qu on place ou enleve une piece mais on ne l affiche pas au debut car il a deja ete affiche en haut pour la premiere fois

                System.out.println("~~~ Montezuma+ ~~~ N" + (i + 1));

            }
                System.out.print("(! pour quitter)>>> ");
                Piece.setLettre('[');  //Permet de rinitialiser les lettres des pieces au prochain niveau

                entree = clavier.nextLine();

                if (entree.charAt(0) != '<') {
                    manipulationPiece = niveaux[i].JouerNiveau(manipulationPiece, niveaux[i], entree, 1, i);
                    niveausuivant = niveaux[i].VerifierNiveauComplete(manipulationPiece);
                }

                if (entree.charAt(0) == '<') { //mode automatique

                    String commande = "";

                    for (int k = 1; k < entree.length(); k++) { //pour ne pas inclure le <
                        commande += entree.charAt(k);
                    }
                        try {
                            BufferedReader fichier = new BufferedReader(new FileReader("cmd\\" + commande));
                            String ligne;

                            Piece.setLettre('[');  //Permet de rinitialiser les lettres des pieces au prochain niveau
                            System.out.println("~~~ Montezuma+ ~~~ N" + (i + 1));


                            ligne = fichier.readLine();

                            while (ligne != null && !niveausuivant) {
                                if (ligne == null) {

                                    System.out.println("Il n y a plus de commande. La partie est fini");
                                    System.exit(0);

                                }

                                manipulationPiece = niveaux[i].JouerNiveau(manipulationPiece, niveaux[i], ligne, 2, i);
                                niveausuivant = niveaux[i].VerifierNiveauComplete(manipulationPiece);

                                ligne = fichier.readLine();
                            }


                            manipulationPiece = 0;

                        } catch (IOException e) {

                            System.out.println("Fichier inexistant");

                        }
                    }

            }   while (!niveausuivant) ;

                    manipulationPiece = 0;

                    if (i == niveaux.length - 1) {

                        System.out.println("Felicitation tu as completer tous les niveaux");

                    } else {

                        System.out.println("Felicitation tu passes au niveau suivant");
                        System.out.println("Appuie sur entree pour passer au prochain niveau");
                        clavier.nextLine();

                    }
                }
            }

        }




