package io.kimo.tmdb.domain.entity;

import java.util.List;

public class ShowDetailEntity {

    private String homepage;
    private List<CompanyEntity> production_companies;
    private String overview;
    private String number_of_seasons;

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<CompanyEntity> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<CompanyEntity> production_companies) {
        this.production_companies = production_companies;
    }

    public String getSeasons(){return number_of_seasons;}

    public void setSeasons(String number_of_seasons){this.number_of_seasons = number_of_seasons;}
}
