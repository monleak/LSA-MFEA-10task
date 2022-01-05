package Function;

import static java.lang.Math.pow;

public class ROSENBROCK {
    public static double Rosenbrock(double[] X){
        int dim = X.length;
        double sum = 0;
        for(int i=0;i<dim-1;i++){
            sum = sum + (100*pow(X[i+1]-X[i]*X[i],2)+pow(X[i]-1,2));
        }
        return sum;
    }
}
