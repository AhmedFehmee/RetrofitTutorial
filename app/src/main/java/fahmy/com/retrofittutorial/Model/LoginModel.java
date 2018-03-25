package fahmy.com.retrofittutorial.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fahmy on 3/25/2018.
 */

public class LoginModel {

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("code")
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
