package cn.hydro.demo.model.oneDModel.section;


import cn.hydro.demo.model.oneDModel.common.MathC;

public class Section {
    //断面数据
    private int Num; //编号
    private String Name; // 断面名称
    private double ds; // 断面位置
    private double[] RDs; //岸边距
    private double[] H; //水深
    private int[] BondIndex; //左右边界，河底索引
    private double dh = 0.15;
    private double[] Z; // 水位
    private double[] A; // 面积
    private double[] C; // 湿周
    private double[] B; // 水面宽
    //public double R; // 水力半径
    private int length; //插值系列长度
    private int rlength; //断面系列长度

    public Section(){
        BondIndex = new int[3];
        dh = 0.15;
    }
    private void SetMemory(int length){
        Z = new double[length];
        A = new double[length];
        C = new double[length];
        B = new double[length];
    }
    //断面处理：
    public Section(int length){
        this.length = length;
        SetMemory(length);
    }
    public void setSection(int rlength, String Name, double ds,
                           double[] RDs, double[] H){
        this.rlength = rlength;
        this.setName(Name);
        this.ds = ds;
        RDs = new double[rlength];
        H = new double[rlength];
        BondIndex[1] = MathC.GetIndex(H,false);
        BondIndex[0] =  MathC.GetIndex(H, 0, BondIndex[1], true);
        BondIndex[2] =  MathC.GetIndex(H, BondIndex[1], H.length, true);
        double Bond =  H[BondIndex[0]]<H[BondIndex[2]]?H[BondIndex[0]]:H[BondIndex[2]];
        length = (int)((Bond - H[BondIndex[1]])/dh) + 1;
        SetMemory(length);
        Z[0] = BondIndex[1];
        A[0] = 0;
        C[0] = 0;
        B[0] = 0;
        for(int i = 1; i < length; ++i){
            Z[i] = Z[i-1] + dh;
            //B[i] = getB(Z[i], RDs, H, )
        }
    }
    public void setSection(int Num, String Name, double ds,
                           double[] Z, double[] A, double[] C, int length){
        this.setNum(Num);
        this.setName(Name);
        this.ds = ds;
        this.Z = Z;
        this.A = A;
        this.C = C;
        this.length = length;
    }
    // 水面宽计算
    private void setB(){
        B[0] = 0;
        for(int i = 1; i < length; i++){
            B[i] = (A[i] - A[i -1])/(Z[i] - Z[i -1]);
        }
    }
    //根据水位计算水面宽
    public double BSection(double Z){
        setB();
        double ret = interp(Z, 1);
        return ret;
    }
    //根据水位计算湿周
    public double CSection(double Z){
        double ret = interp(Z, 2);
        return ret;
    }
    //根据水位计算断面面积
    public double ASection(double Z){
        double ret = interp(Z, 3);
        return ret;
    }

    //根据水位计算断面面积
    public double RSection(double Z){
        double ret1 = interp(Z, 3); // 面积
        double ret2 = interp(Z, 2); // 湿周
        if(ret1 <= 0 || ret2 <= 0){
            System.out.println("RSection计算错误：面积或者湿周出现0或者负数");
        }
        double ret = ret1/ret2;
        return ret;
    }

    public double getds(){
        return ds;
    }

    double interp(double z, int index){
        double ret = 0;
        double[] Y = new double[length];
        String[] error = {"","B","C","A"};
        if(index == 1){
            //水面宽
            Y = B;
        }else if(index == 2){
            //湿周
            Y = C;
        }else if(index == 3){
            //面积
            Y = A;
        }
        else{
            System.out.println("indedx错误");
        }

        if(z<Z[0] || z > Z[length - 1]){
            System.out.println(error[index]+"Section索引溢出");
        }
        for(int i = 1; i < length; ++i){

            if(z < Z[i] || i == length - 1){
                ret = (z - Z[i -1])/(Z[i] - Z[i-1])*(Y[i] - Y[i-1])+Y[i-1];
                break;
            }
        }
        return ret;
    }
    public int getNum() {
        return Num;
    }
    public void setNum(int num) {
        Num = num;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
}
