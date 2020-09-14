package 高并发;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Test1 {
    public static void main(String[] args) {
        Map map = new ConcurrentHashMap();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            map.put("a", "a");
//            list.add("apqemnfa"+i);
        }
        System.out.println(map.size());
        System.out.println(System.currentTimeMillis()-start);
    }
}
