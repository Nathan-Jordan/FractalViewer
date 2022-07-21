package Fractals;

public class ThreadData {
    int xStart, yStart;
    int xEnd, yEnd;

    short[] dataBufferR, dataBufferG, dataBufferB;

    public ThreadData(int xStart, int yStart, int xEnd, int yEnd, int length) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;

        this.dataBufferR = new short[length];
        this.dataBufferG = new short[length];
        this.dataBufferB = new short[length];
    }

    public void setDataIndexR(int i, short data) {
        this.dataBufferR[i] = data;
    }

    public void setDataIndexG(int i, short data) {
        this.dataBufferG[i] = data;
    }

    public void setDataIndexB(int i, short data) {
        this.dataBufferB[i] = data;
    }


    public short[] getDataBufferR() {
        return dataBufferR;
    }

    public short[] getDataBufferG() {
        return dataBufferG;
    }

    public short[] getDataBufferB() {
        return dataBufferB;
    }
}
