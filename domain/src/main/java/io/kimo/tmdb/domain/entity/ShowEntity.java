package io.kimo.tmdb.domain.entity;

public class ShowEntity {
    private int id;
    private String release_date;
    private String poster_path;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }
    public String getTitle() {
        return name;
    }

    public void setTitle(String name) {
        this.name = name;
    }
}
