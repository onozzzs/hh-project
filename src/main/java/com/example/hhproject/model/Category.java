package com.example.hhproject.model;

public enum Category {
    POST {
        public String makeContent(String username, String targetName) {
            return username + " uploads new post";
        }
    },
    COMMENT {
        public String makeContent(String username, String targetName) {
            return username + " comments on " + targetName;
        }
    },
    LIKE {
        public String makeContent(String username, String targetName) {
            return username + " likes " + targetName;
        }
    },
    FOLLOW {
        public String makeContent(String username, String targetName) {
            return username + " follows " + targetName;
        }
    };

    public abstract String makeContent(String username, String targetName);
}
