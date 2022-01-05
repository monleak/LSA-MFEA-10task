package MFEA.TSP;

import Function.*;

import static MFEA.TSP.main.*;

public class DanhGiaQuanThe {
    public static double TinhGiaTri(NST temp, int task){
        double fcost = 0;
        if(task==0){
            fcost = SPHERE.Sphere(maHoa(temp.Gen,task+1)); //task1
        }
        if(task==1){
            fcost = SPHERE.Sphere2(maHoa(temp.Gen,task+1)); //task2
        }
        if(task==2){
            fcost = SPHERE.Sphere3(maHoa(temp.Gen,task+1)); //task3
        }
        if(task==3){
            fcost = Weierstrass.Weierstrass1(maHoa(temp.Gen,task+1)); //task4
        }
        if(task==4){
            fcost = ROSENBROCK.Rosenbrock(maHoa(temp.Gen,task+1)); //task5
        }
        if(task==5){
            fcost = ACKLEY.Ackley(maHoa(temp.Gen,task+1)); //task6
        }
        if(task==6){
            fcost = Weierstrass.Weierstrass2(maHoa(temp.Gen,task+1)); //task7
        }
        if(task==7){
            fcost = SCHWEFEL.Schwefel(maHoa(temp.Gen,task+1)); //task8
        }
        if(task==8){
            fcost = GRIEWANK.Griewank(maHoa(temp.Gen,task+1)); //task9
        }
        if(task==9){
            fcost = RASTRIGIN.Rastrigin(maHoa(temp.Gen,task+1)); //task10
        }
        return fcost;
    }
    public static double[] maHoa(double[] Gen,int Task){
        //Mã hóa các cá thể từ không gian chung ra không gian riêng để tính toán
        double[] temp = new double[Gen.length];
        if(Task==1){
            for (int i=0;i<Gen.length;i++){
                temp[i] = (Gen[i]-0.5)*200;
            }
        }
        if(Task==2){
            for (int i=0;i<Gen.length;i++){
                temp[i] = (Gen[i]-0.5)*200;
            }
        }
        if(Task==3){
            for (int i=0;i<Gen.length;i++){
                temp[i] = (Gen[i]-0.5)*200;
            }
        }
        if(Task==4){
            //Riêng task 4 chỉ có 25 phần tử
            for (int i=0;i<Gen.length;i++){
                temp[i] = Gen[i]-0.5;
            }
        }
        if(Task==5){
            for (int i=0;i<Gen.length;i++){
                temp[i] = (Gen[i]-0.5)*100;
            }
        }
        if(Task==6){
            for (int i=0;i<Gen.length;i++){
                temp[i] = (Gen[i]-0.5)*100;
            }
        }
        if(Task==7){
            for (int i=0;i<Gen.length;i++){
                temp[i] = Gen[i]-0.5;
            }
        }
        if(Task==8){
            for (int i=0;i<Gen.length;i++){
                temp[i] = (Gen[i]-0.5)*1000;
            }
        }
        if(Task==9){
            for (int i=0;i<Gen.length;i++){
                temp[i] = (Gen[i]-0.5)*200;
            }
        }
        if(Task==10){
            for (int i=0;i<Gen.length;i++){
                temp[i] = (Gen[i]-0.5)*100;
            }
        }
        return temp;
    }
    public static void danhGiaCaThe(NST[] dsNST){
        for (int i=0;i<dsNST.length;i++){
            dsNST[i].rank =0;
        }
        for (int i=0;i<dsNST.length;i++){
//            if(Eval[dsNST[i].skill_factor]<maxEval){
//                Eval[dsNST[i].skill_factor]++; //Tăng số lần đánh giá của tác vụ này lên 1
//                totalEval++;
//            }
            //Duyệt qua tất cả cá thể và tính kết quả của từng tác vụ
//            if(dsNST[i].skill_factor==0){
//                dsNST[i].f_cost = SPHERE.Sphere(maHoa(dsNST[i].Gen,dsNST[i].skill_factor+1)); //task1
//            }
//            if(dsNST[i].skill_factor==1){
//                dsNST[i].f_cost = SPHERE.Sphere2(maHoa(dsNST[i].Gen,dsNST[i].skill_factor+1)); //task2
//            }
//            if(dsNST[i].skill_factor==2){
//                dsNST[i].f_cost = SPHERE.Sphere3(maHoa(dsNST[i].Gen,dsNST[i].skill_factor+1)); //task3
//            }
//            if(dsNST[i].skill_factor==3){
//                dsNST[i].f_cost = Weierstrass.Weierstrass1(maHoa(dsNST[i].Gen,dsNST[i].skill_factor+1)); //task4
//            }
//            if(dsNST[i].skill_factor==4){
//                dsNST[i].f_cost = ROSENBROCK.Rosenbrock(maHoa(dsNST[i].Gen,dsNST[i].skill_factor+1)); //task5
//            }
//            if(dsNST[i].skill_factor==5){
//                dsNST[i].f_cost = ACKLEY.Ackley(maHoa(dsNST[i].Gen,dsNST[i].skill_factor+1)); //task6
//            }
//            if(dsNST[i].skill_factor==6){
//                dsNST[i].f_cost = Weierstrass.Weierstrass2(maHoa(dsNST[i].Gen,dsNST[i].skill_factor+1)); //task7
//            }
//            if(dsNST[i].skill_factor==7){
//                dsNST[i].f_cost = SCHWEFEL.Schwefel(maHoa(dsNST[i].Gen,dsNST[i].skill_factor+1)); //task8
//            }
//            if(dsNST[i].skill_factor==8){
//                dsNST[i].f_cost = GRIEWANK.Griewank(maHoa(dsNST[i].Gen,dsNST[i].skill_factor+1)); //task9
//            }
//            if(dsNST[i].skill_factor==9){
//                dsNST[i].f_cost = RASTRIGIN.Rastrigin(maHoa(dsNST[i].Gen,dsNST[i].skill_factor+1)); //task10
//            }
            dsNST[i].f_cost = TinhGiaTri(dsNST[i],dsNST[i].skill_factor);
        }

        //Xếp rank cho các cá thể
        double[] good = new double[soTacVu]; //Lưu các giá trị tốt thứ rank của các tác vụ
        int[] idGood = new int[soTacVu]; //Lưu cá thể tốt thứ rank của các tác vụ
        for (int rank=maxCaThe;rank>=1;rank--){
            for (int i=0;i<good.length;i++){
                good[i]=0;
            }
            //Các cá thể có fcost càng nhỏ thì cá thể đó càng tốt (Nghĩa là rank của cá thể đó sẽ cao hơn)
            for (int i=0;i<dsNST.length;i++){
                if(good[dsNST[i].skill_factor] < dsNST[i].f_cost && dsNST[i].rank == 0){
                    good[dsNST[i].skill_factor] = dsNST[i].f_cost;
                    idGood[dsNST[i].skill_factor] = i;
                }
            }
            for (int k=0;k<soTacVu;k++){
                dsNST[idGood[k]].rank = rank;
            }
        }
        //Tính scalarfitness
//        for (int i=0;i<dsNST.length;i++){
//            dsNST[i].scalar_fitness = 1/dsNST[i].rank;
//        }
    }
}
