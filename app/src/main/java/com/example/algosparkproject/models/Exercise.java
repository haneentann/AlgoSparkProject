package com.example.algosparkproject.models;

public class Exercise {
    private String exerciseId;
    private String title;
    private String description;
    private String codeTemplate;
    private String expectedOutput;

    public Exercise() {
    }

    public Exercise(String exerciseId, String title, String description, String codeTemplate, String expectedOutput) {
        this.exerciseId = exerciseId;
        this.title = title;
        this.description = description;
        this.codeTemplate = codeTemplate;
        this.expectedOutput = expectedOutput;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCodeTemplate() {
        return codeTemplate;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCodeTemplate(String codeTemplate) {
        this.codeTemplate = codeTemplate;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }
}