package ConKUeror.domain.controller;

import ConKUeror.UI.Frames.MapView;
import ConKUeror.UI.Frames.ArrowAnimation.AnimationThread;
import ConKUeror.UI.Frames.ArrowAnimation.arrowAnimation;
import ConKUeror.domain.model.Modes.GameLogic;

public class ArrowAnimationController {
    
    private AnimationThread animationThread;
    private arrowAnimation arrowAnimation;
    private MapView map;
    private GameLogic gmode;

public ArrowAnimationController(AnimationThread animationThread, arrowAnimation arrowAnimation, MapView map) {

    this.animationThread = animationThread;
    this.arrowAnimation = arrowAnimation;
    this.map = map;



}

public void startAnimation() {
    animationThread.start();
}







}
