package Fractals;

public class ThreadData {
    int offset;
    int size;

    short[] dataBufferR, dataBufferG, dataBufferB;


    public ThreadData(int offset, int size) {
        this.offset = offset;
        this.size = size;

        this.dataBufferR = new short[size];
        this.dataBufferG = new short[size];
        this.dataBufferB = new short[size];
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


    public void addDataIndexR(int i, short data) {
        this.dataBufferR[i] += data;
    }

    public void addDataIndexG(int i, short data) {
        this.dataBufferG[i] += data;
    }

    public void addDataIndexB(int i, short data) {
        this.dataBufferB[i] += data;
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

    public int getOffset() {
        return offset;
    }

    public int getSize() {
        return size;
    }
}
