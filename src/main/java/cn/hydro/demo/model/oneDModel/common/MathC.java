package cn.hydro.demo.model.oneDModel.common;

public class MathC {
    // 线性插值
    public static double Interp(double x, double[] X, double[] Y){
        return x;
    }
    // 得到数组start与End之间的最值，不包含end
    //start和end默认为整个数组
    // TRUE为最大值， false为最小值
    public static int  GetIndex(double[] Array, boolean model){

        return 	GetIndex(Array, 0, Array.length, model);
    }
    public static int GetIndex(double[] Array, int start, int end, boolean model){
        double Max = Array[start];
        int ret = start;
        for(int i = start + 1; i < end; ++i){
            if(Array[i] > Max ^ model){
                Max = Array[i];
                ret = i;
            }
        }
        return ret;

    }
}
