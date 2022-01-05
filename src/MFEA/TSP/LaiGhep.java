package MFEA.TSP;

import java.util.Random;
import static java.lang.Math.pow;

import static MFEA.TSP.main.*;
import static MFEA.TSP.DanhGiaQuanThe.*;
import static MFEA.TSP.KhoiTao.*;

public class LaiGhep {
    public static int LuaChonChaMe(){
        int temp;
        do{
            //Lựa chọn cá thể trở thành cha mẹ trong thế hệ theHe
            temp = genRandom(maxCaThe*soTacVu)-1; //random cá thể
            //Kiểm tra xem cá thể vừa chọn có hợp lệ không (Do kích thước quần thể giảm tuyến tính qua các thế hệ)
            // tác vụ = temp/maxCaThe
            // Id cá thể trong tác vụ = temp%maxCaThe
        } while (temp%maxCaThe >= Ni[temp/maxCaThe]);
        return temp;
    }
    public static NST mutation(NST parent){
//    Hàm đột biến của anh đạo
        NST ind = parent;

        for(int i=1; i<=globalGen; i++){
            if(genRandomDouble() < 1.0/globalGen){
                double u = genRandomDouble();
                if(u<=0.5){
                    double del=Math.pow((2*u), 1.0/(1+5)) - 1;
                    ind.Gen[i-1] =ind.Gen[i-1]*(del+1);
                }else{
                    double del= 1 - Math.pow(2*(1-u), 1.0/(1+5));
                    ind.Gen[i-1] = ind.Gen[i-1] +del*(1-ind.Gen[i-1]);
                }
            }
            if(ind.Gen[i-1]>1) ind.Gen[i-1] =parent.Gen[i-1] + genRandomDouble()*(1-parent.Gen[i-1]);
            else if(ind.Gen[i-1]<0) ind.Gen[i-1] = parent.Gen[i-1]*genRandomDouble();
        }
        return ind;
    }

    public static void variable_swap(NST p1, NST p2){
    //Hàm swap của anh đạo
        for(int i=1; i<=globalGen; i++){
            if(genRandomDouble() > 0.5){
                double temp1 = p1.Gen[i-1];
                double temp2 = p2.Gen[i-1];
                p1.Gen[i-1] = temp2;
                p2.Gen[i-1] = temp1;
            }
        }
    }

