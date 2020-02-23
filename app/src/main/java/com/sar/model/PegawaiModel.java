package com.sar.model;

public class PegawaiModel {
    public String id, nip, name, email, username, password, gender, hp, address, dosen;

    public PegawaiModel(String id, String nip, String name, String email, String username, String password, String gender, String hp, String address, String dosen) {
        this.id = id;
        this.nip = nip;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.hp = hp;
        this.address = address;
        this.dosen = dosen;
    }

    public String getId() {
        return id;
    }

    public String getNip() {
        return nip;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getHp() {
        return hp;
    }

    public String getAddress() {
        return address;
    }

    public String getDosen() {
        return dosen;
    }
}
