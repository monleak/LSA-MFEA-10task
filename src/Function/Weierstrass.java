package Function;

import static java.lang.Math.*;
import static MFEA.TSP.main.*;

public class Weierstrass {
    public static double Weierstrass2(double[] X){
        int dim = X.length;
        double a=0.5;
        double b=3;
        int kmax=20;
        double sum=0;
        for(int i=0;i<dim;i++){
            for(int k=0;k<=kmax;k++){
                sum = sum+ pow(a,k)*cos(2*Π*pow(b,k)*(X[i]+0.4+0.5));
            }
        }
        for(int k=0;k<=kmax;k++){
            sum = sum - dim*pow(a,k)*cos(2*Π*pow(b,k)*0.5);
        }
        return sum;
    }
    public static double Weierstrass1(double[] X){
        int dim = 25;
        double a=0.5;
        double b=3;
        int kmax=20;
        double sum=0;
        for(int i=0;i<dim;i++){
            for(int k=0;k<=kmax;k++){
                sum = sum+ pow(a,k)*cos(2*Π*pow(b,k)*(X[i]+0.4+0.5));
            }
        }
        for(int k=0;k<=kmax;k++){
            sum = sum - dim*pow(a,k)*cos(2*Π*pow(b,k)*0.5);
        }
        return sum;
    }
}
