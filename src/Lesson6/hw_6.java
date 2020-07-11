package Lesson6;


public class hw_6 {
    public static void main(String[] args)  {
        int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        int[] arr2 = {1, 2, 3, 5, 6, 7, 8, 9, 0};
        //System.out.println(arr1);

        //print1arr(DoSmth1(arr1));
        //print1arr(DoSmth1(arr2));


    }
    public static int[]  DoSmth1(int[] arr_in) throws Exception {

        int ind=-1;

        for (int i=0;i<arr_in.length;i++) {
            if (arr_in[i]==4) {
                ind = i;
            }
        }
        if (ind>-1) {
            int[] arr_out = new int[arr_in.length - ind];
            System.arraycopy(arr_in, ind, arr_out, 0, arr_in.length - ind);
            return arr_out;
        } else
        {
            throw new Exception("Ошибка в данных");
        }
    }

    public static boolean  DoSmth2(int[] arr_in) throws Exception {

        int ind=-1;

        for (int i=0;i<arr_in.length;i++) {
            if ((arr_in[i]==4)|| (arr_in[i]==1)){
                ind = i;
            }
        }
        return (ind>-1)? true:false;

    }

    public static void print1arr(int[] arr)
    {
        for (int i=0;i<arr.length;i++) {
            System.out.println(arr[i]);
        }
        System.out.println("==");
    }

}
