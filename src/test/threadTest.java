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