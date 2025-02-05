package com.craftorganizer.projectservice.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "patterns")
public class Pattern {
    @Id
    private String id;
    private String ravelryId;

    @NotBlank(message = "Pattern name is required")
    private String patternName;

    private String pictureUrl;
    private String difficulty;

    @NotBlank(message = "Pattern type is required")
    private String type;

    private String categories;

    @NotBlank(message = "Pattern notes are required")
    private String notes;

    private String yarnName;
    private String yarnPly;
    private String yarnWpi;
    private String needleSizeUs;
    private String needleSizeMetric;
    private String needleName;
    private String downloadable;
    private String downloadUrl;
    private String ravelryUrl;

    public Pattern() {
    }

    public Pattern(String id, String ravelryUrl, String downloadUrl, String downloadable, String needleName,
                   String needleSizeMetric, String needleSizeUs, String yarnWpi, String yarnPly, String yarnName,
                   String notes, String categories, String type, String difficulty, String pictureUrl,
                   String patternName, String ravelryId) {
        this.id = id;
        this.ravelryUrl = ravelryUrl;
        this.downloadUrl = downloadUrl;
        this.downloadable = downloadable;
        this.needleName = needleName;
        this.needleSizeMetric = needleSizeMetric;
        this.needleSizeUs = needleSizeUs;
        this.yarnWpi = yarnWpi;
        this.yarnPly = yarnPly;
        this.yarnName = yarnName;
        this.notes = notes;
        this.categories = categories;
        this.type = type;
        this.difficulty = difficulty;
        this.pictureUrl = pictureUrl;
        this.patternName = patternName;
        this.ravelryId = ravelryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRavelryId() {
        return ravelryId;
    }

    public void setRavelryId(String ravelryId) {
        this.ravelryId = ravelryId;
    }

    public String getPatternName() {
        return patternName;
    }

    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public String getYarnPly() {
        return yarnPly;
    }

    public void setYarnPly(String yarnPly) {
        this.yarnPly = yarnPly;
    }

    public String getYarnWpi() {
        return yarnWpi;
    }

    public void setYarnWpi(String yarnWpi) {
        this.yarnWpi = yarnWpi;
    }

    public String getNeedleSizeUs() {
        return needleSizeUs;
    }

    public void setNeedleSizeUs(String needleSizeUs) {
        this.needleSizeUs = needleSizeUs;
    }

    public String getNeedleSizeMetric() {
        return needleSizeMetric;
    }

    public void setNeedleSizeMetric(String needleSizeMetric) {
        this.needleSizeMetric = needleSizeMetric;
    }

    public String getNeedleName() {
        return needleName;
    }

    public void setNeedleName(String needleName) {
        this.needleName = needleName;
    }

    public String getDownloadable() {
        return downloadable;
    }

    public void setDownloadable(String downloadable) {
        this.downloadable = downloadable;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getRavelryUrl() {
        return ravelryUrl;
    }

    public void setRavelryUrl(String ravelryUrl) {
        this.ravelryUrl = ravelryUrl;
    }
}
