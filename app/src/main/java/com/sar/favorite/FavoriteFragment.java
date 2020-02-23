package com.sar.favorite;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sar.MainActivity;
import com.sar.NewsDetailActivity;
import com.sar.R;
import com.sar.adapter.FavoriteAdapter;
import com.sar.roomDB.FavoriteList;
import com.sar.tools.Toolkit;
import com.sar.tools.interfaces.OnBackPressed;
import com.sar.tools.interfaces.RvListener;

import java.util.List;

public class FavoriteFragment extends Fragment implements OnBackPressed {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rvNews;
    private FavoriteAdapter favoriteAdapter;
    private ScrollView scrollView;
    private TextView tvEmpty;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    List<FavoriteList> favoriteLists;

    public FavoriteFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvNews = view.findViewById(R.id.rv_news);
        progressBar = view.findViewById(R.id.pb_favorite);
        tvEmpty = view.findViewById(R.id.tv_empty);
        scrollView = view.findViewById(R.id.scrollView1);
        toolbar = view.findViewById(R.id.toolbar);

        rvNews.setHasFixedSize(true);
        rvNews.setLayoutManager(new LinearLayoutManager(getContext()));

        initToolbar();
        getFavData();

        rvNews.addOnItemTouchListener(new Toolkit.RecyclerTouchListener(getContext(), rvNews, new RvListener() {
            @Override
            public void onClick(View view, final int position) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(this.getClass().getName(), "rv onClick START" );

                        try {
                            String id_berita = String.valueOf(favoriteLists.get(position).getId());
                            String image = favoriteLists.get(position).getImage();
                            String title = favoriteLists.get(position).getName();
                            String date = favoriteLists.get(position).getDate();
                            String content = favoriteLists.get(position).getContent();

                            Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                            intent.putExtra("id_berita", id_berita);
                            intent.putExtra("image", image);
                            intent.putExtra("title", title);
                            intent.putExtra("date", date);
                            intent.putExtra("content", content);
                            startActivity(intent);

                            Log.e(this.getClass().getName(), "rv onClick : id berita => " + id_berita);
                        } catch (Exception e){
                            e.printStackTrace();
                        }

                        Log.e(this.getClass().getName(), "rv onClick END" );

                    }
                }, 400);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }

    private void getFavData() {
        try {
            favoriteLists = MainActivity.favoriteDatabase.favoriteDao().getFavoriteData();

            if (favoriteLists.size()>0) {
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                favoriteAdapter = new FavoriteAdapter(favoriteLists, getContext());
                rvNews.setAdapter(favoriteAdapter);
            } else {
                progressBar.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);

            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initToolbar(){
        //Toolbar
        toolbar.setTitle("Favorit");
        toolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    public void onBackPressed(){
        getActivity().getSupportFragmentManager().popBackStack();
    }

}