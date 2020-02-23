package com.sar.profile;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.sar.LoginActivity;
import com.sar.R;
import com.sar.tools.SharedPrefManager;
import com.sar.tools.Toolkit;
import com.sar.tools.interfaces.AlertListener;
import com.sar.tools.interfaces.OnBackPressed;

import java.util.Objects;

public class ProfileFragment extends Fragment implements OnBackPressed {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tvUsername, tvName, tvId, tvIdTitle, tvAddressTitle, tvAddress;
    private RelativeLayout rlLogout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        rlLogout = view.findViewById(R.id.rl_logout);
        tvName = view.findViewById(R.id.tv_name);
        tvId = view.findViewById(R.id.tv_id);
        tvUsername = view.findViewById(R.id.tv_username);
        tvIdTitle = view.findViewById(R.id.tv_id_title);
        tvIdTitle = view.findViewById(R.id.tv_id_title);
        tvAddressTitle = view.findViewById(R.id.tv_address_title);
        tvAddress = view.findViewById(R.id.tv_address);

        filterUser();

        rlLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout();
            }
        });

        return view;

    }

    private void filterUser(){
        String TAG_NIM = "NPM";
        String TAG_NIP = "NIP";
        String TAG_CLASS = "Kelas";
        String TAG_ADDRESS = "Alamat";

        String code = SharedPrefManager.getInstance(getContext()).getCode();

        if (code.equals("2")){
            tvUsername.setText("@"+SharedPrefManager.getInstance(getContext()).getDataMahasiswa().getUsername());
            tvName.setText(SharedPrefManager.getInstance(getContext()).getDataMahasiswa().getName());
            tvId.setText(SharedPrefManager.getInstance(getContext()).getDataMahasiswa().getNim());
            tvIdTitle.setText(TAG_NIM);
            tvAddressTitle.setText(TAG_CLASS);
            tvAddress.setText(SharedPrefManager.getInstance(getContext()).getDataMahasiswa().getMhs_class());
        } else {
            tvUsername.setText("@"+SharedPrefManager.getInstance(getContext()).getDataPegawai().getUsername());
            tvName.setText(SharedPrefManager.getInstance(getContext()).getDataPegawai().getName());
            tvId.setText(SharedPrefManager.getInstance(getContext()).getDataPegawai().getNip());
            tvIdTitle.setText(TAG_NIP);
            tvAddressTitle.setText(TAG_ADDRESS);
            tvAddress.setText(SharedPrefManager.getInstance(getContext()).getDataPegawai().getAddress());
            Log.e(this.getClass().getName(), "filterUser nip => "+SharedPrefManager.getInstance(getContext()).getDataPegawai().getNip());

        }
    }

    private void dialogLogout() {
        Toolkit.alertDialog(getContext(), getString(R.string.txt_info), "Apakah anda akan logout akun?", "Ya", "Tidak", false,
                new AlertListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void PositiveMethod(final DialogInterface dialog, final int id) {
                        SharedPrefManager.getInstance(getContext()).logout();
                        Objects.requireNonNull(getActivity()).finish();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void NegativeMethod(DialogInterface dialog, int id) {
                        //action
                    }
                });
    }

    @Override
    public void onBackPressed(){
        getActivity().getSupportFragmentManager().popBackStack();
    }
}