import java.awt.*;

public class UiElement {
    int x, y;
    int w, h;
    String text;
    int fontSize;
    Font font;
    Color colour;

    Color originalColour;
    Font originalFont;

    public UiElement(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.font = new Font("Verdana", Font.PLAIN, 12);

        this.colour = Color.BLACK;
    }


    void draw(Graphics g) {
        setStyle(g);

        g.fillRect(x, y, w, h);

        resetStyle(g);
    }




    void setStyle(Graphics g){
        originalColour = g.getColor();
        originalFont = g.getFont();

        g.setColor(colour);
        g.setFont(font);
    }

    void resetStyle(Graphics g){
        g.setColor(originalColour);
        g.setFont(originalFont);
    }



    void setText(String text, int fontSize){
        this.text = text;
        this.fontSize = fontSize;

        this.font = new Font("Verdana", Font.PLAIN, fontSize);
    }
    void setFontSize(int fontSize){
        this.fontSize = fontSize;

        font = new Font(font.getFontName(), font.getStyle(), fontSize);
    }
    void setFontFamily(String fontFamily){
        font = new Font(fontFamily, font.getStyle(), font.getSize());
    }

    boolean checkCollision() {


        return true;
    }
}
