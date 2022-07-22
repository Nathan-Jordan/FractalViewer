package Fractals;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import Main.Constants;

public class Buddhabrot extends Fractal{

    public Buddhabrot(int w, int h, int iterations, int samples, boolean looped) {
        super(w, h, iterations, samples, looped);
    }

    @Override
    public void generate() {

        int part = samples / coreCount;

        for (int ix = 0; ix < coreCount - 1; ix++) {
            Future<ThreadData> data = service.submit(new Thread(part * ix, part, w * h));
            futures.add(data);
        }

        Future<ThreadData> data = service.submit(new Thread(part * (coreCount - 1), samples - (part * coreCount) + part, w * h));
        futures.add(data);


        //Create colours "colourBuffer" function (threads?)
        for (Future<ThreadData> future: futures) {
            try {
                ThreadData tData = future.get();

                for (int ix = 0; ix < tData.getDataBufferR().length; ix++) {
                    cbR[ix] += tData.getDataBufferR()[ix];
                    cbG[ix] += tData.getDataBufferG()[ix];
                    cbB[ix] += tData.getDataBufferB()[ix];
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        //Thread?? - this is already pretty quick
        cbR = colourBuffer(cbR);
        cbG = colourBuffer(cbG);
        cbB = colourBuffer(cbB);

        createImage();
    }



    class Thread extends ThreadData implements Callable<ThreadData> {

        public Thread(int offset, int size, int arrSize) {
            super(offset, size, arrSize);
        }

        @Override
        public ThreadData call() {
            //pixelData = new int[width * height * 3];

            float maxRadius = 4;

            for (int ix = offset; ix < offset + size; ix++) {
                double[] tR = new double[iterations];
                double[] tI = new double[iterations];

                double cr = Math.random() * (maxR - minR) + minR;
                double ci = Math.random() * (maxI - minI) + minI;
                double zr = cr;
                double zi = ci;


                int z = 0;
                for (; z < iterations && ((zr * zr + zi * zi) < maxRadius); z++) {
                    //z = z^2 + c - mandelbrot

                    double zrTMP = zr;
                    zr = zr * zr - zi * zi + cr;
                    zi = zrTMP * zi * 2 + ci;

                    tR[z] = zr;
                    tI[z] = zi;
                }


                if (z < iterations) {    //If z has not gone out of iteration range
                    for (int sample = 0; sample < z; sample++) {

                        int x = (int) ((tR[sample] - minR) * ((double) w / (maxR - minR)));
                        int y = (int) ((tI[sample] - minI) * ((double) h / (maxI - minI)));


                        if (x >= 0 && x < w && y >= 0 && y < h) {
                            int pixelOffset = (x * w + y);

                            addDataIndexR(pixelOffset, (short) (sample < maxItR ? 1 : 0));
                            addDataIndexG(pixelOffset, (short) (sample < maxItG ? 1 : 0));
                            addDataIndexB(pixelOffset, (short) (sample < maxItB ? 1 : 0));
                        }
                    }
                }
            }

            return this;
        }
    }
}
