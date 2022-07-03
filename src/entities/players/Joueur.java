package entities.players;

import java.util.ArrayList;

import entities.cards.*;

/**
 * Brief: Classe Joueur, permettant de manipuler un
 *        joueur.
 * @author Anxian Zhang, Vick Ye
 * @version 7
 * @since 09/03/2022
 */
public class Joueur {
    private static final int MAX_CARTES_MAIN = 10;
    private final String nom_joueur;
    private int total_ox_head;
    private int ox_head_per_turn;
    private Card carte_choisi;
    private final ArrayList<Card> cartes_en_main;

    /**
     * Constructeur permettant de cr�er de nouvelles
     * instances de types Joueur.
     * @param nom le  nom du joueur
     */
    public Joueur(String nom) {
        this.nom_joueur = nom;
        this.total_ox_head = 0;
        this.ox_head_per_turn = 0;
        this.carte_choisi = null;
        this.cartes_en_main = new ArrayList<>();
    }

    /**
     * Distribue 10 cartes � un joueur depuis un
     * paquet de cartes.
     * @param packet le paquet de cartes
     * @see #RangeCartes()
     * @see AllCards#Piocher()
     */
    public void PiocheCards(AllCards packet) {
        assert(this.cartes_en_main.size() < 10 && this.total_ox_head == 0);

        for (int i = 0; i < MAX_CARTES_MAIN; ++i) {
            this.cartes_en_main.add(packet.Piocher());
        }
        this.RangeCartes();
    }

    /**
     * Range les cartes d'un joueur par ordre croissant
     * selon le num�ro de chaque carte.
     * @see Card#CompareCard(Card, Card)
     */
    private void RangeCartes(){
        this.cartes_en_main.sort(Card::CompareCard);
    }

    /**
     * Permet l'acc�s au nom d'un joueur.
     * @return le nom du joueur
     */
    public String getNomJoueur() {
        return this.nom_joueur;
    }

    /**
     * V�rifie si le num�ro de la carte entr�e existe
     * dans les cartes en main d'un joueur.
     * @param num le num�ro de carte donn� en entr�e
     * @return vrai si la carte existe, sinon faux
     * @see Card#IsEqualsNum(int)
     */
    public boolean HasCard(int num){
        for (Card c : this.cartes_en_main)
            if (c.IsEqualsNum(num)) {
                this.carte_choisi = c;
                return true;
            }
        return false;
    }

    /**
     * V�rifie si un joueur poss�de une carte
     * sp�cifique parmis ses cartes en main.
     * @param c la carte sp�cifique
     * @return vrai si c'est le cas, sinon faux
     */
    public boolean HasPlayedCard(Card c){
        return this.carte_choisi.IsEqualsCard(c);
    }

    /**
     * Permet de conna�tre le nom d'un joueur.
     * @return une cha�ne de caract�res contenant le nom du joueur
     */
    public String toStringPlayerName(){
        return "(" + this.nom_joueur + ")";
    }

    /**
     * Cherche, depuis les cartes en main d'un joueur,
     * la carte qui correspond au num�ros de la carte
     * donn�e en entr�e.
     * @param num le num�ro de la carte donn� en entr�e
     * @return la carte du joueur correspondante
     * @see Card#FindCard(int)
     */
    public Card FindCard(int num){
        Card card;
        for (Card c : this.cartes_en_main) {
            card = c.FindCard(num);
            if (card != null)
                return card;
        }
        return null;
    }

    /**
     * Affecte le nombre de malus r�colt� par un joueur �
     * chaque tour au nombre total de malus r�colt� par
     * ce-denier durant la partie en cours.
     * @param ox_head_per_turn les points de malus du joueur par tour
     */
    public void GatherOxHead(int ox_head_per_turn) {
        this.ox_head_per_turn = ox_head_per_turn;
        this.total_ox_head += this.ox_head_per_turn;
    }

    /**
     * Initialise le nombre de t�tes de boeufs ramass� par un joueur � chaque tour � 0.
     */
    public void InitOxHeadPerTurn(){
        this.ox_head_per_turn = 0;
    }

