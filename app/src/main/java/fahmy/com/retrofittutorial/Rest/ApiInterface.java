package fahmy.com.retrofittutorial.Rest;

import fahmy.com.retrofittutorial.Model.LoginModel;
import fahmy.com.retrofittutorial.Model.UploadModel;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Fahmy on 3/24/2018.
 */

public interface ApiInterface {
    @POST("api/login")
    Call<LoginModel> callLoginService(@Query("email") String email, @Query("password") String password);

    @Multipart
    @POST("http://demo.extra4it.net/blogger/api/blogs")
    Call<UploadModel> uploadFile(@Header("token") String token,
                                 @Query("title") String title, @Query("content") String content,
                                 @Query("type_id") String typeID, @Part MultipartBody.Part audio);

}
