package com.sar.model;

public class CommentModel {
    private String id_comment, id_news, status, id_user, username, nama, comment, created_at;

    public CommentModel(String id_comment, String id_news, String status, String id_user, String username, String nama, String comment, String created_at) {
        this.id_comment = id_comment;
        this.id_news = id_news;
        this.status = status;
        this.id_user = id_user;
        this.username = username;
        this.nama = nama;
        this.comment = comment;
        this.created_at = created_at;
    }

    public String getId_comment() {
        return id_comment;
    }

    public String getId_news() {
        return id_news;
    }

    public String getStatus() {
        return status;
    }

    public String getId_user() {
        return id_user;
    }

    public String getUsername() {
        return username;
    }

    public String getNama() {
        return nama;
    }

    public String getComment() {
        return comment;
    }

    public String getCreated_at() {
        return created_at;
    }
}
