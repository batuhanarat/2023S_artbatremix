package ConKUeror.domain.model.Player.Strategies.RealPlayer;


import java.io.Serializable;


import java.util.List;

import ConKUeror.domain.model.Army.Army;
import ConKUeror.domain.model.Army.Artillery;
import ConKUeror.domain.model.Army.Cavalry;
import ConKUeror.domain.model.Army.Infantry;

import ConKUeror.domain.model.Board.DiceRoller;
import ConKUeror.domain.model.Player.Strategies.IAttackBehaviour;

public class RealPlayerAttack implements IAttackBehaviour,Serializable {

    @Override
    public boolean attack(List<Infantry> attackingInfantries, List<Cavalry> attackingCavalries,
    List<Artillery> attackingArtilleries, Army defendingArmy)
    {
        if (DiceRoller.getDiceRollerInstance().rollForAttack(attackingInfantries, attackingCavalries, attackingArtilleries,
        defendingArmy))
        {
            System.out.println("Attack is successful");
            return DiceRoller.getDiceRollerInstance().rollForAttack(attackingInfantries, attackingCavalries, attackingArtilleries,
            defendingArmy);
        }
        else
        {
            System.out.println("Attack failed");
            return DiceRoller.getDiceRollerInstance().rollForAttack(attackingInfantries, attackingCavalries, attackingArtilleries,
            defendingArmy);
        }

    }
    
}
