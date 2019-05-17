package io.kimo.tmdb.presentation.mapper;


import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import io.kimo.tmdb.domain.BaseMapper;
import io.kimo.tmdb.domain.entity.CompanyEntity;
import io.kimo.tmdb.domain.entity.ShowDetailEntity;
import io.kimo.tmdb.domain.entity.ShowEntity;
import io.kimo.tmdb.presentation.Utils;
import io.kimo.tmdb.presentation.mvp.model.ShowModel;

public class ShowMapper extends BaseMapper<ShowEntity, ShowModel> {

    @Override
    public ShowModel toModel(ShowEntity entity) {
        ShowModel showModel = new ShowModel();

        showModel.setId(entity.getId());
        showModel.setName(entity.getTitle());
        showModel.setYearOfRelease(Utils.getYearFromServerDate(entity.getRelease_date()));

        if(!TextUtils.isEmpty(entity.getPoster_path())) {
            showModel.setSmallCover(Utils.buildCompleteImageURL(entity.getPoster_path(), "w154"));
            showModel.setBigCover(Utils.buildCompleteImageURL(entity.getPoster_path(), "original"));
        }

        return showModel;
    }

    @Override
    public ShowModel deserializeModel(String serializedModel) {
        return gson.fromJson(serializedModel, ShowModel.class);
    }

    public ShowModel addDetails(ShowModel receiver, ShowDetailEntity detailsToBeInserted) {

        receiver.setHomepage(detailsToBeInserted.getHomepage());

        List<String> companies = new ArrayList<>();
        for(CompanyEntity company : detailsToBeInserted.getProduction_companies()) {
            companies.add(company.getName());
        }
        //remove the first and the last character
        receiver.setCompanies(companies.toString().substring(1,companies.toString().length()-1));

        receiver.setSeasons(detailsToBeInserted.getSeasons());
        receiver.setOverview(detailsToBeInserted.getOverview());

        return receiver;
    }
}
