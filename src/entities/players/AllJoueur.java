package entities.players;

import entities.cards.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Brief: Classe AllJoueur, permettant de manipuler
 *        la liste de joueurs pr�sent dans la partie.
 * @author Anxian Zhang, Vick Ye
 * @version 6
 * @since 09/03/2022
 */
public class AllJoueur {
    private final ArrayList<Joueur> liste_joueur;
    private static final int MIN_JOUEURS = 2;
    private int nb_joueurs = 0;

    /**
     * Constructeur permettant de cr�er de nouvelles
     * instances de type Alljoueur.
     */
    public AllJoueur(){
        this.liste_joueur = new ArrayList<>();
    }

    /**
     * V�rifie si le nombre total de joueur initialis�
     * est strictement inf�rieur � MIN JOUEURS (soit 2).
     * @return vrai si c'est le cas, faux sinon
     */
    public boolean IsBellowNbJoueurs(){
        return this.nb_joueurs < MIN_JOUEURS;
    }

    /**
     * Initialise tous les joueurs puis distribue 10
     * cartes � chacun.
     * @param nomJ le nom du joueur, pour l'initialisation
     *             d'un nouveau joueur
     * @param packet le paquet pour initialiser les
     *               cartes en main du joueur initialis�
     * @see Joueur#PiocheCards(AllCards)
     */
    public void addJoueur(String nomJ, AllCards packet) {
        this.liste_joueur.add(new Joueur(nomJ));
        this.liste_joueur.get(this.nb_joueurs).PiocheCards(packet);
        ++this.nb_joueurs;
    }

    /**
     * Cherche un joueur en fonction d'une carte jou�e.
     * @param c la carte jou�e
     * @return le joueur � trouver
     * @see Joueur#HasCard(int)
     */
    public Joueur FindPlayer(Card c){
        Joueur j = null;

        for (int i = 0; i < this.nb_joueurs; ++i){
            if (this.liste_joueur.get(i).HasCard(c.getNum_card()))
                j = this.liste_joueur.get(i);
        }
        return j;
    }

    /**
     * Permet l'acc�s au nombre de joueurs enregistr�
     * lors de l'initialisation de la liste de joueurs.
     * @return le nombre de joueur
     */
    public int getNbjoueurs() {
        return this.nb_joueurs;
    }

    /**
     * Retourne l'ini�me joueur enregistr� lors de
     * l'initialisation de la liste de joueurs.
     * @param i indice du joueur
     * @return l'ini�me joueur
     */
    public Joueur getJoueur(int i) {
        return this.liste_joueur.get(i);
    }

    /**
     * Retourne la cha�ne de caract�res permettant de visualiser
     * les joueurs qui ont �t� enregistr�s dans le programme,
     * tout en les remerciant.
     * @return le message de remerciement
     */
    public String Remerciment() {
        StringBuilder sb = new StringBuilder();

        sb.append("Les ").append(this.nb_joueurs).append(" joueurs sont ");
        for(int i = 0; i < this.nb_joueurs; ++i) {
            if (i+1 == this.nb_joueurs)
                sb.append(" et ");
            else if( i > 0)
                sb.append(", ");
            sb.append(this.liste_joueur.get(i).getNomJoueur());
        }
        sb.append(". Merci de jouer � 6 qui prend !");

        return sb.toString();
    }

    /**
     * Retourne la cha�ne de caract�res permettant de visualiser
     * l'ordre de passage des joueurs en fonction des cartes qu'ils
     * ont jou�. Si le choix de la s�rie est � true alors le programme
     * ajoute � la fin: "vont �tre pos�es.", sinon "ont �t� pos�es.",
     * puis retire la carte de la main du joueur.
     * @param cartesJouees les cartes jou�es par les joueurs
     * @param cs choix de la serie, true/false
     * @return l'ordre de passage des joueurs
     * @see Joueur#toStringPlayerName()
     * @see Joueur#HasPlayedCard(Card)
     */
    public String toStringPassage (ArrayList<Card> cartesJouees, Boolean cs){
        StringBuilder sb = new StringBuilder("Les cartes ");
        for(int i = 0; i < cartesJouees.size(); ++i) {
            if(i+1 == cartesJouees.size())
                sb.append(" et ");
            else if (i > 0)
                sb.append(", ");
            sb.append(cartesJouees.get(i).getNum_card());
            for(int j = 0; j < cartesJouees.size(); ++j) {
                if(this.liste_joueur.get(j).HasPlayedCard(cartesJouees.get(i))) {
                    sb.append(" ").append(this.liste_joueur.get(j).toStringPlayerName());
                }
            }
        }
        if (cs)
            sb.append(" vont �tre pos�es.");
        else
            sb.append(" ont �t� pos�es.");

        return sb.toString();
    }

