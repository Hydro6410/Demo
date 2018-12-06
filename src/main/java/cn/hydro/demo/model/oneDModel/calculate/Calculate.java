package cn.hydro.demo.model.oneDModel.calculate;




import cn.hydro.demo.model.oneDModel.DataOut.DataOut;
import cn.hydro.demo.model.oneDModel.boundary.Boundary;
import cn.hydro.demo.model.oneDModel.common.FileReader;
import cn.hydro.demo.model.oneDModel.parm.Parm;
import cn.hydro.demo.model.oneDModel.section.Section;
import cn.hydro.demo.model.oneDModel.suddenPollution.SuddenPollution;

import java.text.DecimalFormat;

/*
 * 支流为2条，时间长度为456
 * 位置为79,138
 * 对应编号为483,422
 */
public class Calculate {
    int tIndex; // 时间索引
    double dt;  // 步长
    int dsIndex; // 断面索引
    public double[] ds; // 里程

    int dlength;
    int tlength;
    double th; // 参数
    public double Z[][];
    public double Q[][];
    //public double C[][];
    public double u[][]; // 流速
    double g;	// 重力加速度
    double alpha;

    public Section[] getSectionData(){
        FileReader fr = new FileReader();
        String filePath = "Datain/DX_1D.txt";
        return fr.ReadSection(filePath);
    }

