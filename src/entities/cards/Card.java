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
     * Constructeur permettant de créer de nouvelles
     * instances de Card.
     * @param num le numéro qui sera attribué à la carte
     * @see #Generate_ox_head()
     */
    public Card(int num) throws RuntimeException{
        if (num <= 0 || num > AllCards.MAX_CARD)
            throw new RuntimeException("Not Allowed value");
        this.num_card = num;
        this.num_ox_head = this.Generate_ox_head();
    }

    /**
     * Permet l'accès au numéro de la carte.
     * @return le numéro de la carte
     */
    public int getNum_card() {
        return this.num_card;
    }

    /**
     * Compare 2 cartes afin de déduir si la première
     * possède un numéro inférieur, égal ou supèrieur
     * à celui du deuxième.
     * @param c1 carte n°1
     * @param c2 carte n°2
     * @return la differénce entre les 2 cartes
     */
    public static int CompareCard(Card c1, Card c2){
        return c1.num_card - c2.num_card;
    }

    /**
     * Génére le nombre têtes de boeufs correspondant à
     * la carte créée.
     * @return le nombre de tête de boeufs
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
     * Renvoie le numéro de la carte et son nombre de
     * têtes de boeufs si celui-ci est différent de 1,
     * sinon renvoie uniquement le numéro de la carte.
     * @return la chaîne de caractères
     * @see #HeadEquals1()
     */
    public String toString() {
        if (!this.HeadEquals1())
            return this.num_card + " (" + this.num_ox_head + ")";
        return Integer.toString(this.num_card);
    }

    /**
     * Indique si le nombre de têtes de boeufs est égal à 1.
     * @return vrai si c'est le cas, sinon faux
     */
    public boolean HeadEquals1(){
        return this.num_ox_head == 1;
    }

    /**
     * Renvoie une chaîne indiquant la carte à poser.
     * @return la chaîne de caractère correspondant à
     * 		   la carte à poser
     */
    public String toStringPoserCard(){
        return "Pour poser la carte " + this.num_card + ", ";
    }

    /**
     * Trouve une carte dont le numéro correspond
     * à un numéro spécifique, si elle existe.
     * @param num le numéro
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
     * Vérifie si le numéro d'une carte correspond
     * à un numéro spécifique.
     * @param num le numéro spécifique
     * @return vrai si c'est le cas, sinon faux
     */
    public boolean IsEqualsNum(int num){
        return this.num_card == num;
    }

    /**
     * Vérifie si le numéro d'une carte est égal à
     * celui d'une deuxième.
     * @param c la deuxième carte
     * @return vrai si oui, sinon faux
     */
    public boolean IsEqualsCard(Card c){
        return this.num_card == c.num_card;
    }

    /**
     * Incrémente le nombre de têtes de boeufs d'une
     * carte à la somme totale des têtes de boeufs.
     * @param total la somme totale des têtes de boeufs
     * @return la somme totale des têtes de boeufs +
     * 		   les têtes de boeufs de la carte
     */
    public int SumTotal(int total){
        return this.num_ox_head + total;
    }
}