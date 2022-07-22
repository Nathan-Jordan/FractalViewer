package Fractals;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import Main.Constants;

public class Mandelbrot extends Fractal {

    public Mandelbrot(int w, int h, int iterations, boolean looped) {
        super(w, h, iterations, looped);
    }


    @Override
    public void generate() {
        //ADD RGB THRESHOLDS

        int part = (w * h) / coreCount;

        for (int ix = 0; ix < coreCount - 1; ix++) {
            Future<ThreadData> data = service.submit(new Thread(part * ix, part));
            futures.add(data);
        }

        Future<ThreadData> data = service.submit(new Thread(part * (coreCount - 1), w * h - (part * coreCount) + part));
        futures.add(data);


        //Create colours "colourBuffer" function (threads?)
        for (Future<ThreadData> future: futures) {
            try {
                ThreadData tData = future.get();

                System.arraycopy(tData.getDataBufferR(), 0, cbR, tData.getOffset(), tData.getSize());
                System.arraycopy(tData.getDataBufferG(), 0, cbG, tData.getOffset(), tData.getSize());
                System.arraycopy(tData.getDataBufferB(), 0, cbB, tData.getOffset(), tData.getSize());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        //Thread??
        cbR = colourBuffer(cbR);
        cbG = colourBuffer(cbG);
        cbB = colourBuffer(cbB);

        createImage();
    }


    class Thread extends ThreadData implements Callable<ThreadData> {

        public Thread(int offset, int size) {
            super(offset, size);
        }

        @Override
        public ThreadData call() {

            int count = 0;

            for (int p = offset; p < offset + size; p++, count++) {
                int x = p % w;
                int y = p / w;

                double cr = (x / (double) w) * (maxR - minR) + minR;
                double ci = (y / (double) h) * (maxI - minI) + minI;
                double zr = cr;
                double zi = ci;

                int z = 1;
                for (; z < iterations && ((zr * zr + zi * zi) < maxRadius); z++) {
                    //z = z^2 + c - mandelbrot

                    double zrTMP = zr;
                    zr = zr * zr - zi * zi + cr;
                    zi = zrTMP * zi * 2 + ci;
                }

                short colourR = 0;
                short colourG = 0;
                short colourB = 0;

                if (z < iterations) {
                    colourR = (short) ((Math.sin(z / maxItR) + 1) / 2d * Constants.BYTEMAX);
                    colourG = (short) ((Math.sin(z / maxItG) + 1) / 2d * Constants.BYTEMAX);
                    colourB = (short) ((Math.sin(z / maxItB) + 1) / 2d * Constants.BYTEMAX);
                }

                setDataIndexR(count, colourR);
                setDataIndexG(count, colourG);
                setDataIndexB(count, colourB);
            }

            return this;
        }
    }
}