    public static NST[] laiGhep_cungSkillfactor(NST cha,NST me){
//        if(Eval[cha.skill_factor]<maxEval){
//            Eval[cha.skill_factor]++; //Tăng số lần đánh giá của tác vụ này lên 1
//            totalEval++;
//        }
        //Lai ghép 2 cha mẹ có cùng skillfactor
        NST[] child = new NST[2];
        for(int i=0;i<2;i++){
            child[i] = new NST();
            child[i].khoiTaoDoiTuong();
            child[i].skill_factor = cha.skill_factor;
        }
        //Ở đây ta sử dụng phương pháp Lai ghép chéo hóa nhị phân - SBX
        //Đầu tiên random 1 số thực u trong khoảng [0,1]
        //Tính hệ số Beta
            //Chọn 1 Nc là hệ số phân bố thường nằm trong khoảng 2 đến 10, Nc càng cao thì cá thể con sinh ra càng gần cha mẹ
        int Nc=2;
        double cf[] = new double[globalGen];
        for(int i=0; i<globalGen; i++){
            cf[i] = 1;
            double u = genRandomDouble();
            if(u<=0.5){
                cf[i]= Math.pow((2*u), 1.0/(Nc +1));
            }else{
                cf[i]= Math.pow(2*(1-u), -1.0/(Nc +1));
            }
        }
        for(int i=0;i<globalGen; i++){
            double v = 0.5*((1+cf[i])*cha.Gen[i] + (1-cf[i])*me.Gen[i]);
            if(v>1) v=1;
            else if(v<0) v= 0;
            child[0].Gen[i]= v;

            double v2 = 0.5*((1-cf[i])*cha.Gen[i] + (1+cf[i])*me.Gen[i]);
            if(v2>1) v2= 1;
            else if(v2<0) v2= 0;
            child[1].Gen[i]= v2;
        }
        variable_swap(child[0],child[1]);
        //Đột biến 2 con
        child[0] = mutation(child[0]);
        child[1] = mutation(child[1]);
        return child;
    }
    public static NST[] laiGhep_khacSkillfactor(NST cha, NST me, double rmp){
        NST[] child = new NST[2];
        for(int i=0;i<2;i++){
            child[i] = new NST();
            child[i].khoiTaoDoiTuong();
        }
        if(genRandomDouble()<rmp){
            //Ở đây ta sử dụng phương pháp Lai ghép chéo hóa nhị phân - SBX
            //Đầu tiên random 1 số thực u trong khoảng [0,1]
            //Tính hệ số Beta
            //Chọn 1 Nc là hệ số phân bố thường nằm trong khoảng 2 đến 10, Nc càng cao thì cá thể con sinh ra càng gần cha mẹ
            int Nc=2;
            double cf[] = new double[globalGen];
            for(int i=0; i<globalGen; i++){
                cf[i] = 1;
                double u = genRandomDouble();
                if(u<=0.5){
                    cf[i]= Math.pow((2*u), 1.0/(Nc +1));
                }else{
                    cf[i]= Math.pow(2*(1-u), -1.0/(Nc +1));
                }
            }
            for(int i=0;i<globalGen; i++){
                double v = 0.5*((1+cf[i])*cha.Gen[i] + (1-cf[i])*me.Gen[i]);
                if(v>1) v=1;
                else if(v<0) v= 0;
                child[0].Gen[i]= v;

                double v2 = 0.5*((1-cf[i])*cha.Gen[i] + (1+cf[i])*me.Gen[i]);
                if(v2>1) v2= 1;
                else if(v2<0) v2= 0;
                child[1].Gen[i]= v2;
            }
            variable_swap(child[0],child[1]);
            //Đột biến 2 con và đặt skill factor của 2 con ngẫu nhiên bằng của cha hoặc mẹ
            child[0] = mutation(child[0]);
            child[1] = mutation(child[1]);
            for(int i=0;i<2;i++){
                if(genRandom(2)==1){
                    child[i].skill_factor = cha.skill_factor;
                }else{
                    child[i].skill_factor = me.skill_factor;
                }
            }
        }else {
            //Chọn 2 cá thể có cùng skillfactor với cha mẹ (skill factor của cha mẹ khác nhau nên 2 cá thể cũng có skill factor khác nhau)
            int me1 = (genRandom(Ni[cha.skill_factor])-1)+cha.skill_factor*maxCaThe; //Cá thể me1 cùng skill factor với cha sẽ sử dụng để lai  cùng với cha
            int cha1 = (genRandom(Ni[cha.skill_factor])-1)+me.skill_factor*maxCaThe; //Cá thể cha1 cùng skill factor với cha sẽ sử dụng để lai  cùng với me
            NST[] child1 = laiGhep_cungSkillfactor(dsNST[1-(theHe%2)][me1],cha);
            NST[] child2 = laiGhep_cungSkillfactor(dsNST[1-(theHe%2)][cha1],me);
            child[0] = child1[0];
            child[1] = child2[0];
        }
        return child;
    }
//    public static double randomGauss(double µ, double σ){
//        //Hàm lấy mẫu theo phân phối chuẩn
//        Random output = new Random();
//        double number = output.nextGaussian()*σ+µ;
//        return number;
//    }
    public static double randomGauss(double µ, double σ){
        //Hàm random Gauss của anh thắng
        Random output = new Random();
        return µ + σ*Math.sqrt(-2.0 * Math.log(output.nextDouble())) * Math.sin(2.0 * Math.PI * output.nextDouble());
    }
    public static double tinhDelta(NST pA, NST pB, NST oA, NST oB){
        //Hàm tính giá trị Tỷ lệ phần trăm cải thiện tốt nhất
        //pA, pB là 2 cá thể cha mẹ
        //oA, oB là 2 cá thể con
        double Delta =0;
        if(oA.skill_factor==pA.skill_factor){
            double temp = (pA.f_cost-oA.f_cost)/pA.f_cost;
            if(Delta<temp) Delta = temp;
        }else {
            double temp = (pB.f_cost-oA.f_cost)/pB.f_cost;
            if(Delta<temp) Delta = temp;
        }
        if(oB.skill_factor==pA.skill_factor){
            double temp = (pA.f_cost-oB.f_cost)/pA.f_cost;
            if(Delta<temp) Delta = temp;
        }else {
            double temp = (pB.f_cost-oB.f_cost)/pB.f_cost;
            if(Delta<temp) Delta = temp;
        }
        return Delta;
    }
    public static void updateM(double[][] S,double[][] δ,int[] dem1){
        //Cập nhật bộ nhớ lịch sử thành công
        for (int id=0;id<45;id++){
            for(int i=0;i<2;i++){
                for (int j=i+1;j<2;j++){
                    if(dem1[id]>0){
                        double tuSo=0,mauSo=0;
                        for (int i1=0;i1<dem1[id];i1++){
                            tuSo+=δ[id][i1]*Math.pow(S[id][i1],2);
                        }
                        for (int i1=0;i1<dem1[id];i1++){
                            mauSo+=δ[id][i1]*S[id][i1];
                        }
                        M[id][p] = tuSo/mauSo;
                        p = p+1;
                        if(p==M[id].length) p=0;
                    }
                }
            }
        }
    }
    public static int tinhIdM(int sf1, int sf2){
        //Hàm nhận đầu vào là skill factor của 2 cá thể (sf1 != sf2)
        //Đầu ra là id của bộ nhớ M tương ứng
        int p1,p2,id;
        if(sf1<sf2){
            p1 = sf1;
            p2 = sf2;
        }else {
            p1 = sf2;
            p2 = sf1;
        }
        if(p1==1){
            id = p2 - p1 -1;
        }else {
            id=p2-p1;
            id = id + tinhIdM(p1-1,10);
        }
        return id;
    }
    public static void LPSR(){
        //Chiến lược giảm quy mô dân số tuyến tính
        //Điểm khác biệt giữa LSA MFEA và SA MFEA
        for(int i=0;i<soTacVu;i++){
            //Duyệt qua tất cả các tác vụ
            Ni[i] = (Nmin-maxCaThe)/(maxEval) * Eval[i] + maxCaThe;
        }
    }
    public static int[] theHeTiepTheo(double[][] S, double[][] δ){
        NST[] O = new NST[maxCaThe*soTacVu]; //Mảng lưu trữ các con được sinh ra
        int dem=0; //Đếm số lượng con đã sinh ra
        int[] demS = new int[45]; //Đếm số lượng phần tử được thêm vào S và δ
        for(int i=0;i<45;i++){
            demS[i]=0;
        }
        while(dem<soTacVu*maxCaThe){
            int cha = LuaChonChaMe();
            int me = LuaChonChaMe();
            while(me==cha) me = LuaChonChaMe();
            if(dsNST[1-(theHe%2)][cha].skill_factor == dsNST[1-(theHe%2)][me].skill_factor){
                NST[] child = laiGhep_cungSkillfactor(dsNST[1-(theHe%2)][cha],dsNST[1-(theHe%2)][me]);
                danhGiaCaThe(child);
                for (int i=0;i<child.length;i++){
                    O[dem] = child[i];
                    if(Eval[child[i].skill_factor]<maxEval){
                        totalEval++;
                        Eval[child[i].skill_factor]++; //Tăng số lần đánh giá của tác vụ này lên 1
                    }
                    dem++;
                }
            }else {
                int id = tinhIdM(dsNST[1-(theHe%2)][cha].skill_factor+1,dsNST[1-(theHe%2)][me].skill_factor+1);
                int idµ = genRandom(5)-1;
                double µ = M[id][idµ];
                double rmp = randomGauss(µ,σ);
                NST[] child = laiGhep_khacSkillfactor(dsNST[1-(theHe%2)][cha],dsNST[1-(theHe%2)][me],rmp);
                danhGiaCaThe(child);
                for (int i=0;i<child.length;i++){
                    O[dem] = child[i];
                    if(Eval[child[i].skill_factor]<maxEval){
                        totalEval++;
                        Eval[child[i].skill_factor]++; //Tăng số lần đánh giá của tác vụ này lên 1
                    }
                    dem++;
                }
                Delta = tinhDelta(dsNST[1-(theHe%2)][cha],dsNST[1-(theHe%2)][me],child[0],child[1]);
                if(Delta>0){
                    S[id][demS[id]] = rmp;
                    δ[id][demS[id]] = Delta;
                    demS[id]++;
                }
            }
        }
        //Xác nhập O và P
        NST[] total = new NST[maxCaThe*soTacVu*2];
        for(int i=0;i<maxCaThe*soTacVu;i++){
            total[i] = dsNST[1-(theHe%2)][i];
        }
        for(int i=maxCaThe*soTacVu;i<2*maxCaThe*soTacVu;i++){
            total[i] = O[i-maxCaThe*soTacVu];
        }
        //Loại bỏ các cá thể tồi ở mỗi tác vụ, giữ sô lượng cá thể mỗi tác vụ ở mức maxCaThe
        double[] min = new double[soTacVu]; //Biến lưu giá trị
        double[] oldmin = new double[soTacVu]; //Biến lưu giá trị max trước đó
        for (int i=0;i<oldmin.length;i++){
            //Đặt giá trị oldmin =0
            oldmin[i]=0;
        }
        int[] id = new int[soTacVu];
        for (int k=0;k<maxCaThe;k++){
            for (int i=0;i<min.length;i++){
                //Đặt giá trị min ban đầu là lớn nhất
                min[i]=1.7976931348623157 * pow(10,308);
            }
            for(int i=0;i<2*maxCaThe*soTacVu;i++){
                //Tìm cá thể tốt nhất ở mỗi tác vụ
                if(min[total[i].skill_factor] > total[i].f_cost && total[i].f_cost > oldmin[total[i].skill_factor]){
                    min[total[i].skill_factor] = total[i].f_cost;
                    id[total[i].skill_factor] = i;
                }
            }
            for (int i=0;i<id.length;i++){
                oldmin[i] = min[i];
                dsNST[theHe%2][i*maxCaThe+k] = total[id[i]];
            }
        }
        return demS;
    }
}
