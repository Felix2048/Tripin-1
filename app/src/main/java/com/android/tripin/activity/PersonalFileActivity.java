package com.android.tripin.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.tripin.MainActivity;
import com.android.tripin.R;
import com.android.tripin.base.BaseActivity;
import com.android.tripin.model.PersonalFileModel;
import com.android.tripin.presenter.PersonalFilePresenter;
import com.android.tripin.view.IPersonalFileView;

public class PersonalFileActivity extends BaseActivity implements IPersonalFileView,View.OnClickListener{

    private final static String TAG = PersonalFileActivity.class.getSimpleName();
    private ImageView image_back_to_home;
    private ImageView wipe_user_name;
    private ImageView wipe_user_phone;
    private ImageView wipe_user_email;
    private EditText personal_file_user_name;
    private EditText personal_file_phone;
    private EditText personal_file_email;
    private Button btn_change_personal_file;
    private Button btn_log_out;
    PersonalFilePresenter personalFilePresenter;

    void init() {
        image_back_to_home = (ImageView) findViewById(R.id.image_back_to_home);
        wipe_user_email = (ImageView ) findViewById(R.id.wipe_user_email);
        wipe_user_name = (ImageView) findViewById(R.id.wipe_user_name);
        wipe_user_phone = (ImageView) findViewById(R.id.wipe_user_phone);
        personal_file_email = (EditText) findViewById(R.id.personal_file_email);
        personal_file_phone = (EditText) findViewById(R.id.personal_file_phone);
       personal_file_user_name = (EditText) findViewById(R.id.personal_file_user_name);
        btn_change_personal_file = (Button) findViewById(R.id.btn_change_personal_file);
        btn_log_out = (Button) findViewById(R.id.btn_log_out);
        wipe_user_phone.setOnClickListener(this);
        wipe_user_name.setOnClickListener(this);
        wipe_user_email.setOnClickListener(this);
        image_back_to_home.setOnClickListener(this);
        btn_log_out.setOnClickListener(this);
        btn_change_personal_file.setOnClickListener(this);
    }

    @Override
    protected int getContextViewId() {
        return R.layout.activity_personal_file;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_file);
        init();
        personalFilePresenter = new PersonalFilePresenter(new PersonalFileModel(),this);
    }

    @Override
    public void showLogoutSuccess() {
        Toast.makeText(this, R.string.log_out_success,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showChangeSuccess() {
        Toast.makeText(this, R.string.change_personal_file_success,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showChangeFailed() {
        Toast.makeText(this, R.string.change_personal_file_failed,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(this, R.string.change_personal_file_netword_error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getUserName() {
        return personal_file_user_name.getText().toString();
    }

    @Override
    public String getUserPhone() {
        return personal_file_phone.getText().toString();
    }

    @Override
    public String getUserEmail() {
        return personal_file_email.getText().toString();
    }

    public void  setMyHint() {
        personal_file_user_name.setHint(this.getUserName());
        personal_file_email.setHint(this.getUserEmail());
        personal_file_phone.setHint(this.getUserPhone());
    }

    public void backToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wipe_user_email:
                personal_file_email.setHint("");
                break;
            case R.id.wipe_user_name:
                personal_file_user_name.setHint("");
                break;
            case R.id.wipe_user_phone:
                personal_file_phone.setHint("");
                break;
            case R.id.image_back_to_home:
                /**
                 * 返回到主界面
                 */
                backToHome();
                break;
            case R.id.btn_change_personal_file:
                /**
                 * 点击修改资料
                 */
                personalFilePresenter.changePersonalFile();
                break;
            case R.id.btn_log_out:
                /**
                 * 注销
                 */
                personalFilePresenter.logout();
                break;
           default:
                break;

        }
    }
}
