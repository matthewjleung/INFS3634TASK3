package com.example.cat_app_task3;

public class CatDetailModel {

    private CatModel[] breeds;
    private String id;

    private String url;

    public CatModel[] getBreeds() {
        return breeds;
    }

    public void setBreeds(CatModel[] breeds) {
        this.breeds = breeds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
