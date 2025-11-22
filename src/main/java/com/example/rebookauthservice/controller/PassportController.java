package com.example.rebookauthservice.controller;

import com.example.rebookauthservice.service.PassportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("passports")
@RequiredArgsConstructor
public class PassportController {
    private final PassportService passportService;
    // 패스포트 생성

}
