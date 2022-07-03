package tests;

import entities.cards.AllCards;
import entities.cards.Card;
import entities.players.AllJoueur;
import entities.players.Joueur;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class AllJoueurTest {

    /**
     * Test les valeurs lors de la cr�ation de nouveaux joueurs.
     */
    @Test
    public void testAllJoueur() {
        // Test de la m�thode IsBellowNbJoueurs()
        AllJoueur aj = new AllJoueur();
        assertTrue(aj.IsBellowNbJoueurs());

        AllCards paquet = new AllCards();
        paquet.Generate_cards();
        aj.addJoueur("John",paquet);
        aj.addJoueur("Paul",paquet);
        aj.addJoueur("George",paquet);
        aj.addJoueur("Ringo",paquet);

        // Tests d'ajout de joueurs dans une instance de type AllJoueur.
        assertEquals(aj.getJoueur(0).toStringPlayerName(), "(John)");
        assertEquals(aj.getJoueur(1).toStringPlayerName(), "(Paul)");
        assertEquals(aj.getJoueur(2).toStringPlayerName(), "(George)");
        assertEquals(aj.getJoueur(3).toStringPlayerName(), "(Ringo)");

        // Test du nombre de joueurs ajout�s.
        assertFalse(aj.IsBellowNbJoueurs());
        assertEquals(aj.getNbjoueurs(), 4);

        assertEquals(aj.Remerciment(),
                "Les 4 joueurs sont John, Paul, George et Ringo. " +
                        "Merci de jouer � 6 qui prend !");

        assertTrue(aj.getJoueur(0).HasCard(8));
        assertTrue(aj.getJoueur(1).HasCard(14));
        assertFalse(aj.getJoueur(2).HasCard(5));
        assertFalse(aj.getJoueur(3).HasCard(100));

        assertEquals(aj.getJoueur(0).toString(),
                "- Vos cartes : 1, 2, 3, 4, 5 (2), 6, 7, 8, 9, 10 (3)");
        assertEquals(aj.getJoueur(1).toString(),
                "- Vos cartes : 11 (5), 12, 13, 14, 15 (2)," +
                        " 16, 17, 18, 19, 20 (3)");
        assertEquals(aj.getJoueur(2).toString(),
                "- Vos cartes : 21, 22 (5), 23, 24, 25 (2)," +
                        " 26, 27, 28, 29, 30 (3)");
        assertEquals(aj.getJoueur(3).toString(),
                "- Vos cartes : 31, 32, 33 (5), 34, 35 (2)," +
                        " 36, 37, 38, 39, 40 (3)");
    }

    /**
     * Tests d'initialisation d'une nouvelle instance de type Joueur.
     */
    @Test
    public void testInitJoueur() {
        // Test de cr�ation d'une nouvelle instance de type Joueur.
        Joueur j = new Joueur("Bojji");
        assertEquals(j.toStringPlayerName(), "(Bojji)");
        assertEquals(j.toString(), "- Vos cartes : ");
        assertEquals(j.toStringOxHeadPerTurn(),
                "Bojji a ramass� 0 t�te de boeufs");
        assertEquals(j.toStringFinalScore(),
                "Bojji a ramass� 0 t�te de boeufs");

        AllCards paquet = new AllCards();
        paquet.Generate_cards();
        j.PiocheCards(paquet);

        // Test la distribution des cartes � un joueur.
        for (int i = 0; i < 10; ++i) {
            assertTrue(j.HasCard(i + 1));
        }

        /* Test la cha�ne de caract�res contenant la liste de cartes
         * en main d'un joueur;*/
        assertEquals(j.toString(),
                "- Vos cartes : 1, 2, 3, 4, 5 (2), 6, 7, 8, 9, 10 (3)");
    }

    /**
     * Test de la m�thode qui retrouve un joueur qui poss�de une carte sp�cifique.
     */
    @Test
    public void testFindPlayer() {
        AllJoueur aj = new AllJoueur();
        AllCards ac = new AllCards();
        ac.Generate_cards();

        aj.addJoueur("Michel", ac);
        aj.addJoueur("Jean", ac);
        assertFalse(aj.IsBellowNbJoueurs());
        assertEquals(aj.getNbjoueurs(), 2);

        assertEquals(aj.FindPlayer(new Card(6)).toStringPlayerName(),
                "(Michel)");
        assertEquals(aj.FindPlayer(new Card(19)).toStringPlayerName(),
                "(Jean)");
    }

    /**
     * Tests concernant toutes les valeurs relatives aux t�tes de boeufs des joueurs.
     */
    @Test
    public void testOxHead() {
        AllJoueur aj = new AllJoueur();
        AllCards ac = new AllCards();
        ac.Generate_cards();
        aj.addJoueur("Michel", ac);
        aj.addJoueur("Jean", ac);

        /* Test d'affectation de t�tes de boeufs aux joueurs d'une
         * instance de type AllJoueur.*/
        aj.getJoueur(0).GatherOxHead(3);
        aj.getJoueur(1).GatherOxHead(2);
        assertEquals(aj.getJoueur(1).getOxHeadPerTurn(), 2);
        assertEquals(aj.getJoueur(0).getOxHeadPerTurn(), 3);

        assertEquals(aj.getJoueur(0).toStringOxHeadPerTurn(),
                "Michel a ramass� 3 t�tes de boeufs");
        assertEquals(aj.toStringPlayersOxHead(),
                "Jean a ramass� 2 t�tes de boeufs" + "\n" +
                "Michel a ramass� 3 t�tes de boeufs");

        /* Test de r�initialisation de toutes les t�tes de boeufs
         * ramass�es � chaque tour par un joueur.*/
        aj.InitGamersOxHeadPerTurn();
        assertEquals(aj.getJoueur(0).getOxHeadPerTurn(), 0);
        assertEquals(aj.getJoueur(1).getOxHeadPerTurn(), 0);

        /* Test de trie par le nombre de t�tes de boeufs ramass�es
         * au total par chaque joueur.*/
        aj.OrderByTotalOxHead();
        assertEquals(aj.getJoueur(1).toStringPlayerName(), "(Michel)");
        assertEquals(aj.getJoueur(0).toStringPlayerName(), "(Jean)");

        aj.getJoueur(0).GatherOxHead(8);
        aj.getJoueur(1).GatherOxHead(1);

        assertEquals(aj.toStringPlayersOxHead(),
                "Michel a ramass� 1 t�te de boeufs" + "\n" +
                "Jean a ramass� 8 t�tes de boeufs");

        aj.OrderByTotalOxHead();
        assertEquals(aj.getJoueur(0).toStringPlayerName(), "(Michel)");
        assertEquals(aj.getJoueur(1).toStringPlayerName(), "(Jean)");

        /* Test la cha�ne de charact�res retourn�e contenant le
         * score final d'un joueur.*/
        assertEquals(aj.getJoueur(1).toStringFinalScore(),
                "Jean a ramass� 10 t�tes de boeufs");

        /* Test la cha�ne de charact�res retourn�e contenant le
         * score final de tout les joueurs.*/
        assertEquals(aj.toStringFinalPlayersOxHead(), "** Score final" + "\n"
                + "Michel a ramass� 4 t�tes de boeufs" + "\n"
                + "Jean a ramass� 10 t�tes de boeufs");
    }

    /**
     * Test de trie des joueurs par ordre alphab�tique.
     */
    @Test
    public void testOrderByName() {
        AllJoueur aj = new AllJoueur();
        AllCards ac = new AllCards();
        ac.Generate_cards();
        aj.addJoueur("Michel", ac);
        aj.addJoueur("Jean", ac);
        aj.addJoueur("Michel",ac);
        assertEquals(aj.getJoueur(0).toStringPlayerName(), "(Michel)");
        assertEquals(aj.getJoueur(1).toStringPlayerName(), "(Jean)");
        assertEquals(aj.getJoueur(2).toStringPlayerName(), "(Michel)");

        aj.OrderByGamerName();
        assertEquals(aj.getJoueur(0).toStringPlayerName(), "(Jean)");
        assertEquals(aj.getJoueur(1).toStringPlayerName(), "(Michel)");
        assertEquals(aj.getJoueur(2).toStringPlayerName(), "(Michel)");

        //Test de comparaison de deux joueurs � leurs noms.
        assertTrue(Joueur.compareNom(aj.getJoueur(0),aj.getJoueur(1))<0);
        assertTrue(Joueur.compareNom(aj.getJoueur(1),aj.getJoueur(0))>0);
        assertEquals(Joueur.compareNom(aj.getJoueur(1), aj.getJoueur(1)), 0);
    }

    /**
     * Test des m�thodes toStringChooseSerie(),
     * HasPlayedCard() et RemoveCard()
     */
    @Test
    public void testToStringChooseSerie() {
        Joueur j = new Joueur("John");
        AllCards paquet = new AllCards();
        paquet.Generate_cards();
        j.PiocheCards(paquet);
        assertTrue(j.HasCard(10));

        /* Test la cha�ne retourn�e demandant � un joueur de choisir
         * une s�rie � vider pour poser une carte.*/
        Card c = j.FindCard(10);
        assertEquals(j.toStringChooseSerie(c),
                  "Pour poser la carte 10, " +
                        "John doit choisir la s�rie qu'il va ramasser.");

        // V�rifie si un joueur poss�de la carte qu'il veut jouer.
        assertTrue(j.HasPlayedCard(c));

        //Test si la carte jou� a bien �t� retir� de la main du joueur.
        j.RemoveCard(c);
        assertFalse(j.HasCard(10));
        assertEquals(j.toString(),
                "- Vos cartes : 1, 2, 3, 4, 5 (2), 6, 7, 8, 9");
    }

    /**
     * Test de la cha�ne de charact�res retourn�e par toStringPassage().
     */
    @Test
    public void testToStringPassage() {
        AllJoueur aj = new AllJoueur();
        AllCards paquet = new AllCards();
        paquet.Generate_cards();
        aj.addJoueur("Luck", paquet);
        aj.addJoueur("Rob", paquet);

        aj.getJoueur(0).HasCard(5);
        aj.getJoueur(1).HasCard(13);
        ArrayList<Card> lc = new ArrayList<>();
        lc.add(aj.getJoueur(1).FindCard(13));
        lc.add(aj.getJoueur(0).FindCard(5));
        assertEquals(aj.toStringPassage(lc,true),
                "Les cartes 13 (Rob) et 5 (Luck) vont �tre pos�es.");
        assertEquals(aj.toStringPassage(lc,false),
                "Les cartes 13 (Rob) et 5 (Luck) ont �t� pos�es.");
    }
}