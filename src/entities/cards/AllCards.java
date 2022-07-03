
package entities.cards;

import java.util.ArrayList;
import java.util.Random;

/**
 * Brief: Classe AllCards, permettant de manipuler un
 *        paquet de cartes.
 * @author Anxian Zhang, Vick Ye
 * @version 3
 * @since 08/03/2022
 */
public class AllCards {
    public static final int MAX_CARD = 104;
    private final ArrayList<Card> cards;

    /**
     * Constructeur permettant de créer de nouvelles
     * instances de type AllCards.
     */
    public AllCards() {
        this.cards = new ArrayList<>();
    }

    /**
     * Génére toutes les cartes constituant un nouveau paquet
     * @see #Rand_Card()
     */
    public void Generate_cards() {
        for (int i = 0; i < MAX_CARD; ++i) {
            this.cards.add(new Card(i + 1));
        }
    }

    /**
     * Mélange les cartes générées.
     */
    public void Rand_Card() {
        Random position = new Random();
        int new_position;
        for (int i = 0; i < MAX_CARD; ++i) {
            new_position = position.nextInt(MAX_CARD);
            Card tmp = this.cards.get(i);
            this.cards.set(i ,this.cards.get(new_position));
            this.cards.set(new_position , tmp);
        }
    }

    /**
     * Retire une carte du paquet lorsqu'elle est distribuée à
     * un joueur.
     * @return la carte retirée
     */
    public Card Piocher(){
        assert (!IsEmpty());
        Card c = this.cards.get(0);
        this.cards.remove(c);
        return c;
    }

    /**
     * Vérifie si le paquet est vide.
     * @return vrai si c'est le cas, sinon faux
     */
    public boolean IsEmpty(){
        return this.cards.size() == 0;
    }
}