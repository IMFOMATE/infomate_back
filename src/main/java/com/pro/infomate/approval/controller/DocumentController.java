package com.pro.infomate.approval.controller;

import com.pro.infomate.approval.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DocumentController {

  private final DocumentService documentService;

  
}
