package com.example.hhproject.model;

public enum NotificationType {
    FOLLOW {
        public String makeMessage(String followerId) {
            return followerId + " follows you";
        }
    };

    public abstract String makeMessage(String followerId);
}
