package entities.series;

import entities.cards.*;
import entities.players.Joueur;

import java.util.ArrayList;

public class AllSeries {
    private final ArrayList<Serie> series;
    public static final int MAX_SERIES = 4;

    /**
     * Constructeur permettant de créer de nouvelles
     * instances de type Series.
     * @param packet le paquet de cartes qui sera utilisé pour
     *               l'initilisation de la liste de séries
     * @see #CreateSeries(AllCards)
     */
    public AllSeries(AllCards packet){
        this.series = new ArrayList<>();
        this.CreateSeries(packet);
    }

    /**
     * Crée les 4 séries de cartes avant d'initiliser chaque
     * série avec une carte du paquet.
     * @param packet le paquet de cartes utilisé pour
     * 				 l'initialisation d'une série
     */
    private void CreateSeries(AllCards packet){
        for(int i = 0; i < MAX_SERIES; ++i)
            this.series.add(new Serie(packet));
    }

    /**
     * Concatène toutes les séries.
     * @return les seriés concaténées
     * @see Serie#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < MAX_SERIES; ++i) {
            sb.append(this.series.get(i).toString());
            if (i + 1 != MAX_SERIES)
                sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Place la carte choisie dans une série si elle ne contient pas
     * déjà 5 cartes, sinon les têtes de boeufs des cartes se trouvant
     * dans la série sont afféctées au joueur qui a choisi la carte.
     *
     * @param c la carte choisie
     * @param j le joueur
     * @see #CalculateMinValues(Card)
     * @see #SearchSerie(int, Card)
     * @see Joueur#GatherOxHead(int)
     * @see Joueur#RemoveCard(Card)
     * @see Serie#IsFull()
     * @see Serie#addCarte(Card)
     * @see Serie#GatherOxHeadSerie()
     */
    public void PlaceCardInSerie(Card c, Joueur j, Serie s){
        if (s == null){
            int min_value = CalculateMinValues(c);
            s = SearchSerie(min_value, c);
            if (!s.IsFull())
                s.addCarte(c);
            else{
                j.GatherOxHead(s.GatherOxHeadSerie());
                s.addCarte(c);
            }
        }
        else {
            j.GatherOxHead(s.GatherOxHeadSerie());
            s.addCarte(c);
        }
        j.RemoveCard(c);
    }

    /**
     * Vérifie si un numéro spécifique se trouve dans l'intervalle [1;4].
     * @param num le numéro spécifique
     * @return vrai si c'est le cas, sinon faux
     */
    public boolean HasSerie(int num){
        return num >= 1 && num <= 4;
    }

    /**
     * Trouve la série où sera placé la carte lorsque
     * la difference calculée entre la carte choisie et la
     * dernière carte carte d'une série est égale à la
     * valeur minimun calculer avant l'appel de cette méthode.
     * @param value la valeur minimale caculée avant l'appel
     * 				de cette méthode
     * @param c la carte choisie
     * @return la série correspondante
     * @see Serie#GetDifference(Card)
     */
    private Serie SearchSerie(int value, Card c){
        Serie s = null;
        for (int i = 0; i < MAX_SERIES; ++i){
            if (this.series.get(i).GetDifference(c) == value)
                s = this.series.get(i);
        }
        return s;
    }

    /**
     * Indique si une carte peut être posée dans l'une des séries
     * @param c la carte
     * @return true si la différence minimale parmis toutes les
     *         series est positif (donc jouable), sinon false
     * @see #CalculateMinValues(Card)
     */
    public boolean CanPlaceCard(Card c){
        return CalculateMinValues(c) > 0;
    }

    /**
     * Calcule la valeur minimale entre la carte choisie et
     * la dernière carte de chaque série, afin de retourner
     * la valeur la plus petite, si elle est positive, qui sera
     * utilisée pour le placement future de la carte dans
     * une série, sinon renvoie un entier relatif.
     * @param c la carte choisie
     * @return le minmun s'il est positif sinon renvoie
     *         un entier relatif
     * @see #SearchMinValue(ArrayList)
     * @see Serie#GetDifference(Card)
     */
    private int CalculateMinValues (Card c){
        ArrayList<Integer> list_diffrences = new ArrayList<>();
        for (int i = 0; i < MAX_SERIES; ++i)
            list_diffrences.add(this.series.get(i).GetDifference(c));

        return SearchMinValue(list_diffrences);
    }

    /**
     * Cherche la valeur la plus petite parmis les 4 différences
     * calculées.
     * @param liste la liste des différences calculées
     * @return le minmun s'il est positif sinon renvoie
     *         un entier relatif
     * @see #SearchMaxValue(ArrayList)
     */
    private int SearchMinValue(ArrayList<Integer> liste){
        int min = SearchMaxValue(liste);
        for (Integer integer : liste)
            if (integer > 0)
                if (integer < min)
                    min = integer;
        return min;
    }

    /**
     * Cherche la valeur maximale d'une liste de différences
     * calculées antérieurement.
     * @param liste la liste de différences
     * @return la valeur la plus grande
     */
    private int SearchMaxValue(ArrayList<Integer> liste){
        int max = liste.get(0);
        for (int i = 1; i < liste.size(); ++i)
            if(liste.get(i) > 0)
                if (liste.get(i) > max)
                    max = liste.get(i);
        return max;
    }

    /**
     * Permet d'accéder à l'inième série de la liste de séries.
     * @param i l'indice de la série
     * @return l'inième série
     */
    public Serie getSerie(int i) {
        return this.series.get(i-1);
    }
}