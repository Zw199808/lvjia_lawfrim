package com.xinou.lawfrim.common.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    /**
     * 根据目录获取目录下文件数量
     * @param directoryName 目录
     * @return 文件数量
     */
    public static int fileCount(String directoryName) {

        int fileCount = 0;
        File d = new File(directoryName);
        File[] list = d.listFiles();
        //如果没有此目录,返回0
        if (list == null) {
            return 0;
        }
        //如果是文件,文件数+1,否则,目录数+1
        for (File aList : list) {
            if (aList.isFile()) {
                fileCount++;
            }
        }
        return fileCount;
    }

    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }

    /**
     * 得到或者创建目录
     * @param directoryName 目录
     * @return Path
     */
    public static Path getOrCreatDirectory(String directoryName){

        Path path = Paths.get(directoryName);
        //文件数量 != 0 如果目录下有文件,不初始化目录,
        int fileCount = fileCount(path.toString());

        if (fileCount == 0) {
            //初始化目录
            try {
                Files.createDirectory(path);
            }catch (IOException ignored) {
            }
        }
        return path;
    }

    /**
     * 得到或者创建文件对象
     * @param directoryName 目录名
     * @param fileName 文件名
     * @return 文件
     */
    public static File getOrCreateFile(String directoryName, String fileName) {
        String directoryPath = getOrCreatDirectory(directoryName).toUri().getPath();
        String filePath = directoryPath + fileName;
        return new File(filePath);
        //会创建文件
//        Path file = null;
//        try {
//            if (Files.notExists(Paths.get(filePath))) {
//                file = Files.createFile(Paths.get(filePath));
//            }
//        }catch (IOException ignored) {
//        }
//        return file == null ? null : file.toFile();
        //
    }

    public static ResponseEntity downFile(Path directoryName, String filename){
        try {
            //获得文件的path路径
            Path path = directoryName.resolve(filename);
            //获得resource
            Resource resource = new UrlResource(path.toUri());
            //如果文件存在或可读,返回resource
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity
                        .ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)+ "\"")
                        .body(resource);
            } else {
                System.out.println("error");
                return null;
            }
        } catch (MalformedURLException e) {
            System.out.println("error");
            return null;
        }
    }
}
