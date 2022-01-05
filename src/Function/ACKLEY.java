package Function;

import static MFEA.TSP.main.Π;
import static java.lang.Math.*;

public class ACKLEY {
    public static double Ackley(double[] X){
        int dim = X.length;
        int a = 20;
        double b =0.2;
        double c=2*Π;
        double sum1=0, sum2=0;
        for(int i=0;i<dim;i++){
            sum1 = sum1+ (X[i]-40)*(X[i]-40);
            sum2 = sum2+ cos(c*(X[i]-40));
        }
        double sum3;
        sum3 = -a*exp(-b*sqrt(sum1/dim))-exp(sum2/dim)+a+exp(1);
        return sum3;
    }
}
