package Lesson7;

public class TestClass1 {
    @BeforeSuite
    public static void before(){
        System.out.println("BeforeSuite");
    }

    @Test (priority = 1)
    public static void addTest1(){
        System.out.println("test 1");
    }

    @Test (priority = 10)
    public static void addTest2(){

        System.out.println("test 2");
    }

    @Test (priority = 3)
    public static void addTest3(){

        System.out.println("test 3");
    }

    @Test (priority = 4)
    public static void addTest4(){

        System.out.println("test 4");
    }
    @Test (priority = 4)
    public static void addTest5(){

        System.out.println("test 5");
    }
    @Test (priority = 6)
    public static void addTest6(){

        System.out.println("test 6");
    }
    @AfterSuite
    public static void after(){
        System.out.println("AfterSuite");
    }

}
