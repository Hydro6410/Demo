package cn.hydro.demo.model.oneDModel.suddenPollution;

public class SuddenPollution {
    public double Q[];
    public double C[];
    public double CDox[];
    public double CCODMN[];
    public double CNH3N[];
    public int length; //数据长度
    public int Num; //支流编号

    public double[] GetQ(){
        return Q;
    }
    public double[] GetCNH3N(){
        return CNH3N;
    }
    public SuddenPollution(int length){
        this.length = length;
        Q = new double[length];
        C = new double[length];
        CDox = new double[length];
        CCODMN = new double[length];
        CNH3N = new double[length];
    }
}
