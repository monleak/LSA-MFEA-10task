package Function;

public class SPHERE {
    public static double Sphere(double[] X){
        double sum=0;
        int dim = X.length;
        for(int i=0;i< dim;i++){
            sum = sum+X[i]*X[i];
        }
        return sum;
    }
    public static double Sphere2(double[] X){
        double sum=0;
        int dim = X.length;
        for(int i=0;i< dim;i++){
            sum = sum+(X[i]-80)*(X[i]-80);
        }
        return sum;
    }
    public static double Sphere3(double[] X){
        double sum=0;
        int dim = X.length;
        for(int i=0;i< dim;i++){
            sum = sum+(X[i]+80)*(X[i]+80);
        }
        return sum;
    }
}
