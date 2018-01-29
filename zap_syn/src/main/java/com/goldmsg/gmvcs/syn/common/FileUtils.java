package com.goldmsg.gmvcs.syn.common;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtils {

    public static boolean readLastNLine(File file, long numRead,OutputStream os){
        List<String> list = readLastNLine(file, numRead);
        if (null == list){
            return false;
        }
        try{
            for (String s : list){
//                System.out.print(s);
                os.write(s.getBytes());
            }
        }catch (Exception e){
            return false;
        }finally {
            try {
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public static List<String> readLastNLine(File file, long numRead) {
        // 定义结果集
        List<String> result = new ArrayList<String>();
        //行数统计
        long count = 0;

        // 排除不可读状态
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            return null;
        }

        // 使用随机读取
        RandomAccessFile fileRead = null;
        try {
            //使用读模式
            fileRead = new RandomAccessFile(file, "r");
            //读取文件长度
            long length = fileRead.length();
            //如果是0，代表是空文件，直接返回空结果
            if (length == 0L) {
                return result;
            } else {
                //初始化游标
                long pos = length - 1;
                while (pos > 0) {
                    pos--;
                    //开始读取
                    fileRead.seek(pos);
                    //如果读取到\n代表是读取到一行
                    if (fileRead.readByte() == '\n') {
                        //使用readLine获取当前行
                        String line = new String(fileRead.readLine().getBytes("ISO-8859-1"), "utf-8");
                        //保存结果
                        result.add(line + "\n");

                        //打印当前行
                        //System.out.println(line);

                        //行数统计，如果到达了numRead指定的行数，就跳出循环
                        count++;
                        if (count == numRead) {
                            break;
                        }
                    }
                }
                if (pos == 0) {
                    fileRead.seek(0);
                    result.add(fileRead.readLine());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileRead != null) {
                try {
                    //关闭资源
                    fileRead.close();
                } catch (Exception e) {
                }
            }
        }
       // Collections.reverse(result);
        return result;
    }

}
