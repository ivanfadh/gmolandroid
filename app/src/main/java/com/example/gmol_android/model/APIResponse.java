package com.example.gmol_android.model;

/**
 * Created by User on 3/25/2018.
 */

public class APIResponse {
    private Object data;
    private String message;
    private int status_code;

    public APIResponse(Object data, String message, int status_code) {
        this.data = data;
        this.message = message;
        this.status_code = status_code;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }
}
