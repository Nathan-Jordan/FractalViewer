package Fractals;

public class Fractal {
    //merge the 2?
    short[] buddhaDataR, buddhaDataG, buddhaDataB;  //Data for RGB channels
    short[] cbR, cbG, cbB;  //Colour data for RGB channels

    int w, h;   //Width, height
    int iterations; //Depth/detail
    boolean looped;    //If more detail is continuously added

    float maxItR, maxItG, maxItB;   //Threshold for RGB channels

    //R = x = real
    //I = y = imaginary
    double centerR = 0;
    double centerI = 0;
    double minR = -2;
    double minI = -2;
    double maxR = 2;
    double maxI = 2;

    //Basic constructor
    Fractal(int w, int h, boolean looped, int iterations) {
        this.w = w;
        this.h = h;
        this.looped = looped;
        this.iterations = iterations;
    }

    void init() {
        buddhaDataR = new short[w * h];
        buddhaDataG = new short[w * h];
        buddhaDataB = new short[w * h];
        cbR = new short[w * h];
        cbG = new short[w * h];
        cbB = new short[w * h];
    }

    void generate() {

    }
}
