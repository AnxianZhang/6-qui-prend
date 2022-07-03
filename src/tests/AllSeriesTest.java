package tests;

import entities.cards.AllCards;
import entities.cards.Card;
import entities.players.Joueur;
import entities.series.AllSeries;
import org.junit.Test;

import static org.junit.Assert.*;

public class AllSeriesTest {

    /**
     * Test répectivement à chaque saut de ligne:
     *  - la création de series
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
                "- série n° 1 : 2" + "\n" +
                      "- série n° 2 : 3" + "\n" +
                      "- série n° 3 : 4" + "\n" +
                      "- série n° 4 : 5 (2)");

        assertFalse(as.CanPlaceCard(c));
        c = packet.Piocher();
        assertTrue(as.CanPlaceCard(c));
    }

    /**
     * test la différence entre une carte et la dernière
     * carte d'une série mais aussi le placement des cartes
     * et la vérification des malus récolté si le joueur
     * ajoute une 6eme carte à une serie qui en posède deja 5
     *
     * dans ce test on considère que les cartes ajouté, sont
     * du même paquet et que les carte ajouté sont des cartes
     * que le joueur posède
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
                "- série n° 1 : 1, 6, 87" + "\n" +
                      "- série n° 2 : 2, 8, 60 (3)" + "\n" +
                      "- série n° 3 : 3, 10 (3), 55 (7)" + "\n" +
                      "- série n° 4 : 4, 12, 22 (5)");

        // test de placement de carte
        Joueur j = new Joueur("Aqua");
        as.PlaceCardInSerie(new Card(90), j, null);
        as.PlaceCardInSerie(new Card(43), j, null);
        as.PlaceCardInSerie(new Card(101), j, null);
        assertEquals(as.toString(),
                  "- série n° 1 : 1, 6, 87, 90 (3), 101" + "\n" +
                        "- série n° 2 : 2, 8, 60 (3)" + "\n" +
                        "- série n° 3 : 3, 10 (3), 55 (7)" + "\n" +
                        "- série n° 4 : 4, 12, 22 (5), 43");

        // test si une 6eme cartes est posé
        as.PlaceCardInSerie(new Card(104), j, null);
        assertEquals(as.toString(),
                  "- série n° 1 : 104" + "\n" +
                        "- série n° 2 : 2, 8, 60 (3)" + "\n" +
                        "- série n° 3 : 3, 10 (3), 55 (7)" + "\n" +
                        "- série n° 4 : 4, 12, 22 (5), 43");
        assertEquals(j.getOxHeadPerTurn(), 7);
    }
}