package tests;

import entities.cards.AllCards;
import entities.cards.Card;
import entities.players.Joueur;
import entities.series.AllSeries;
import org.junit.Test;

import static org.junit.Assert.*;

public class AllSeriesTest {

    /**
     * Test r�pectivement � chaque saut de ligne:
     *  - la cr�ation de series
     *  - l'affichages des series
     *  - et si l'ajout d'une carte est possible ou pas
     */
    @Test
    public void testAllSeriesInit() {
        AllCards packet = new AllCards();
        packet.Generate_cards();
        Card c = packet.Piocher();
        AllSeries as = new AllSeries(packet);
        for (int i = 1; i <= AllSeries.MAX_SERIES; ++i)
            assertFalse(as.getSerie(i).IsFull());

        assertEquals(as.toString(),
                "- s�rie n� 1 : 2" + "\n" +
                      "- s�rie n� 2 : 3" + "\n" +
                      "- s�rie n� 3 : 4" + "\n" +
                      "- s�rie n� 4 : 5 (2)");

        assertFalse(as.CanPlaceCard(c));
        c = packet.Piocher();
        assertTrue(as.CanPlaceCard(c));
    }

    /**
     * test la diff�rence entre une carte et la derni�re
     * carte d'une s�rie mais aussi le placement des cartes
     * et la v�rification des malus r�colt� si le joueur
     * ajoute une 6eme carte � une serie qui en pos�de deja 5
     *
     * dans ce test on consid�re que les cartes ajout�, sont
     * du m�me paquet et que les carte ajout� sont des cartes
     * que le joueur pos�de
     */
    @Test
    public void TestDifference (){
        AllCards packet = new AllCards();
        packet.Generate_cards();

        // test d'ajout de carte
        AllSeries as = new AllSeries(packet);
        for (int i = 1; i <= AllSeries.MAX_SERIES; ++i){
            packet.Piocher();
            as.getSerie(i).addCarte(packet.Piocher());
        }
        as.getSerie(1).addCarte(new Card(87));
        as.getSerie(2).addCarte(new Card(60));
        as.getSerie(3).addCarte(new Card(55));
        as.getSerie(4).addCarte(new Card(22));
        assertEquals(as.toString(),
                "- s�rie n� 1 : 1, 6, 87" + "\n" +
                      "- s�rie n� 2 : 2, 8, 60 (3)" + "\n" +
                      "- s�rie n� 3 : 3, 10 (3), 55 (7)" + "\n" +
                      "- s�rie n� 4 : 4, 12, 22 (5)");

        // test de placement de carte
        Joueur j = new Joueur("Aqua");
        as.PlaceCardInSerie(new Card(90), j, null);
        as.PlaceCardInSerie(new Card(43), j, null);
        as.PlaceCardInSerie(new Card(101), j, null);
        assertEquals(as.toString(),
                  "- s�rie n� 1 : 1, 6, 87, 90 (3), 101" + "\n" +
                        "- s�rie n� 2 : 2, 8, 60 (3)" + "\n" +
                        "- s�rie n� 3 : 3, 10 (3), 55 (7)" + "\n" +
                        "- s�rie n� 4 : 4, 12, 22 (5), 43");

        // test si une 6eme cartes est pos�
        as.PlaceCardInSerie(new Card(104), j, null);
        assertEquals(as.toString(),
                  "- s�rie n� 1 : 104" + "\n" +
                        "- s�rie n� 2 : 2, 8, 60 (3)" + "\n" +
                        "- s�rie n� 3 : 3, 10 (3), 55 (7)" + "\n" +
                        "- s�rie n� 4 : 4, 12, 22 (5), 43");
        assertEquals(j.getOxHeadPerTurn(), 7);
    }
}