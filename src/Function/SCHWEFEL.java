package Function;

import static java.lang.Math.*;

public class SCHWEFEL {
    public static double Schwefel(double[] X){
        int dim = X.length;
        double sum1=0;
        for(int i=0;i<dim;i++){
            sum1 = sum1 + (X[i]-420.9687)*sin(sqrt(abs(X[i]-420.9687)));
        }
        double sum2;
        sum2 = 418.9829*dim-sum1;
        return sum2;
    }
}
