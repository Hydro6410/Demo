package cn.hydro.demo.model.oneDModel;


import cn.hydro.demo.model.oneDModel.DataOut.DataOut;
import cn.hydro.demo.model.oneDModel.boundary.Boundary;
import cn.hydro.demo.model.oneDModel.calculate.Calculate;
import cn.hydro.demo.model.oneDModel.calculate.CalculateC;
import cn.hydro.demo.model.oneDModel.common.FileReader;
import cn.hydro.demo.model.oneDModel.parm.Parm;
import cn.hydro.demo.model.oneDModel.suddenPollution.SuddenPollution;

public class Main {



    public static void main(String[] args) {
        FileReader fr = new FileReader();
        // 读取边界数据
        String filePath = "Datain/QZCDynamic_1D.txt";
        Boundary boundary = fr.ReadBoundary(filePath);

        // 读取支流数据
        filePath = "Datain/Suddenpollution/";
        SuddenPollution[] suddenPollution = fr.ReadSuddenPollution(filePath);

        Parm para = new Parm();

        DataOut dataOut = new DataOut();

        // 模型计算
        // 自设参数
        para.numT=240;
        para.KF = 0.2/24.0/3600.0;
        para.Ecoff = 20;
        para.th = 0.7;
        para.g = 9.8;
        para.dt = 1 * 3600;
        para.alpha = 1.2;

//        int numT = para.numT;
//        double KF = para.KF;
//        double Dt= para.dt;
//        double Ecoff= para.Ecoff;
//
//        //输出参数
//        double[] Qs = new double[numT*513];
//        double[] Zs = new double[numT*513];
//        double[] Us = new double[numT*513];
//        //由于每次只能计算一个污染物浓度，所以三次模型计算输出三个污染物浓度
//        double[] DOX = new double[numT*513];
//        double[] CODMN = new double[numT*513];
//        double[] NH3N = new double[numT*513];
//        //默认进行预报
//        double[] ZBoundary = boundary.GetZBoundary();
//        double[] QBoundary = boundary.GetQBoundary();
//        double[] CBoundaryCODMN = boundary.GetCBoundaryCODMN();
//
//        double[] QSuddenPollution = new double[suddenPollution.length*suddenPollution[0].length];
//        for(int i = 0; i < suddenPollution.length; i++){
//            for(int j = 0; j < suddenPollution[0].length; j++){
//                QSuddenPollution[i*suddenPollution[0].length+j] = suddenPollution[i].Q[j];
//            }
//        }
//
//        double[] CSuddenPollutionCODMN = new double[suddenPollution.length*suddenPollution[0].length];
//        for(int i = 0; i < suddenPollution.length; i++){
//            for(int j = 0; j < suddenPollution[0].length; j++){
//                QSuddenPollution[i*suddenPollution[0].length+j] = suddenPollution[i].CCODMN[j];
//            }
//        }
//
//
//        for(int i=0;i<numT;i++){
//            ZBoundary[i] = boundary.GetZBoundary(i);
//        }
//        IntByReference n=new IntByReference(numT);
//        DoubleByReference dt=new DoubleByReference(Dt);
//        DoubleByReference kf=new DoubleByReference(KF);
//        DoubleByReference eco=new DoubleByReference(Ecoff);
//
//        try {
//            n=new IntByReference(numT);
//
//            dt=new DoubleByReference(Dt);
//            kf=new DoubleByReference(KF);
//            eco=new DoubleByReference(Ecoff);
//            F95ONED libsxCODMN = (F95ONED) Native.loadLibrary("ONEDDLL",F95ONED.class);
//            libsxCODMN.ONEDDLL(n,dt,kf,eco,QBoundary,ZBoundary,CBoundaryCODMN,QSuddenPollution,CSuddenPollutionCODMN,Qs,Zs,Us,CODMN);
//            //invoke(Native.class.getPackage().getName() + ".Native", "dispose");
//            libsxCODMN = null;
//            Runtime.getRuntime().gc();
//
////            n=new IntByReference(numT);
////            dt=new DoubleByReference(Dt);
////            kf=new DoubleByReference(KF);
////            eco=new DoubleByReference(Ecoff);
////
////            F95ONED libsxNH3N = (F95ONED) Native.loadLibrary("ONEDDLL.dll",F95ONED.class);
////            libsxNH3N.ONEDDLL(n,dt,kf,eco,QBoundary,ZBoundary,CBoundaryNH3N,QSuddenPollution,CSuddenPollutionNH3N,Qs,Zs,Us,NH3N);
////            //PrivateUtil.invoke(Native.class.getPackage().getName() + ".Native", "dispose");
////            libsxNH3N =null;
////            Runtime.getRuntime().gc();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Calculate calc = new Calculate(boundary, suddenPollution, para, dataOut);
        CalculateC calculateC = new CalculateC(boundary, suddenPollution, para, dataOut);

        dataOut.write();

        // 河网数据输出
        calc.CalcResult();
    }
}
