package com.sar.model;

public class MahasiswaModel {
    public String nim, name, username, password, prodi, mhs_class, pa;

    public MahasiswaModel(String nim, String name, String username, String password, String prodi, String mhs_class, String pa) {
        this.nim = nim;
        this.name = name;
        this.username = username;
        this.password = password;
        this.prodi = prodi;
        this.mhs_class = mhs_class;
        this.pa = pa;
    }

    public String getNim() {
        return nim;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProdi() {
        return prodi;
    }

    public String getMhs_class() {
        return mhs_class;
    }

    public String getPa() {
        return pa;
    }


}
