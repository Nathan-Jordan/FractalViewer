import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class View extends JComponent {
    Generate gen;
    ArrayList<UiElement> uiElements = new ArrayList<>();


    View(Generate gen) {
        this.gen = gen;

        createUi();

        addMouseListener(new mouseEvent()); //Add mouse and key listeners
        addKeyListener(new keyEvent());
        addMouseMotionListener(new mouseMoveEvent());
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;


        g2d.drawImage(gen.getImg(), 0, 0, null);

        drawUi(g2d);
    }

    void createUi() {
        Button test = new Button(10, 10, 100, 30);
        test.setText("Test", 12);
        uiElements.add(test);
    }

    void drawUi(Graphics g){
        for (UiElement element : uiElements) {
            element.draw(g);
        }
    }


    private class mouseMoveEvent extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            uiElements.get(0).setText(String.valueOf(e.getX()),12);
            for (UiElement element : uiElements) {
                //if (element.checkCollision(e.getX())) {

                //}
            }
        }
    }

    private class mouseEvent extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent mEvent) {
            double targetMag = 1;
            if (mEvent.getButton() == 1) {
                switch (gen.currFractal) {
                    case MANDELBROT -> {
                        gen.centerR = (mEvent.getX() / (double) gen.width) * (gen.maxR - gen.minR) + gen.minR;
                        gen.centerI = (mEvent.getY() / (double) gen.height) * (gen.maxI - gen.minI) + gen.minI;
                    }
                    default -> {
                        gen.centerR = (mEvent.getY() / (double) gen.width) * (gen.maxR - gen.minR) + gen.minR;
                        gen.centerI = (mEvent.getX() / (double) gen.height) * (gen.maxI - gen.minI) + gen.minI;
                    }
                }
            } else if (mEvent.getButton() == 3) {
                targetMag = 2;
            }

            double minRTmp = gen.minR;
            double minITmp = gen.minI;
            gen.minR = gen.centerR - ((gen.maxR - gen.minR) / 2) / targetMag;
            gen.minI = gen.centerI - ((gen.maxI - gen.minI) / 2) / targetMag;
            gen.maxR = gen.centerR + ((gen.maxR - minRTmp) / 2) / targetMag;
            gen.maxI = gen.centerI + ((gen.maxI - minITmp) / 2) / targetMag;

            gen.redraw = true;
            gen.init();
        }
    }

    private class keyEvent extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent kEvent) {
            if (kEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {

            }
        }
    }
}
