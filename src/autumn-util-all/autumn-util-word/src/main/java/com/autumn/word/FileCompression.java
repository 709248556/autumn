package com.autumn.word;


import com.autumn.exception.ArgumentNullException;
import com.autumn.exception.ArgumentOverflowException;
import com.autumn.util.StringUtils;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 文件压缩
 */
public class FileCompression {
    /**
     * 块大小
     */
    public static final int BLOCK_SIZE = 4096;

    /**
     * 压缩文件（压缩在工程目录）
     *
     * @param files            文件集合
     * @param zipFileName      压缩文件名称
     * @param zipLevel         压缩级别(0-9)
     * @param deleteSourceFile 删除原文件
     * @param comment          注解
     * @param password         密码
     */
    public static void compressFile(List<String> files, String zipFileName, int zipLevel, boolean deleteSourceFile, String comment, String password) {
        if (zipLevel < 0 || zipLevel > 9) {
            throw new ArgumentOverflowException("zipLevel", "zipLevel 必须介于 0 - 9 之间。");
        }
        if (files == null) {
            throw new ArgumentNullException("files");
        }
        if (files.size() == 0) {
            throw new IllegalArgumentException("至少需要一个文件以上。");
        }
        for (int i = 0; i < files.size(); i++) {
            if (StringUtils.isNullOrBlank(files.get(i))) {
                throw new IllegalArgumentException("文件集合包含 null 或 空白 的文件路径。");
            }
        }
        if (StringUtils.isNullOrBlank(zipFileName)) {
            throw new ArgumentNullException("zipFileName");
        }

        ZipOutputStream output = null;
        try {
            File zipFile = new File(System.getProperty("user.dir") + File.separator + zipFileName + ".zip");
            if (!zipFile.exists()) {
                zipFile.createNewFile();
            }
            output = new ZipOutputStream(new FileOutputStream(zipFile));
            output.setLevel(zipLevel);
            if (comment != null) {
                output.setComment(comment.trim());
            }
            byte[] buf = new byte[FileCompression.BLOCK_SIZE];
            for (String file : files) {
                ZipEntry entry = new ZipEntry(new File(file).getName());
                entry.setTime(System.currentTimeMillis());
                output.putNextEntry(entry);
                FileInputStream in = new FileInputStream(new File(file));
                int len;
                while ((len = in.read(buf)) > 0) {
                    output.write(buf, 0, len);
                }
                output.closeEntry();
                in.close();
            }
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (deleteSourceFile) {
            for (String file : files) {
                try {
                    new File(file).delete();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 压缩文件夹（压缩在文件夹同级目录）
     *
     * @param dirPath     要打包的文件夹
     * @param zipFileName 目标文件名
     * @param zipLevel    压缩级别(0-9)
     * @param deleteDir   是否删除原文件夹
     * @param comment     注解
     * @param password    密码
     */
    public static void compressDirectory(String dirPath, String zipFileName, int zipLevel, boolean deleteDir, String comment, String password) {
        if (zipLevel < 0 || zipLevel > 9) {
            throw new ArgumentOverflowException("zipLevel", "zipLevel 必须介于 0 - 9 之间。");
        }
        if (StringUtils.isNullOrBlank(zipFileName)) {
            zipFileName = dirPath.substring(dirPath.lastIndexOf(File.separator) + 1);
            zipFileName = dirPath.substring(0, dirPath.lastIndexOf(File.separator)) + File.separator + zipFileName + ".zip";
        }
        ZipOutputStream output = null;
        try {
            File zipFile = new File(zipFileName);
            if (!zipFile.exists()) {
                zipFile.createNewFile();
            }
            output = new ZipOutputStream(new FileOutputStream(zipFile));
            output.setLevel(zipLevel);
            if (comment != null) {
                output.setComment(comment.trim());
            }
            CRC32 crc = new CRC32();
            HashMap<String, Date> fileList = getAllFiles(dirPath);
            for (Map.Entry<String, Date> item : fileList.entrySet()) {
                FileInputStream fs = new FileInputStream(new File(item.getKey().toString()));
                byte[] buffer = new byte[fs.available()];
                fs.read(buffer, 0, buffer.length);
                ZipEntry entry = new ZipEntry(item.getKey().substring(dirPath.length()));
                entry.setTime(item.getValue().getTime());
                entry.setSize(fs.available());
                fs.close();
                crc.reset();
                crc.update(buffer);
                entry.setCrc(crc.getValue());
                output.putNextEntry(entry);
                output.write(buffer, 0, buffer.length);
            }
        } catch (Exception e) {

        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (deleteDir) {
            try {
                new File(dirPath).delete();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 获取所有文件
     *
     * @return
     */
    private static HashMap<String, Date> getAllFiles(String dir) throws FileNotFoundException {
        HashMap<String, Date> filesList = new HashMap<>();
        File baseFile = new File(dir);
        if (!baseFile.exists() || !baseFile.isDirectory()) {
            throw new FileNotFoundException("目录:" + baseFile.getAbsolutePath() + "没有找到!");
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                filesList.putAll(getAllFiles(file.getAbsolutePath()));
            } else {
                if (file.getName().indexOf(".") != -1) {
                    filesList.put(file.getAbsolutePath(), new Date(file.lastModified()));
                }
            }
        }
        return filesList;
    }


    /**
     * 解压缩文件
     *
     * @param zipFileName 压缩包文件名（完整路径名）
     * @param targetDir   解压缩目标路径
     * @param password    密码
     */
    public static void decompress(String zipFileName, String targetDir, String password) {
        if (zipFileName == null) {
            throw new ArgumentNullException("zipFileName");
        }
        String directoryName = targetDir;
        if (!new File(directoryName).exists()) //生成解压目录
        {
            new File(directoryName).mkdirs();
        }
        String currentDirectory = directoryName;
        byte[] data = new byte[FileCompression.BLOCK_SIZE];
        ZipEntry theEntry;
        ZipInputStream input = null;
        try {
            input = new ZipInputStream(new FileInputStream(new File(zipFileName)));
            while ((theEntry = input.getNextEntry()) != null) {
                if (theEntry.isDirectory()) {
                    if (!new File(currentDirectory + theEntry.getName()).exists())
                    {
                        new File(currentDirectory + theEntry.getName()).mkdirs();
                    }
                } else {
                    if (!"".equals(theEntry.getName())) {
                        if (theEntry.getName().contains(File.separator)) {
                            String parentDirPath = theEntry.getName().substring(0, theEntry.getName().lastIndexOf(File.separator) + 1);
                            if (!new File(parentDirPath).exists())
                            {
                                new File(currentDirectory + parentDirPath).mkdirs();
                            }
                        }
                        FileOutputStream fs = new FileOutputStream(new File(currentDirectory + theEntry.getName()));
                        try {
                            int sourceBytes;
                            do {
                                sourceBytes = input.read(data, 0, data.length);
                                fs.write(data, 0, sourceBytes);
                            }
                            while (sourceBytes > 0);
                        } finally {
                            fs.close();
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
        finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}