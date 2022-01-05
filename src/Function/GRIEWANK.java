package Function;

import static java.lang.Math.*;

public class GRIEWANK {
    public static double Griewank(double[] X){
        int dim = X.length;
        double sum1=0;
        double sum2=1;
        for(int i=0;i<dim/2;i++){
            sum1 = sum1 + (X[i]+80)*(X[i]+80);
            sum2 = sum2 * cos((X[i]+80)/sqrt(i+1));
        }
        for(int i=dim/2;i<dim;i++){
            sum1 = sum1 + (X[i]-80)*(X[i]-80);
            sum2 = sum2 * cos((X[i]-80)/sqrt(i+1));
        }
        return 1+sum1/4000-sum2;
    }
}
