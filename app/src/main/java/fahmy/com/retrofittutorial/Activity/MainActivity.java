package fahmy.com.retrofittutorial.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fahmy.com.retrofittutorial.FilePath;
import fahmy.com.retrofittutorial.Model.LoginModel;
import fahmy.com.retrofittutorial.Model.UploadModel;
import fahmy.com.retrofittutorial.R;
import fahmy.com.retrofittutorial.Rest.ApiClient;
import fahmy.com.retrofittutorial.Rest.ApiInterface;
import fahmy.com.retrofittutorial.Utility;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.response)
    TextView response;
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_upload_audio)
    Button btnUploadAudio;
    @BindView(R.id.choose_audio)
    Button chooseAudio;
    @BindView(R.id.tv_audio_file)
    TextView tvAudioFile;

    ApiInterface apiService;
    int AUDIO_FILE = 1;
    String selectedPath;
    Uri selectedFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        apiService = ApiClient.getClient(ApiInterface.class);
        Utility.requestStoragePermission(MainActivity.this, MainActivity.this);
    }

    @OnClick({R.id.btn_login, R.id.choose_audio, R.id.btn_upload_audio})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (Utility.isNetworkAvailable(MainActivity.this)) {
                    Call<LoginModel> callLogin = apiService.callLoginService(etUser.getText().toString(),
                            etPassword.getText().toString());
                    callLogin.enqueue(new Callback<LoginModel>() {
                        @Override
                        public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                            Log.e("Success Response", response.body().getMessage() + "");
                        }

                        @Override
                        public void onFailure(Call<LoginModel> call, Throwable t) {
                            // Log error here since request failed
                            Log.e("Failed Response", t.toString());
                        }
                    });
                }
                break;
            case R.id.choose_audio:
                if (Utility.requestStoragePermission(MainActivity.this, MainActivity.this)) {
                    Intent intent_upload = new Intent();
                    intent_upload.setType("audio/*");
                    intent_upload.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent_upload, AUDIO_FILE);
                }
                break;
            case R.id.btn_upload_audio:
                if ((Utility.requestStoragePermission(MainActivity.this, MainActivity.this))
                        && Utility.isNetworkAvailable(MainActivity.this)) {
                    File file = new File(selectedPath);
                    ApiInterface apiService = ApiClient.getClient(ApiInterface.class);
                    RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    //RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("audio", file.getName(), mFile);
                    Call<UploadModel> call = apiService.uploadFile("aKE9HyLkHHQ4vgLUYIzXqmINRzMQkqSubxES5qcN",
                            "Test Retrofit", "Test", "2", fileToUpload);
                    call.enqueue(new Callback<UploadModel>() {
                        @Override
                        public void onResponse(Call<UploadModel> call, Response<UploadModel> response) {
                            //uploadModel responseBody = response.body();
                            Log.e("Success Response", response.body().getMessage() + "");
                            Toast.makeText(getApplicationContext() , response.body().getMessage() , Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<UploadModel> call, Throwable t) {
                            // Log error here since request failed
                            Log.e("Failed Response", t.toString());
                            Toast.makeText(getApplicationContext() , t.toString() , Toast.LENGTH_LONG).show();
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AUDIO_FILE) {
                //the selected audio.
                selectedFileUri = data.getData();
                selectedPath = FilePath.getPath(this, selectedFileUri);

                if (selectedPath != null && !selectedPath.equals("")) {
                    tvAudioFile.setVisibility(View.VISIBLE);
                    tvAudioFile.setText(selectedPath);
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
