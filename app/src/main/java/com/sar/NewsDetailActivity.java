package com.sar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.sar.constant.General;
import com.sar.dao.NewsDao;
import com.sar.dialog.CommentDialog;
import com.sar.roomDB.FavoriteDatabase;
import com.sar.roomDB.FavoriteList;

import java.util.ArrayList;
import java.util.List;

import static com.sar.constant.Server.IMAGE_NEWS_URL;

public class NewsDetailActivity extends AppCompatActivity implements CommentDialog.OnFragmentInteractionListener{

    private TextView tvTitle, tvContent, tvDate;
    private ImageView ivNews, ivFavorite;
    private NachoTextView nachosTag;
    private String id_berita, content, title, image, date, stag;
    private FloatingActionButton fabComment;
    private List<String> tag = new ArrayList<>();
    private Bundle bundle;

    public static FavoriteDatabase favoriteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        bundle = new Bundle();
        bundle=getIntent().getExtras();

        if(bundle!=null) {
            id_berita = bundle.getString("id_berita", null);
            title = bundle.getString("title", null);
            image = bundle.getString("image", null);
            date = bundle.getString("date", null);
            content = bundle.getString("content", null);
            iniComponent();
            Log.e(this.getClass().getName(), "onCreate Id Berita => "+id_berita);

        }

        Log.e(this.getClass().getName(), "onCreate Title => "+title);

        initToolbar();

        nachosTag.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);
        Log.e(this.getClass().getName(), "callbackNewsTag => "+image);
        Log.e(this.getClass().getName(), "callbackNewsTag Before GLIDE ");
        Glide.with(this)
                .load(IMAGE_NEWS_URL + image)
                .apply(new RequestOptions().placeholder(R.drawable.image_thumb_12)
                        .fitCenter())
                .into(ivNews);

        loadTag(id_berita);

    }

    public void loadTag(String id){
        Log.e(this.getClass().getName(), "loadTAG => START ");
        NewsDao.getNewsTag(getApplicationContext(), id, this);
    }

    public void callbackNewsTag(List<String> tag, boolean result){
        this.tag = tag;

        nachosTag.setText(tag);
        Log.e(this.getClass().getName(), "callbackNewsTag After GLIDE ");

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BeritaSAR");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Tools.setSystemBarColor(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void iniComponent() {
        tvContent = findViewById(R.id.tv_content);
        tvTitle = findViewById(R.id.tv_title);
        nachosTag = findViewById(R.id.nachos_tag);
        ivNews = findViewById(R.id.iv_news);
        tvDate = findViewById(R.id.tv_date);
        fabComment = findViewById(R.id.fab_comment);
        ivFavorite = findViewById(R.id.iv_favorite);

        favoriteDatabase = Room.databaseBuilder(getApplicationContext(),FavoriteDatabase.class,"myfavdb").allowMainThreadQueries().build();

        final String subDate = date.substring(0, 16);
        tvTitle.setText(title);
        tvContent.setText(content);
        tvDate.setText(subDate);

        if (favoriteDatabase.favoriteDao().isFavorite(Integer.parseInt(id_berita))==1) {
            ivFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            ivFavorite.setImageResource(R.drawable.ic_favorite_border_black);
        }

        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteList favoriteList=new FavoriteList();

                int id = Integer.parseInt(id_berita);
                String imageroom = image;
                String name = title;
                String subdate = subDate;
                String contentroom = content;

                favoriteList.setId(id);
                favoriteList.setImage(imageroom);
                favoriteList.setName(name);
                favoriteList.setDate(subdate);
                favoriteList.setContent(contentroom);

                if (favoriteDatabase.favoriteDao().isFavorite(id)!=1){
                    ivFavorite.setImageResource(R.drawable.ic_favorite);
                    favoriteDatabase.favoriteDao().addData(favoriteList);

                }else {
                    ivFavorite.setImageResource(R.drawable.ic_favorite_border_black);
                    favoriteDatabase.favoriteDao().delete(favoriteList);
                }
            }
        });

        fabComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogComment();
            }
        });


    }

    private void showDialogComment () {
        bundle.putString(CommentDialog.KEY_ID_NEWS, id_berita);
        final CommentDialog dialog = new CommentDialog();
        dialog.setArguments(bundle);
        dialog.setRequestCode(General.DIALOG_QUEST_CODE);
        dialog.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_AppCompat_Light_NoActionBar);
        dialog.show(getSupportFragmentManager(), this.getLocalClassName());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.e(this.getClass().getName(), "onFragmentInteraction START");
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
        Log.e(this.getClass().getName(), "onStop START");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.e(this.getClass().getName(), "onDestroy CLICKED ");
    }

    @Override
    public void onBackPressed() {
        finish();
//        moveTaskToBack(true);
    }

}
