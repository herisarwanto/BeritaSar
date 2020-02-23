package com.sar.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.sar.constant.UserConstant;
import com.sar.model.MahasiswaModel;
import com.sar.model.PegawaiModel;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "SharedPrefManager";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_CODE = "code";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TAG_USERNAME, null) != null;
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TAG_USERNAME);
        editor.apply();

//        Intent dt = new Intent(mCtx, LoginActivity.class);
//        dt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        mCtx.startActivity(dt);

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void saveMahasiswa(MahasiswaModel mahasiswaModel){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UserConstant.MHS_NIM, mahasiswaModel.getNim());
        editor.putString(UserConstant.MHS_NAME, mahasiswaModel.getName());
        editor.putString(UserConstant.USERNAME, mahasiswaModel.getUsername());
        editor.putString(UserConstant.PASSWORD, mahasiswaModel.getPassword());
        editor.putString(UserConstant.MHS_CLASS, mahasiswaModel.getMhs_class());
        editor.putString(UserConstant.MHS_PRODI, mahasiswaModel.getProdi());
        editor.putString(UserConstant.MHS_PA, mahasiswaModel.getPa());
        editor.apply();
    }

    public MahasiswaModel getDataMahasiswa() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new MahasiswaModel(
                sharedPreferences.getString(UserConstant.MHS_NIM, null),
                sharedPreferences.getString(UserConstant.MHS_NAME, null),
                sharedPreferences.getString(UserConstant.USERNAME, null),
                sharedPreferences.getString(UserConstant.PASSWORD, null),
                sharedPreferences.getString(UserConstant.MHS_CLASS, null),
                sharedPreferences.getString(UserConstant.MHS_PRODI, null),
                sharedPreferences.getString(UserConstant.MHS_PA, null)
                );
    }

    public void savePegawai(PegawaiModel pegawaiModel){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UserConstant.EMPLOYEE_ID, pegawaiModel.getId());
        editor.putString(UserConstant.NIP, pegawaiModel.getNip());
        editor.putString(UserConstant.FULL_NAME, pegawaiModel.getName());
        editor.putString(UserConstant.EMAIL, pegawaiModel.getEmail());
        editor.putString(UserConstant.USERNAME, pegawaiModel.getUsername());
        editor.putString(UserConstant.PASSWORD, pegawaiModel.getPassword());
        editor.putString(UserConstant.GENDER, pegawaiModel.getGender());
        editor.putString(UserConstant.HP, pegawaiModel.getHp());
        editor.putString(UserConstant.ADDRESS, pegawaiModel.getAddress());
        editor.putString(UserConstant.DOSEN, pegawaiModel.getDosen());
        editor.apply();
    }

    public PegawaiModel getDataPegawai() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new PegawaiModel(
                sharedPreferences.getString(UserConstant.EMPLOYEE_ID, null),
                sharedPreferences.getString(UserConstant.NIP, null),
                sharedPreferences.getString(UserConstant.FULL_NAME, null),
                sharedPreferences.getString(UserConstant.EMAIL, null),
                sharedPreferences.getString(UserConstant.USERNAME, null),
                sharedPreferences.getString(UserConstant.PASSWORD, null),
                sharedPreferences.getString(UserConstant.GENDER, null),
                sharedPreferences.getString(UserConstant.HP, null),
                sharedPreferences.getString(UserConstant.ADDRESS, null),
                sharedPreferences.getString(UserConstant.DOSEN, null)
        );
    }

    //for user identity mhs/employee/lecture
    //code 1=lecture, 2=mhs, 3=employee
    public void saveCode(String code){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_CODE, code);
        editor.apply();
    }

    public String getCode(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_CODE, null);
    }
}
