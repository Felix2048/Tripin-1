package com.android.tripin.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.tripin.R;
import com.android.tripin.base.BaseActivity;

import java.util.Calendar;

public class PinDetailActivity extends BaseActivity implements View.OnClickListener{

    private TextView arriveTimeText;
    private TextView leaveTimeText;
    private EditText pinNote;
    private Button checkPinButton;
    private Button deletePinButton;
    private int mYear,mMonth,mDay;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_pin_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_detail);
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
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
                new DatePickerDialog(PinDetailActivity.this, onDateSetListener, mYear, mMonth, mDay).show();
                break;
            case R.id.text_leave_time:
                break;
            case R.id.edit_pin_note:
                break;
            case R.id.btn_check_pin:
                break;
            case R.id.btn_delete_pin:
                break;
            default:
                break;
        }
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            }

        }
    };
}
