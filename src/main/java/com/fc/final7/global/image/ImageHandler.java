package com.fc.final7.global.image;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ImageHandler {
    final int POST_THUMBNAIL = 750;
    final int POST_CONTENT = 800;
    final int REVIEW_CONTENT = 700;
    final String LOCAL_URL = "/home/ec2-user/gotogether/images/";
    final String HTTPS_URL = "https://fc-final7.kro.kr/images/";

    /**
     * 이미지 저장 시 사용 ( 꼭 parameter 모두 소문자로 주세요 )
     * @param imageType : post / review
     * @param imageRole : thumbnail / content
     * @param id : productId / reviewId ( int )
     * @param multipartFile : save multipart image file
     * @param sequence : image sequence number ( 0 : none 썸네일 용도 )
     * @return image URL
     * @throws IOException
     */
    public String saveImage(String imageType, String imageRole, int id, int sequence, MultipartFile multipartFile) throws IOException {

        String orgImageName = multipartFile.getOriginalFilename();
        String orgImageExt = FilenameUtils.getExtension(orgImageName);

        // 이미지 저장 위치 저장
        String imagePath = LOCAL_URL;
        String imageName = Integer.toString(id);
        if (sequence == 0){
            imageName += "." + orgImageExt;
        } else {
            imageName += "-" + sequence + "." + orgImageExt;
        }

        // 기존 이미지 사이즈 확인 ( 비율 유지 )
        BufferedImage image = ImageIO.read(multipartFile.getInputStream());
        int originWidth = image.getWidth();
        int originHeight = image.getHeight();

        // 유형별 이미지 리사이징 및 이미지 저장 위치 정리
        if (imageType.equals("post") && imageRole.equals("thumbnail")) {
            image = resizeImage(image, POST_THUMBNAIL, POST_THUMBNAIL);
            imagePath += "products/" + id + "/";
        } else if (imageType.equals("post") && imageRole.equals("content")) {
            image = resizeImage(image, POST_CONTENT, POST_CONTENT * originHeight / originWidth);
            imagePath += "products/" + id + "/";
        } else if (imageType.equals("review") && imageRole.equals("content")) {
            image = resizeImage(image, REVIEW_CONTENT, REVIEW_CONTENT * originHeight / originWidth);
            imagePath += "reviews/" + id + "/";
        }

        // 이미지 저장
        ImageIO.write(image, orgImageExt, new File(imagePath + imageName));

        // 이미지 저장 경로 반환
        return HTTPS_URL + imageType +"s/" + id + "/" + imageName;

    }

    /**
     * 폴더 생성 메소드 ( 이미 존재하는 폴더는 내용 초기화 )
     * @param folderType : post / review ( 꼭 소문자 )
     * @param id : productId / reviewId ( int )
     * @return "success"
     * @throws IOException
     */
    public String createFolder(String folderType, int id) throws IOException {
        Path folderPath = Paths.get(LOCAL_URL + folderType + "s" + id + "/");

        FileSystemUtils.deleteRecursively(folderPath);
        Files.createDirectories(folderPath);

        return "success";
    }

    BufferedImage resizeImage(BufferedImage orgImage, int targetWidth, int targetHeight) {
        return Scalr.resize(orgImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_EXACT, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
    }
}
