package com.craftorganizer.projectservice.controller;

import com.craftorganizer.projectservice.model.Pattern;
import com.craftorganizer.projectservice.service.RavelryService;
import com.craftorganizer.projectservice.service.dto.PatternDto;
import com.craftorganizer.projectservice.service.dto.PatternPreview;
import com.craftorganizer.projectservice.swagger.DescriptionVariables;
import com.craftorganizer.projectservice.swagger.HTMLResponseMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Ravelry API", description = DescriptionVariables.RAVELRY)
@RestController
@RequestMapping("/api/ravelry")
public class RavelryController {

    private final RavelryService ravelryService;

    public RavelryController(RavelryService ravelryService) {
        this.ravelryService = ravelryService;
    }

    @GetMapping
    @Operation(
            summary = "Gets a list of pattern basic info from Ravelry",
            description = "Returns a list of pattern basic info from Ravelry",
            responses = {
                    @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200),
                    @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
                    @ApiResponse(responseCode = "401", description = HTMLResponseMessages.HTTP_401),
                    @ApiResponse(responseCode = "403", description = HTMLResponseMessages.HTTP_403),
                    @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
                    @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500),
                    @ApiResponse(responseCode = "503", description = HTMLResponseMessages.HTTP_503)
            }
    )
    public ResponseEntity<List<PatternPreview>> getPatternsFromRavelry(@RequestParam String query,
                                                                       @RequestParam(defaultValue = "5") int pageSize,
                                                                       @RequestParam(defaultValue = "1") int page) throws Exception {
        List<PatternPreview> patterns = ravelryService.searchPatterns(query, pageSize, page);
        return ResponseEntity.ok(patterns);
    }

    @GetMapping("/{patternId}")
    @Operation(
            summary = "Gets pattern info from Ravelry",
            description = "Returns pattern info from Ravelry",
            responses = {
                    @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200),
                    @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
                    @ApiResponse(responseCode = "401", description = HTMLResponseMessages.HTTP_401),
                    @ApiResponse(responseCode = "403", description = HTMLResponseMessages.HTTP_403),
                    @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
                    @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500),
                    @ApiResponse(responseCode = "503", description = HTMLResponseMessages.HTTP_503)
            }
    )
    public ResponseEntity<PatternDto> getPatternByIdFromRavelry(@PathVariable String patternId) throws Exception {
        PatternDto patternDto = ravelryService.getPatternDetails(patternId);
        return ResponseEntity.ok(patternDto);
    }

    @PostMapping("/{patternId}")
    @Operation(
            summary = "Saves pattern info from Ravelry",
            description = "Fetches pattern details from Ravelry and saves them in the database",
            responses = {
                    @ApiResponse(responseCode = "201", description = HTMLResponseMessages.HTTP_201),
                    @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
                    @ApiResponse(responseCode = "401", description = HTMLResponseMessages.HTTP_401),
                    @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
                    @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500),
                    @ApiResponse(responseCode = "503", description = HTMLResponseMessages.HTTP_503)
            }
    )
    public ResponseEntity<Pattern> savePatternFromRavelry(@PathVariable String patternId) throws Exception {
        Pattern savedPattern = ravelryService.savePattern(patternId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPattern);
    }
}


