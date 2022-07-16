import java.awt.*;

public class Generate extends Thread {
    int frame;

    @Override
    public void run() {
        super.run();

        while(true) {
            frame++;
            generate();
            getColourBuffer();
        }
    }

    static final int BYTEMAX = 0xff;

    Constants.CurrentFractal currFractal;
    int width, height;
    int[] buddhaData;
    int[] cb;

    int[] buddhaDataR, buddhaDataG, buddhaDataB;
    int[] cbR, cbG, cbB;

    float thresholdR = 123;
    float thresholdG = 253;
    float thresholdB = 12;

    double centerR = 0;
    double centerI = 0;
    double minR = -2;
    double minI = -2;
    double maxR = 2;
    double maxI = 2;
    //R = x = real
    //I = y = imaginary

    boolean redraw = true;

    public Generate(int width, int height, Constants.CurrentFractal currentFractal) {
        this.width = width;
        this.height = height;
        this.currFractal = currentFractal;
    }

    public Generate(Generate gen) {
        this.width = gen.width;
        this.height = gen.height;
        this.currFractal = gen.currFractal;
    }


    void init() {
        switch (currFractal) {
            case MANDELBROT, BUDDHABROT -> buddhaData = new int[width * height * 3];
            case FALSE_BUDDHABROT -> {
                buddhaDataR = new int[width * height];
                buddhaDataG = new int[width * height];
                buddhaDataB = new int[width * height];
            }
            default -> {
            }
        }
    }

    void generate() {
        switch (currFractal) {
            case MANDELBROT -> {
                if (redraw) generateMandelbrot();
                redraw = false;
            }
            case BUDDHABROT -> generateBuddhabrot();
            case FALSE_BUDDHABROT -> {
                buddhaDataR = generateFalseBuddhabrot(5000, buddhaDataR);
                buddhaDataG = generateFalseBuddhabrot(500, buddhaDataG);
                buddhaDataB = generateFalseBuddhabrot(50, buddhaDataB);
            }
            default -> {
            }
        }
    }


    void generateMandelbrot() {
        int maxIt = 200;
        float maxRadius = 4;

        for (int ix = 0; ix < width; ix++) {
            for (int iy = 0; iy < height; iy++) {

                double cr = (ix / (double) width) * (maxR - minR) + minR;
                double ci = (iy / (double) height) * (maxI - minI) + minI;
                double zr = cr;
                double zi = ci;

                int z = 1;
                for (; z < maxIt && ((zr * zr + zi * zi) < maxRadius); z++) {
                    //z = z^2 + c - mandelbrot

                    double zrTMP = zr;
                    zr = zr * zr - zi * zi + cr;
                    zi = zrTMP * zi * 2 + ci;
                }

                int colourR = 0;
                int colourG = 0;
                int colourB = 0;

                if (z < maxIt) {
                    colourR = colourKernel(z, thresholdR);
                    colourG = colourKernel(z, thresholdG);
                    colourB = colourKernel(z, thresholdB);
                }

                int pixelOffset = (ix + iy * width) * 3;
                buddhaData[pixelOffset] = colourR;
                buddhaData[pixelOffset + 1] = colourG;
                buddhaData[pixelOffset + 2] = colourB;
            }
        }
    }

    int colourKernel(int i, float threshold) {
        return (int) ((Math.sin(i / threshold) + 1) / 2d * BYTEMAX);
    }


    void generateBuddhabrot() {
        //pixelData = new int[width * height * 3];

        System.out.println("Gen");
        int maxIt = 400;
        float maxRadius = 4;

        for (int ix = 0; ix < 1_000_000; ix++) {
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

                    int x = (int) ((tR[sample] - minR) * ((double) width / (maxR - minR)));
                    int y = (int) ((tI[sample] - minI) * ((double) height / (maxI - minI)));


                    if (x >= 0 && x < width && y >= 0 && y < height) {
                        int pixelOffset = (x * width + y) * 3;

                        buddhaData[pixelOffset] += sample < thresholdR ? 1 : 0;
                        buddhaData[pixelOffset + 1] += sample < thresholdG ? 1 : 0;
                        buddhaData[pixelOffset + 2] += sample < thresholdB ? 1 : 0;
                    }
                }
            }
        }
    }

    int[] generateFalseBuddhabrot(int it, int[] data) {
        //System.out.println("Gen");

        int maxIt = it;
        float maxRadius = 4;

        for (int ix = 0; ix < 100_000; ix++) {
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

                    int x = (int) ((tR[sample] - minR) * ((double) width / (maxR - minR)));
                    int y = (int) ((tI[sample] - minI) * ((double) height / (maxI - minI)));


                    if (x >= 0 && x < width && y >= 0 && y < height) {  //If pixel is in screen
                        int pixelOffset = (x * width + y);

                        data[pixelOffset]++;
                    }
                }
            }
        }

        return data;
    }


    int[] colourBuffer(final int[] bufferIn) {
        int[] buffer = new int[bufferIn.length];
        double maxVal = 0;

        for (int i = 0; i < bufferIn.length; i++) {
            maxVal = Math.max(maxVal, bufferIn[i]);
        }

        double gamma = 2;

        for (int i = 0; i < bufferIn.length; i++) {
            //pixelData[i] = (int) ((pixelData[i] / maxVal) * BYTEMAX);
            buffer[i] = (int) (Math.pow((bufferIn[i] / maxVal), 1 / gamma) * BYTEMAX);
        }

        return buffer;
    }

    void getColourBuffer() {
        switch (currFractal) {
            case MANDELBROT -> {
                cb = colourBuffer(buddhaData);
            }
            case BUDDHABROT -> cb = colourBuffer(buddhaData);
            case FALSE_BUDDHABROT -> {
                cbR = colourBuffer(buddhaDataR);
                cbG = colourBuffer(buddhaDataG);
                cbB = colourBuffer(buddhaDataB);
            }
            default -> {
            }
        }
    }

    Color getPixelCol(int x, int y) {
        switch (currFractal) {
            case MANDELBROT, BUDDHABROT -> {
                return new Color(cb[(x + y * width) * 3],
                        cb[(x + y * width) * 3 + 1],
                        cb[(x + y * width) * 3 + 2]);
            }
            case FALSE_BUDDHABROT -> {
                return new Color(cbR[(x + y * width)],
                        cbG[(x + y * width)],
                        cbB[(x + y * width)]);
            }
            default -> {
                return Color.BLACK;
            }
        }
    }
}
