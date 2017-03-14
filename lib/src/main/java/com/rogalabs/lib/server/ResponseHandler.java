package com.rogalabs.lib.server;

import com.android.volley.VolleyError;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class ResponseHandler {

    public static final Integer RESPONSE_STATUS_200 = 200;
    public static final Integer RESPONSE_STATUS_201 = 201;
    public static final Integer RESPONSE_STATUS_401 = 401;
    public static final Integer RESPONSE_STATUS_404 = 404;
    public static final Integer RESPONSE_STATUS_405 = 405;
    public static final Integer RESPONSE_STATUS_422 = 422;
    public static final Integer RESPONSE_STATUS_500 = 500;
    public static final Integer RESPONSE_STATUS_502 = 502;

    public ErrorHandler errorHandler(VolleyError volleyError) {
        ErrorHandler error = new ErrorHandler();
        try {
            Integer code = volleyError.networkResponse.statusCode;
            error.setCode(code);
            if (code.equals(RESPONSE_STATUS_404)
                    || code.equals(RESPONSE_STATUS_422)
                    || code.equals(RESPONSE_STATUS_500)) {
                String jsonData = new String(volleyError.networkResponse.data, "UTF-8");
                error.setMessage(jsonData);
                error.setSuccess(false);
            } else if (code.equals(RESPONSE_STATUS_401)) {
                error.setMessage("Você precisa estar cadastrado para utilizar o serviço");
                error.setSuccess(false);
            } else {
                error.setMessage("Problemas no serviço ou conexão. Tente novamente");
                error.setSuccess(false);
            }
        } catch (NullPointerException | UnsupportedEncodingException ne) {
            error.setMessage("Problemas no serviço. Tente mais tarde");
            error.setSuccess(false);
        }
        return error;
    }

    public class ErrorHandler implements Serializable {

        private boolean success;
        private String message;
        private Integer code;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
