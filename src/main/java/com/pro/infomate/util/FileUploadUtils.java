package com.pro.infomate.util;

import com.pro.infomate.approval.dto.response.DocFileResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class FileUploadUtils {

    public static String saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {

        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String replaceFileName = fileName + "." + FilenameUtils.getExtension(multipartFile.getResource().getFilename());

        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(replaceFileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ex){
            throw new IOException("Could not save file: " + fileName, ex);
        }

        return replaceFileName;
    }

    public static boolean deleteFile(String uploadDir, String fileName) {

        boolean result = false;
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)) {
            result = true;
        }
        try {
            Path filePath = uploadPath.resolve(fileName);
            Files.delete(filePath);
            result = true;
        }catch (IOException ex){
            log.info("Could not delete file: " + fileName, ex);
            //에러관련 처리
        }

        return result;
    }

    public static List<DocFileResponse> saveMultiFiles(String uploadDir, List<MultipartFile> multipartFiles) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        List<DocFileResponse> savedFile = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {

            //필요데이터
            String originalFileName = multipartFile.getOriginalFilename();
            String type = FilenameUtils.getExtension(originalFileName);
            String replaceFileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(originalFileName);
            long size = multipartFile.getSize();

            //파일 dto에
            DocFileResponse file = new DocFileResponse();
            file.setOriginalName(originalFileName);
            file.setFileType(type);
            file.setFileName(replaceFileName);
            file.setFileSize(size);

            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(replaceFileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

                savedFile.add(file);
            } catch (IOException ex) {
                throw new IOException("Could not save file: " + originalFileName, ex);
            }
        }
        return savedFile;
    }

    public static boolean deleteMultiFiles(String uploadDir, List<DocFileResponse> files) {
        boolean allDeleted = true;

        for (DocFileResponse file : files) {
            Path filePath = Paths.get(uploadDir).resolve(file.getFileName());

            try {
                Files.delete(filePath);
            } catch (IOException ex) {
                ex.printStackTrace();
                allDeleted = false;
            }
        }
        return allDeleted;
    }
}