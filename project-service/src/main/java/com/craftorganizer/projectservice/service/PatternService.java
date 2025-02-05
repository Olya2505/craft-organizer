package com.craftorganizer.projectservice.service;

import com.craftorganizer.projectservice.mapper.PatternMapper;
import com.craftorganizer.projectservice.model.Pattern;
import com.craftorganizer.projectservice.repository.PatternRepository;
import com.craftorganizer.projectservice.service.dto.PatternDto;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Log4j2
@Service
public class PatternService {

    private final PatternRepository patternRepository;
    private final PatternMapper patternMapper;

    private static final Logger logger = LogManager.getLogger(PatternService.class);

    public PatternService(PatternRepository patternRepository, PatternMapper patternMapper) {
        this.patternRepository = patternRepository;
        this.patternMapper = patternMapper;
    }

    public PatternDto createPattern(PatternDto patternDto) {
        Pattern savedPattern = patternRepository.save(patternMapper.toEntity(patternDto));
        logger.info("New pattern created: {}", patternDto.getPatternName());
        return patternMapper.toDto(savedPattern);
    }

    public PatternDto getPatternById(String id) {
        return patternRepository.findById(id)
                .map(patternMapper::toDto)
                .orElseThrow(() -> {
                    logger.warn("Pattern not found with ID: {}", id);
                    return new NoSuchElementException("Pattern not found with ID: " + id);
                });
    }

    public List<PatternDto> getAllPatterns() {
        return patternRepository.findAll().stream()
                .map(patternMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PatternDto updatePattern(String id, PatternDto patternDto) {
        Pattern existingPattern = patternRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pattern not found with ID: " + id));

        patternMapper.updateEntity(patternDto, existingPattern);
        Pattern updatedPattern = patternRepository.save(existingPattern);

        logger.info("Pattern updated successfully with ID: {}", id);
        return patternMapper.toDto(updatedPattern);
    }

    @Transactional
    public void deletePattern(String id) {
        Pattern pattern = patternRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pattern not found with ID: " + id));

        patternRepository.delete(pattern);
        logger.info("Pattern deleted successfully with ID: {}", id);
    }
}

