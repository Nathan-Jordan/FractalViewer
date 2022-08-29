package Main;

import Fractals.*;
import javax.swing.*;


public class Main {

    static class ThreadRunner extends Thread {
        Fractal fractal;

        public ThreadRunner(Fractal fractal) {
            this.fractal = fractal;
            fractal.init();
        }

        public void run() {
            fractal.generate();

            while(fractal.isLooped()){
                fractal.generate();
            }
        }
    }

    public static void main(String[] args) {
        int frameSize = 1000;

        //Fractal fractal = new Mandelbrot(frameSize, frameSize + 20, 150);
        //Fractal fractal = new Buddhabrot(frameSize, frameSize, 1_000, 10_000_000);
        Fractal fractal = new TESTFalseBuddhabrot(frameSize, frameSize, 2_000, 200, 20, 1_000_000);
        //Fractal fractal = new FalseBuddhabrot(frameSize, frameSize, 20,  1_000_000, false);

        fractal.setLooped(true);
        ThreadRunner startThread = new ThreadRunner(fractal);
        startThread.start();


        View view = new View(fractal);
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

