package ConKUeror.domain.model.Player;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ConKUeror.domain.model.Board.ArmyCard.ArmyType;
import ConKUeror.domain.model.Board.ChanceCard.ChanceType;
import ConKUeror.domain.model.Modes.GameLogic;
import ConKUeror.UI.Panels.ChanceCardWindow;
import ConKUeror.domain.controller.ChanceObserverListener;
import ConKUeror.domain.controller.HandlerFactory;
import ConKUeror.domain.controller.TerritoryButtonListener;
import ConKUeror.domain.model.Army.Army;
import ConKUeror.domain.model.Board.*;;;;

public class PlayerInventory implements Serializable {

    private Player p;

    private int infantryCount; // card
    private int cavalryCount; // card
    private int artilleryCount; // card
    private Army army;
    private List<ArmyCard> armyCards = new ArrayList<>();
    GameLogic gMode = HandlerFactory.getInstance().getGameLogic();
    private List<Territory> ownedTerritories;
    private List<Continent> ownedContinents = new ArrayList<>();
    private int armies;
    private List<TerritoryCard> territoryCards;
    private List<ChanceCard> chanceCards;
    private List<TerritoryButtonListener> territoryButtonListeners = new ArrayList<>();
    private List<ChanceObserverListener> coListeners = new ArrayList<>();
    private static int MAX_ARMY_CARD_PER_TURN = 1;
    private static int CLICKED_ARMY_BUTTON = 0;
    private static int MAX_TERR_CARD = 1;
    private static int MAX_CHANCE_CARD = 1;

    private static final List<String> NORTH_AMERICA = Arrays.asList("Territory Card 0", "Territory Card 1",
            "Territory Card 2", "Territory Card 5", "Territory Card 3", "Territory Card 4", "Territory Card 6",
            "Territory Card 7", "Territory Card 8");
    private static final List<String> SOUTH_AMERICA = Arrays.asList("Territory Card 9", "Territory Card 10",
            "Territory Card 11", "Territory Card 12");
    private static final List<String> EUROPE = Arrays.asList("Territory Card 21", "Territory Card 22",
            "Territory Card 23", "Territory Card 20", "Territory Card 25", "Territory Card 24", "Territory Card 26");
    private static final List<String> AFRICA = Arrays.asList("Territory Card 13", "Territory Card 14",
            "Territory Card 15", "Territory Card 16", "Territory Card 17", "Territory Card 18");
    private static final List<String> ASIA = Arrays.asList("Territory Card 19", "Territory Card 29",
            "Territory Card 37", "Territory Card 28", "Territory Card 30", "Territory Card 31", "Territory Card 32",
            "Territory Card 33", "Territory Card 27", "Territory Card 34", "Territory Card 36", "Territory Card 35",
            "Territory Card 37");
    private static final List<String> AUSTRALIA = Arrays.asList("Territory Card 38", "Territory Card 39",
            "Territory Card 40", "Territory Card 41");
    private static final Map<String, List<String>> CONTINENTS = new HashMap<String, List<String>>() {
        {
            put("North America",
                    Arrays.asList("Territory Card 0", "Territory Card 1", "Territory Card 2", "Territory Card 5",
                            "Territory Card 3", "Territory Card 4", "Territory Card 6", "Territory Card 7",
                            "Territory Card 8"));
            put("South America",
                    Arrays.asList("Territory Card 9", "Territory Card 10", "Territory Card 11", "Territory Card 12"));
            put("Europe", Arrays.asList("Territory Card 21", "Territory Card 22", "Territory Card 23",
                    "Territory Card 20", "Territory Card 25", "Territory Card 24", "Territory Card 26"));
            put("Africa", Arrays.asList("Territory Card 13", "Territory Card 14", "Territory Card 15",
                    "Territory Card 16", "Territory Card 17", "Territory Card 18"));
            put("Asia",
                    Arrays.asList("Territory Card 19", "Territory Card 29", "Territory Card 37", "Territory Card 28",
                            "Territory Card 30", "Territory Card 31", "Territory Card 32", "Territory Card 33",
                            "Territory Card 27", "Territory Card 34", "Territory Card 36", "Territory Card 35",
                            "Territory Card 37"));
            put("Australia",
                    Arrays.asList("Territory Card 38", "Territory Card 39", "Territory Card 40", "Territory Card 41"));
        }
    };

