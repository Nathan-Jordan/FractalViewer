package Fractals;

import Main.Constants;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Fractal {

    BufferedImage image;
    //merge the 2?
    //short[] buddhaDataR, buddhaDataG, buddhaDataB;  //Data for RGB channels
    short[] cbR, cbG, cbB;  //Colour data for RGB channels

    int w, h;   //Width, height
    int iterations; //Depth/detail
    int samples;
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

    ExecutorService service;
    List<Future<ThreadData>> futures = new ArrayList<>();
    int coreCount = Runtime.getRuntime().availableProcessors() - 1;


    public Fractal(int w, int h, int iterations, boolean looped) {
        this.w = w;
        this.h = h;
        this.iterations = iterations;
        this.looped = looped;

        service = Executors.newFixedThreadPool(coreCount);
    }

    public Fractal(int w, int h, int iterations, int samples, boolean looped) {
        this(w, h, iterations, looped);
        this.samples = samples;
    }


    public void init() {
        //buddhaDataR = new short[w * h];
        //buddhaDataG = new short[w * h];
        //buddhaDataB = new short[w * h];
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

    short[] colourBuffer(final short[] bufferIn) {
        short[] buffer = new short[bufferIn.length];
        double maxVal = 0;

        for (int i = 0; i < bufferIn.length; i++) {
            maxVal = Math.max(maxVal, bufferIn[i]);
        }

        double gamma = 2;

        for (int i = 0; i < bufferIn.length; i++) {
            //pixelData[i] = (int) ((pixelData[i] / maxVal) * BYTEMAX);
            buffer[i] = (short) (Math.pow((bufferIn[i] / maxVal), 1 / gamma) * Constants.BYTEMAX);
        }

        return buffer;
    }

    void createImage() {
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                image.setRGB(x, y, ((cbR[x + y * w] & 0x0ff) << 16) |
                        ((cbG[x + y * w] & 0x0ff) << 8) |
                        ((cbB[x + y * w] & 0x0ff)));
            }
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}
