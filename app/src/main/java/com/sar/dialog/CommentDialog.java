package com.sar.dialog;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sar.R;
import com.sar.adapter.CommentAdapter;
import com.sar.dao.NewsDao;
import com.sar.model.CommentModel;
import com.sar.tools.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class CommentDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String KEY_ID_NEWS = "id_berita";

    private RecyclerView rvMessage;
    private EditText etMessage;
    private ImageButton ibSend;
    private List<CommentModel> commentModelList;
    private int request_code = 0;
    private String news_id, status, user_id, username, name, comment, code;
    private Bundle bundle;
    private CommentAdapter commentAdapter;

    private OnFragmentInteractionListener mListener;

    public CommentDialog() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CommentDialog newInstance(String param1, String param2) {
        CommentDialog fragment = new CommentDialog();
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
        View view = inflater.inflate(R.layout.dialog_comment, container, false);

        rvMessage = view.findViewById(R.id.rv_message);
        etMessage = view.findViewById(R.id.et_message);
        ibSend = view.findViewById(R.id.ib_send);

        commentModelList = new ArrayList<>();
        bundle = new Bundle();
        bundle = getArguments();

        if(bundle!=null) {
            news_id = bundle.getString(KEY_ID_NEWS);
        }

        checkStatus();
        loadComments();

        //Toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Komentar");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitleTextColor(Color.BLACK);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ibSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiData();
            }
        });

        return view;
    }

    public void checkStatus(){
        code = SharedPrefManager.getInstance(getContext()).getCode();
        if (code.equals("1")){
            status = "Dosen";
            user_id = SharedPrefManager.getInstance(getContext()).getDataPegawai().getId();
            username = SharedPrefManager.getInstance(getContext()).getDataPegawai().getUsername();
            name = SharedPrefManager.getInstance(getContext()).getDataPegawai().getName();
        } else if(code.equals("2")){
            status = "Mahasiswa";
            user_id = SharedPrefManager.getInstance(getContext()).getDataMahasiswa().getNim();
            username = SharedPrefManager.getInstance(getContext()).getDataMahasiswa().getUsername();
            name = SharedPrefManager.getInstance(getContext()).getDataMahasiswa().getName();
        } else {
            status = "Pegawai";
            user_id = SharedPrefManager.getInstance(getContext()).getDataPegawai().getId();
            username = SharedPrefManager.getInstance(getContext()).getDataPegawai().getUsername();
            name = SharedPrefManager.getInstance(getContext()).getDataPegawai().getName();
        }
    }

    public void loadComments(){
        Log.e(this.getClass().getName(), "loadComment => "+news_id);

        NewsDao.getCommentPerNews(getContext(), news_id, this);
    }

    public void callbackComments(List<CommentModel> list){
        this.commentModelList = list;

        commentAdapter = new CommentAdapter(getContext(), commentModelList);

        rvMessage.setHasFixedSize(true);
        rvMessage.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMessage.setAdapter(commentAdapter);

        Log.e(this.getClass().getName(), "callback Comment => "+list.size());
    }

    public void validasiData(){
        String message = etMessage.getText().toString();

        if (TextUtils.isEmpty(message)) {
              Toast.makeText(getActivity(), "Tidak ada komentar!" , Toast.LENGTH_LONG).show();
            return;
        } else {
            try {
                Log.e(this.getClass().getName(), "validasiData => Try");
                Log.e(this.getClass().getName(), "validasiData => id berita - "+news_id);
                Log.e(this.getClass().getName(), "validasiData => status - "+status);
                Log.e(this.getClass().getName(), "validasiData => id user - "+user_id);
                Log.e(this.getClass().getName(), "validasiData => username - "+username);
                Log.e(this.getClass().getName(), "validasiData => name - "+name);
                Log.e(this.getClass().getName(), "validasiData => comment - "+message);

                NewsDao.commentUser(getContext(), news_id, status, user_id, username, name, message, this);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void calbackComment(boolean result){
        if(result) {
            loadComments();
            etMessage.setText(null);
            Toast.makeText(getActivity(), "Berhasil dikirim", Toast.LENGTH_LONG).show();
        } else {
            loadComments();
            Toast.makeText(getActivity(), "Gagal mengirim", Toast.LENGTH_LONG).show();
        }
    }

    public void setRequestCode(int request_code) {
        this.request_code = request_code;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
