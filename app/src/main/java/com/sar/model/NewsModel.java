package com.sar.model;

public class NewsModel {
    private String id, id_user, title, content, image, created_at;

    public NewsModel(String id, String id_user, String title, String content, String image, String created_at) {
        this.id = id;
        this.id_user = id_user;
        this.title = title;
        this.content = content;
        this.image = image;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public String getId_user() {
        return id_user;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public String getCreated_at() {
        return created_at;
    }
}
