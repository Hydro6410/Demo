package cn.hydro.demo.model.oneDModel.boundary;

public class Boundary {
    private double[] QBoundary; // 边界流量
    private double[] ZBoundary; // 边界水位
    private double[] CBoundary; // 边界水质
    private double[] CBoundaryDOX;
    private double[] CBoundaryCODMN;
    private double[] CBoundaryNH3N;
    public int length;
    public Boundary(int length){
        this.length=length;
        QBoundary = new double[length];
        ZBoundary = new double[length];
        CBoundary = new double[length];
        CBoundaryDOX = new double[length];
        CBoundaryCODMN = new double[length];
        CBoundaryNH3N = new double[length];
    }
    public void SetBoundary(int index, double Q, double Z, double C){
        QBoundary[index] = Q;
        ZBoundary[index] = Z;
        CBoundary[index] = C;
    }
    public void SetBoundary(double[] Q, double[] Z){
        QBoundary = Q;
        ZBoundary = Z;
    }
    public void SetCBoundary(double[] CBoundary){
        this.CBoundary = CBoundary;
    }
    public void SetCBoundaryDOX(double[] CBoundaryDOX){
        this.CBoundaryDOX = CBoundaryDOX;
    }
    public void SetCBoundaryCODMN(double[] CBoundaryCODMN){
        this.CBoundaryCODMN = CBoundaryCODMN;
    }
    public void SetCBoundaryNH3N(double[] CBoundaryNH3N){
        this.CBoundaryNH3N = CBoundaryNH3N;
    }
    public double[] GetQBoundary(){
        return QBoundary;
    }

    public double[] GetZBoundary(){
        return ZBoundary;
    }
    public double[] GetCBoundary(){
        return CBoundary;
    }
    public double[] GetCBoundaryDOX(){
        return CBoundaryDOX;
    }

    public double[] GetCBoundaryCODMN(){
        return CBoundaryCODMN;
    }
    public double[] GetCBoundaryNH3N(){
        return CBoundaryNH3N;
    }
    public double GetQBoundary(int index){
        return QBoundary[index];
    }

    public double GetZBoundary(int index){
        return ZBoundary[index];
    }

    public double GetCBoundary(int index){
        return CBoundary[index];
    }
    public double GetCBoundaryDOX(int index){
        return CBoundaryDOX[index];
    }
    public double GetCBoundaryCODMN(int index){
        return CBoundaryCODMN[index];
    }
    public double GetCBoundaryNH3N(int index){
        return CBoundaryNH3N[index];
    }
}
