package entities.series;

import java.util.ArrayList;

import entities.cards.*;

/**
 * Brief: Classe Serie, permettant de manipuler une s�rie de cartes.
 * @author Anxian Zhang, Vick Ye
 * @version 5
 * @since 09/03/2022
 */
public class Serie {
    private final ArrayList<Card> serie;
    private final int num_serie;
    private static int ctp_num_serie = 0;
    public static final int MAX_CARDS_SERIE = 5;

    /**
     * Constructeur permettant de cr�er de nouvelles instances
     * de type s�rie, puis affecte une carte en d�but de s�rie.
     * @param packet le paquet qui sera utis� pour
     * 				 l'affectation de la premi�re carte d'une
     * 				 s�rie.
     * @see #InitSeries(AllCards)
     */
    public Serie(AllCards packet) throws RuntimeException{
        if (ctp_num_serie == 4)
          ctp_num_serie = 0;
        this.serie = new ArrayList<>();
        this.num_serie = ++ctp_num_serie;
        InitSeries(packet);
    }

    /**
     * Initialise une s�rie cr��e en pla�ant
     * une carte � son d�but.
     * @param packet le paquet utilis� pour l'initialisation
     * @see AllCards#Piocher()
     */
    private void InitSeries(AllCards packet){
        this.serie.add(packet.Piocher());
    }

    /**
     * Ajoute une carte � une s�rie.
     * @param c la carte � ajouter
     */
    public void addCarte(Card c) {
        this.serie.add(c);
    }

    /**
     * Retourne le nombre de cartes qu'une s�rie poss�de.
     * @return le nombre de cartes
     */
    public boolean IsFull() {
        return this.serie.size() == MAX_CARDS_SERIE;
    }

    /**
     * Fait la somme de toutes les t�tes de boeufs d'une s�rie,
     * puis la vide.
     * @return la sommes des t�tes de boeufs de la s�rie
     * @see Card#SumTotal(int)
     */
    public int GatherOxHeadSerie(){
        int total = 0;
        for (Card card : this.serie) {
            total = card.SumTotal(total);
        }
        this.serie.clear();
        return total;
    }

    /**
     * Retourne une cha�ne sp�cifiant les cartes d'un s�rie
     * @return les cartes de la s�rie
     * @see Card#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("- s�rie n� ").append(this.num_serie).append(" : ");
        for (int i = 0; i < this.serie.size(); ++i) {
            sb.append(this.serie.get(i).toString());
            if (i+1 != this.serie.size())
                sb.append(", ");
        }
        return sb.toString();
    }

    /**
     * Permet de conna�tre la diff�rence entre une carte
     * sp�cifique et une carte de la s�rie.
     * @param c la carte sp�cifique
     * @return la dif�rence les deux cartes
     * @see Card#CompareCard(Card, Card)
     */
    public int GetDifference(Card c) {
        return Card.CompareCard(c, this.serie.get(this.serie.size()-1));
    }
}