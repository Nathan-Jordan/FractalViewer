package test;


import java.awt.*;

public class test {
    public static void main(String[] args) {

        int rgb = ((255&0x0ff)<<16)|((255&0x0ff)<<8)|(255&0x0ff);
        System.out.println(rgb);
    }
}
