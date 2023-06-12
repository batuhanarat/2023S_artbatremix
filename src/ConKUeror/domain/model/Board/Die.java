package ConKUeror.domain.model.Board;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import ConKUeror.domain.model.Player.Player;
import ConKUeror.domain.model.Player.PlayerExpert;

public class Die  implements Serializable{

    private static Die dieInstance = null;

    private Random random;
    private int faceValue;
    private int indexValue;
    private int customValue;
   

    private Die()
    {
        this.random = new Random();
     
    }

    public static Die getDieInstance()
    {
        if (dieInstance == null)
        {
            dieInstance = new Die();
        }
        return dieInstance;
    }

    public void rollDie()
    {
        faceValue =  random.nextInt(7);
    }

    public int getFaceValue() {

        return faceValue;
    }


   public void customRollDie(int i) {
        customValue = random.nextInt(i);
   }

   public int getCustomValue(int i) {
        customRollDie(i);
        return customValue;
   }




 

    

    
    
}
