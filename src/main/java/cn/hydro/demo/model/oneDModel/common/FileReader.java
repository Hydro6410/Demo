package cn.hydro.demo.model.oneDModel.common;



import cn.hydro.demo.model.oneDModel.boundary.Boundary;
import cn.hydro.demo.model.oneDModel.parm.Parm;
import cn.hydro.demo.model.oneDModel.section.Section;
import cn.hydro.demo.model.oneDModel.suddenPollution.SuddenPollution;

import java.io.*;
import java.util.Vector;

public class FileReader {
    public FileReader() {

    }
    boolean compare(String lhs, String rth){
        return lhs.substring(0, 5).equals(rth.substring(0, 5));
    }
    //读取断面数据
    public Section[] ReadSection(String filePath){
        Vector<Section> section = new Vector<Section>();
        Section tempSection;
        //Section[] section;
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
            String test = tempArray[0];
            int start = 0;
            int end = 1;
            for(int i = 0; i < tempArray.length + 1; ++i){
                if(i == tempArray.length || !compare(test, tempArray[i])){

                    end = i;
                    if(i < tempArray.length)
                        test = tempArray[i];
                    tempSection = new Section(end - start);
                    String[] t = tempArray[start].split("\t");
                    int Num; //编号
                    String Name; // 断面名称
                    double ds; // 断面位置
                    double[] Z = new double[end-start]; // 水位
                    double[] A = new double[end-start]; // 面积
                    double[] C = new double[end-start]; // 湿周
                    Num = Integer.valueOf(t[0]);
                    Name = t[1];
                    ds = Double.valueOf(t[2]);
                    for(int j = start; j < end; ++j){
                        String[] tempAArray = tempArray[j].split("\t");
                        Z[j-start] = Double.valueOf(tempAArray[3]);
                        A[j-start] = Double.valueOf(tempAArray[4]);
                        C[j-start] = Double.valueOf(tempAArray[5]);
                    }
                    tempSection.setSection(Num, Name, ds, Z, A, C, end - start);
                    section.add(tempSection);
                    start = end;
                }
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
        Section[] retS = new Section[section.size()];
        section.copyInto(retS);
        return retS;
    }
    //读取支流数据
    public SuddenPollution[] ReadSuddenPollution(String filePath){
        File dir = new File(filePath);
        if(!dir.exists()){
            System.out.println(filePath + " not exists");
            return null;
        }
        String[] files = dir.list();
        SuddenPollution[] suddenPollution = new SuddenPollution[files.length];
        String encoding = "ISO-8859-1";
        for(int i = 0; i < files.length; ++i){
            File file = new File(filePath+files[i]);
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
                suddenPollution[i] = new SuddenPollution(tempArray.length);
                String[] SNum = files[i].split("\\.");
                suddenPollution[i].Num = Integer.parseInt(SNum[0]);
                for(int j = 1; j < tempArray.length; ++j){
                    String[] tempAArray = tempArray[j].split("\t");      ///修
                    suddenPollution[i].Q[j] = Double.valueOf(tempAArray[0]);
                    suddenPollution[i].C[j] = Double.valueOf(tempAArray[1]);
                }
            } catch (UnsupportedEncodingException e) {
                System.err.println("The OS does not support " + encoding);
                e.printStackTrace();
                return null;
            }
        }


        return suddenPollution;
    }
    //读取上下边界数据
    public Boundary ReadBoundary(String filePath){
        Boundary boundary;
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
            boundary = new Boundary(tempArray.length);
            for(int i = 0; i < tempArray.length; ++i){
                String[] tempAArray = tempArray[i].split("\t");

                double Q, Z, C;
                Q = Double.valueOf(tempAArray[0]);
                Z = Double.valueOf(tempAArray[1]);
                C = Double.valueOf(tempAArray[2]);
                boundary.SetBoundary(i, Q, Z, C);

            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
        return boundary;
    }
    //读取起始数据
    public Parm ReadPara(String filePath){
        Parm para;
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
            para = new Parm(tempArray.length);
            for(int i = 0; i < tempArray.length; ++i){
                String[] tempAArray = tempArray[i].split("\t");

                para.Num[i] = Integer.valueOf(tempAArray[0]);
                para.Name[i] = tempAArray[1];
                para.Z[i] = Double.valueOf(tempAArray[2]);
                para.NR[i] = Double.valueOf(tempAArray[3]);

            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
        return para;
    }
}
