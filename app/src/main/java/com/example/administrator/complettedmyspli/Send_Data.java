package com.example.administrator.complettedmyspli;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 27/04/2017.
 */

public class Send_Data extends StringRequest {
    private static final String SEND_DATA_URL = "http://devsinai.com/send_data.php";
    private Map<String, String> MapData;
    public Send_Data(String name, String information,String img, Response.Listener<String> listener) {
        super(Method.POST, SEND_DATA_URL, listener, null);
        MapData = new HashMap<>();
        MapData.put("writer", name);
        MapData.put("postcontent", information);
        MapData.put("img", img);
    }
    @Override
    public Map<String, String> getParams() {
        return MapData;
    }
}