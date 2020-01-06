package com.example.pum;

public class ConnectionParam {
    private  String ip = "46.41.139.170";
    private  String port = ":3018";
    private  String pHttp = "http://";
    private String addMessageString = "http://46.41.139.170:3018/addMesage";

    String getAddMessagePath(){
        String admMsg = "/addMesage";
        return pHttp + ip + port + admMsg;
    }
}
