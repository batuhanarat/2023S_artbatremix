package src.ConKUeror;


import src.ConKUeror.UI.Frames.BuildModeScreen;
import src.ConKUeror.UI.Frames.ArrowAnimation.Animation;
import src.ConKUeror.domain.controller.BuildHandler;
import src.ConKUeror.domain.controller.HandlerFactory;

public class Main {


public static void main(String[] args) {

  HandlerFactory controller = HandlerFactory.getInstance();
  BuildHandler buildHandler = controller.giveBuildHandler();
  BuildModeScreen buildModeScreen = new BuildModeScreen(buildHandler);

 


}
}

