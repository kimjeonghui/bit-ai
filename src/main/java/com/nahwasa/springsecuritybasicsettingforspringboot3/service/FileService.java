package com.nahwasa.springsecuritybasicsettingforspringboot3.service;

import com.nahwasa.springsecuritybasicsettingforspringboot3.domain.File;
import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.DashboardDto;
import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.FileDto;
import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.PageInfoDTO;
import com.nahwasa.springsecuritybasicsettingforspringboot3.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {
    private final FileRepository fileRepository;

    @Transactional(readOnly = false)
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
        if (file.isEmpty())
            throw new Exception("파일이 존재하지 않습니다.");
        File findedFile = file.get();
        return FileDto.builder().userId(findedFile.getUserId())
                .resultPath(findedFile.getResultPath())
                .originPath(findedFile.getOriginPath())
                .result(findedFile.getResult())
                .createdAt(findedFile.getCreatedAt())
                .build();
    }

    public Map<String, Object> getFiles(int pageNum, int limit) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updatedAt");
        Pageable pageable = PageRequest.of(pageNum, limit, sort);
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

    public DashboardDto.DogsCountByBreedDto dogsCountByBreed(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate findDate = LocalDate.parse(date, formatter);
        LocalDateTime currentStartPreviousEnd = findDate.atStartOfDay();
        LocalDateTime currentEnd = findDate.plusDays(1).atStartOfDay();
        LocalDateTime previousStart = findDate.minusDays(1).atStartOfDay();

        Map<String, Long> breedMap = new HashMap<>();

        // check-point
        // 한번의 호출을 통해 그룹화하여 이후에 나누기
        List<File> currentFiles = fileRepository.findByCreatedAtBetween(currentStartPreviousEnd, currentEnd);
        List<File> previousFiles = fileRepository.findByCreatedAtBetween(previousStart, currentStartPreviousEnd);

        for (File currentFile : currentFiles) {
            List<String> result = currentFile.getResult();
            for (String breed : result)
                breedMap.put(breed, breedMap.getOrDefault(breed, 0L) + 1);
        }
        Map<Long, List<String>> sort = new TreeMap<>(Comparator.reverseOrder());
        for (String breed : breedMap.keySet()) {
            List<String> tmpList = sort.getOrDefault(breedMap.get(breed), new ArrayList<>());
            tmpList.add(breed);
            sort.put(breedMap.get(breed), tmpList);
        }
        Map<String, Long> currentDayDogsMap = new LinkedHashMap<>();

        int index = 0;
        Long otherCount = 0L;
        for (Long l : sort.keySet()) {
            List<String> breeds = sort.get(l);
            for (String breed : breeds) {
                if (index < 11)
                    currentDayDogsMap.put(breed, l);
                else
                    otherCount += l;
                index++;
            }
        }
        if (index >= 11 && otherCount != 0)
            currentDayDogsMap.put("other", otherCount);

        breedMap.clear();
        sort.clear();

        for (File previousFile : previousFiles) {
            List<String> result = previousFile.getResult();
            for (String breed : result)
                breedMap.put(breed, breedMap.getOrDefault(breed, 0L) + 1);
        }
        Map<String, Long> previousDayDogsMap = new LinkedHashMap<>();
        for (String key : currentDayDogsMap.keySet()) {
            previousDayDogsMap.put(key, breedMap.getOrDefault(key, 0L));
        }

        return DashboardDto.DogsCountByBreedDto.builder()
                .currentDayDogs(currentDayDogsMap)
                .previousDayDogs(previousDayDogsMap)
                .build();
    }
}
