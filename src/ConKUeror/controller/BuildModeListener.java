package src.ConKUeror.controller;


//This interface is created for Observer design pattern.
//BuildMode is publisher and BuildMode Screen is the observer.
public interface BuildModeListener {
    
    void onBoardEvent(String msg);

}

