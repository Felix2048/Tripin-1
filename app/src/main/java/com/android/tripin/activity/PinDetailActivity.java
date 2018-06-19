package com.android.tripin.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.tripin.R;
import com.android.tripin.base.BaseActivity;
import com.android.tripin.entity.Pin;
import com.android.tripin.manager.DataManager;
import com.android.tripin.model.PinDetailModel;
import com.android.tripin.presenter.interfaces.PinDetailPresenter;
import com.android.tripin.view.IPinDetailView;

import java.util.Calendar;
import java.util.List;
import java.util.Date;

public class PinDetailActivity extends BaseActivity implements IPinDetailView, View.OnClickListener{

    private TextView arriveTimeText;
    private TextView leaveTimeText;
    private EditText pinNote;
    private EditText pinTitle;
    private Button checkPinButton;
    private Button deletePinButton;
    private ImageButton pin_detail_return;
    private PinDetailPresenter pinDetailPresenter;

    private Pin pin;
    private List<Pin> pinList = DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).getPinList();

    @Override
    protected int getContextViewId() {
        return R.layout.activity_pin_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_detail);
        init();
        pin = (Pin) getIntent().getSerializableExtra("pin");
        initInfo();
        pinDetailPresenter = new PinDetailPresenter(this,new PinDetailModel());
    }

    private void initInfo() {
        if (null != pin.getPinArrival()) {
            String arrivalText = pin.getPinArrival().getHours() + "时" + pin.getPinArrival().getMinutes() + "分";
            arriveTimeText.setText(arrivalText);
        }
        if (null != pin.getPinDeparture()) {
            String departureText = pin.getPinDeparture().getHours() + "时" + pin.getPinDeparture().getMinutes() + "分";
            leaveTimeText.setText(departureText);
        }
        if (null != pin.getPinNotes()) {
            pinNote.setText(pin.getPinNotes());
        }
        if (null != pin.getPinTitle()) {
            pinTitle.setText(pin.getPinTitle());
        }
    }

    void init() {
        arriveTimeText = (TextView) findViewById(R.id.text_arrive_time);
        leaveTimeText = (TextView) findViewById(R.id.text_leave_time);
        pinNote = (EditText) findViewById(R.id.edit_pin_note);
        pinTitle = (EditText) findViewById(R.id.edit_pin_title);
        checkPinButton = (Button) findViewById(R.id.btn_check_pin);
        deletePinButton = (Button) findViewById(R.id.btn_delete_pin);
        pin_detail_return = (ImageButton) findViewById(R.id.pin_detail_return);
        pin_detail_return.setOnClickListener(this);
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
                 Date arrivalTime = new Date();
                 new TimePickerDialog(PinDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                     @Override
                     public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                         String text = hourOfDay + "时" + minute + "分";
                         Date arrivalTime = new Date();
                         arrivalTime.setHours(hourOfDay);
                         arrivalTime.setMinutes(minute);
                         pin.setPinArrival(arrivalTime);
                         arriveTimeText.setText(text);
                     }
                 }, arrivalTime.getHours(), arrivalTime.getMinutes(), true).show();
                 break;
            case R.id.text_leave_time:
                Date departureDate = new Date();
                new TimePickerDialog(PinDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String text = hourOfDay + "时" + minute + "分";
                        Date departureTime = new Date();
                        departureTime.setHours(hourOfDay);
                        departureTime.setMinutes(minute);
                        pin.setPinDeparture(departureTime);
                        leaveTimeText.setText(text);
                    }
                }, departureDate.getHours(), departureDate.getMinutes(), true).show();
                break;
            case R.id.edit_pin_note:
                String s = pinNote.getText().toString();
                pinNote.setHint(s);
                break;
            case R.id.edit_pin_title:
                String s1 = pinTitle.getText().toString();
                pinTitle.setHint(s1);
                break;
            case R.id.btn_check_pin:
                String pinNotes = pinNote.getText().toString();
                pin.setPinNotes(pinNotes);
                String pinTitleEdit = pinTitle.getText().toString();
                pin.setPinTitle(pinTitleEdit);
                for (Pin temp : pinList) {
                    if (temp.getPinID() == pin.getPinID()) {
                        temp.setPinTitle(pin.getPinTitle());
                        temp.setPinArrival(pin.getPinArrival());
                        temp.setPinDeparture(pin.getPinDeparture());
                        temp.setPinNotes(pin.getPinNotes());
                        break;
                    }
                }
                DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).setUpdated(true);
                finish();
                break;
            case R.id.btn_delete_pin:
                DataManager.deletePin(pin);
                DataManager.getPlanMapDiagramHashMap().get(DataManager.getCurrentPlan()).setUpdated(true);
                finish();
                break;
            case R.id.pin_detail_return:
                onBackPressed();
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
