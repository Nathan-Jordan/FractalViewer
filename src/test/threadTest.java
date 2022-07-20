package test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class threadTest {
    public static void main(String[] args) {
        //https://www.youtube.com/watch?v=NEZ2ASoP_nY&ab_channel=DefogTech
        ExecutorService service = Executors.newFixedThreadPool(2);

        List<Future> futures = new ArrayList<>();
        for (int ix = 1; ix < 4; ix++) {
            futures.add(service.submit(new Foo(ix)));
        }

        int[] finalArr = new int[6];

        for (Future<int[]> future : futures) {
            try {
                int[] arr = future.get();
                System.out.println(Arrays.toString(arr));
                System.arraycopy(arr, 0, finalArr, arr[0]-1, arr.length);
            } catch (Exception e) {

            }
        }

        System.out.println(Arrays.toString(finalArr));
    }
}

class Foo implements Callable<int[]> {
    int length;

    Foo(int length) {
        this.length = length;
    }

    @Override
    public int[] call() throws Exception {
        int[] intArr = new int[length];
        Arrays.fill(intArr, length);
        return intArr;
    }
}




class FooRunningMain {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);

        Future<int[]> f1 = service.submit(new FooRunning(3));
        Future<int[]> f2 = service.submit(new FooRunning(6));
        Thread.sleep(1000);
        service.shutdownNow();
        f1.cancel(true);


        service = Executors.newFixedThreadPool(2);
        Future<int[]> f3 = service.submit(new FooRunning(10));
        Future<int[]> f4 = service.submit(new FooRunning(10));
    }
}

class FooRunning implements Callable<int[]> {
    int length;

    FooRunning(int length) {
        this.length = length;
    }

    @Override
    public int[] call() throws Exception {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            System.out.println(i + " : " + length);
        }
        int[] intArr = new int[length];
        Arrays.fill(intArr, length);
        System.out.println(length);
        return intArr;
    }
}