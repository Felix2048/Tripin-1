package com.android.tripin.activity;

import android.app.DatePickerDialog;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tripin.R;
import com.android.tripin.base.BaseActivity;
import com.android.tripin.model.PinDetailModel;
import com.android.tripin.presenter.interfaces.PinDetailPresenter;
import com.android.tripin.view.IPinDetailView;

import java.util.Calendar;

public class PinDetailActivity extends BaseActivity implements IPinDetailView, View.OnClickListener{

    private TextView arriveTimeText;
    private TextView leaveTimeText;
    private EditText pinNote;
    private Button checkPinButton;
    private Button deletePinButton;
    private PinDetailPresenter pinDetailPresenter;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_pin_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_detail);
        init();
        pinDetailPresenter = new PinDetailPresenter(this,new PinDetailModel());
    }

    void init() {
        arriveTimeText = (TextView) findViewById(R.id.text_arrive_time);
        leaveTimeText = (TextView) findViewById(R.id.text_leave_time);
        pinNote = (EditText) findViewById(R.id.edit_pin_note);
        checkPinButton = (Button) findViewById(R.id.btn_check_pin);
        deletePinButton = (Button) findViewById(R.id.btn_delete_pin);
        arriveTimeText.setOnClickListener(this);
        leaveTimeText.setOnClickListener(this);
        pinNote.setOnClickListener(this);
        checkPinButton.setOnClickListener(this);
        deletePinButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_arrive_time:
                 Calendar c = Calendar.getInstance();
                 new DatePickerDialog(PinDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                         arriveTimeText.setText(year+"年"+month+"月"+dayOfMonth+"日");
                     }
                 },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
                 break;
            case R.id.text_leave_time:
                Calendar c1 = Calendar.getInstance();
                new DatePickerDialog(PinDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        leaveTimeText.setText(year+"年"+month+"月"+dayOfMonth+"日");
                    }
                },c1.get(Calendar.YEAR),c1.get(Calendar.MONTH),c1.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.edit_pin_note:
                String s = pinNote.getText().toString();
                pinNote.setHint(s);
                break;
            case R.id.btn_check_pin:

                break;
            case R.id.btn_delete_pin:
                break;
            default:
                break;
        }
    }


    @Override
    public void showChangeSuccess() {
        Looper.prepare();
        Toast.makeText(this, R.string.change_pin_detail_success,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    @Override
    public void showChangeFailed() {
        Looper.prepare();
        Toast.makeText(this, R.string.change_pin_detail_failed,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    @Override
    public void showDeleteSuccess() {
        Looper.prepare();
        Toast.makeText(this, R.string.delete_pin_success,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    @Override
    public void showDeleteFailed() {
        Looper.prepare();
        Toast.makeText(this, R.string.delete_pin_failed,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    @Override
    public void showNetworkError() {
        Looper.prepare();
        Toast.makeText(this, R.string.pin_detail_network_error,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    @Override
    public String getArriveTime() {
        return arriveTimeText.getText().toString().trim();
    }

    @Override
    public String getLeaveTime() {
        return leaveTimeText.getText().toString().trim();
    }

    @Override
    public String getPinNotes() {
        return pinNote.getHint().toString().trim();
    }
}
