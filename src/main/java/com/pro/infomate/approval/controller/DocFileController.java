package com.pro.infomate.approval.controller;

import com.pro.infomate.exception.FileDownloadException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class DocFileController {


  @Value("${files.files-dir}")
  private String FILES_DIR;
  @Value("${files.files-url}")
  private String FILES_URL;

  @Operation(summary = "파일 다운로드", description = "결재시 등록한 파일다운로드가 진행됩니다.", tags = {"DocFileController"})
  @GetMapping("/download/{filename:.+}")
  public ResponseEntity<Resource> fileDownload(@PathVariable("filename") String filename) {

    try {
      System.out.println("filename = " + filename);
      Path filePath = Paths.get(FILES_DIR).resolve(filename);
      System.out.println("filePath = " + filePath);
      Resource resource = new UrlResource(filePath.toUri());
      System.out.println("resource = " + resource);

      if (resource.exists() && resource.isReadable()) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", filename);

        System.out.println("headers = " + headers);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
      } else {
        // 파일이 존재하지 않을 경우 처리
        throw new FileDownloadException(filename + "가 존재하지 않습니다" );
      }
    } catch (IOException e) {
      // 파일을 열 수 없거나 관련 예외 처리
      throw new FileDownloadException("file download ERROR : " + filename, e);
    }
  }



}
