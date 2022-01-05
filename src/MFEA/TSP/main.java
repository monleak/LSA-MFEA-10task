package MFEA.TSP;

import static MFEA.TSP.KhoiTao.*;
import static MFEA.TSP.DanhGiaQuanThe.*;
import static MFEA.TSP.LaiGhep.*;
import static java.lang.Math.pow;

public class main {
    static int theHe = 0; //biến đếm thế hệ
    //id Thế hệ hiện tại = theHe % 2
    //id Thế hệ trước đó = 1 - Thế hệ hiện tại
    static int maxTheHe = 5000; //Số thế hệ tối đa
    static int maxCaThe = 100; //Số cá thể ở mỗi tác vụ (Nghĩa là tổng số cá thể của quần thể = Số tác vụ * maxCaThe)
    static int soTacVu = 10; //Số lượng tác vụ
    static int globalGen = 50; //Số lượng Gen của cá thể trong không gian chung

    static double[][] M = new double[45][5]; // Bộ nhớ lịch sử thành công , 10 tác vụ => có 10C2 = 45 bộ nhớ
    static int p=0; //Vị trí update M tiếp theo
    static double σ = 0.1; //Giá trị độ lệch chuẩn tối ưu nhất (=0.1) (Đọc tại bài LSA-MFEA của anh Thắng)
    static double Delta = 0; // Tỷ lệ phần trăm cải thiện tốt nhất

    public static double Π = 3.14159; //Giá trị xấp xỉ của số pi
    public static double e = 2.71828; //Giá trị xấp xỉ của e

    public static int Nmin = maxCaThe/2; //Số cá thể tối thiểu ở mỗi tác vụ
    public static int[] Ni = new int[soTacVu]; // số lượng cá thể ban đầu ở mỗi tác vụ
    public static int[] Eval = new int[soTacVu]; //Max số lần đánh giá ở mỗi tác vụ là 100000
    public static int totalEval = 0; //tổng số đánh giá
    static int maxEval = 100000;

    public static boolean print_theHe_0 = false; //Có hay không in ra thế hệ đầu tiên
    public static boolean print_ketQua_theHe = true; //Có hay không in kết quả từng task của từng thế hệ

    static NST[][] dsNST = new NST[2][soTacVu*maxCaThe]; //[thế hệ][tác vụ][cá thể] đưa ra cá thế ở thế hệ tương ứng, ở đây chỉ lưu lại 2 thế hệ là thế hệ hiện tại và thế hệ trước đó
    public static void main(String[] args){
        System.out.println("Chương trình đã chạy!");
        for (int i=0 ; i<45 ; i++){
            //Ở thế hệ ban đầu chưa có dữ liệu gì nên bộ nhớ được đặt mặc định là 0.5
            for(int j=0;j<5;j++){
                M[i][j] = 0.5;
            }
        }
        for(int i=0;i<2;i++){
            for(int j=0;j<soTacVu*maxCaThe;j++){
                dsNST[i][j] = new NST();
                dsNST[i][j].khoiTaoDoiTuong();
            }
        }

        for (int k=0;k<soTacVu;k++){
            //Tổng số đánh giá của các tác vụ ban đầu = 0
            Eval[k] = 0;
            //Số lượng cá thể của mỗi tác vụ ban đầu bằng maxCaThe
            Ni[k] = maxCaThe;
        }
        khoiTao();
        danhGiaCaThe(dsNST[theHe%2]);
        if(print_theHe_0){
            for (int k=0;k<soTacVu;k++){
                for (int i=k*maxCaThe;i<(k+1)*maxCaThe;i++){
                    double[] temp = maHoa(dsNST[theHe%2][i].Gen,k+1);
                    System.out.print(k+1+" "+i+" ");
                    for (int j=0;j<globalGen;j++){
                        System.out.print(temp[j]+" ");
                    }
                    System.out.print(" Fcost:"+dsNST[theHe%2][i].f_cost+" Skillfactor: "+dsNST[theHe%2][i].skill_factor+" Rank: "+dsNST[theHe%2][i].rank+"\n");
                }
            }
        }
        if(print_ketQua_theHe){
            double[] good = new double[soTacVu]; //Ghi lại kết quả tốt nhất của từng tác vụ qua các thế hệ
            for (int i=0;i<dsNST[theHe%2].length;i++){
                if(dsNST[theHe%2][i].rank == 1){
                    good[dsNST[theHe%2][i].skill_factor] = dsNST[theHe%2][i].f_cost;
                }
            }
            System.out.print(theHe+": ");
            for (int i=0;i<good.length;i++){
                System.out.print(good[i]+"\t");
            }
            System.out.print("\n");
        }
        while (totalEval<maxEval*soTacVu){
            theHe++;
            double[][] S = new double[45][soTacVu*maxCaThe];
            double[][] δ = new double[45][soTacVu*maxCaThe];
            int[] demS = theHeTiepTheo(S,δ);
            danhGiaCaThe(dsNST[theHe%2]);
            updateM(S,δ,demS);
            LPSR();
            if(print_ketQua_theHe){
                double[] good = new double[soTacVu]; //Ghi lại kết quả tốt nhất của từng tác vụ qua các thế hệ
//                double temp;
//                for (int k=0;k<soTacVu;k++){
//                    good[k] = 1.7976931348623157 * pow(10,308);
//                }
//                for (int i=0;i<dsNST[theHe%2].length;i++){
//                    for (int k=0;k<soTacVu;k++){
//                        temp = TinhGiaTri(dsNST[theHe%2][i],k);
//                        if(good[k] > temp){
//                            good[k] = temp;
//                        }
//                    }
//                }
                for (int i=0;i<soTacVu*maxCaThe;i++){
                    if(dsNST[theHe%2][i].rank == 1){
                        good[dsNST[theHe%2][i].skill_factor] = dsNST[theHe%2][i].f_cost;
                    }
                }
                System.out.print(theHe+": ");
                for (int i=0;i<soTacVu;i++){
                    System.out.print(good[i]+"\t");
                }
                System.out.print("\n");
            }
        }
        System.out.println("\nKẾT QUẢ CUỐI CÙNG:");
        double[] good = new double[soTacVu]; //Ghi lại kết quả tốt nhất của từng tác vụ qua các thế hệ
        int[] id = new int[soTacVu];
        double temp1;
        for (int k=0;k<soTacVu;k++){
            good[k] = 1.7976931348623157 * pow(10,308);
        }
        for (int i=0;i<dsNST[theHe%2].length;i++){
            for (int k=0;k<soTacVu;k++){
                temp1 = TinhGiaTri(dsNST[theHe%2][i],k);
                if(good[k] > temp1){
                    good[k] = temp1;
                    id[k] = i;
                }
            }
        }
        for (int i=0;i<soTacVu;i++){
            System.out.print(good[i]+"\t");
        }
        System.out.println("\nCác cá thể tối ưu:");
        for (int k=0;k<soTacVu;k++){
            double[] temp = maHoa(dsNST[theHe%2][id[k]].Gen,k+1);
            System.out.print(k+1+" "+id[k]+" ");
            for (int j=0;j<globalGen;j++){
                System.out.print(temp[j]+" ");
            }
            System.out.print("\n");
//            System.out.print(" Fcost:"+dsNST[theHe%2][id[k]].f_cost+" Skillfactor: "+dsNST[theHe%2][id[k]].skill_factor+" Rank: "+dsNST[theHe%2][id[k]].rank+"\n");
        }
    }
}
