package Main;

import Fractals.Fractal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class View extends JComponent {
    Fractal fractal;
    ArrayList<UiElement> uiElements = new ArrayList<>();


    View(Fractal fractal) {
        this.fractal = fractal;

        createUi();

        addMouseListener(new mouseEvent()); //Add mouse and key listeners
        addKeyListener(new keyEvent());
        addMouseMotionListener(new mouseMoveEvent());
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(fractal.getImage(), 0, 0,null);

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

            if (mEvent.getButton() == 1) {
                fractal.moveTo(mEvent.getX(), mEvent.getY());
                //If not mandelbrot
                //fractal.centerR = (mEvent.getY() / (double) fractal.width) * (fractal.maxR - fractal.minR) + fractal.minR;
                //fractal.centerI = (mEvent.getX() / (double) fractal.height) * (fractal.maxI - fractal.minI) + fractal.minI;

            } else if (mEvent.getButton() == 3) {
                fractal.zoom(2);
            }
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
