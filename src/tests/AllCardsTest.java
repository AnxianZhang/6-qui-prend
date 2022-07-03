package tests;

import static org.junit.Assert.*;

import entities.cards.AllCards;
import entities.cards.Card;
import org.junit.Test;

import java.util.ArrayList;

public class AllCardsTest {

    /**
     * Vérifit si le maximun de carte à piocher est bien de 104
     */
    @Test
    public void testAllCards(){
        AllCards ac = new AllCards();
        ac.Generate_cards();
        ac.Rand_Card();

        assertFalse(ac.IsEmpty());
        int ctp = 0;
        while (true){
            ac.Piocher();
            ++ctp;
            if (ctp == AllCards.MAX_CARD) {
                assertTrue(ac.IsEmpty());
                return;
            }
            else
                assertFalse(ac.IsEmpty());
        }
    }

    /**
     * Vérification sur les valeurs de 2 cartes
     */
    @Test
    public void testCeation(){
        Card c = new Card(55);
        assertTrue(c.IsEqualsNum(55));
        assertFalse(c.HeadEquals1());
        assertEquals(c.toString(), "55 (7)");

        Card c2 = new Card(3);
        assertEquals(c2.toString(), "3");
        assertTrue(c2.HeadEquals1());
        assertNotEquals(c.getNum_card(), 103);
        assertFalse(c2.IsEqualsNum(7));

        assertFalse(c2.IsEqualsCard(c));
        assertTrue(c2.IsEqualsCard(c2));
    }

    /**
     * Test du bon fonctionnement de la somme
     */
    @Test
    public void testSumToal(){
        int total_malus = 0;
        ArrayList<Card> list_card = new ArrayList<>();
        list_card.add(new Card(55));
        list_card.add(new Card(80));
        list_card.add(new Card(1));

        for (Card c : list_card)
            total_malus = c.SumTotal(total_malus);

        assertEquals(total_malus, 11);
    }

    /**
     * Test si la comparaison de carte fonctionne bien
     */
    @Test
    public void testCompare() {
        Card c1 = new Card(9), c2 = new Card(26);
        assertEquals(Card.CompareCard(c1, c2),
                -17);
    }

    /**
     * Test si la carte recherché correspond bien à celle
     * que nous avaons déclaré
     */
    @Test
    public void testFindCard(){
        Card c = new Card(50);
        assertEquals(c.FindCard(50), c);
        assertNull(c.FindCard(55)); // car 50 != 55
        assertEquals(c.toStringPoserCard(),
                "Pour poser la carte 50, ");
    }
}