    public Calculate(){}
    public Calculate(Boundary b, SuddenPollution[] suddenPollution, Parm parm, DataOut dataOut){


        Section[] s = getSectionData();
        parm.getPara(b.GetZBoundary(0));

        for(int i = 0; i < s.length; ++i){
            System.out.println(""+i+"\t"+ s[i].getNum()+"\t" +(s[i].getNum()-parm.Num[i]));
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
        Z = new double[tlength][];
        Q = new double[tlength][];
        //C = new double[tlength][];
        u = new double[tlength][];
        for(int i = 0; i < tlength; ++i){
            Z[i] = new double[dlength];
            Q[i] = new double[dlength];
            //C[i] = new double[dlength];
            u[i] = new double[dlength];
        }

        for(int i = 0; i < tlength; ++i){
            Q[i][0] = b.GetQBoundary(i);		// 上边界流量
            Z[i][dlength-1] = b.GetZBoundary(i);	// 下边界水位
            //NR[i][0] = b.GetCBoundary(i);		// 上边界水质
        }
        // 起始边界条件
        for(int i = 0; i < parm.length; ++i){
            Q[0][i] = Q[0][0];			// 流量
            Z[0][i] = parm.Z[i];	    // 水位
            //NR[0][i] = parm.NR[i];		// 水质
        }
        for(int i = 0; i < parm.length; ++i){
            System.out.println(""+parm.Z[i]+"\t"+ s[i].ASection(parm.Z[i]));
        }
        g = parm.g;
        alpha = parm.alpha;
        alpha = 1.2;


        for(int i = 0; i < dlength; ++i){
            u[tIndex][i] = Q[tIndex][i]/s[i].ASection(Z[tIndex][i]);
        }

        while(tIndex < tlength - 1){
            next(b,s,suddenPollution, parm, tIndex); // 计算下一时段水位流量，重置下一时段para
            tIndex++;
        }
        dataOut.Z = this.Z;
        dataOut.Q = this.Q;
        dataOut.U = this.u;
        //dataOut.C = this.C;
    }

    public double getSuddenPollution(int sectionNum, SuddenPollution[] su, int tIndex){
        double ret = 0.0;
        for(int i = 0; i < su.length; ++i){
            if(sectionNum == su[i].Num){
                return su[i].Q[tIndex];
            }
        }
        return ret;
    }

    public double getSuddenPollutionC(int sectionNum, SuddenPollution[] su, int tIndex){
        double ret = 0.0;
        for(int i = 0; i < su.length; ++i){
            if(sectionNum == su[i].Num){
                return su[i].C[tIndex];
            }
        }
        return ret;
    }
    private void next(Boundary b, Section[] s, SuddenPollution[] su, Parm parm, int tIndex){
        double C[] = new double[dlength];
        double D[] = new double[dlength];
        double E[] = new double[dlength];
        double F[] = new double[dlength];
        double G[] = new double[dlength];
        double Ph[] = new double[dlength];
        //double us[] = new double[dlength];


        //u[tIndex] = us;



        for(int i = 0; i < dlength - 1; ++i ){

            double q = getSuddenPollution(s[i].getNum(), su, tIndex)/ds[i];
            double q1 = getSuddenPollution(s[i+1].getNum(), su, tIndex)/ds[i+1];

            C[i] = (s[i].BSection(Z[tIndex][i]) + s[i+1].BSection(Z[tIndex][i+1]))/2.0 * ds[i] / 2.0 / dt /th;
            D[i] = (q+q1)*ds[i]/2/th-(1-th)/th*(Q[tIndex][i+1] - Q[tIndex][i])
                    + C[i]*(Z[tIndex][i+1]+Z[tIndex][i]);

            double chezy = 1.0/parm.NR[i]*Math.pow(s[i+1].RSection(Z[tIndex][i+1]), 1.0/6.0);

            E[i] = ds[i]/2.0/th/dt-(alpha*u[tIndex][i]) +
                    g*Math.abs(u[tIndex][i+1])/2.0/th/chezy/chezy/s[i].RSection(Z[tIndex][i])*ds[i];

            G[i] = ds[i]/2.0/th/dt + alpha*u[tIndex][i+1] + g*Math.abs(u[tIndex][i+1])/2.0/th/chezy/chezy/s[i+1].RSection(Z[tIndex][i+1])*ds[i];
            F[i] = g*(s[i].ASection(Z[tIndex][i])+s[i+1].ASection(Z[tIndex][i+1]))/2.0;
            Ph[i] = ds[i]/2.0/th/dt*(Q[tIndex][i+1]+Q[tIndex][i])-(1-th)/th*alpha*(u[tIndex][i+1]*Q[tIndex][i+1] -u[tIndex][i]*Q[tIndex][i])
                    -(1-th)/th*(g*(s[i].ASection(Z[tIndex][i]) + s[i+1].ASection(Z[tIndex][i+1]))/2.0)*(Z[tIndex][i+1]-Z[tIndex][i]);
        }

        //
        double Y1, Y2, Y3, Y4;
        double[] S = new double[dlength];
        double[] T = new double[dlength];
        double[] P = new double[dlength];
        double[] V = new double[dlength];
        P[0] = Q[tIndex][0];
        V[0] = 0;


        /*下水位边界已知*/
        for(int i = 0; i < dlength - 1; ++i){
            Y1 = V[i] + C[i];
            Y2 = F[i] + E[i]*V[i];
            Y3 = D[i] + P[i];
            Y4 = Ph[i] - E[i]*P[i];
            S[i+1] = (G[i]*Y3 - Y4)/(Y1*G[i] + Y2);
            T[i+1] = (C[i]*G[i] - F[i])/(Y1*G[i] + Y2);
            P[i+1] = Y3 - Y1*S[i+1];
            V[i+1] = C[i] - Y1*T[i+1];
        }

        for(int i = dlength - 2; i >= 0; --i){
            Z[tIndex+1][i] = S[i+1] - T[i+1] * Z[tIndex+1][i+1];
            Q[tIndex+1][i+1] = P[i+1] - V[i+1] * Z[tIndex+1][i+1];

        }

        for(int i = 0; i < dlength; ++i){
            u[tIndex+1][i] = Q[tIndex+1][i]/s[i].ASection(Z[tIndex+1][i]);
        }




//        StringBuffer strBuffer1 = new StringBuffer();
//        StringBuffer strBuffer = new StringBuffer();
//        DecimalFormat df = new DecimalFormat("######0.00");
//        String filePath;
//        String str = "u\tS\tT\tP\tV\tC\tD\tE\tF\tG\tPh\r\n";
//        strBuffer1.append(str);
//        str = "Z\tQ\r\n";
//        strBuffer.append(str);
//        for(int i = 0; i < dlength - 1; ++i){
//            str = ""+ df.format(us[i]) + df.format(S[i]) + "\t" + df.format(T[i]) + "\t" +
//                    df.format(P[i]) + "\t" + df.format(V[i]) + "\t" +
//                    df.format(C[i]) +"\t"+ df.format(D[i]) + "\t" +
//                    df.format(E[i]) + "\t" + df.format(F[i]) + "\t" +
//                    df.format(G[i]) + "\t" + df.format(Ph[i]) + "\r\n";
//            strBuffer1.append(str);
//            str = "" + df.format(s[i].getNum()) + "\t" + df.format(Z[tIndex][i]) + "\t" + df.format(Q[tIndex][i]) + "\t" +
//                    df.format(s[i].ASection(Z[tIndex][i])) + "\t" + df.format(s[i].BSection(Z[tIndex][i])) + "\r\n";
//            strBuffer.append(str);
//        }
//
//
//        filePath = "S/outdataS" + tIndex + ".txt";
//        FileWrite file1 = new FileWrite(filePath, strBuffer1.toString(), false);
//        filePath = "Z/outdata" + tIndex + ".txt";
//        FileWrite file = new FileWrite(filePath, strBuffer.toString(), false);

    }



    public void dataout(int tIndex, int dIndex){
		/*
		Q[tIndex][dIndex];
		Z[tIndex][dIndex];
		*/
    }

    public void dataout(){
		/*
		 * for(int i = 0; i <tlength; ++i){

			for(int j = 0; j < dlength; ++j){

			}
		}
		*/
    }
    public void CalcResult(){

    }
}
