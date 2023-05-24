package src.ConKUeror.UI.Frames.ArrowAnimation;

import java.awt.Graphics2D;
import java.awt.Image;

interface Arrow {
        void move();
        void rotateAngle(double angle);
        void draw(Graphics2D g, float x, float y, float pathHeight, float pathWidth,float lineHeight,float lineWidth);
}