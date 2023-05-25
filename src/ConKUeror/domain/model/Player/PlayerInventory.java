package src.ConKUeror.domain.model.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import src.ConKUeror.domain.model.Board.ArmyCard.ArmyType;
import src.ConKUeror.domain.model.Army.Army;
import src.ConKUeror.domain.model.Board.*;;;;
public class PlayerInventory {

private Player p;

private int infantryCount; // card
private int cavalryCount; // card
private int artilleryCount; // card
private Army army;
private List<ArmyCard> armyCards = new ArrayList<>();
private List<Territory> ownedTerritories;
private List<Continent> ownedContinents = new ArrayList<>();
private int armies;
private List<TerritoryCard> territoryCards;
private List<ChanceCard> chanceCards;
   public PlayerInventory() {
        this.army = new Army();
        this.armyCards = new ArrayList<>();
        this.ownedTerritories = new ArrayList<>();
        this.territoryCards = new ArrayList<>();
        this.armies = 0;
    }
    public List<TerritoryCard> getTerritoryCards() {
        return territoryCards;
    }

    public Army getArmy() {

        return this.army;
    }
    public List<ArmyCard> getArmyCards() {
        return armyCards;
    }

    public String getArmyCardstoString() {
        return armyCards.toString();
    }
    public int getArtilleryCount() {
        return artilleryCount;
    }

    public void setArtilleryCount(int artilleryCount) {
        this.artilleryCount = artilleryCount;
    }

    public int getInfantryCount() {
        return infantryCount;
    }

    public void setInfantryCount(int infantryCount) {
        this.infantryCount = infantryCount;
    }

    public int getCavalryCount() {
        return cavalryCount;
    }

    public void setCavalryCount(int cavalryCount) {
        this.cavalryCount = cavalryCount;
    }
    public void countByTypes() {
        // Check if the player has a valid set of 3 cards to trade
        // This is just a simple example, you might need to implement more complex rules


        for (ArmyCard card : armyCards) {
            switch (card.getType()) {
                case INFANTRY:
                    infantryCount++;
                    break;
                case CAVALRY:
                    cavalryCount++;
                    break;
                case ARTILLERY:
                    artilleryCount++;
                    break;
            }
        }

        // return (infantryCount >= 3 || cavalryCount >= 3 || artilleryCount >= 3 ||
        //         (infantryCount >= 1 && cavalryCount >= 1 && artilleryCount >= 1));
    }
    public void addTerritory(Territory territory) {
        ownedTerritories.add(territory);

    }

    public List<Territory> getOwnedTerritories() {
        return this.ownedTerritories;
    }

    public void addArmyCard(ArmyCard card) {
        armyCards.add(card);
        if (card.getType().equals(ArmyType.INFANTRY)) {
            infantryCount += 1;
        }

        else if (card.getType().equals(ArmyType.CAVALRY)) {
            cavalryCount += 1;
        }

        else {
            artilleryCount += 1;
        }
    }



    public void addTerritoryCard(TerritoryCard territoryCard) {
        territoryCards.add(territoryCard);


    }


    public void useTerritoryCards() {
        // boolean valid = true;
        // Continent continent = Board.getContinentByTerritoryID(territoryCards.get(0).getTerritory().getId());
        // List<Integer> tCardNames = new ArrayList<>();

        // for (TerritoryCard tc : territoryCards) {
        //     tCardNames.add(tc.getTerritory().getId());
        // }

        // for (Territory territory : continent.getTerritories()) {
        //    if (!tCardNames.contains(territory.getId())) {
        //         System.out.println("Cards are not enough!");
        //         valid = false;
        //         break;

        //    }

        // }
        // if (valid) {
        //     for (Territory t : continent.getTerritories()) {
        //         ownedTerritories.add(t);
        //     }
        //     ownedContinents.add(continent);
        //     System.out.println(continent.getName() + " is captured!");
        // }

        List<String> CONTINENT_CHECK = new ArrayList<>();
        Continent exampleCont = new Continent("EXAMPLE CONTINENT");

        CONTINENT_CHECK.add("Territory Card 1");
        CONTINENT_CHECK.add("Territory Card 2");
        CONTINENT_CHECK.add("Territory Card 3");
        CONTINENT_CHECK.add("Territory Card 4"); // for EXAMPLE_CONTINENT

        List<String> territoryNames = new ArrayList<>();




        for (TerritoryCard t : territoryCards) {
            territoryNames.add(t.getName());
        }

        if (allElementsIncluded(CONTINENT_CHECK, territoryNames)) {
            System.out.println("CONTINENT IS CONQUERED");
            ownedContinents.add(exampleCont);
        }

        else {
            System.out.println("NOT ENOUGH CARDS TO CONQUER");
        }

    }

