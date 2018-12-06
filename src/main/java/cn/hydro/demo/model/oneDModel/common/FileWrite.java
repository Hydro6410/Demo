package cn.hydro.demo.model.oneDModel.common;

import java.io.FileWriter;
import java.io.IOException;

///model = true为追加
///model = false为覆盖
public class FileWrite {
    public FileWrite(String filePath, String content, Boolean model){
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath, model);
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            // 写文件失败
        }
    }
}
