package src.ConKUeror.domain.model.Player;

import java.awt.Color;
import java.util.List;

import src.ConKUeror.domain.model.Army.Army;
import src.ConKUeror.domain.model.Board.Card;
import src.ConKUeror.domain.model.Board.Territory;
import src.ConKUeror.domain.model.Player.Strategies.IAttackBehaviour;
import src.ConKUeror.domain.model.Player.Strategies.IDeployBehaviour;
import src.ConKUeror.domain.model.Player.Strategies.IFortifyBehaviour;

public class Player {

        public PlayerInventory inv = new PlayerInventory();
        //private Army deployedArmy = new Army();

        private Color playerColor;

        IDeployBehaviour db;
        IAttackBehaviour ab;
        IFortifyBehaviour fb;



        private String name;
        //private List<Army> armies;
        //private List<Territory> territories;
        private List<Card> cards;

        public Player(String name,
        IDeployBehaviour db,
        IAttackBehaviour ab,
        IFortifyBehaviour fb,
        PlayerInventory inv) {

            this.name = name;
            this.db = db;
            this.ab = ab;
            this.fb = fb;
            this.inv = inv;


        }





        public void setColor(Color color) {
                this.playerColor = color;

        }
        public Color getColor() {
            return playerColor;
        }


        public void deploy(Territory t, int army) {
            db.deploy(t,army);
        }
        public void attack(int attackingArmy, int defendingArmy) {
            ab.attack(attackingArmy, defendingArmy);
        }

        public void fortify() {
            fb.fortify();
        }

        public String getName() {

            return this.name;
        }

        public List<Card> getCards() {

            return this.cards;
        }

        public PlayerInventory getInventory() {
            return inv;


        }

        


    }


