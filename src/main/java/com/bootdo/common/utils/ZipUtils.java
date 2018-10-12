package com.bootdo.common.utils;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * @author xumx
 * @date 2018/9/30
 */
public class ZipUtils {

    private static int BUF_SIZE = 1024;
    private static String ZIP_ENCODEING = "GBK";

    /**
     * 压缩文件或文件夹
     *
     * @param zipFileName
     * @param inputFile
     * @throws Exception
     */
    public void zip(String zipFileName, String inputFile) throws Exception {
        zip(zipFileName, new File(inputFile));
    }

    /**
     * 压缩文件或文件夹
     *
     * @param zipFileName
     * @param inputFile
     * @throws Exception
     */
    public static void zip(String zipFileName, File inputFile) throws Exception {
        // 未指定压缩文件名，默认为"ZipFile"
        if (zipFileName == null || zipFileName.equals(""))
            zipFileName = "ZipFile";

        // 添加".zip"后缀
        if (!zipFileName.endsWith(".zip"))
            zipFileName += ".zip";

        // 创建文件夹
        String path = Pattern.compile("[\\/]").matcher(zipFileName).replaceAll(File.separator);
        int endIndex = path.lastIndexOf(File.separator);
        path = path.substring(0, endIndex);
        File f = new File(path);
        f.mkdirs();
        // 开始压缩
        {
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFileName)));
            zos.setEncoding(ZIP_ENCODEING);
            compress(zos, inputFile, "");
            zos.close();
        }
    }

    /**
     * 解压缩zip压缩文件到指定目录
     *
     * @param unZipFileName
     * @param outputDirectory
     * @throws Exception
     */
    public static void unZip(String unZipFileName, String outputDirectory) throws Exception {
        // 创建输出文件夹对象
        File outDirFile = new File(outputDirectory);
        outDirFile.mkdirs();
        // 打开压缩文件文件夹
        ZipFile zipFile = new ZipFile(unZipFileName, ZIP_ENCODEING);
        for (Enumeration entries = zipFile.getEntries(); entries.hasMoreElements(); ) {
            ZipEntry ze = (ZipEntry) entries.nextElement();
            File file = new File(outDirFile, ze.getName());
            if (ze.isDirectory()) {// 是目录，则创建之
                file.mkdirs();
            } else {
                File parent = file.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                InputStream is = zipFile.getInputStream(ze);
                inStream2outStream(is, fos);
                fos.close();
                is.close();
            }
        }
        zipFile.close();
    }

    /**
     * 压缩一个文件夹或文件对象到已经打开的zip输出流 <b>不建议直接调用该方法</b>
     *
     * @param zos
     * @param f
     * @param fileName
     * @throws Exception
     */
    public static void compress(ZipOutputStream zos, File f, String fileName) throws Exception {
        if (f.isDirectory()) {
            // 压缩文件夹  
            File[] fl = f.listFiles();
            zos.putNextEntry(new ZipEntry(fileName + "/"));
            fileName = fileName.length() == 0 ? "" : fileName + "/";
            for (int i = 0; i < fl.length; i++) {
                compress(zos, fl[i], fileName + fl[i].getName());
            }
        } else {
            // 压缩文件  
            zos.putNextEntry(new ZipEntry(fileName));
            FileInputStream fis = new FileInputStream(f);
            inStream2outStream(fis, zos);
            fis.close();
            zos.closeEntry();
        }
    }

    private static void inStream2outStream(InputStream is, OutputStream os) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        int bytesRead = 0;
        for (byte[] buffer = new byte[BUF_SIZE]; ((bytesRead = bis.read(buffer, 0, BUF_SIZE)) != -1); ) {
            bos.write(buffer, 0, bytesRead); // 将流写入  
        }
    }

    /**
     * @param srcfile 文件名数组
     * @param zipfile 压缩后文件
     */
    public static void zipFiles(java.io.File[] srcfile, java.io.File zipfile) {
        byte[] buf = new byte[1024];
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                    zipfile));
            out.setEncoding("GBK");
            for (int i = 0; i < srcfile.length; i++) {
                FileInputStream in = new FileInputStream(srcfile[i]);
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
