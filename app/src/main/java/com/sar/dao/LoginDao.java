package com.sar.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sar.LoginActivity;
import com.sar.constant.UserConstant;
import com.sar.model.MahasiswaModel;
import com.sar.model.PegawaiModel;
import com.sar.tools.SharedPrefManager;
import com.sar.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.sar.constant.Server.LOGIN_URL;

public abstract class LoginDao {

    public static void loginUser(final Context context, final String username, final String password, final String status, final LoginActivity activity) {

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL + "?username=" +
                username + "&password=" + password+ "&status=" + status,
                new Response.Listener<String>() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {

                        Log.e("LoginDao", "onResponse => "+response);

                        try {
                            //converting the string to json array object
                            JSONObject obj = new JSONObject(response);

                            final int code = obj.getInt("status");
                            final String message = obj.getString("message");

                            Log.e("LoginDao", "befor if");

                            if (code == 200) {

                                JSONObject user = obj.getJSONObject("user");

                                if (status.equals("Mahasiswa")) {

                                    MahasiswaModel dataModel = new MahasiswaModel(
                                            user.getString(UserConstant.MHS_NIM),
                                            user.getString(UserConstant.MHS_NAME),
                                            user.getString(UserConstant.USERNAME),
                                            user.getString(UserConstant.PASSWORD),
                                            user.getString(UserConstant.MHS_PRODI),
                                            user.getString(UserConstant.MHS_CLASS),
                                            user.getString(UserConstant.MHS_PA)
                                    );

                                    SharedPrefManager.getInstance(context).saveMahasiswa(dataModel);
                                    SharedPrefManager.getInstance(context).saveCode("2");

                                } else {
                                    PegawaiModel dataModel = new PegawaiModel(
                                            user.getString(UserConstant.EMPLOYEE_ID),
                                            user.getString(UserConstant.NIP),
                                            user.getString(UserConstant.FULL_NAME),
                                            user.getString(UserConstant.EMAIL),
                                            user.getString(UserConstant.USERNAME),
                                            user.getString(UserConstant.PASSWORD),
                                            user.getString(UserConstant.GENDER),
                                            user.getString(UserConstant.HP),
                                            user.getString(UserConstant.ADDRESS),
                                            user.getString(UserConstant.DOSEN)
                                            );

                                    Log.e(this.getClass().getName(), "else NIP => "+dataModel.getNip());

                                    if(status.equals("Dosen")) {
                                        SharedPrefManager.getInstance(context).saveCode("1");
                                    } else {
                                        SharedPrefManager.getInstance(context).saveCode("3");
                                    }

                                    SharedPrefManager.getInstance(context).savePegawai(dataModel);

                                }

                                activity.setResultLogin(code, message);

                            } else {
                                activity.setResultLogin(code, message);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(this.getClass().getName(), "catch => "+e.getMessage());

                        }
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
                params.put("username", username);
                params.put("password", password);
                params.put("status", status);

                return params;
            }

        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

}
