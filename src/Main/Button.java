package Main;

import java.awt.*;

public class Button extends UiElement {

    public Button(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    void draw(Graphics g){
        setStyle(g);

        g.setColor(Color.WHITE);
        g.fillRect(x, y, w, h);

        g.setColor(Color.BLACK);
        g.setFont(font);
        int textW = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x + w/2 - textW/2, y + fontSize/2 + h/2);

        resetStyle(g);
    }
}
