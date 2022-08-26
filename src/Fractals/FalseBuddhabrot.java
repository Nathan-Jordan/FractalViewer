package Fractals;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class FalseBuddhabrot extends Fractal{

    public FalseBuddhabrot(int w, int h, int iterations, int samples, boolean looped) {
        super(w, h, iterations, samples);
    }

    @Override
    public void generate() {
        cbR = generateFalseBuddhabrot(2000);
        cbG = generateFalseBuddhabrot(200);
        cbB = generateFalseBuddhabrot(20);

        //Thread?? - this is already pretty quick
        cbR = colourBuffer(cbR);
        cbG = colourBuffer(cbG);
        cbB = colourBuffer(cbB);

        createImage();
    }

    short[] generateFalseBuddhabrot(int it) {
        //System.out.println("Gen");
        short[] data = new short[w * h];

        int maxIt = it;
        float maxRadius = 4;

        for (int ix = 0; ix < 20_000_000; ix++) {
            double[] tR = new double[maxIt];
            double[] tI = new double[maxIt];

            double cr = Math.random() * (maxR - minR) + minR;
            double ci = Math.random() * (maxI - minI) + minI;
            double zr = cr;
            double zi = ci;

            int z = 0;
            for (; z < maxIt && ((zr * zr + zi * zi) < maxRadius); z++) {
                //z = z^2 + c - mandelbrot

                double zrTMP = zr;
                zr = zr * zr - zi * zi + cr;
                zi = zrTMP * zi * 2 + ci;

                tR[z] = zr;
                tI[z] = zi;
            }


            if (z < maxIt) {    //If z has not gone out of iteration range
                for (int sample = 0; sample < z; sample++) {

                    int x = (int) ((tR[sample] - minR) * ((double) w / (maxR - minR)));
                    int y = (int) ((tI[sample] - minI) * ((double) h / (maxI - minI)));


                    if (x >= 0 && x < w && y >= 0 && y < h) {  //If pixel is in screen
                        data[x * w + y]++;
                    }
                }
            }
        }

        return data;
    }
}