    public static boolean allElementsIncluded(List<String> list1, List<String> list2) {
        for (String element : list1) {
            if (!list2.contains(element)) {
                return false;
            }
        }
        return true;
    }




    public void removeInfantryCard(int n) {
        if (infantryCount != 0) {
            int counter = 0;
            Iterator<ArmyCard> iterator = armyCards.iterator();

            while (iterator.hasNext()) {
                ArmyCard aC = iterator.next();

                if (aC.getType().equals(ArmyType.INFANTRY) && counter < n) {
                    iterator.remove();
                    counter += 1;
                    infantryCount -= 1;
                }
            }
        }
    }


    public void removeArtilleryCard(int n) {
        if (artilleryCount != 0) {
            int counter = 0;
            Iterator<ArmyCard> iterator = armyCards.iterator();

            while (iterator.hasNext()) {
                ArmyCard aC = iterator.next();

                if (aC.getType().equals(ArmyType.ARTILLERY) && counter < n) {
                    iterator.remove();
                    counter += 1;
                    artilleryCount -= 1;
                }
            }
        }
    }

    public void removeCavalryCard(int n) {
        if (cavalryCount != 0) {
            int counter = 0;
            Iterator<ArmyCard> iterator = armyCards.iterator();

            while (iterator.hasNext()) {
                ArmyCard aC = iterator.next();

                if (aC.getType().equals(ArmyType.CAVALRY) && counter < n) {
                    iterator.remove();
                    counter += 1;
                    cavalryCount -= 1;
                }
            }
        }
    }








    public void useArmyCards() {
        int testCase = isValid();



        if (testCase == 0) {
            System.out.println("You can not use Army Cards due to lack of cards.");
        }

        else if (testCase == 1) {
            System.out.println("Test Case 1 occured, transition happening.");
            addCavalries(1);

            removeInfantryCard(3);


        }
        else if (testCase == 2) {
            System.out.println("Test Case 2 occured, transition happening.");

            addCavalries(2);
            removeInfantries(2);
            // deleteArmyCards(0, 2);
            // deleteArmyCards(2, 1);

            removeCavalries(1);

        }

        else if (testCase == 3) {
            System.out.println("Test Case 3 occured, transition happening.");
            addArtilleries(2);
           removeInfantries(2);
            removeArtilleries(1);
        }

        else if (testCase == 4) {
            System.out.println("Test Case 4 occured, transition happening.");
            addCavalries(1);
            addArtilleries(1);
            removeInfantryCard(1);
            removeCavalryCard(2);
        }


        else {
            System.out.println("Test Case 5 occured, transition happening.");
            addArtilleries(3);
            removeArtilleryCard(1);
            removeCavalryCard(2);


        }
    }

    public int getNumberOfArmies() {
        int totalArmies = 0;

        // Iterate through all the territories owned by the player
        for (Territory territory : ownedTerritories) {
            // Add the number of armies in the current territory to the total
            totalArmies += territory.getTotalUnit();
        }

        return totalArmies;
    }
    public int tradeCardsAndGetAdditionalArmies() {

        int additionalArmies = 0;

        // if (canTradeCards()) {
        //     // Remove the traded cards from the player's hand
        //     // and calculate the additional armies


        //     // Add the additional armies to the player's total armies
        //     armies += additionalArmies;
        // }

        return additionalArmies;
    }

    public void addArmies(int additionalArmies) {

        armies += additionalArmies;
    }

    public void addInfantries(int n) {
        army.addInfantries(n);
    }
    public void addArtilleries(int n) {
        army.addArtilleries(n);
    }

    public void addCavalries(int n) {
        army.addCavalries(n);
    }

    public int getTotalArmy() {
       return  army.getTotalArmyUnit();
    }

    public void removeInfantries(int n) {

        army.removeInfantries(n);
    }

    public void removeCavalries(int n) {

        army.removeCavalries(n);
    }

    public void removeArtilleries(int n) {

        army.removeArtilleries(n);
    }

    public int isValid() {



        if (infantryCount >= 3) {
            return 1; // TEST CASE 1 / 3 INFANTRY
        }

        else if (infantryCount >= 2 && cavalryCount >= 1) {
            return 2; // TEST CASE 2 / 2 INFANTRY AND 1 CAVALRY
        }

        else if (infantryCount >= 2 && artilleryCount >= 1) {
            return 3; // TEST CASE 3 / 2 INFANTRY AND 1 ARTILLERY
        }

        else if (infantryCount >= 1 && cavalryCount>= 2) {
            return 4;
        }

        else if (artilleryCount >= 1 && cavalryCount >= 2) {
            return 5;
        }

        return 0;


    }







public void deleteCards(Card c[]) {

    for(Card c1: c) {

        p.getCards().remove(c1);

    }
}
public void setOwnedTerritories(List<Territory> territories) {
    ownedTerritories = new ArrayList<>(territories);
}
}