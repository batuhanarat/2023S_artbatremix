package ConKUeror.UI.Buttons;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JButton;

import ConKUeror.domain.model.Player.Player;

public class CustomButton extends JButton implements Serializable {
    private transient Color backgroundColor;
    private String text;
    private Font font;

    public CustomButton(String text, Font font, Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.text = text;
        this.font = font;
        setContentAreaFilled(true);
        addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e ) {
        //    getParent().dispatchEvent(e);
            CustomButton.this.backgroundColor = Color.GREEN;
        }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    @Override
    public void paint(Graphics g) {
        paintComponent(g);
        paintBorder(g);
        g.setFont(font);
        g.setColor(Color.BLACK);
        int stringWidth = g.getFontMetrics().stringWidth(text);
        int stringHeight = g.getFontMetrics().getHeight();
        g.drawString(text, (getWidth() - stringWidth) / 2, (getHeight() + stringHeight / 2) / 2);
    }
}


