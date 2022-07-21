package Main;

import Fractals.Fractal;
import Fractals.Mandelbrot;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        int frameSize = 1000;

        Fractal fractal = new Mandelbrot(frameSize, frameSize, 10000, false);
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

