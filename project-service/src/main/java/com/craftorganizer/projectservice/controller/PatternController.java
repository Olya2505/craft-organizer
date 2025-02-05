package com.craftorganizer.projectservice.controller;


import com.craftorganizer.projectservice.service.PatternService;
import com.craftorganizer.projectservice.service.dto.PatternDto;
import com.craftorganizer.projectservice.swagger.DescriptionVariables;
import com.craftorganizer.projectservice.swagger.HTMLResponseMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Pattern Controller", description = DescriptionVariables.PATTERN)
@RestController
@RequestMapping("/api/patterns")
public class PatternController {

    private final PatternService patternService;

    public PatternController(PatternService patternService) {
        this.patternService = patternService;
    }

    @PostMapping
    @Operation(
            summary = "Creates a new pattern",
            description = "Creates a new pattern entry with the provided details",
            responses = {
                    @ApiResponse(responseCode = "201", description = HTMLResponseMessages.HTTP_201),
                    @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
                    @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
            }
    )
    public ResponseEntity<PatternDto> createPattern(@Valid @RequestBody PatternDto patternDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patternService.createPattern(patternDto));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieves a pattern by ID",
            description = "Fetches a pattern using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200),
                    @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
                    @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
            }
    )
    public ResponseEntity<PatternDto> getPatternById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(patternService.getPatternById(id));
    }

    @GetMapping
    @Operation(
            summary = "Retrieves all patterns",
            description = "Fetches a list of all stored patterns",
            responses = {
                    @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200),
                    @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
            }
    )
    public ResponseEntity<List<PatternDto>> getAllPatterns() {
        return ResponseEntity.status(HttpStatus.OK).body(patternService.getAllPatterns());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Updates an existing pattern",
            description = "Updates the details of an existing pattern identified by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200),
                    @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
                    @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
                    @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
            }
    )
    public ResponseEntity<PatternDto> updatePattern(@PathVariable String id,
                                                    @Valid @RequestBody PatternDto patternDto) {
        return ResponseEntity.status(HttpStatus.OK).body(patternService.updatePattern(id, patternDto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletes a pattern by ID",
            description = "Removes a pattern from the database using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = HTMLResponseMessages.HTTP_204),
                    @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
                    @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
            }
    )
    public ResponseEntity<Void> deletePattern(@PathVariable String id) {
        patternService.deletePattern(id);
        return ResponseEntity.noContent().build();
    }
}

