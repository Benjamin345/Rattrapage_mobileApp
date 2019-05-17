package io.kimo.tmdb.presentation.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.kimo.tmdb.R;
import io.kimo.tmdb.presentation.mapper.ShowMapper;
import io.kimo.tmdb.presentation.mvp.model.ShowModel;
import io.kimo.tmdb.presentation.mvp.presenter.ShowListPresenter;
import io.kimo.tmdb.presentation.mvp.view.ShowListView;
import io.kimo.tmdb.presentation.ui.BaseFragment;
import io.kimo.tmdb.presentation.ui.adapter.ShowsAdapter;

public class ShowListFragment extends BaseFragment implements ShowListView {

    public static final String TAG = ShowListFragment.class.getSimpleName();
    public static final String SHOWS = TAG + ".SHOWS";

    private RecyclerView recyclerView;
    private View loadingView, retryView, emptyView;
    private TextView retryMsg, emptyMsg;
    private Button retryButton;

    private ShowsAdapter adapter;
    private ShowListPresenter presenter;

    private List<ShowModel> shows;

    public static ShowListFragment newInstance(List<ShowModel> shows) {

        ShowListFragment fragment = new ShowListFragment();

        Bundle args = new Bundle();
        args.putStringArrayList(SHOWS, (ArrayList<String>) new ShowMapper().serializeModels(shows));

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Bundle args = getArguments();

        if(args != null) {
            shows = new ShowMapper().deserializeModels(args.getStringArrayList(SHOWS));
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public void instantiatePresenter() {
        presenter = new ShowListPresenter(this, shows);
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
        return R.layout.fragment_recycler;
    }

    @Override
    public void mapGUI(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        loadingView = view.findViewById(R.id.view_loading);
        emptyView = view.findViewById(R.id.view_empty);
        retryView = view.findViewById(R.id.view_retry);

        emptyMsg = (TextView) emptyView.findViewById(R.id.text);
        retryMsg = (TextView) retryView.findViewById(R.id.text);

        retryButton = (Button) retryView.findViewById(R.id.button);
    }

    @Override
    public void configureGUI() {

        //RECYLCER VIEW CONFIGURATIONS
        adapter = new ShowsAdapter(getActivity());
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(adapter);
        //RECYLCER VIEW CONFIGURATIONS


        //RETRY BUTTON CONFIGURATIONS
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createView();
            }
        });
        //RETRY BUTTON CONFIGURATIONS

    }

    @Override
    public void renderShows(List<ShowModel> shows) {
        adapter.setData(shows);
    }

    @Override
    public void clearShows() {
        adapter.clearData();
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
    public void showRetry(String msg) {
        retryMsg.setText(msg);
        retryView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        retryView.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty(String msg) {
        emptyMsg.setText(msg);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void showView() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideView() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showFeedback(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void destroyItself() {
        getActivity().finish();
    }
}
