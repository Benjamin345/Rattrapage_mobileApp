package io.kimo.tmdb.presentation.ui.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.kimo.tmdb.R;
import io.kimo.tmdb.presentation.mvp.model.ShowModel;
import io.kimo.tmdb.presentation.mvp.presenter.SearchShowsPresenter;
import io.kimo.tmdb.presentation.mvp.view.SearchShowsView;
import io.kimo.tmdb.presentation.ui.BaseFragment;

public class SearchShowsFragment extends BaseFragment implements SearchShowsView {

    public static final int QUERY_SUBMITTED = 1;

    private SearchView searchView;
    private View mainView, loadingView;

    private Handler queryHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == QUERY_SUBMITTED) {
                presenter.performSearch(typedQuery);
            }
        }
    };

    private SearchShowsPresenter presenter;

    private String typedQuery;

    public static SearchShowsFragment newInstance() {
        return new SearchShowsFragment();
    }

    @Override
    public void instantiatePresenter() {
        presenter = new SearchShowsPresenter(this);
    }

    @Override
    public void initializePresenter() {
        presenter.createView();
    }

    @Override
    public void finalizePresenter() {
        presenter.destroyView();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_search_shows;
    }

    @Override
    public void mapGUI(View view) {
        searchView = (SearchView) view.findViewById(R.id.search_view);
        mainView = view.findViewById(R.id.results_container);
        loadingView = view.findViewById(R.id.view_loading);
    }

    @Override
    public void configureGUI() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!query.equals(typedQuery)) { // avoid a consecutive api request
                    presenter.performSearch(query);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                typedQuery = newText; // store the query

                queryHandler.removeMessages(QUERY_SUBMITTED);
                queryHandler.sendEmptyMessageDelayed(QUERY_SUBMITTED, 1000);

                return false;
            }
        });
    }

    @Override
    public void showView() {
        mainView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideView() {
        mainView.setVisibility(View.GONE);
    }

    @Override
    public void showFeedback(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void destroyItself() {
        getActivity().finish();
    }

    @Override
    public void renderShowsList(List<ShowModel> shows) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.results_container, ShowListFragment.newInstance(shows))
                .commit();
    }

    @Override
    public void removeShowsList() {
        renderShowsList(new ArrayList<ShowModel>());
    }

    @Override
    public void cleanTimer() {
        queryHandler.removeMessages(QUERY_SUBMITTED);
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showRetry(String msg) {}

    @Override
    public void hideRetry() {}

    @Override
    public void showEmpty(String msg) {}

    @Override
    public void hideEmpty() {}
}
