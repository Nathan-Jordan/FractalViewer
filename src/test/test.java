package test;

import javax.swing.*;

public class test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("LayeredPaneDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new JLayeredPane();
        newContentPane.add(new TestFrame1(), new Integer(1));
        newContentPane.add(new TestFrame2(), new Integer(2));
        //newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.setSize(500, 500);  //Set size
        frame.setVisible(true);
        frame.repaint();
    }
}
