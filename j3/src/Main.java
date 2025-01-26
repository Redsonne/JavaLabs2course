import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {


            double[][] m = getMatrix("src/matrix.txt");
            int n = m.length;


//            for (int i=0;i<n;i++){
//                for (int j=0;j<m[i].length;j++){
//                    System.out.print(m[i][j]+" ");
//
//                }
//                System.out.println();
//            }
            double res[] = sol(m);
            for (int i = 0; i < res.length; i++) {

                System.out.print("x" + (i + 1) + "=" + res[i] + "  ");


            }


        } catch (MyEx e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (InputMismatchException e) {
            System.out.println("не число");
        }
    }


    public static double[][] getMatrix(String file) throws MyEx, FileNotFoundException,InputMismatchException {
        Scanner sc = new Scanner(new File(file));
        int n = sc.nextInt();
        double matrix[][] = new double[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = new double[n + 1 - i];
            for (int j = 0; j < matrix[i].length; j++) {
                if (sc.hasNext()) {
                    matrix[i][j] = sc.nextDouble();
                } else throw new MyEx(" мало elements");
            }
        }
        if (sc.hasNext()) {
            throw new MyEx("много elements");


        }
        return matrix;
    }

    public static class MyEx extends Exception {
        MyEx(String s) {
            super(s);
        }
    }

    public static double[] sol(double[][] m)throws MyEx {
        int n = m.length - 1;

        double res[] = new double[n + 1];
        for (int i = n; i >= 0; i--) {
            int nn = m[i].length - 1;
            if(m[i][0]==0){
                throw new MyEx("деление на ноль");
            }
            m[i][nn]= (double)(m[i][nn]/m[i][0]);

            res[i] = m[i][nn];
            for (int j = 0; j < i; j++) {
                int nm = m[j].length - 1;
                m[j][nm]=  (double) ( m[j][nm] - m[i][nn] * m[j][nm - 1 - (n - i)]);

            }

        }

        return res;
    }
}