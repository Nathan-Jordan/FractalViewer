package Fractals;

import java.awt.image.BufferedImage;

public class Fractal {

    BufferedImage image;
    //merge the 2?
    short[] buddhaDataR, buddhaDataG, buddhaDataB;  //Data for RGB channels
    short[] cbR, cbG, cbB;  //Colour data for RGB channels

    int w, h;   //Width, height
    int iterations; //Depth/detail
    boolean looped;    //If more detail is continuously added

    float maxItR = 255;
    float maxItG = 255;
    float maxItB = 255;   //Threshold for RGB channels

    //R = x = real
    //I = y = imaginary
    double centerR = 0;
    double centerI = 0;
    double minR = -2;
    double minI = -2;
    double maxR = 2;
    double maxI = 2;
    float maxRadius = 4;


    //Default constructor (Mandelbrot)
    public Fractal(int w, int h, int iterations, boolean looped) {
        this.w = w;
        this.h = h;
        this.iterations = iterations;
        this.looped = looped;
    }

    public void init() {
        buddhaDataR = new short[w * h];
        buddhaDataG = new short[w * h];
        buddhaDataB = new short[w * h];
        cbR = new short[w * h];
        cbG = new short[w * h];
        cbB = new short[w * h];
    }

    public void generate() {

    }


    public void moveTo(int x, int y) {
        centerR = (x / (double) w) * (maxR - minR) + minR;
        centerI = (y / (double) h) * (maxI - minI) + minI;

        updateFrame(1);
    }

    public void zoom(int targetMag) {
        updateFrame(targetMag);
    }

    public void updateFrame(double targetMag) {
        double minRTmp = minR;
        double minITmp = minI;
        minR = centerR - ((maxR - minR) / 2) / targetMag;
        minI = centerI - ((maxI - minI) / 2) / targetMag;
        maxR = centerR + ((maxR - minRTmp) / 2) / targetMag;
        maxI = centerI + ((maxI - minITmp) / 2) / targetMag;

        init();
        generate();
    }

    public BufferedImage getImage() {
        return image;
    }
}