    /**
     * Retire une carte qui se trouve dans la main
     * d'un joueur.
     * @param c la carte � retirer
     */
    public void RemoveCard(Card c){
        this.cartes_en_main.remove(c);
    }

    /**
     * Permet de conna�tre la s�rie � ramasser, pour pouvoir
     * poser une carte.
     * @param c la carte � poser
     * @return la cha�ne de caract�res indiquant la s�rie � ramasser
     */
    public String toStringChooseSerie(Card c){
        return c.toStringPoserCard() + this.nom_joueur +
                " doit choisir la s�rie qu'il va ramasser.";
    }

    /**
     * Permet � un joueur de visualiser ses cartes en main
     * @return la cha�ne de carat�res sp�cifiant les cartes d'un joueur
     * @see Card#toString()
     */
    public String toString(){
        StringBuilder sb = new StringBuilder("- Vos cartes : ");

        for (int idx = 0; idx < this.cartes_en_main.size(); ++idx) {
            sb.append(this.cartes_en_main.get(idx).toString());
            if (idx+1 != this.cartes_en_main.size())
                sb.append(", ");
        }
        return sb.toString();
    }

    /**
     * Permet de conna�tre le nombre de t�tes de boeufs ramass�
     * par un joueur � la fin d'un tour.
     * @return le nombre de t�tes de boeufs.
     */
    public int getOxHeadPerTurn() {
        return this.ox_head_per_turn;
    }

    /**
     * Compare le nom de deux joueurs afin de d�terminer si le nom du premier
     * pr�c�de, est post�rieur ou est identique � celui du deuxi�me.
     * @param j1 : le premier joueur.
     * @param j2 : le deuxi�me.
     * @return 0 lorsque les noms sont identiques.
     * Un nombre positif lorsque le nom du premier pr�c�de celui du deuxi�me.
     * Un nombre n�gatif lorsque le nom premier est post�rieur � ceuli du
     * deuxi�me.
     */
    public static int compareNom(Joueur j1, Joueur j2) {
        return j1.nom_joueur.compareTo(j2.nom_joueur);
    }

    /**
     * Compare le nombre total de t�tes de boeufs accumul� lors de la partie
     * de deux joueurs afin de d�terminer si le premier en a plus, moins ou si
     * les deux poss�dent le m�me nombre de t�tes de boeufs.
     * @param j1 : le premier joueur.
     * @param j2 : le deuxi�me.
     * @return 0 lorsque le nombre est �gal et le nom des joueurs identiques.
     * Un nombre positif lorsque le premier en poss�de plus que le deuxi�me ou
     * lorsque le nombre est �gal mais le nom du premier pr�c�de celui du
     * deuxi�me.
     * Un nombre n�gatif lorsque le premier en poss�de moins que le deuxi�me ou
     * lorsque le nombre est �gal mais le nom du premier esr post�rieur � celui
     * du deuxi�me.
     * @see #compareNom(Joueur, Joueur)
     */
    public static int compareNbTotalOxHead(Joueur j1, Joueur j2){
        if (j1.total_ox_head == j2.total_ox_head)
            return compareNom(j1, j2);
        return j1.total_ox_head - j2.total_ox_head;
    }

    /**
     * Permet de conna�tre le nombre de t�tes de boeufs ramass� par un joueur.
     * @return le nombre de t�tes de boeufs ramass� par le joueur.
     */
    public String toStringOxHeadPerTurn() {
        StringBuilder sb = new StringBuilder(this.nom_joueur);
        sb.append(" a ramass� ").append(this.ox_head_per_turn);
        if(this.ox_head_per_turn>1) {
            return sb.append(" t�tes de boeufs").toString();
        }
        return sb.append(" t�te de boeufs").toString();
    }

    /**
     * Permet de conna�tre le nombre total de t�tes de boeufs ramass� par un
     * joueur durant toute la partie.
     * @return le nombre total de t�tes de boeufs ramass� par un joueur
     */
    public String toStringFinalScore(){
        StringBuilder sb = new StringBuilder(this.nom_joueur);
        sb.append(" a ramass� ").append(this.total_ox_head);
        if(this.total_ox_head>1) {
            return sb.append(" t�tes de boeufs").toString();
        }
        return sb.append(" t�te de boeufs").toString();
    }
}