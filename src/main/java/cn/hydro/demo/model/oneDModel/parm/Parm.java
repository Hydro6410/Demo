package cn.hydro.demo.model.oneDModel.parm;

import java.io.*;

public class Parm {
    public int numT; //时间序列长度
    public double KF;
    public int dnumT;
    public double Ecoff;
    public double dt; // 步长
    public int length; // 断面个数
    public int[] Num; // 编号
    public String Name[]; // 断面名称
    public double Z[]; // 水位
    public double Q[]; // 流量？？？？
    public double NR[]; // 水质
    public double th; // 参数
    public double g; // 重力加速度
    public double alpha; //
    public double n; //糙率
    //public double R; // 水力半径
    public Parm(){}
    public Parm(int length){
        this.length = length;
    }
    private void setMemery(int length){
        Num = new int[length];
        Name = new String[length];
        Z = new double[length];
        Q = new double[length];
        NR = new double[length];
    }
    public void getPara(double ZBoundaryt0){
        String filePath = "Datain/Zini.txt";
        String encoding = "ISO-8859-1";
        File file = new File(filePath);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String tempText = new String(filecontent, encoding);
            String[] tempArray = tempText.split("\n");
            this.length = tempArray.length;
            setMemery(this.length);
            for(int i = 0; i < tempArray.length; ++i){
                String[] tempAArray = tempArray[i].split("\t");

                this.Num[i] = Integer.valueOf(tempAArray[0]);
                this.Name[i] = tempAArray[1];
                this.Z[i] = Double.valueOf(tempAArray[2]);
                this.NR[i] = Double.valueOf(tempAArray[3]);

            }
            for(int i = 0; i < tempArray.length; ++i){
                this.Z[i] += (ZBoundaryt0-this.Z[tempArray.length-1]);
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return;
        }
    }
}
