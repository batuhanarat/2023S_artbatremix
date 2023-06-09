package ConKUeror.domain.model.Player.Strategies.ComputerPlayer;



import java.util.List;
import java.io.Serializable;
import ConKUeror.domain.model.Army.Army;
import ConKUeror.domain.model.Army.Artillery;
import ConKUeror.domain.model.Army.Cavalry;
import ConKUeror.domain.model.Army.Infantry;

import ConKUeror.domain.model.Player.Strategies.IAttackBehaviour;

public class ComputerPlayerAttack implements IAttackBehaviour,Serializable {

    @Override
    public boolean attack(List<Infantry> attackingInfantries, List<Cavalry> attackingCavalries,
    List<Artillery> attackingArtilleries, Army defendingArmy)
    {
        return false;
    }
    
    
}
