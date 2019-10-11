package com.changyue.o2o.util;

import com.changyue.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-14 22:28
 */
public class ImageUtil {

    private static String baseFile = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random RANDOM = new Random();

    public static String generateThumbnails(ImageHolder thumbnail, String targetAddr) throws IOException {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtils.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getImage()).size(200, 200)
                    .outputQuality(.9f)
                    .toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return relativeAddr;
    }

    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) throws IOException {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtils.getImgBasePath() + relativeAddr);

        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 640)
                    .watermark(Positions.BOTTOM_CENTER, ImageIO.read(new File(baseFile + "/watermark.png")), 0.25f)
                    .outputQuality(.9f)
                    .toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * @param targerAddr
     */
    private static void makeDirPath(String targerAddr) throws IOException {
        String realFilePath = PathUtils.getImgBasePath() + targerAddr;
        File dirPath = new File(realFilePath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获得文件的扩展名
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }


    /**
     * 随机生成文件名
     */
    private static String getRandomFileName() {
        int randomNum = RANDOM.nextInt(89999) + 10000;
        String format = SIMPLE_DATE_FORMAT.format(new Date());
        return format + randomNum;
    }

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("src/main/resources/1.jpeg"))
                .size(1200, 1200)
                .watermark(Positions.BOTTOM_CENTER, ImageIO.read(new File(baseFile + "/unsplash.jpg")), .25f)
                .outputQuality(.8f)
                .toFile("src/main/resources/new.jpg");
    }


    /**
     * 删除文件以及目录路径
     */
    public static void deleteImgFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtils.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (File file : files) {
                    file.delete();
                }
            }
            fileOrPath.delete();
        }

    }
}
