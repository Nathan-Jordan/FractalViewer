import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        int frameSize = 1000;

        Generate gen = new Generate(frameSize, frameSize, Constants.CURRENTFRACTAL.FALSE_BUDDHABROT);
        gen.init();
        gen.start();


        while (!gen.isImageCreated()) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        View view = new View(gen);
        JFrame f = new JFrame();

        f.add(view);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(frameSize, frameSize);  //Set size
        f.setVisible(true); //Show window


        //Main loop
        while (true) {
            view.repaint();
        }
    }
}
