package com.craftorganizer.projectservice.service;

import com.craftorganizer.projectservice.api.RavelryApi;
import com.craftorganizer.projectservice.mapper.PatternMapper;
import com.craftorganizer.projectservice.model.Pattern;
import com.craftorganizer.projectservice.service.dto.PatternDto;
import com.craftorganizer.projectservice.repository.PatternRepository;
import com.craftorganizer.projectservice.service.dto.PatternPreview;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class RavelryService {
    private static final String NO_DATA = "N/A";

    private final OAuth10aService oAuthService;
    private final OAuth1AccessToken oAuthToken;
    private final PatternMapper patternMapper;
    private final PatternRepository patternRepository;
    private static final Logger logger = LogManager.getLogger(RavelryService.class);

    private final String apiUrl = System.getenv("API_URL");

    @Autowired
    public RavelryService(PatternMapper patternMapper, PatternRepository patternRepository) {
        this.patternMapper = patternMapper;
        this.patternRepository = patternRepository;
        this.oAuthService = new ServiceBuilder(System.getenv("CONSUMER_KEY"))
                .apiSecret(System.getenv("CONSUMER_SECRET"))
                .callback(System.getenv("CALLBACK_URL"))
                .build(new RavelryApi());
        this.oAuthToken = new OAuth1AccessToken(System.getenv("ACCESS_TOKEN"), System.getenv("ACCESS_SECRET"));
    }

    public List<PatternPreview> searchPatterns(String query, int pageSize, int page) throws Exception {
        logger.info("Searching patterns with query: '{}', pageSize: {}, page: {}", query, pageSize, page);

        int offset = Math.max((page - 1) * pageSize, 0);

        String url = String.format("%s/patterns/search.json?query=%s&page_size=%d&offset=%d&availability=free",
                apiUrl, URLEncoder.encode(query, StandardCharsets.UTF_8), pageSize, offset);

        OAuthRequest request = new OAuthRequest(Verb.GET, url);
        oAuthService.signRequest(oAuthToken, request);
        Response response = oAuthService.execute(request);
        logger.info("Received response with status code: {}", response.getCode());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode patternsArray = mapper.readTree(response.getBody()).get("patterns");

        List<PatternPreview> resultList = new ArrayList<>();
        if (patternsArray != null && patternsArray.isArray()) {
            for (JsonNode pattern : patternsArray) {
                if (pattern.has("free") && !pattern.get("free").asBoolean()) continue;

                resultList.add(new PatternPreview(
                        getTextValue(pattern, "id"),
                        getTextValue(pattern, "name"),
                        getTextValue(pattern, "first_photo", "small_url")
                ));
            }
        }
        logger.info("Found {} patterns matching query '{}'", resultList.size(), query);
        return resultList;
    }

    public PatternDto getPatternDetails(String patternId) throws Exception {
        logger.info("Fetching details for pattern ID: {}", patternId);
        String url = apiUrl + "/patterns/" + patternId + ".json";

        OAuthRequest request = new OAuthRequest(Verb.GET, url);
        oAuthService.signRequest(oAuthToken, request);
        Response response = oAuthService.execute(request);

        logger.info("Received response with status code: {}", response.getCode());

        JsonNode patternNode = new ObjectMapper().readTree(response.getBody()).get("pattern");
        if (patternNode == null) {
            logger.warn("No pattern details found for ID: {}", patternId);
            return null;
        }

        PatternDto patternDto = new PatternDto();
        patternDto.setRavelryId(getTextValue(patternNode, "id"));
        patternDto.setPatternName(getTextValue(patternNode, "name"));
        patternDto.setPictureUrl(getTextValue(patternNode, "photos", "small_url"));
        patternDto.setDifficulty(getTextValue(patternNode, "difficulty_average"));
        patternDto.setCategories(getCategories(patternNode));
        patternDto.setNotes(getTextValue(patternNode, "notes"));
        patternDto.setYarnName(getYarnWeight(patternNode));
        patternDto.setYarnPly(getYarnPly(patternNode));
        patternDto.setYarnWpi(getYarnWpi(patternNode));

        JsonNode needleSize = patternNode.get("pattern_needle_sizes");
        if (needleSize != null && needleSize.isArray() && !needleSize.isEmpty()) {
            JsonNode firstNeedle = needleSize.get(0);
            patternDto.setNeedleName(getTextValue(firstNeedle, "name"));
            patternDto.setNeedleSizeUs(getTextValue(firstNeedle, "us"));
            patternDto.setNeedleSizeMetric(getTextValue(firstNeedle, "metric"));
            patternDto.setType(getCraftType(firstNeedle));
        }

        patternDto.setDownloadable(getTextValue(patternNode, "downloadable"));
        patternDto.setDownloadUrl(getTextValue(patternNode, "download_location", "url"));
        patternDto.setRavelryUrl("https://www.ravelry.com/patterns/library/" + getTextValue(patternNode, "permalink"));

        logger.info("Pattern details fetched successfully for ID: {}", patternId);
        return patternDto;
    }

    public Pattern savePattern(String patternId) throws Exception {
        PatternDto patternDto = getPatternDetails(patternId);
        if (patternDto == null) {
            throw new RuntimeException("Pattern details not found for ID: " + patternId);
        }

        Pattern pattern = patternMapper.toEntity(patternDto);
        pattern = patternRepository.save(pattern);
        logger.info("Pattern saved successfully with ID: {}", pattern.getId());
        return pattern;
    }

    private String getTextValue(JsonNode node, String fieldName) {
        return Optional.ofNullable(node.get(fieldName)).map(JsonNode::asText).orElse(NO_DATA);
    }

    private String getTextValue(JsonNode node, String parentField, String childField) {
        return Optional.ofNullable(node.get(parentField))
                .map(parent -> parent.get(childField))
                .map(JsonNode::asText)
                .orElse(NO_DATA);
    }

    private String getCategories(JsonNode node) {
        return Optional.ofNullable(node.get("pattern_categories"))
                .filter(JsonNode::isArray)
                .filter(arr -> !arr.isEmpty())
                .map(arr -> arr.get(0).get("name").asText(NO_DATA))
                .orElse(NO_DATA);
    }

    private String getYarnWeight(JsonNode node) {
        return Optional.ofNullable(node.get("yarn_weight"))
                .map(weight -> weight.get("name").asText(NO_DATA))
                .orElse(NO_DATA);
    }

    private String getYarnPly(JsonNode node) {
        return Optional.ofNullable(node.get("packs"))
                .filter(JsonNode::isArray)
                .filter(arr -> !arr.isEmpty())
                .map(arr -> arr.get(0).get("yarn_weight"))
                .map(weight -> weight.get("ply").asText(NO_DATA))
                .orElse(NO_DATA);
    }

    private String getYarnWpi(JsonNode node) {
        return Optional.ofNullable(node.get("packs"))
                .filter(JsonNode::isArray)
                .filter(arr -> !arr.isEmpty())
                .map(arr -> arr.get(0).get("yarn_weight"))
                .map(weight -> weight.get("wpi").asText(NO_DATA))
                .orElse(NO_DATA);
    }

    private String getCraftType(JsonNode needleNode) {
        boolean isCrochet = needleNode.has("crochet") && needleNode.get("crochet").asBoolean();
        boolean isKnitting = needleNode.has("knitting") && needleNode.get("knitting").asBoolean();
        return isCrochet && isKnitting ? "Crochet and Knitting" :
                isCrochet ? "Crochet" :
                        isKnitting ? "Knitting" : NO_DATA;
    }
}
