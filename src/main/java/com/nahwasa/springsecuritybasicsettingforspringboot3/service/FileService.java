package com.nahwasa.springsecuritybasicsettingforspringboot3.service;

import com.nahwasa.springsecuritybasicsettingforspringboot3.domain.File;
import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.FileDto;
import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.PageInfoDTO;
import com.nahwasa.springsecuritybasicsettingforspringboot3.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public void saveFile(FileDto fileDto){
        File file = File.builder()
                .userId(fileDto.getUserId())
                .resultPath(fileDto.getResultPath())
                .originPath(fileDto.getOriginPath())
                .result(fileDto.getResult())
                .build();

        fileRepository.save(file);
    }

    public FileDto getById(Long fileId) throws Exception {
        Optional<File> file = fileRepository.findById(fileId);
        if(file.isEmpty())throw new Exception("파일이 존재하지 않습니다.");
        File findedFile = file.get();
        return FileDto.builder().userId(findedFile.getUserId())
                .resultPath(findedFile.getResultPath())
                .originPath(findedFile.getOriginPath())
                .result(findedFile.getResult())
                .createdAt(findedFile.getCreatedAt())
                .build();
    }

    public Map<String, Object> getFiles(int pageNum, int limit) {
        Pageable pageable = PageRequest.of(pageNum, limit);
        Page<File> page = fileRepository.findAll(pageable);

        List<FileDto> fileDtos = page.getContent().stream().map(file ->
                FileDto.builder()
                        .userId(file.getUserId())
                        .originPath(file.getOriginPath())
                        .resultPath(file.getResultPath())
                        .result(file.getResult())
                        .createdAt(file.getCreatedAt())
                        .build()
        ).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("data", fileDtos);
        response.put("pageInfo", PageInfoDTO.of(
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                (int) page.getTotalElements()
        ));

        return response;
    }

}
