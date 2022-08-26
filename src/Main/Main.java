package Main;

import Fractals.*;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        int frameSize = 1000;

        //Fractal fractal = new Mandelbrot(frameSize, frameSize + 20, 150);
        //Fractal fractal = new Buddhabrot(frameSize, frameSize, 1_000, 10_000_000, false);
        //Fractal fractal = new TESTFalseBuddhabrot(frameSize, frameSize, 2_000, 200, 20, 10_000_000, false);
        Fractal fractal = new FalseBuddhabrot(frameSize, frameSize, 20,  1_000_000, false);
        fractal.init();
        fractal.generate();

        /*
        Main.Generate gen = new Main.Generate(frameSize, frameSize, Main.Constants.FRACTAL.FALSE_BUDDHABROT);
        gen.init();

        Thread genThread = new Thread(gen);
        genThread.start();



        while (!gen.isImageCreated()) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
         */

        View view = new View(fractal);
        JFrame f = new JFrame();

        f.add(view);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(frameSize, frameSize);  //Set size
        f.setVisible(true); //Show window


        //Main.Main loop
        while (true) {
            view.repaint();
        }
    }
}

