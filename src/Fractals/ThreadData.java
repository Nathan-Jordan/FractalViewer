package Fractals;

public class ThreadData {
    int offset;
    int size;
    int arrSize;

    short[] dataBufferR, dataBufferG, dataBufferB;
    short[] dataBuffer;


    public ThreadData(int offset, int size) {
        this(offset, size, size);
    }

    public ThreadData(int offset, int size, int arrSize) {
        this.offset = offset;
        this.size = size;
        this.arrSize = arrSize;
    }

    public void init(){
        this.dataBufferR = new short[arrSize];
        this.dataBufferG = new short[arrSize];
        this.dataBufferB = new short[arrSize];
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
    public void addDataIndex(int i, short data) {
        this.dataBuffer[i] += data;
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
    public short[] getDataBuffer() {
        return dataBuffer;
    }

    public int getOffset() {
        return offset;
    }

    public int getSize() {
        return size;
    }
}
