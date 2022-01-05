package Function;

import static MFEA.TSP.main.*;
import static java.lang.Math.cos;

public class RASTRIGIN {
    public static double Rastrigin(double[] X){
        int dim = X.length;
        double sum = 10*dim;
        for(int i=0;i<dim/2;i++){
            sum = sum + ((X[i]+40)*(X[i]+40)-10*cos(2*Π*(X[i]+40)));
        }
        for(int i=dim/2;i<dim;i++){
            sum = sum + ((X[i]-40)*(X[i]-40)-10*cos(2*Π*(X[i]-40)));
        }
        return sum;
    }
}
