package com.sar.constant;

public abstract class Server {
//    192.168.43.87
//    192.168.123.126

    private static final String URL_BASE = "http://192.168.43.87/service_beritasar/";
    private static final String SERVICE_URL_BASE = URL_BASE + "index.php/services/";

    private static final String URL_IMAGE_BASE = "http://192.168.43.87/berita_sar/";
    public static final String IMAGE_NEWS_URL = URL_IMAGE_BASE + "assets/img/doc/";

    public static final String LOGIN_URL = SERVICE_URL_BASE + "Login/loginUser";
    public static final String ALL_NEWS_URL = SERVICE_URL_BASE + "News/allNews";
    public static final String NEWS_TAG_URL = SERVICE_URL_BASE + "News/newsTag";
    public static final String COUNT_PER_TAG_URL = SERVICE_URL_BASE + "News/countPerTag";
    public static final String NEWS_COMMENT_URL = SERVICE_URL_BASE + "News/newsComment";
    public static final String COMMENT_PER_NEWS_URL = SERVICE_URL_BASE + "News/commentPerNews";

}
