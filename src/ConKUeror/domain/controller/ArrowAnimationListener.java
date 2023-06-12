package ConKUeror.domain.controller;

import java.util.List;

import ConKUeror.domain.model.Board.Territory;

public interface ArrowAnimationListener {

    void drawArrows(Territory t, ArrayList<Integer> territoriesAvailableForAttack);
    
}
