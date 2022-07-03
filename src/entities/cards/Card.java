package entities.cards;

/**
 * Brief: Classe Card, permettant de manipuler une
 *        carte
 * @author Anxian Zhang, Vick Ye
 * @version 3
 * @since 08/03/2022
 */
public class Card {
    private final int num_card;
    private final int num_ox_head;

    /**
     * Constructeur permettant de cr�er de nouvelles
     * instances de Card.
     * @param num le num�ro qui sera attribu� � la carte
     * @see #Generate_ox_head()
     */
    public Card(int num) throws RuntimeException{
        if (num <= 0 || num > AllCards.MAX_CARD)
            throw new RuntimeException("Not Allowed value");
        this.num_card = num;
        this.num_ox_head = this.Generate_ox_head();
    }

    /**
     * Permet l'acc�s au num�ro de la carte.
     * @return le num�ro de la carte
     */
    public int getNum_card() {
        return this.num_card;
    }

    /**
     * Compare 2 cartes afin de d�duir si la premi�re
     * poss�de un num�ro inf�rieur, �gal ou sup�rieur
     * � celui du deuxi�me.
     * @param c1 carte n�1
     * @param c2 carte n�2
     * @return la differ�nce entre les 2 cartes
     */
    public static int CompareCard(Card c1, Card c2){
        return c1.num_card - c2.num_card;
    }

    /**
     * G�n�re le nombre t�tes de boeufs correspondant �
     * la carte cr��e.
     * @return le nombre de t�te de boeufs
     */
    private int Generate_ox_head() {
        if (this.num_card == 55)
            return 7;
        else if (this.num_card % 10 == 0)
            return 3;
        else if (this.num_card % 5 == 0 && this.num_card % 10 != 0)
            return 2;
        else if (this.num_card % 11 == 0)
            return 5;
        else
            return 1;
    }

    /**
     * Renvoie le num�ro de la carte et son nombre de
     * t�tes de boeufs si celui-ci est diff�rent de 1,
     * sinon renvoie uniquement le num�ro de la carte.
     * @return la cha�ne de caract�res
     * @see #HeadEquals1()
     */
    public String toString() {
        if (!this.HeadEquals1())
            return this.num_card + " (" + this.num_ox_head + ")";
        return Integer.toString(this.num_card);
    }

    /**
     * Indique si le nombre de t�tes de boeufs est �gal � 1.
     * @return vrai si c'est le cas, sinon faux
     */
    public boolean HeadEquals1(){
        return this.num_ox_head == 1;
    }

    /**
     * Renvoie une cha�ne indiquant la carte � poser.
     * @return la cha�ne de caract�re correspondant �
     * 		   la carte � poser
     */
    public String toStringPoserCard(){
        return "Pour poser la carte " + this.num_card + ", ";
    }

    /**
     * Trouve une carte dont le num�ro correspond
     * � un num�ro sp�cifique, si elle existe.
     * @param num le num�ro
     * @return la carte si elle existe, sinon
     * 		   un ensemble vide
     * @see #IsEqualsCard(Card)
     */
    public Card FindCard (int num){
        if (IsEqualsNum(num))
            return this;
        return null;
    }

    /**
     * V�rifie si le num�ro d'une carte correspond
     * � un num�ro sp�cifique.
     * @param num le num�ro sp�cifique
     * @return vrai si c'est le cas, sinon faux
     */
    public boolean IsEqualsNum(int num){
        return this.num_card == num;
    }

    /**
     * V�rifie si le num�ro d'une carte est �gal �
     * celui d'une deuxi�me.
     * @param c la deuxi�me carte
     * @return vrai si oui, sinon faux
     */
    public boolean IsEqualsCard(Card c){
        return this.num_card == c.num_card;
    }

    /**
     * Incr�mente le nombre de t�tes de boeufs d'une
     * carte � la somme totale des t�tes de boeufs.
     * @param total la somme totale des t�tes de boeufs
     * @return la somme totale des t�tes de boeufs +
     * 		   les t�tes de boeufs de la carte
     */
    public int SumTotal(int total){
        return this.num_ox_head + total;
    }
}