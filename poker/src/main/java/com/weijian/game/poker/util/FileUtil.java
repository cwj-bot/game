package com.weijian.game.poker.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileUtil {

    public static void writeFile(String data, String fileName) {

        BufferedWriter bufferWriter = null;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName, false);
            bufferWriter = new BufferedWriter(fileWriter);
            bufferWriter.write(data);
        } catch (Exception e) {
            log.error("写入文件异常 e = {}", e);
        } finally {
            if (bufferWriter != null)
                try {
                    bufferWriter.close();
                } catch (IOException e1) {
                    log.error("写入文件异常 e = {}", e1);
                }

            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    log.error("写入文件异常 e = {}", e);
                }
            }
        }
    }

    public static String readFile(String path, String fileName) {
        File file = new File(path, fileName);
        FileInputStream is = null;
        StringBuilder stringBuilder = null;
        try {
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
                file.createNewFile();
            }
            if (file.length() != 0) {
                is = new FileInputStream(file);
                InputStreamReader streamReader = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(streamReader);
                String line;
                stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                is.close();
            } else {
                return null;
            }

        } catch (Exception e) {
            log.error("读取文件异常 e = {}", e);
        }
        return String.valueOf(stringBuilder);

    }
}
