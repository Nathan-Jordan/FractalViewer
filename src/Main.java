import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        int frameSize = 1000;

        Generate gen = new Generate(frameSize, frameSize, Constants.CurrentFractal.FALSE_BUDDHABROT);

        View view = new View(gen);
        JFrame f = new JFrame();

        f.add(view);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(frameSize, frameSize);  //Set size
        f.setVisible(true); //Show window


        while (true){
            view.repaint();
        }
    }
}
