package ConKUeror.domain.controller;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import ConKUeror.UI.Frames.MapView;
import ConKUeror.UI.Frames.ArrowAnimation.AnimationThread;
import ConKUeror.UI.Frames.ArrowAnimation.arrowAnimation;
import ConKUeror.domain.model.Board.Board;
import ConKUeror.domain.model.Modes.BuildMode;
import ConKUeror.domain.model.Modes.GameLogic;
import ConKUeror.domain.model.Modes.StartMode;

public class ArrowAnimationController implements ActionListener {
    
    private AnimationThread animationThread;
    private arrowAnimation arrowAnimation;
    private MapView map;
    private GameLogic gmode;
    private static ArrowAnimationController instance;

    Board board;
    StartMode sMode;
    BuildMode buildMode;

private ArrowAnimationController() throws IOException  {

    this.buildMode = new BuildMode();
    this.sMode = new StartMode(buildMode);
    this.board = new Board();
    this.map = new MapView();
    this.gmode = new GameLogic(null, null);

}

public static ArrowAnimationController getInstance() throws IOException {
    if (instance == null) {
        instance = new ArrowAnimationController();
    }
    return instance;
}


// public Graphics2D getGraphics() {
// return MapView.g2d;
// }


public void startAnimation() {
    animationThread.start();
}


@Override
public void actionPerformed(ActionEvent e) {

    // map.g2d
    
    // try {
    //     // gmode.prepareGame(null, null);
    // } catch (InterruptedException e1) {
    //     // TODO Auto-generated catch block
    //     e1.printStackTrace();
    // }
    // map.getMPanel().repaint();
    


    


   
}











}
