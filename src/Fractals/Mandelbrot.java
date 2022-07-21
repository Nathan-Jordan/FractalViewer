package Fractals;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import Main.Constants;

public class Mandelbrot extends Fractal {

    ExecutorService service;
    List<Future<ThreadData>> futures = new ArrayList<>();
    int coreCount = Runtime.getRuntime().availableProcessors();

    public Mandelbrot(int w, int h, int iterations, boolean looped) {
        super(w, h, iterations, looped);

        service = Executors.newFixedThreadPool(coreCount);
    }

    @Override
    public void generate() {
        //super.generate(); nothing


        //ADD RGB THRESHOLDS


        //Create threads for Mandelbrot
        //Get results from thread

        coreCount = 9;

        int part = (w * h) / (coreCount - 1);
        int xEnd = 0;
        int yEnd = 0;
        int length = 0;
        int previousLength = 0;

        for (int ix = 1; ix < coreCount; ix++) {
            int yStart = yEnd;
            int xStart = xEnd;


            if (ix == coreCount - 1) {
                xEnd = 0;
                yEnd = h;
            } else {
                yEnd = part * ix / h;
                xEnd = part * ix - yEnd * w;
            }


            length = Math.abs(previousLength - (xEnd + yEnd * h));
            previousLength = xEnd + yEnd * h;


            System.out.println(xStart + " : " + yStart + " : " + xEnd + " : " + yEnd + "   -   " + length);


            Future<ThreadData> data = service.submit(new MandelbrotThread(xStart, xEnd, yStart, yEnd, length));
            futures.add(data);
        }



        //Create colours "colourBuffer" function (threads?)
        for (Future<ThreadData> future: futures) {
            try {
                ThreadData tData = future.get();

                System.arraycopy(tData.getDataBufferR(), 0, cbR,
                        tData.getxStart() + tData.getyStart() * h, tData.getLength());
                System.arraycopy(tData.getDataBufferG(), 0, cbG,
                        tData.getxStart() + tData.getyStart() * h, tData.getLength());
                System.arraycopy(tData.getDataBufferB(), 0, cbB,
                        tData.getxStart() + tData.getyStart() * h, tData.getLength());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        cbR = colourBuffer(cbR);
        cbG = colourBuffer(cbG);
        cbB = colourBuffer(cbB);

        //Create image
        createImage();
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






    class MandelbrotThread implements Callable<ThreadData> {

        int xStart, yStart;
        int xEnd, yEnd;
        int length;


        public MandelbrotThread(int xStart, int xEnd, int yStart, int yEnd, int length) {
            this.xStart = xStart;
            this.xEnd = xEnd;
            this.yStart = yStart;
            this.yEnd = yEnd;
            this.length = length;
        }


        @Override
        public ThreadData call() {
            ThreadData tData = new ThreadData(xStart, yStart, xEnd, yEnd, length);

            int count = 0;

            for (int iy = yStart; iy < yEnd; iy++) {
                for (int ix = xStart; ix < 1000; ix++) {
                    double cr = (ix / (double) w) * (maxR - minR) + minR;
                    double ci = (iy / (double) h) * (maxI - minI) + minI;
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

                    int pixelOffset = (count++);
                    tData.setDataIndexR(pixelOffset, colourR);
                    tData.setDataIndexG(pixelOffset, colourG);
                    tData.setDataIndexB(pixelOffset, colourB);
                }
            }

            return tData;
        }
    }
}
