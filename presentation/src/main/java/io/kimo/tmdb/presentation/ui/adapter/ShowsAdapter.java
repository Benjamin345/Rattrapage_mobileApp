package io.kimo.tmdb.presentation.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.kimo.tmdb.R;
import io.kimo.tmdb.presentation.TMDb;
import io.kimo.tmdb.presentation.mvp.model.ShowModel;
import io.kimo.tmdb.presentation.ui.activity.ShowDetailActivity;

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ViewHolder> {

    private Context context;
    private List<ShowModel> data = new ArrayList<>();

    public ShowsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ShowModel> shows) {
        data = shows;
        notifyDataSetChanged();
    }

    public void clearData() {
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_show, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ShowModel showModel = data.get(i);

        viewHolder.title.setText(showModel.getName());

        if (TextUtils.isEmpty(showModel.getYearOfRelease())) {
            viewHolder.subtitle.setVisibility(View.GONE);
        } else {
            viewHolder.subtitle.setText(showModel.getYearOfRelease());
        }

        TMDb.PICASSO.load(showModel.getSmallCover()).into(viewHolder.cover);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{

        private ImageView cover;
        private TextView title, subtitle;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
            cover = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    context.startActivity(ShowDetailActivity.getCallingIntent(context, data.get(getPosition())));
                }
            }, 200); // <--- Giving time to the ripple effect finish before opening a new activity
        }
    }
}
