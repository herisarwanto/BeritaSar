package com.sar.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleService;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sar.LoginActivity;
import com.sar.NewsDetailActivity;
import com.sar.constant.CommentConstant;
import com.sar.constant.UserConstant;
import com.sar.dialog.CommentDialog;
import com.sar.home.HomeFragment;
import com.sar.model.CommentModel;
import com.sar.model.MahasiswaModel;
import com.sar.model.NewsModel;
import com.sar.model.PegawaiModel;
import com.sar.tools.SharedPrefManager;
import com.sar.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sar.constant.Server.ALL_NEWS_URL;
import static com.sar.constant.Server.COMMENT_PER_NEWS_URL;
import static com.sar.constant.Server.LOGIN_URL;
import static com.sar.constant.Server.NEWS_COMMENT_URL;
import static com.sar.constant.Server.NEWS_TAG_URL;

public abstract class NewsDao {

    private static String TAG_NEWS = "news";
    private static String TAG_TG = "tags";
    private static String TAG_ID = "id_berita";
    private static String TAG_IDUSER = "id_user";
    private static String TAG_TITLE = "judul";
    private static String TAG_CONTENT = "isi_berita";
    private static String TAG_TAGS = "tag";
    private static String TAG_IMAGE = "gambar";
    private static String TAG_CREATEDAT = "dibuat_tanggal";

    public static void getAllNews(final Context context, final String id_user, final HomeFragment fragment) {
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, ALL_NEWS_URL+"?id_user="+id_user,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {

                        List<NewsModel> newsModel = new ArrayList< >();

                        try {

                            JSONObject obj = new JSONObject(response);

                            final String pesan = obj.getString("status");

                            if (pesan.equals("200")) {

                                JSONArray jsonArray = obj.getJSONArray(TAG_NEWS);

                                for (int i = 1; i < jsonArray.length(); i++) { //jumlah looping

                                    //getting product object from json array
                                    JSONObject hastag = jsonArray.getJSONObject(i);

                                    newsModel.add(new NewsModel(
                                            hastag.getString(TAG_ID),
                                            hastag.getString(TAG_IDUSER),
                                            hastag.getString(TAG_TITLE),
                                            hastag.getString(TAG_CONTENT),
                                            hastag.getString(TAG_IMAGE),
                                            hastag.getString(TAG_CREATEDAT)
                                    ));

                                    Log.e(this.getClass().getName(), "LISTTT => "+newsModel.size());
                                    fragment.callbackAllNews(newsModel, true);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(this.getClass().getName(), "onErrorResponse => "+error);
//                        fragment.callbackAllNews(null, false);
                    }
                }){
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void getHeadlineNews(final Context context, final String id_user, final HomeFragment fragment) {
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, ALL_NEWS_URL+"?id_user="+id_user,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {

                        List<NewsModel> newsModel = new ArrayList< >();

                        try {

                            JSONObject obj = new JSONObject(response);

                            final String pesan = obj.getString("status");

                            if (pesan.equals("200")) {

                                JSONArray jsonArray = obj.getJSONArray(TAG_NEWS);

                                for (int i = 0; i < jsonArray.length(); i++) { //jumlah looping

                                    //getting product object from json array
                                    JSONObject hastag = jsonArray.getJSONObject(i);

                                    newsModel.add(new NewsModel(
                                            hastag.getString(TAG_ID),
                                            hastag.getString(TAG_IDUSER),
                                            hastag.getString(TAG_TITLE),
                                            hastag.getString(TAG_CONTENT),
                                            hastag.getString(TAG_IMAGE),
                                            hastag.getString(TAG_CREATEDAT)
                                    ));

                                    fragment.callbackHeadlineNews(newsModel, true);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(this.getClass().getName(), "onErrorResponse => "+error);
//                        fragment.callbackAllNews(null, false);
                    }
                }){
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void getNewsTag(final Context context, final String id_berita, final NewsDetailActivity activity) {
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, NEWS_TAG_URL+"?id_berita="+id_berita,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        Log.e(this.getClass().getName(), "onResponse => START");

                        List<String> tag = new ArrayList<>();

                        try {

                            Log.e(this.getClass().getName(), "TRY => START");

                            JSONObject obj = new JSONObject(response);

                            final String pesan = obj.getString("status");

                            Log.e(this.getClass().getName(), "Pesan => "+pesan);
                            Log.e(this.getClass().getName(), "Id berita => "+id_berita);

                            if (pesan.equals("200")) {

                                JSONArray jsonArray = obj.getJSONArray(TAG_TG);

                                for (int i = 0; i < jsonArray.length(); i++) { //jumlah looping

                                    //getting product object from json array
                                    JSONObject hastag = jsonArray.getJSONObject(i);

                                    tag.add(hastag.getString("tag"));

                                    Log.e(this.getClass().getName(), "IF TAG => "+tag);
                                    activity.callbackNewsTag(tag, true);

                                }
                            }

                            Log.e(this.getClass().getName(), "onResponse => START");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(this.getClass().getName(), "onErrorResponse => "+error);
//                        fragment.callbackAllNews(null, false);
                    }
                }){
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void getCommentPerNews(final Context context, final String id_berita, final CommentDialog dialog) {
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, COMMENT_PER_NEWS_URL+"?id_berita="+id_berita,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        Log.e(this.getClass().getName(), "onResponse => START");

                        List<CommentModel> commentModelList = new ArrayList<>();

                        try {

                            Log.e(this.getClass().getName(), "TRY => START");

                            JSONObject obj = new JSONObject(response);

                            final String pesan = obj.getString("status");

                            if (pesan.equals("200")) {

                                JSONArray jsonArray = obj.getJSONArray("komentar");

                                for (int i = 0; i < jsonArray.length(); i++) { //jumlah looping

                                    //getting product object from json array
                                    JSONObject cmt = jsonArray.getJSONObject(i);

                                    commentModelList.add(new CommentModel(
                                            cmt.getString(CommentConstant.COMMENT_ID),
                                            cmt.getString(CommentConstant.NEWS_ID),
                                            cmt.getString(CommentConstant.STATUS),
                                            cmt.getString(CommentConstant.USER_ID),
                                            cmt.getString(CommentConstant.USERNAME),
                                            cmt.getString(CommentConstant.NAME),
                                            cmt.getString(CommentConstant.COMMENT),
                                            cmt.getString(CommentConstant.CREATED_AT)
                                    ));

                                    Log.e(this.getClass().getName(), "Comment => "+commentModelList.size());
                                    dialog.callbackComments(commentModelList);
                                }
                            }

                            Log.e(this.getClass().getName(), "onResponse => END");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(this.getClass().getName(), "onErrorResponse => "+error);
//                        fragment.callbackAllNews(null, false);
                    }
                }){
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void commentUser(final Context context, final String news_id, final String status, final String user_id,
                                   final String username, final String name, final String comment, final CommentDialog dialog) {

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, NEWS_COMMENT_URL,
                new Response.Listener<String>() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {

                        Log.e("LoginDao", "onResponse => "+response);

                        dialog.calbackComment(true);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(this.getClass().getName(), "onErrorResponse => "+error);
                    }
                }){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_berita", news_id);
                params.put("status", status);
                params.put("id_user", user_id);
                params.put("username", username);
                params.put("nama", name);
                params.put("komentar", comment);

                return params;
            }

        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }
}
