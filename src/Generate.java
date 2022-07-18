import java.awt.image.BufferedImage;

public class Generate implements Runnable {

    boolean hasRunOnce = false;
    BufferedImage image;

    @Override
    public void run() {
        while(true) {
            generate();
            getColourBuffer();

            switch (currFractal){
                case MANDELBROT, BUDDHABROT:
                    createImageSingleChannel();
                case FALSE_BUDDHABROT:
                    createImageRGBChannels();
            }

            hasRunOnce = true;
        }
    }

    Constants.FRACTAL currFractal;
    int width, height;
    short[] buddhaData;
    short[] cb;

    short[] buddhaDataR, buddhaDataG, buddhaDataB;
    short[] cbR, cbG, cbB;

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

    public Generate(int width, int height, Constants.FRACTAL currentFractal) {
        this.width = width;
        this.height = height;
        this.currFractal = currentFractal;
    }


    void init() {
        switch (currFractal) {
            case MANDELBROT, BUDDHABROT -> buddhaData = new short[width * height * 3];
            case FALSE_BUDDHABROT -> {
                buddhaDataR = new short[width * height];
                buddhaDataG = new short[width * height];
                buddhaDataB = new short[width * height];
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

                byte colourR = 0;
                byte colourG = 0;
                byte colourB = 0;

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

    byte colourKernel(int i, float threshold) {
        return (byte) ((Math.sin(i / threshold) + 1) / 2d * Constants.BYTEMAX);
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

    short[] generateFalseBuddhabrot(int it, short[] data) {
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

    short[] colourBuffer(final short[] bufferIn) {
        short[] buffer = new short[bufferIn.length];
        double maxVal = 0;

        for (int i = 0; i < bufferIn.length; i++) {
            maxVal = Math.max(maxVal, bufferIn[i]);
        }

        double gamma = 2;

        for (int i = 0; i < bufferIn.length; i++) {
            //pixelData[i] = (int) ((pixelData[i] / maxVal) * BYTEMAX);
            buffer[i] = (byte) (Math.pow((bufferIn[i] / maxVal), 1 / gamma) * Constants.BYTEMAX);
        }

        return buffer;
    }



    void createImageRGBChannels() {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, ((cbR[x + y * width] & 0x0ff) << 16) |
                                        ((cbG[x + y * width] & 0x0ff) << 8) |
                                        ((cbB[x + y * width] & 0x0ff)));
            }
        }
    }

    void createImageSingleChannel(){
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, ((cb[x + y * width * 3] & 0x0ff) << 16) |
                                        ((cb[x + y * width * 3 + 1] & 0x0ff) << 8) |
                                        ((cb[x + y * width * 3 + 2] & 0x0ff)));
            }
        }
    }



    void getColourBuffer() {
        switch (currFractal) {
            case MANDELBROT, BUDDHABROT -> cb = colourBuffer(buddhaData);
            case FALSE_BUDDHABROT -> {
                cbR = colourBuffer(buddhaDataR);
                cbG = colourBuffer(buddhaDataG);
                cbB = colourBuffer(buddhaDataB);
            }
            default -> {
            }
        }
    }

    BufferedImage getImg(){
        return image;
    }

    public boolean isImageCreated() {
        return image != null;
    }
}
