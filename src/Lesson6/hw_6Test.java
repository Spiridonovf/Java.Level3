package Lesson6;

import org.junit.Assert;

import static Lesson6.hw_6.DoSmth1;
import static Lesson6.hw_6.DoSmth2;
import static Lesson6.hw_6.print1arr;

class hw_6Test {
    int[] arr1;
    int[] arr2;
    int[] arr3;
    int[] arr4;
    int[] arr5;
    int[] arr6;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        arr1 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        arr2 =  new int[]{1, 2, 3, 5, 6, 7, 8, 9, 0};
        arr3 =new int[] {1, 2, 4, 4, 2, 3, 4, 1, 7};
        arr4 =new int[] {1, 2, 4};
        arr5 =new int[] {2, 2, 3};
        arr6 =new int[] {1, 2, 8};
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void doSmth1() throws Exception {
        print1arr(DoSmth1(arr1));
    }
    @org.junit.jupiter.api.Test
    void doSmth2() throws Exception {
        print1arr(DoSmth1(arr2));
    }

    @org.junit.jupiter.api.Test
    void doSmth3() throws Exception {
        print1arr(DoSmth1(arr3));
    }

    @org.junit.jupiter.api.Test
    void doSmth4() throws Exception {
        Assert.assertEquals(true,DoSmth2(arr4));
    }
    @org.junit.jupiter.api.Test
    void doSmth5() throws Exception {
        Assert.assertEquals(true,DoSmth2(arr5));
    }
    @org.junit.jupiter.api.Test
    void doSmth6() throws Exception {
        Assert.assertEquals(true,DoSmth2(arr6));
    }
}