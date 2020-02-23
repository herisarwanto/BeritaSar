package com.sar.home;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sar.NewsDetailActivity;
import com.sar.R;
import com.sar.adapter.HomeAdapter;
import com.sar.dao.NewsDao;
import com.sar.model.NewsModel;
import com.sar.tools.SharedPrefManager;
import com.sar.tools.Toolkit;
import com.sar.tools.interfaces.OnBackPressed;
import com.sar.tools.interfaces.RvListener;

import java.util.ArrayList;
import java.util.List;

import static com.sar.constant.Server.IMAGE_NEWS_URL;

public class HomeFragment extends Fragment implements OnBackPressed {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public HomeFragment() {
        // Required empty public constructor
    }

    private ImageView ivNews;
    private TextView tvTittle, tvDate;
    private RecyclerView rvNews;
    private String id_user, id_berita;
    private String content, title, date, image;
    private List<NewsModel> newsList;
    private List<NewsModel> newsModelList;

    private HomeAdapter homeAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private ActionBar actionBar;
    
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ivNews = view.findViewById(R.id.iv_news);
        tvTittle = view.findViewById(R.id.tv_title);
        tvDate = view.findViewById(R.id.tv_date);
        rvNews = view.findViewById(R.id.rv_news);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        toolbar = view.findViewById(R.id.toolbar);

        id_user = SharedPrefManager.getInstance(getContext()).getCode();
        newsModelList = new ArrayList<>();
        newsList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvNews.setLayoutManager(linearLayoutManager);
        rvNews.setHasFixedSize(true);

        initToolbar();
        loadAllNews();

        String data = SharedPrefManager.getInstance(getContext()).getCode();
        FirebaseMessaging.getInstance().subscribeToTopic(data);
//        Log.e(this.getClass().getName(), "onCreate Image => ");

        return view;
    }

    public void initToolbar(){
        //Toolbar
        toolbar.setTitle("BeritaSAR");
        toolbar.setTitleTextColor(Color.WHITE);
        }

        public void showHeadlineNews(){
//            String subDate = date.substring(0,10);
            //ambil index nol untuk headline
            String thumb = newsList.get(0).getImage();
            tvTittle.setText(newsList.get(0).getTitle());
            tvDate.setText(newsList.get(0).getCreated_at());

            Glide.with(this)
                    .load(IMAGE_NEWS_URL + thumb)
                    .apply(new RequestOptions().placeholder(R.drawable.image_thumb_12)
                            .fitCenter())
                    .into(ivNews);

            tvTittle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.e(this.getClass().getName(), "onClick title => "+title);
                    Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                    intent.putExtra("id_berita", newsList.get(0).getId());
                    intent.putExtra("image", newsList.get(0).getImage());
                    intent.putExtra("title", newsList.get(0).getTitle());
                    intent.putExtra("date", newsList.get(0).getCreated_at());
                    intent.putExtra("content", newsList.get(0).getContent());
                    startActivity(intent);
                }
            });

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadAllNews();
                }
            });
        }



    private void showNews(){
      Log.e(this.getClass().getName(), " showNews START ");

        try {

            homeAdapter = new HomeAdapter(getContext(), newsModelList);
            rvNews.setAdapter(homeAdapter);

            rvNews.addOnItemTouchListener(new Toolkit.RecyclerTouchListener(getContext(), rvNews, new RvListener() {
                @Override
                public void onClick(View view, final int position) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(this.getClass().getName(), "rv onClick START" );

                            try {
                                id_berita = newsModelList.get(position).getId();
                                image = newsModelList.get(position).getImage();
                                title = newsModelList.get(position).getTitle();
                                date = newsModelList.get(position).getCreated_at();
                                content = newsModelList.get(position).getContent();

                                Log.i(this.getClass().getName(), "rv onClick id berita => "+id_berita);

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


        } catch (Exception e){
            e.printStackTrace();
        }

        Log.e(this.getClass().getName(), " showNews STOP ");

    }

    public void loadAllNews(){
        Log.e(this.getClass().getName(), "loadAllNews START");
        swipeProgress(true);
        NewsDao.getHeadlineNews(getContext(), id_user, this);
        NewsDao.getAllNews(getContext(), id_user, this);
    }

    public void callbackHeadlineNews(List<NewsModel> list, boolean result){
        if(result==true){
            Log.e(this.getClass().getName(), "callbackHeadlineNews START");

            this.newsList = list;
            showHeadlineNews();

//            for (int i=1; i<list.size(); i++){
//                id_berita = list.get(i).getId();
//                title = list.get(i).getTitle();
//                date = newsModels.get(i).getCreated_at();
//                image = newsModels.get(i).getImage();
//                content = newsModels.get(i).getContent();
//                showNews();
//            }

        }
    }

    public void callbackAllNews(List<NewsModel> newsModels, boolean result){
        swipeProgress(false);
        Log.e(this.getClass().getName(), "callbackAllNews result => "+result);

        if(result==true){
            this.newsModelList = newsModels;

            Log.e(this.getClass().getName(), "callbackAllNews True");

            for (int i=0; i<newsModels.size(); i++){
                id_berita = newsModels.get(i).getId();
                title = newsModels.get(i).getTitle();
                date = newsModels.get(i).getCreated_at();
                image = newsModels.get(i).getImage();
                content = newsModels.get(i).getContent();
            }

            showNews();
        }
        Log.e(this.getClass().getName(), "callbackAllNews STOP");
    }

    private void swipeProgress(final boolean show) {
        if (!show) {
            swipeRefreshLayout.setRefreshing(show);
            return;
        }
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(show);
            }
        });
    }

    @Override
    public void onBackPressed(){
        getActivity().getSupportFragmentManager().popBackStack();
    }

}