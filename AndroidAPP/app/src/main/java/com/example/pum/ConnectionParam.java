package com.example.pum;

public class ConnectionParam {
    private  String ip = "46.41.139.170";
    private  String port = ":3018";
    private  String pHttp = "http://";
    private  String admMsg ="";

    String getAddMessagePath(){
        admMsg = "/addMesage";
        return pHttp + ip + port + admMsg;
    }

    String getConForKeyPath(){
        admMsg = "/getConv?keyConv=";
        return pHttp + ip + port + admMsg;
    }

    String geNewKeyFromServerPath(){
        admMsg = "/newKey?who=";
        return pHttp + ip + port + admMsg;
    }
}
