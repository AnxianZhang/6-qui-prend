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
     * Constructeur permettant de créer de nouvelles
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
     * Distribue 10 cartes à un joueur depuis un
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
     * selon le numéro de chaque carte.
     * @see Card#CompareCard(Card, Card)
     */
    private void RangeCartes(){
        this.cartes_en_main.sort(Card::CompareCard);
    }

    /**
     * Permet l'accès au nom d'un joueur.
     * @return le nom du joueur
     */
    public String getNomJoueur() {
        return this.nom_joueur;
    }

    /**
     * Vérifie si le numéro de la carte entrée existe
     * dans les cartes en main d'un joueur.
     * @param num le numéro de carte donné en entrée
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
     * Vérifie si un joueur possède une carte
     * spécifique parmis ses cartes en main.
     * @param c la carte spécifique
     * @return vrai si c'est le cas, sinon faux
     */
    public boolean HasPlayedCard(Card c){
        return this.carte_choisi.IsEqualsCard(c);
    }

    /**
     * Permet de connaître le nom d'un joueur.
     * @return une chaîne de caractères contenant le nom du joueur
     */
    public String toStringPlayerName(){
        return "(" + this.nom_joueur + ")";
    }

    /**
     * Cherche, depuis les cartes en main d'un joueur,
     * la carte qui correspond au numéros de la carte
     * donnée en entrée.
     * @param num le numéro de la carte donné en entrée
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
     * Affecte le nombre de malus récolté par un joueur à
     * chaque tour au nombre total de malus récolté par
     * ce-denier durant la partie en cours.
     * @param ox_head_per_turn les points de malus du joueur par tour
     */
    public void GatherOxHead(int ox_head_per_turn) {
        this.ox_head_per_turn = ox_head_per_turn;
        this.total_ox_head += this.ox_head_per_turn;
    }

    /**
     * Initialise le nombre de têtes de boeufs ramassé par un joueur à chaque tour à 0.
     */
    public void InitOxHeadPerTurn(){
        this.ox_head_per_turn = 0;
    }

    /**
     * Retire une carte qui se trouve dans la main
     * d'un joueur.
     * @param c la carte à retirer
     */
    public void RemoveCard(Card c){
        this.cartes_en_main.remove(c);
    }

    /**
     * Permet de connaître la série à ramasser, pour pouvoir
     * poser une carte.
     * @param c la carte à poser
     * @return la chaîne de caractères indiquant la série à ramasser
     */
    public String toStringChooseSerie(Card c){
        return c.toStringPoserCard() + this.nom_joueur +
                " doit choisir la série qu'il va ramasser.";
    }

    /**
     * Permet à un joueur de visualiser ses cartes en main
     * @return la chaîne de caratères spécifiant les cartes d'un joueur
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
     * Permet de connaître le nombre de têtes de boeufs ramassé
     * par un joueur à la fin d'un tour.
     * @return le nombre de têtes de boeufs.
     */
    public int getOxHeadPerTurn() {
        return this.ox_head_per_turn;
    }

    /**
     * Compare le nom de deux joueurs afin de déterminer si le nom du premier
     * précède, est postérieur ou est identique à celui du deuxième.
     * @param j1 : le premier joueur.
     * @param j2 : le deuxième.
     * @return 0 lorsque les noms sont identiques.
     * Un nombre positif lorsque le nom du premier précède celui du deuxième.
     * Un nombre négatif lorsque le nom premier est postérieur à ceuli du
     * deuxième.
     */
    public static int compareNom(Joueur j1, Joueur j2) {
        return j1.nom_joueur.compareTo(j2.nom_joueur);
    }

    /**
     * Compare le nombre total de têtes de boeufs accumulé lors de la partie
     * de deux joueurs afin de déterminer si le premier en a plus, moins ou si
     * les deux possèdent le même nombre de têtes de boeufs.
     * @param j1 : le premier joueur.
     * @param j2 : le deuxième.
     * @return 0 lorsque le nombre est égal et le nom des joueurs identiques.
     * Un nombre positif lorsque le premier en possède plus que le deuxième ou
     * lorsque le nombre est égal mais le nom du premier précède celui du
     * deuxième.
     * Un nombre négatif lorsque le premier en possède moins que le deuxième ou
     * lorsque le nombre est égal mais le nom du premier esr postérieur à celui
     * du deuxième.
     * @see #compareNom(Joueur, Joueur)
     */
    public static int compareNbTotalOxHead(Joueur j1, Joueur j2){
        if (j1.total_ox_head == j2.total_ox_head)
            return compareNom(j1, j2);
        return j1.total_ox_head - j2.total_ox_head;
    }

    /**
     * Permet de connaître le nombre de têtes de boeufs ramassé par un joueur.
     * @return le nombre de têtes de boeufs ramassé par le joueur.
     */
    public String toStringOxHeadPerTurn() {
        StringBuilder sb = new StringBuilder(this.nom_joueur);
        sb.append(" a ramassé ").append(this.ox_head_per_turn);
        if(this.ox_head_per_turn>1) {
            return sb.append(" têtes de boeufs").toString();
        }
        return sb.append(" tête de boeufs").toString();
    }

    /**
     * Permet de connaître le nombre total de têtes de boeufs ramassé par un
     * joueur durant toute la partie.
     * @return le nombre total de têtes de boeufs ramassé par un joueur
     */
    public String toStringFinalScore(){
        StringBuilder sb = new StringBuilder(this.nom_joueur);
        sb.append(" a ramassé ").append(this.total_ox_head);
        if(this.total_ox_head>1) {
            return sb.append(" têtes de boeufs").toString();
        }
        return sb.append(" tête de boeufs").toString();
    }
}