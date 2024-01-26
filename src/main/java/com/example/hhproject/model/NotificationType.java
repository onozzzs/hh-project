package com.example.hhproject.model;

public enum NotificationType {
    FOLLOW {
        public String makeMessage(String sender, String receiver) {
            return sender + " follows " + receiver;
        }
    },
    COMMENT {
        public String makeMessage(String sender, String receiver){
            return sender + " comments " + receiver;
        }
    },
    Like {
        public String makeMessage(String sender, String receiver) {
            return sender + " likes " + receiver;
        }
    };

    public abstract String makeMessage(String sender, String receiver);
}
