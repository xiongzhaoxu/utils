package com.sinry.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

/**
 * 二维码工具
 *
 * @author ：Sinr
 * @date ：Created in 2019-03-26 12:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QRCodeUtil {

    /**
     * 宽度
     */
    int width;

    /**
     * 高度
     */
    int height;

    /**
     * 二维码内容
     */
    String content;

    /**
     * 二维码文件名
     */
    String fileName;

    /**
     * 输出的文件格式
     */
    String prefix;

    /**
     * 保存的文件路径
     */
    String savePath;

    public QRCodeUtil(int width, int height, String content, String fileName, String prefix) {
        this.width = width;
        this.height = height;
        this.content = content;
        this.fileName = fileName;
        this.prefix = prefix;
    }

    /**
     * 创建二维码
     */
    public String createQRCode ()
    {
        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        // 设置输入的字符编码
        hints.put(EncodeHintType.CHARACTER_SET, "GBK");
        // 空白画布
        BitMatrix matrix = null;
        try {
            // 对二维码进行base64位编码 encode(内容, 类型【条形码/二维码】, 宽度, 高度, hints)
            matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        // 设置上传目录
        String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        String _filePath = savePath + date + "/" + fileName + '.' + prefix;
        // 写入到文件，如果文件夹不存在则创建
        File file = new File(_filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            MatrixToImageWriter.writeToPath(matrix, prefix, file.toPath());
//            System.out.println("二维码生成成功!  " + file.toURI());
            return file.toURI().toString();
        } catch (IOException e) {
//            System.out.println("二维码生成失败!");
            e.printStackTrace();
            return null;
        }
    }


}
