package test;

import javax.swing.*;
import java.awt.*;

public class TestFrame1 extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(10,10,200,200);
    }
}