    public PlayerInventory() {
        this.army = new Army();
        this.armyCards = new ArrayList<>();
        this.ownedTerritories = new ArrayList<>();
        this.territoryCards = new ArrayList<>();
        this.armies = 0;
        this.chanceCards = new ArrayList<>();
    }

    public List<TerritoryCard> getTerritoryCards() {
        return territoryCards;
    }

    public int getDrawCardRequest() {
        return MAX_ARMY_CARD_PER_TURN;
    }

    public void setDrawCardRequest(int i) {
        MAX_ARMY_CARD_PER_TURN = i;
    }

    public int getTerrCardRequest() {
        return MAX_TERR_CARD;
    }

    public void setTerrCardRequest(int i) {
        MAX_TERR_CARD = i;
    }

    public void setChanceCardRequest(int i) {
        MAX_CHANCE_CARD  = i;
    }

    public int getChanceCardRequest() {
        return MAX_CHANCE_CARD;
    }

    public Army getArmy() {

        return this.army;
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

    public void setOwnedTerritories(List<Territory> territories) {
        ownedTerritories = new ArrayList<>(territories);
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

    public List<ChanceCard> getChanceCards() {
        return chanceCards;
    }

    public void setChanceCards(List<ChanceCard> cd) {
        this.chanceCards = cd;
    }

    public void addChanceCard(ChanceCard card) {
        chanceCards.add(card);
    }

    public void removeChanceCards() {
       if (!chanceCards.isEmpty()) {
        chanceCards.remove(0);
       }

    }


    public List<ArmyCard> getArmyCards() {
        return armyCards;
    }

    public int getArmyCardsLength() {
        return armyCards.size();
    }

    public String getArmyCardstoString() {
        return armyCards.toString();
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
        // (infantryCount >= 1 && cavalryCount >= 1 && artilleryCount >= 1));
    }

    public void addTerritory(Territory territory) {
        ownedTerritories.add(territory);

    }

    public void removeTerritoryFromList(Territory t1) {
        if (ownedTerritories.contains(t1)) {
            ownedTerritories.remove(t1);
        }
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
        List<Integer> cardNumbers = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : CONTINENTS.entrySet()) {
            String continentName = entry.getKey();
            List<String> continent = entry.getValue();
            boolean continentCanBeCreated = true;

            for (String cardName : continent) {
                if (!territoryCards.stream().anyMatch(card -> card.getName().endsWith(cardName))) {
                    continentCanBeCreated = false;
                    break;
                }
            }
            if (continentCanBeCreated) {
                for (String cardName : continent) {
                    territoryCards.removeIf(card -> {
                        if (card.getName().endsWith(cardName)) {
                            // Extract the number and add it to cardNumbers
                            String[] parts = card.getName().split(" ");
                            if (parts.length > 2) {
                                try {
                                    int cardNumber = Integer.parseInt(parts[2]);
                                    cardNumbers.add(cardNumber);
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                            return true;
                        } else {
                            return false;
                        }
                    });
                }
                // Use continentName here
                System.out.println(continentName + " has been created.");
            }
        }

        for (int x : cardNumbers) {
            for (Territory t : Board.getTerritories().values()) {
                if (t.getId() == x) {
                    t.setOwner(p);
                    setTerritoryInfo(t.getId(), p.getInventory().getTotalArmy(), p.getColor(), t.getTotalUnit());
                }
            }

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

    public int returnClicked() {
        return CLICKED_ARMY_BUTTON;
    }

    /*
     * Requires:
     * Modifies: this.ArmyCards, Army.cavalryList, Army.infantryList,
     * Army.artilleryList
     * Effects: No return value, Terminal String output is provided.
     *
     *
     *
     */

    public void useArmyCards(int type) {
        CLICKED_ARMY_BUTTON++;

        boolean validation = isValid(type);
        if (!validation) {
            System.out.println("Not valid type!");
            return;
        }

        if (type == 1) {
            System.out.println("Test Case 1 occured, transition happening.");
            addCavalries(1);

            removeInfantryCard(3);
            return;

        } else if (type == 2) {
            System.out.println("Test Case 2 occured, transition happening.");

            addCavalries(2);
            removeInfantryCard(2);
            removeCavalryCard(1);

            return;

        }

        else if (type == 3) {
            System.out.println("Test Case 3 occured, transition happening.");
            addArtilleries(2);
            // removeInfantries(2);
            removeInfantryCard(2);
            // removeArtilleries(1);
            removeArtilleryCard(1);
            return;
        }

        else if (type == 4) {
            System.out.println("Test Case 4 occured, transition happening.");
            addCavalries(1);
            addArtilleries(1);
            removeInfantryCard(1);
            removeCavalryCard(2);
            return;

        }

        else if (type == 5) {
            System.out.println("Test Case 5 occured, transition happening.");
            addArtilleries(3);
            removeArtilleryCard(1);
            removeCavalryCard(2);
            return;

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
        return army.getTotalArmyUnit();
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

    public boolean isValid(int type) {
        switch (type) {
            case 1:
                if (infantryCount >= 3) {
                    return true;
                } else {
                    return false;
                }

            case 2:
                if (infantryCount >= 2 && cavalryCount >= 1) {
                    return true;
                }

                else {
                    return false;
                }

            case 3:
                if (infantryCount >= 2 && artilleryCount >= 1) {
                    return true;
                } else {
                    return false;
                }

            case 4:
                if (infantryCount >= 1 && cavalryCount >= 2) {
                    return true;
                } else {
                    return false;
                }

            case 5:
                if (artilleryCount >= 1 && cavalryCount >= 2) {
                    return true;
                } else {
                    return false;
                }

        }
        return false;

    }

    public void deleteCards(Card c[]) {

        for (Card c1 : c) {

            p.getCards().remove(c1);

        }
    }

    public void addTerritoryButtonListener(TerritoryButtonListener lis) {
        territoryButtonListeners.add(lis);
    }


    public void addCOListener(ChanceObserverListener coListener) {
        coListeners.add(coListener);
    }

    public void setTerritoryUIforObserver(int ID, int armyUnit, Color color, int territoryArmy) {

        for (ChanceObserverListener cL : coListeners) {
            cL.setTerritoryUI(ID, armyUnit, color, territoryArmy);
        }
    }


    public void setTerritoryInfo(int ID, int armyUnit, Color color, int territoryArmy) {
        for (TerritoryButtonListener l : territoryButtonListeners) {
            l.setTerritoryButtonInfo(ID, armyUnit, color, territoryArmy);
        }
    }

    public void useChanceCard() {
        //ChanceCard chanceCardTurn = new ChanceCard("COUP",ChanceCard.ChanceType.COUP);
       
         ChanceCard chanceCardTurn = this.chanceCards.get(0);

        if (chanceCardTurn == null) {
            System.out.println("NO CARDS FOR NOW !");
            return;
        }   
        Player cardUser = PlayerExpert.getPlayerInTurn();

        ChanceType typeOfCard = chanceCardTurn.getType();

        switch (typeOfCard) {
            case COUP:

                useCoup(cardUser);
                break;
  
            case DRAFT:
                useDraft(cardUser);
                break;

            case REBELLION:
                useRebellion(cardUser);
            break;

            case SECRETWEAPON:
                useSecretWeapon(cardUser);
                break;

            case REVOLT:
                useRevolt(cardUser);
                break;

            default:
                return;
        }

    }   

    public void useCoup(Player p) {
        System.out.println("COUP RUNNED?");
        Territory t = Board.getCurrentTerritory();
        t.setOwner(p);
       // gMode.setTerritoryInfo(t.getId(), p.getInventory().getTotalArmy(), p.getColor(), t.getTotalUnit());
        // setTerritoryUIforObserver(t.getId(), p.getInventory().getTotalArmy(), p.getColor(), t.getTotalUnit());
    }

    public void useSecretWeapon(Player p) {
        Territory t = Board.getCurrentTerritory();
        Player reveal = t.getOwner();
        Random random = new Random();
        int x = random.nextInt(2);

        if (x == 0) { // army card.
            if (reveal.getInventory().getArmyCards().size() > 0) {
                int index = random.nextInt(reveal.getInventory().getArmyCards().size());
                ArmyCard toRevealArmyCard = reveal.getInventory().getArmyCards().get(index);
                String description = reveal.getName() + "  has ArmyCard: " + toRevealArmyCard.getName();

                ChanceCardWindow window = new ChanceCardWindow(description);
                window.createChanceWindow();
            }

            else {
                ChanceCardWindow window = new ChanceCardWindow("Sorry, rival has no army cards : D");
                window.createChanceWindow();
            }
           

            

        }

        else { // territory;
            if (reveal.getInventory().getTerritoryCards().size() > 0)  {
                int index = random.nextInt(reveal.getInventory().getTerritoryCards().size());
                TerritoryCard toRevealTerritoryCard = reveal.getInventory().getTerritoryCards().get(index);
                String description = reveal.getName() + "  has TerritoryCard: " + toRevealTerritoryCard.getName();
                ChanceCardWindow window = new ChanceCardWindow(description);
                window.createChanceWindow();
            }

            else {
                ChanceCardWindow window = new ChanceCardWindow("Sorry, rival has no territory cards : D");
                window.createChanceWindow();
            }
           
        
        }

        p.getInventory().setChanceCardRequest(0);
        chanceCards.remove(0);

    }

    public void useRebellion(Player p) {
        Territory t = Board.getCurrentTerritory();
        if (t.getArmy().getTotalArmyUnit() == 1) {
            t.getArmy().setTotalArmyUnit(0);
            t.setOwner(null);
        }

        else {
            int half = t.getArmy().getTotalArmyUnit() / 2;
            t.getArmy().setTotalArmyUnit(t.getArmy().getTotalArmyUnit() - half);

        }
       // gMode.setTerritoryInfo(t.getId(), p.getInventory().getTotalArmy(), p.getColor(), t.getTotalUnit());
        // setTerritoryUIforObserver(t.getId(), p.getInventory().getTotalArmy(), p.getColor(), t.getTotalUnit());
        p.getInventory().setChanceCardRequest(0);
        chanceCards.remove(0);
        

    }

    public void useDraft(Player p) {
        p.getInventory().setDrawCardRequest(3);
        p.getInventory().setChanceCardRequest(0);
        chanceCards.remove(0);

    }

    public void useRevolt(Player p) {
        Territory[] memory = HandlerFactory.getInstance().getGameLogic().getMemory();

        if (memory[0].getOwner().equals(p) && memory[0].getOwner().equals(p)) {
            Territory t1 = memory[0];
            Territory t2 = memory[1];

            if (t1.getArmy().getTotalArmyUnit() <= 0) {
                ChanceCardWindow newWindow = new ChanceCardWindow("Source army does not have any units.");
                newWindow.createChanceWindow();
                return;
            }
            int infantryNumber = t1.getArmy().getInfantries();
            int artilleryNumber = t1.getArmy().getArtilleries();
            int cavalryNumber = t1.getArmy().getCavalries();

            t1.removeAllArmy();

            t2.getArmy().addArtilleries(artilleryNumber);
            t2.getArmy().addInfantries(infantryNumber);
            t2.getArmy().addCavalries(cavalryNumber);
            p.getInventory().removeTerritoryFromList(t1);

          
             setTerritoryUIforObserver(t1.getId(), p.getInventory().getTotalArmy(), p.getColor(), t1.getTotalUnit());
              setTerritoryUIforObserver(t2.getId(), p.getInventory().getTotalArmy(), p.getColor(), t2.getTotalUnit());
            System.out.println("Revolt is done!");
            
            p.getInventory().setChanceCardRequest(0);
            chanceCards.remove(0);

        }

        else {
            ChanceCardWindow window = new ChanceCardWindow("You must select two continents, both must be yours.");
            window.createChanceWindow();
        }
    }

}