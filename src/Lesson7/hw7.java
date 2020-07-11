package Lesson7;

public class hw7 {
    public static void main(String[] args) {


        Class cs = TestClass1.class;
        try {
            TestRunner.start(cs);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


