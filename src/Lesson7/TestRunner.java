package Lesson7;
import java.lang.reflect.Method;
import java.util.*;



public class TestRunner   {
    private int countB = 0;
    private int countA = 0;

    public static void start(Class obj) throws Exception {
        TestRunner tc = new TestRunner();

        Method[] methods = obj.getDeclaredMethods();
        ArrayList<Method> arr = new ArrayList<>();

        for (Method m: methods) {
            if(m.isAnnotationPresent(BeforeSuite.class)){
                tc.countB++;
            }
            if(m.isAnnotationPresent(AfterSuite.class)){
                tc.countA++;
            }
        }
        if ((tc.countA | tc.countB) > 1) throw new RuntimeException();

        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                m.invoke(null);
            }
        }

        for (Method m : methods) {
            if (m.isAnnotationPresent(Test.class)) {
                arr.add(m);
            }
        }
        arr.sort((c1, c2) -> {return c1.getAnnotation(Test.class).priority() - c2.getAnnotation(Test.class).priority(); });
        for (int i = arr.size() - 1; i >= 0; i--) {
            System.out.println("priority: " + arr.get(i).getAnnotation(Test.class).priority()+" method: "+arr.get(i).getName() );
            arr.get(i).invoke(null);
        }

        for (Method m : methods) {
            if (m.isAnnotationPresent(AfterSuite.class)) {
                m.invoke(null);
            }
        }
    }
}

