package appli;

import entities.cards.*;
import entities.players.*;
import entities.series.*;

import static util.Console.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Brief: L'application qui nous permet de jouer
 *        une partie entre 2 et 10 joueurs inclus
 * @author Anxian Zhang, Vick Ye
 * @version 10
 * @since 09/03/2022
 */
public class Application{
    public static int MAX_TOUR = 10;

    /**
     * Enregistre tous les joueurs qui seront lus dans
     * le fichier config.txt, puis distribue 10 cartes
     * à chacun d'entre eux.
     * @param aj la liste des joueurs
     * @param packet le paquet qui sera utilisé
     */
    public static void EnregistrementJoueurs(AllJoueur aj, AllCards packet){
        try {
            File f = new File("config.txt");
            Scanner sc_file = new Scanner(f);

            while(sc_file.hasNextLine()){
                while(sc_file.hasNextLine()){
                    if (sc_file.hasNext()) {
                        String nomJ = sc_file.nextLine();
                        aj.addJoueur(nomJ, packet);
                        /* si nb_joueur > 10 le programme s'arrete
                         * car celui-ci ne peut plus piocher dans
                         * un paquet étant vide */
                    }
                }
            }

            if (aj.IsBellowNbJoueurs()) {
                System.err.print("There is not enough player,");
                System.err.print(" please try it again.");
                System.exit(0);
            }

            System.out.println(aj.Remerciment());
            sc_file.close();
        } catch (FileNotFoundException e) {
            System.err.println("File cannot be found.");
            System.exit(0);
        }
    }

    /**
     * Demande au joueur de saisir un entier.
     * Si cet entier se trouve parmis les numéros
     * de cartes possédés, sort de la boucle, sinon
     * le programme redemande de saisir une valeur correcte.
     * @param j le joueur
     * @return la carte saisie
     */
    public static Card InputCardJoueur(Joueur j){
        Scanner sc = new Scanner(System.in);
        do {
            if (sc.hasNextInt()){
                int n = sc.nextInt();
                if (j.HasCard(n))
                    return j.FindCard(n);
            }
            else
                sc.next();
            System.out.print("Vous n'avez pas cette carte, ");
            System.out.print("saisissez votre choix : ");
        }while(true);
    }

    /**
     * Demande à un joueur de saisir un entier.
     * si cet entier se trouve parmis les numéros
     * de séries, sort de la boucle, sinon
     * le programme redemande de saisir une valeur correcte.
     * @param as a listes des séries
     * @return la série sélectionnée
     */
    public static Serie InputSerieJoueur(AllSeries as){
        Scanner sc = new Scanner(System.in);
        do {
            if (sc.hasNextInt()){
                int n = sc.nextInt();
                if (as.HasSerie(n))
                    return as.getSerie(n);
            }
            else
                sc.next();
            System.out.print("Ce n'est pas une série valide, ");
            System.out.print("saisissez votre choix : ");
        }while(true);
    }

    public static void main(String[] args){
        int tour = 0; // nombre de tour passé
        AllCards packet = new AllCards();
        packet.Generate_cards();
        packet.Rand_Card();

        AllSeries as = new AllSeries(packet);
        AllJoueur aj = new AllJoueur();

        // les cartes enregistré à chaque tour de table
        ArrayList<Card> cartes_jouer = new ArrayList<>();

        Card choix; // la carte choisis par un joueur
        Serie s; //serie choisie si la carte ne rentre dans 0 serie

        EnregistrementJoueurs(aj, packet);

        do {
            ++tour;

            /* choix de carte pour chaque joueur */
            for (int i = 0; i < aj.getNbjoueurs(); ++i) {
                System.out.println("A " + aj.getJoueur(i).getNomJoueur() +
                        " de jouer.");
                pause();
                System.out.println(as);
                System.out.println(aj.getJoueur(i));
                System.out.print("Saisissez votre choix : ");
                choix = InputCardJoueur(aj.getJoueur(i));
                cartes_jouer.add(choix);
                clearScreen();
            }

            // placement des cartes
            cartes_jouer.sort(Card::CompareCard);
            for (Card c : cartes_jouer) {
                if (as.CanPlaceCard(c))
                    as.PlaceCardInSerie(c, aj.FindPlayer(c), null);
                else {
                    System.out.println(aj.toStringPassage(cartes_jouer,
                            true));
                    System.out.println(aj.FindPlayer(c).
                            toStringChooseSerie(c));
                    System.out.println(as);
                    System.out.print("Saisissez votre choix : ");
                    s = InputSerieJoueur(as);
                    as.PlaceCardInSerie(c, aj.FindPlayer(c), s);
                }
            }

            System.out.println(aj.toStringPassage(cartes_jouer, false));
            System.out.println(as);
            cartes_jouer.clear();

            System.out.println(aj.toStringPlayersOxHead());
            aj.InitGamersOxHeadPerTurn();
        }while (tour != MAX_TOUR);

        aj.OrderByTotalOxHead();
        System.out.println(aj.toStringFinalPlayersOxHead());
    }
}