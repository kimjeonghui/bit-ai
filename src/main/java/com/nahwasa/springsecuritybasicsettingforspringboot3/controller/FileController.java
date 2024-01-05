package com.nahwasa.springsecuritybasicsettingforspringboot3.controller;

import com.nahwasa.springsecuritybasicsettingforspringboot3.config.AdminAuthorize;
import com.nahwasa.springsecuritybasicsettingforspringboot3.config.UserAuthorize;
import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.FileDto;
import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.MemberDto;
import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.PageInfoDTO;
import com.nahwasa.springsecuritybasicsettingforspringboot3.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/file")
public class FileController {
    private final FileService fileService;
    @PostMapping
    public HttpStatus saveFile(@RequestBody FileDto dto) {
        fileService.saveFile(dto);
        return HttpStatus.OK;
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<FileDto> getFileById(@PathVariable Long fileId) throws Exception {
        return ResponseEntity.ok(fileService.getById(fileId));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getFiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Map<String, Object> response = fileService.getFiles(page, size);
        return ResponseEntity.ok(response);
    }
}
