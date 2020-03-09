package com.qf.my.project.regist.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.IntStream;

@SpringBootTest
class MyProjectRegistWebApplicationTests {

    static int arr[] = new int[10];


    @Test
    public void test1() {

        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        //转换成List，方便使用lambda
        List<Integer> list = Arrays.asList(array);
        //用于存放*10后的元素的集合
        List<Integer> myList = new ArrayList<>(list.size());
        list.forEach(item -> {
            myList.add(item * 10);
        });
        //排序
        Collections.sort(myList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        //输出前5个
        for (int i = 0; i < 5; i++) {
            System.out.println(myList.get(i));
        }
    }


    @Test
    void contextLoads() {
        System.out.println(IntStream.range(5, 9).map(a -> a * 10).boxed().filter(a -> a.compareTo(6) > 0).reduce(0, Integer::sum));

    }

}
