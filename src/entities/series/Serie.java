package entities.series;

import java.util.ArrayList;

import entities.cards.*;

/**
 * Brief: Classe Serie, permettant de manipuler une série de cartes.
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
     * Constructeur permettant de créer de nouvelles instances
     * de type série, puis affecte une carte en début de série.
     * @param packet le paquet qui sera utisé pour
     * 				 l'affectation de la première carte d'une
     * 				 série.
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
     * Initialise une série créée en plaçant
     * une carte à son début.
     * @param packet le paquet utilisé pour l'initialisation
     * @see AllCards#Piocher()
     */
    private void InitSeries(AllCards packet){
        this.serie.add(packet.Piocher());
    }

    /**
     * Ajoute une carte à une série.
     * @param c la carte à ajouter
     */
    public void addCarte(Card c) {
        this.serie.add(c);
    }

    /**
     * Retourne le nombre de cartes qu'une série possède.
     * @return le nombre de cartes
     */
    public boolean IsFull() {
        return this.serie.size() == MAX_CARDS_SERIE;
    }

    /**
     * Fait la somme de toutes les têtes de boeufs d'une série,
     * puis la vide.
     * @return la sommes des têtes de boeufs de la série
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
     * Retourne une chaîne spécifiant les cartes d'un série
     * @return les cartes de la série
     * @see Card#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("- série n° ").append(this.num_serie).append(" : ");
        for (int i = 0; i < this.serie.size(); ++i) {
            sb.append(this.serie.get(i).toString());
            if (i+1 != this.serie.size())
                sb.append(", ");
        }
        return sb.toString();
    }

    /**
     * Permet de connaître la différence entre une carte
     * spécifique et une carte de la série.
     * @param c la carte spécifique
     * @return la diférence les deux cartes
     * @see Card#CompareCard(Card, Card)
     */
    public int GetDifference(Card c) {
        return Card.CompareCard(c, this.serie.get(this.serie.size()-1));
    }
}