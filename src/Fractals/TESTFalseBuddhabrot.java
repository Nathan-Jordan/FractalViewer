package Fractals;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TESTFalseBuddhabrot extends Buddhabrot {

    int iterationsG, iterationsB;


    public TESTFalseBuddhabrot(int w, int h, int iterationsR, int iterationsG, int iterationsB, int samples, boolean looped) {
        super(w, h, iterationsR, samples, looped);
        this.iterationsG = iterationsG;
        this.iterationsB = iterationsB;
    }

    @Override
    public void generate() {

        cbR = generateThreads();
        iterations = iterationsG;
        cbG = generateThreads();
        iterations = iterationsB;
        cbB = generateThreads();

        cbR = colourBuffer(cbR);
        cbG = colourBuffer(cbG);
        cbB = colourBuffer(cbB);

        createImage();
    }

    short[] generateThreads() {
        short[] dataBuffer = new short[w * h];

        int part = samples / coreCount;

        for (int ix = 0; ix < coreCount - 1; ix++) {
            Future<ThreadData> data = service.submit(new Thread(part * ix, part, w * h));
            futures.add(data);
        }

        //Final thread
        Future<ThreadData> data = service.submit(new Thread(part * (coreCount - 1), samples - (part * coreCount) + part, w * h));
        futures.add(data);


        //Create colours "colourBuffer" function (threads?)
        for (Future<ThreadData> future : futures) {
            try {
                ThreadData tData = future.get();
                for (int ix = 0; ix < tData.getDataBuffer().length; ix++) {
                    dataBuffer[ix] += tData.getDataBuffer()[ix];
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        futures.clear();

        return dataBuffer;
    }


    class Thread extends Buddhabrot.Thread {

        public Thread(int offset, int size, int arrSize) {
            super(offset, size, arrSize, true);
            init();
        }

        @Override
        void addToDataBuffer(int pixelOffset, int sample) {
            addDataIndex(pixelOffset, (short) 1);
        }
    }
}
