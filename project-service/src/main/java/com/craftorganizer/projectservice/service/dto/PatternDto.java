package com.craftorganizer.projectservice.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PatternDto {
    private String id;
    private String ravelryId;

    @NotBlank(message = "Pattern name is required")
    @Size(min = 5, message = "Project name must be at least 5 characters long")
    private String patternName;

    private String pictureUrl;
    private String difficulty;

    @NotNull(message = "Pattern type is required")
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRavelryUrl() {
        return ravelryUrl;
    }

    public void setRavelryUrl(String ravelryUrl) {
        this.ravelryUrl = ravelryUrl;
    }

    public String getRavelryId() {
        return ravelryId;
    }

    public void setRavelryId(String ravelryId) {
        this.ravelryId = ravelryId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadable() {
        return downloadable;
    }

    public void setDownloadable(String downloadable) {
        this.downloadable = downloadable;
    }

    public String getNeedleName() {
        return needleName;
    }

    public void setNeedleName(String needleName) {
        this.needleName = needleName;
    }

    public String getNeedleSizeMetric() {
        return needleSizeMetric;
    }

    public void setNeedleSizeMetric(String needleSizeMetric) {
        this.needleSizeMetric = needleSizeMetric;
    }

    public String getNeedleSizeUs() {
        return needleSizeUs;
    }

    public void setNeedleSizeUs(String needleSizeUs) {
        this.needleSizeUs = needleSizeUs;
    }

    public String getYarnWpi() {
        return yarnWpi;
    }

    public void setYarnWpi(String yarnWpi) {
        this.yarnWpi = yarnWpi;
    }

    public String getYarnPly() {
        return yarnPly;
    }

    public void setYarnPly(String yarnPly) {
        this.yarnPly = yarnPly;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPatternName() {
        return patternName;
    }

    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }
}
