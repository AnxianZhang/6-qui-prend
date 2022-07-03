package entities.series;

import entities.cards.*;
import entities.players.Joueur;

import java.util.ArrayList;

public class AllSeries {
    private final ArrayList<Serie> series;
    public static final int MAX_SERIES = 4;

    /**
     * Constructeur permettant de cr�er de nouvelles
     * instances de type Series.
     * @param packet le paquet de cartes qui sera utilis� pour
     *               l'initilisation de la liste de s�ries
     * @see #CreateSeries(AllCards)
     */
    public AllSeries(AllCards packet){
        this.series = new ArrayList<>();
        this.CreateSeries(packet);
    }

    /**
     * Cr�e les 4 s�ries de cartes avant d'initiliser chaque
     * s�rie avec une carte du paquet.
     * @param packet le paquet de cartes utilis� pour
     * 				 l'initialisation d'une s�rie
     */
    private void CreateSeries(AllCards packet){
        for(int i = 0; i < MAX_SERIES; ++i)
            this.series.add(new Serie(packet));
    }

    /**
     * Concat�ne toutes les s�ries.
     * @return les seri�s concat�n�es
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
     * Place la carte choisie dans une s�rie si elle ne contient pas
     * d�j� 5 cartes, sinon les t�tes de boeufs des cartes se trouvant
     * dans la s�rie sont aff�ct�es au joueur qui a choisi la carte.
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
     * V�rifie si un num�ro sp�cifique se trouve dans l'intervalle [1;4].
     * @param num le num�ro sp�cifique
     * @return vrai si c'est le cas, sinon faux
     */
    public boolean HasSerie(int num){
        return num >= 1 && num <= 4;
    }

    /**
     * Trouve la s�rie o� sera plac� la carte lorsque
     * la difference calcul�e entre la carte choisie et la
     * derni�re carte carte d'une s�rie est �gale � la
     * valeur minimun calculer avant l'appel de cette m�thode.
     * @param value la valeur minimale cacul�e avant l'appel
     * 				de cette m�thode
     * @param c la carte choisie
     * @return la s�rie correspondante
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
     * Indique si une carte peut �tre pos�e dans l'une des s�ries
     * @param c la carte
     * @return true si la diff�rence minimale parmis toutes les
     *         series est positif (donc jouable), sinon false
     * @see #CalculateMinValues(Card)
     */
    public boolean CanPlaceCard(Card c){
        return CalculateMinValues(c) > 0;
    }

    /**
     * Calcule la valeur minimale entre la carte choisie et
     * la derni�re carte de chaque s�rie, afin de retourner
     * la valeur la plus petite, si elle est positive, qui sera
     * utilis�e pour le placement future de la carte dans
     * une s�rie, sinon renvoie un entier relatif.
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
     * Cherche la valeur la plus petite parmis les 4 diff�rences
     * calcul�es.
     * @param liste la liste des diff�rences calcul�es
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
     * Cherche la valeur maximale d'une liste de diff�rences
     * calcul�es ant�rieurement.
     * @param liste la liste de diff�rences
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
     * Permet d'acc�der � l'ini�me s�rie de la liste de s�ries.
     * @param i l'indice de la s�rie
     * @return l'ini�me s�rie
     */
    public Serie getSerie(int i) {
        return this.series.get(i-1);
    }
}