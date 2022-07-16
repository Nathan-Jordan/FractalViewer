package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class TestFrame2 extends JPanel {
    int i;

    public TestFrame2() {
        addMouseMotionListener(new mouseMoveEvent());
    }

    private class mouseMoveEvent extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            i = e.getX();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Verdana", Font.PLAIN, 46));
        g.drawString(String.valueOf(i), 100,100);
    }
}
