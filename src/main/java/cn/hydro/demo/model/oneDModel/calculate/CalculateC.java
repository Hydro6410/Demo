package cn.hydro.demo.model.oneDModel.calculate;


import cn.hydro.demo.model.oneDModel.DataOut.DataOut;
import cn.hydro.demo.model.oneDModel.boundary.Boundary;
import cn.hydro.demo.model.oneDModel.parm.Parm;
import cn.hydro.demo.model.oneDModel.section.Section;
import cn.hydro.demo.model.oneDModel.suddenPollution.SuddenPollution;

public class CalculateC extends Calculate{

    public double C[][];
    private double AS[][];
    //追赶法
    private double[] TDMA(double[] a, double[] b, double[] c, double[] d){
        int N = a.length;

        c[0] = c[0] / b[0];
        d[0] = d[0] / b[0];

        for(int n = 1; n < N; n++){
            //for n in range(1,N):
            double m = 1.0 / (b[n] - a[n] * c[n - 1]);
            c[n] = c[n] * m;
            d[n] = (d[n] - a[n] * d[n - 1]) * m;
        }

        for(int n = N-2; n > -1; n--){
            //for n in range(N - 2, -1, -1):
            d[n] = d[n] - c[n] * d[n + 1];
        }

        return d;


    }



    public CalculateC(Boundary b, SuddenPollution[] suddenPollution, Parm parm, DataOut dataOut){
        Section[] s = getSectionData();
        parm.getPara(b.GetZBoundary(0));

        for(int i = 0; i < s.length; ++i){
            //System.out.println(""+i+"\t"+ s[i].getNum()+"\t" +(s[i].getNum()-parm.Num[i]));
        }
        tIndex = 0;
        dt = parm.dt;
        dsIndex = 0;
        ds = new double[parm.length];

        for(int i = 0; i < parm.length; ++i){
            ds[i] = s[i].getds();
        }
        dlength = parm.length;
        tlength = b.length;
        th = parm.th;
        Z = dataOut.Z;
        Q = dataOut.Q;
        u = dataOut.U;
        C = new double[tlength][];
        AS = new double[tlength][];

        for(int i = 0; i < tlength; ++i){
            C[i] = new double[dlength];
            AS[i] = new double[dlength];
        }

        for(int i = 0; i < tlength; i++){
            for(int j = 0; j < parm.length; j++){
                AS[i][j] = s[j].ASection(Z[i][j]);
            }
        }

        for(int i = 0; i < tlength; ++i){

//            Q[i][0] = b.GetQBoundary(i);		// 上边界流量
//            Z[i][dlength-1] = b.GetZBoundary(i);	// 下边界水位
            C[i][0] = b.GetCBoundary(i);		// 上边界水质
        }


        for(int j = 0; j < parm.length; j++){
            C[0][j] = b.GetCBoundary(0);
        }
//            Q[i][0] = b.GetQBoundary(i);		// 上边界流量
//            Z[i][dlength-1] = b.GetZBoundary(i);	// 下边界水位
        //C[i][0] = b.GetCBoundary(i);		// 上边界水质

        // 起始边界条件

        for(int i = 0; i < parm.length; ++i){
            //System.out.println(""+parm.Z[i]+"\t"+ s[i].ASection(parm.Z[i]));
        }

        //for()
        g = parm.g;
        alpha = parm.alpha;
        alpha = 1.2;

        double dxTa;
        double dxTb;

        double[] alpha1 = new double[parm.length];
        double[] beta1 = new double[parm.length];
        double[] gamma1 = new double[parm.length];
        double[] delta1 = new double[parm.length];

        double[] at1 = new double[parm.length-1];
        double[] bt1 = new double[parm.length-1];
        double[] ct1 = new double[parm.length-1];
        double[] dt1 = new double[parm.length-1];

        double[] Val = new double[parm.length];



        for(int j = 0; j < tlength-1; ++j){

            for(int i = 1; i < parm.length; i++){

                double qqs = getSuddenPollutionC(s[i].getNum(), suddenPollution, j)*getSuddenPollution(s[i].getNum(), suddenPollution, j)/ds[i]/AS[j][i];

                //qqs = 0;

                dxTa=ds[i]*(ds[i]+ds[i-1])/2.0;
                dxTb=ds[i-1]*(ds[i]+ds[i-1])/2.0;
                alpha1[i] = -parm.Ecoff/dxTa*AS[j+1][i]-Q[j][i-1]/ds[i-1]; //-Ecoff/dxTa*As(i,j+1)-Qs(i-1,j)/deltaX(i-1)
                beta1[i] = (1.0/dt + 2.0*parm.Ecoff*(0.5/dxTa+0.5/dxTb))*AS[j+1][i]+Q[j][i-1]/ds[i-1];//(1.0/deltaT+2.0*Ecoff*(0.5/dxTa+0.5/dxTb))*As(i,j+1)+Qs(i-1,j)/deltaX(i-1)
                gamma1[i] = (-parm.Ecoff/dxTb)*AS[j+1][i];
                delta1[i] = C[j][i]*(1.0/dt*AS[j][i]) - C[j][i -1]*parm.KF*AS[j][i];// - getSuddenPollutionC(s[i].getNum(), suddenPollution, j)*parm.KF*AS[j][i]+ qqs*AS[j][i];// -u[t][i]/ds[i])+C[t][i-1]*(u[t][i]/ds[i]-parm.KF/2);

//                if(qqs != 0){
//                    System.out.println("qqs");
//                    System.out.println(qqs*AS[j][i]);
//                    System.out.println(delta1[i-1]);
//                    System.out.println(getSuddenPollutionC(s[i].getNum(), suddenPollution, j)*parm.KF*AS[j][i]);
//
//                }
            }
            delta1[1] = delta1[1] - alpha1[1]*C[j+1][0];
            alpha1[1] = 0;

            alpha1[parm.length-1] = alpha1[parm.length-1]-gamma1[parm.length-1];

            beta1[parm.length-1] = beta1[parm.length-1] + 2.0*gamma1[parm.length-1];
            gamma1[parm.length-1] = 0;

            alpha1[0] = 0.0;
            beta1[0] = 1.0;
            gamma1[0] = 0.0;
            delta1[0] = C[j][0];

            Val = TDMA(alpha1, beta1, gamma1, delta1);
            for(int i = 0; i < Val.length; i++){
                C[j+1][i] = Val[i];
            }


        }

        dataOut.C = this.C;


    }
}
