package ConKUeror.domain.model.Board;

import java.io.Serializable;
import java.util.ArrayList;

public class MapConstants implements Serializable {
    public ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();



    public  void fillCoordinates() {

        coordinates.add(new Coordinate(68, 108));
        coordinates.add(new Coordinate(154, 101));
        coordinates.add(new Coordinate(371, 64));
        coordinates.add(new Coordinate(300, 176));
        coordinates.add(new Coordinate(229, 179));
        coordinates.add(new Coordinate(150, 161));
        coordinates.add(new Coordinate(158, 245));
        coordinates.add(new Coordinate(236, 268));
        coordinates.add(new Coordinate(172, 345));
        coordinates.add(new Coordinate(244, 403));
        coordinates.add(new Coordinate(242, 498));
        coordinates.add(new Coordinate(272, 584));
        coordinates.add(new Coordinate(346, 476));
        coordinates.add(new Coordinate(499, 464));
        coordinates.add(new Coordinate(590, 544));
        coordinates.add(new Coordinate(591, 642));
        coordinates.add(new Coordinate(692, 647));
        coordinates.add(new Coordinate(629, 483));
        coordinates.add(new Coordinate(582, 416));
        coordinates.add(new Coordinate(662, 363));
        coordinates.add(new Coordinate(561, 307));
        coordinates.add(new Coordinate(468, 310));
        coordinates.add(new Coordinate(455, 229));
        coordinates.add(new Coordinate(469, 128));
        coordinates.add(new Coordinate(536, 144));
        coordinates.add(new Coordinate(543, 238));
        coordinates.add(new Coordinate(648, 180));
        coordinates.add(new Coordinate(750, 168));
        coordinates.add(new Coordinate(733, 268));
        coordinates.add(new Coordinate(789, 377));
        coordinates.add(new Coordinate(865, 317));
        coordinates.add(new Coordinate(1001, 253));
        coordinates.add(new Coordinate(897, 239));
        coordinates.add(new Coordinate(879, 172));
        coordinates.add(new Coordinate(806, 117));
        coordinates.add(new Coordinate(885, 85));
        coordinates.add(new Coordinate(974, 99));
        coordinates.add(new Coordinate(882, 413));
        coordinates.add(new Coordinate(905, 524));
        coordinates.add(new Coordinate(998, 501));
        coordinates.add(new Coordinate(1023, 602));
        coordinates.add(new Coordinate(947, 640));

            //pause-resume
            coordinates.add(new Coordinate(10 , 19));
            //help
            coordinates.add(new Coordinate(160, 19));
            //roll
            coordinates.add(new Coordinate(50,400));
            //execute
            coordinates.add(new Coordinate(50,500 ));
            //next
            coordinates.add(new Coordinate(50, 600));




        }

        public void addConnection(int x, int y) {
            Board.getTerritoryWithIndex(x).addConnectionDual(Board.getTerritoryWithIndex(y));

        }

        public void initalizeConnections() {


            for(int i = 0; i<36; i++) {
                addConnection(i,i+1);

            }

            for(int i = 38; i<41; i++ ) {
                addConnection(i,i+1);

            }

            addConnection(0,36);
            addConnection(0,5);
            addConnection(5,1);
            addConnection(1,4);
            addConnection(2,4);
            addConnection(3,7);
            addConnection(4,7);
            addConnection(4,6);
            addConnection(6,8);
            addConnection(9,12);
            addConnection(10,12);
            addConnection(2,23);
            addConnection(22,25);
            addConnection(22,24);
            addConnection(21,25);
            addConnection(21,13);
            addConnection(20,13);
            addConnection(25,20);
            addConnection(24,26);
            addConnection(20,26);
            addConnection(20,18);
            addConnection(13,18);
            addConnection(13,17);
            addConnection(14,17);
            addConnection(15,17);
            addConnection(17,19);
            addConnection(19,26);
            addConnection(26,28);
            addConnection(28,19);
            addConnection(19,29);
            addConnection(29,37);
            addConnection(28,30);
            addConnection(27,30);
            addConnection(34,30);
            addConnection(34,32);
            addConnection(27,34);
            addConnection(35,33);
            addConnection(36,32);
            addConnection(32,30);
            addConnection(30,37);
            addConnection(36,31);
            addConnection(38,41);
            addConnection(41,40);
            addConnection(38,37);
            addConnection(33,36);



        }


}
