package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class test {
    public static void main(String[] args) {
        long startTime = System.nanoTime();

        initSize();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println(duration);


        startTime = System.nanoTime();

        notInitSize();

        endTime = System.nanoTime();
        duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println(duration);


        startTime = System.nanoTime();

        initSizeArray();

        endTime = System.nanoTime();
        duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println(duration);
    }

    static void initSize(){
        ArrayList<Integer> a1 = new ArrayList<>(2_500_000);
        ArrayList<Integer> a2 = new ArrayList<>(2_500_000);
        ArrayList<Integer> a3 = new ArrayList<>(2_500_000);
        ArrayList<Integer> a4 = new ArrayList<>(2_500_000);

        //Populate
        for (int i = 0; i < 2_500_000; i++) {
            a1.add(i);
            a2.add(i);
            a3.add(i);
            a4.add(i);
        }

        //Combine
        ArrayList<Integer> finalLst = new ArrayList<>();
        finalLst.addAll(a1);
        finalLst.addAll(a2);
        finalLst.addAll(a3);
        finalLst.addAll(a4);

        //Iterate
        int total = 0;
        for (int i = 0; i < finalLst.size(); i++) {
            total += finalLst.get(i);
        }
    }

    static void notInitSize(){
        ArrayList<Integer> a1 = new ArrayList<>();
        ArrayList<Integer> a2 = new ArrayList<>();
        ArrayList<Integer> a3 = new ArrayList<>();
        ArrayList<Integer> a4 = new ArrayList<>();

        //Populate
        for (int i = 0; i < 2_500_000; i++) {
            a1.add(i);
            a2.add(i);
            a3.add(i);
            a4.add(i);
        }

        //Combine
        ArrayList<Integer> finalLst = new ArrayList<>();
        finalLst.addAll(a1);
        finalLst.addAll(a2);
        finalLst.addAll(a3);
        finalLst.addAll(a4);

        //Iterate
        int total = 0;
        for (int i = 0; i < finalLst.size(); i++) {
            total += finalLst.get(i);
        }
    }


    static void initSizeArray(){
        int[] a1 = new int[2_500_000];
        int[] a2 = new int[2_500_000];
        int[] a3 = new int[2_500_000];
        int[] a4 = new int[2_500_000];

        //Populate
        for (int i = 0; i < 2500000; i++) {
            a1[i] = i;
            a2[i] = i;
            a3[i] = i;
            a4[i] = i;
        }

        //Combine
        int[] finalArr = new int[10_000_000];
        System.arraycopy(a1, 0, finalArr, 0, a1.length);
        System.arraycopy(a2, 0, finalArr, 2_500_000, a2.length);
        System.arraycopy(a3, 0, finalArr, 5_000_000, a3.length);
        System.arraycopy(a4, 0, finalArr, 7_500_000, a4.length);

        //Iterate through
        int total = 0;
        for (int i = 0; i < finalArr.length; i++) {
            total += finalArr[i];
        }
    }
}

class tes {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();

        for (int i = 0; i < 10_000_000; i++) {
            a.add(i);
        }

        long startTime = System.nanoTime();

        int total = 0;
        for (int i = 0; i < 10_000_000; i++) {
            total = a.get(i);
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println("For i:");
        System.out.println(duration);




        startTime = System.nanoTime();
        Iterator<Integer> it = a.iterator();
        total = 0;
        while(it.hasNext()){
            total += it.next();
        }

        endTime = System.nanoTime();
        duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println("Iterator");
        System.out.println(duration);
    }
}

class testo {
    public static void main(String[] args) {
        short[] s1 = new short[2];
        short[] s2 = new short[2];

        s1[0] = 1;
        s1[1] = 1;
        s2[0] = 1;
        s2[1] = 1;

        System.out.println(Arrays.equals(s1,s2));
    }
}