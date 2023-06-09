package ConKUeror.domain.model.Player.Strategies;

import java.util.List;

import ConKUeror.domain.model.Army.Army;
import ConKUeror.domain.model.Army.Artillery;
import ConKUeror.domain.model.Army.Cavalry;
import ConKUeror.domain.model.Army.Infantry;

public interface IAttackBehaviour {
   
    boolean attack(List<Infantry> attackingInfantries, List<Cavalry> attackingCavalries,
            List<Artillery> attackingArtilleries, Army defendingArmy);
}
