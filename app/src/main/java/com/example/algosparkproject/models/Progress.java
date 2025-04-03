package com.example.algosparkproject.models;

public class Progress {
    private String userId;
    private String exerciseId;
    private int score;
    private boolean completed;

    public Progress() {
    }

    public Progress(String userId, String exerciseId, int score, boolean completed) {
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.score = score;
        this.completed = completed;
    }

    public String getUserId() {
        return userId;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public int getScore() {
        return score;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