    /**
     * Initialise le nombre de t�tes de boeufs ramass� par tous les joueurs
     * � chaque tour � 0.
     *
     * @see Joueur#InitOxHeadPerTurn()
     */
    public void InitGamersOxHeadPerTurn() {
        for (int i = 0; i < this.nb_joueurs; ++i) {
            this.liste_joueur.get(i).InitOxHeadPerTurn();
        }
    }

    /**
     * Trie les joueurs de la partie par ordre alphab�tique selon leur nom.
     */
    public void OrderByGamerName(){
        this.liste_joueur.sort(Joueur::compareNom);
    }

    /**
     * Trie les joueurs en fonctions de toutes les t�tes de boeufs ramass�es
     * durant la partie.
     */
    public void OrderByTotalOxHead(){
        this.liste_joueur.sort(Joueur::compareNbTotalOxHead);
    }

    /**
     * Permet de conna�tre le nombre de t�tes de boeufs ramass� par chaque
     * joueur lorsqu'il n'est pas nul lors d'un tour. Sinon, renvoie un
     * message par d�faut.
     * @return le message adapt� selon le cas.
     */
    public String toStringPlayersOxHead() {
        StringBuilder sb = new StringBuilder();
        int tmp = 0;
        ArrayList<Integer> joueursOxHead = GetAllPlayerOxHead();

        //cas 1: Aucun joueur ne ramasse de t�te de boeufs.
        if(joueursOxHead.size()==0) {
            sb.append("Aucun joueur ne ramasse de t�te de boeufs.");
            return sb.toString();
        }

        //cas 2: renvoie les joueurs suivis de leurs nombre de malus s'il est sup�rieur � 0.
        for(int i = 0; i < joueursOxHead.size(); ++i) {
            for(int j = 0; j < getNbjoueurs(); ++j) {
                if(liste_joueur.get(j).getOxHeadPerTurn() == joueursOxHead.get(i)) {
                    ++tmp;
                    sb.append(liste_joueur.get(j).toStringOxHeadPerTurn());
                    if(tmp < joueursOxHead.size())
                        sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    /**
     * R�colte toutes les t�tes de boeufs ramass�es par chaque joueur
     * puis les trie.
     * @return la liste de t�tes de boeufs r�colt�es tri�e
     */
    private ArrayList<Integer> GetAllPlayerOxHead (){
        ArrayList<Integer> list = new ArrayList<>();
        for (Joueur joueur : this.liste_joueur) {
            if (joueur.getOxHeadPerTurn() != 0)
                list.add(joueur.getOxHeadPerTurn());
        }
        Collections.sort(list);
        return list;
    }

    /**
     * Permet de conna�tre le nombre de t�tes de boeufs ramass� par chaque
     * joueur � la fin de la partie.
     * @return le message contenant le nombre de t�tes de boeufs
     * 		   corespondant � chaque joueur.
     * @see #OrderByGamerName()
     * @see Joueur#toStringFinalScore()
     * @see Joueur#compareNom(Joueur, Joueur)
     */
    public String toStringFinalPlayersOxHead() {
        StringBuilder sb = new StringBuilder("** Score final");
        sb.append("\n");
        for (int i = 0; i < this.nb_joueurs; ++i) {
            for (int j = 0; j < this.nb_joueurs; ++j) {
                if (i != j) {
                    if (Joueur.compareNom(this.liste_joueur.get(i),
                            this.liste_joueur.get(j)) == 0)
                        this.OrderByGamerName();
                }
            }
        }
        for (int i = 0; i < this.nb_joueurs; ++i) {
            sb.append(this.liste_joueur.get(i).toStringFinalScore());
            if(i<nb_joueurs-1)
                sb.append("\n");
        }
        return sb.toString();
    }
}