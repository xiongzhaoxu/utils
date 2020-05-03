package com.oozol.utils;

import com.beust.jcommander.internal.Maps;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class DownloadZipUtil {

    /**
     * FileUtils下载网络文件
     *
     * @param serverUrl   ：网络文件地址
     * @param savePath：本地保存路径
     * @param zipSavePath ：压缩文件保存路径
     * @return
     */
    public static String downloadFile(String serverUrl, String savePath, String zipSavePath) throws Exception {
        String result;
        File f = new File(savePath);
        if (!f.exists()) {
            if (!f.mkdirs()) {
                throw new Exception("makdirs: '" + savePath + "'fail");
            }
        }
        URL url = new URL(serverUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而放回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0(compatible;MSIE 5.0;Windows NT;DigExt)");
        Long totalSize = Long.parseLong(conn.getHeaderField("Content-Length"));
        if (totalSize > 0) {
            FileUtils.copyURLToFile(url, new File(zipSavePath));
            result = "success";
        } else {
            throw new Exception("can not find serverUrl :{}" + serverUrl);
        }
        return result;
    }


    public static  Map<String,String> readData(String urlStr) throws IOException {

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //得到输入流
        InputStream inputStream = conn.getInputStream();

        ZipInputStream zin = new ZipInputStream(inputStream, Charset.forName("utf-8"));
        BufferedInputStream bs = new BufferedInputStream(zin);
        byte[] bytes = null;
        ZipEntry ze;
        Map<String,String> jsonMap= Maps.newHashMap();

        //循环读取压缩包里面的文件
        while ((ze = zin.getNextEntry()) != null) {
            StringBuilder orginJson = new StringBuilder();
            if (ze.toString().endsWith(".json")) {
                //读取每个文件的字节，并放进数组
                bytes = new byte[(int) ze.getSize()];
                bs.read(bytes, 0, (int) ze.getSize());
                //将文件转成流
                InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(byteArrayInputStream));
                //读取文件里面的内容
                String line;
                while ((line = br.readLine()) != null) {
                    orginJson.append(line);
                }
                //关闭流
                br.close();
                String name=new String(ze.getName().replace(".json",""));
                jsonMap.put(name,orginJson.toString());
            }
        }
        zin.closeEntry();
        inputStream.close();
        return jsonMap;
    }




}

