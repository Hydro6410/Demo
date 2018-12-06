package cn.hydro.demo.model.oneDModel.DataOut;

import cn.hydro.demo.model.oneDModel.common.FileWrite;

import java.text.DecimalFormat;

public class DataOut {
    public double[][] Z;
    public double[][] U;
    public double[][] Q;
    public double[][] C;

    private String writeElement(double[][] e){
        StringBuffer strBuffer = new StringBuffer();
        DecimalFormat df = new DecimalFormat("######0.00");

        String str = "";

        for(int i = 0; i < e.length; ++i){
            for(int j = 0; j < e[0].length; j++){
                strBuffer.append(df.format(e[i][j])+"\t");
            }
            strBuffer.append("\r\n");
        }
        return strBuffer.toString();
    }

    public void write(){
        String filePath = "Dataout/Z" + ".txt";
        new FileWrite(filePath, writeElement(Z), false);

        filePath = "Dataout/U" + ".txt";
        new FileWrite(filePath, writeElement(U), false);                                                                                                                                                                                                                                                                                                                                                                        new FileWrite(filePath, writeElement(U), false);

        filePath = "Dataout/Q" + ".txt";
        new FileWrite(filePath, writeElement(Q), false);

        filePath = "Dataout/C" + ".txt";
        new FileWrite(filePath, writeElement(C), false);

    }
}
