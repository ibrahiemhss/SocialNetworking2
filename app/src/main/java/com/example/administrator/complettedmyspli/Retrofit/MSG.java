package com.example.administrator.complettedmyspli.Retrofit;

/**
 * Created by Administrator on 01/06/2017.
 */

public class MSG {

   private Integer success;


    private  String message;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MSG() {

    }
    public MSG(Integer success, String message) {
        super();
        this.success = success;
        this.message = message;
    }
}
