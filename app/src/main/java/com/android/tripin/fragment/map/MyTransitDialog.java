package com.android.tripin.fragment.map;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.tripin.R;
import com.baidu.mapapi.search.core.RouteLine;

import java.util.List;

/**
 * Created by Felix on 6/16/2018.
 * Description:
 */

public class MyTransitDialog extends Dialog {

    private MapFragment mapFragment;
    private Context context;

    private List<? extends RouteLine> mtransitRouteLines;
    private ListView transitRouteList;
    private RouteLineAdapter mTransitAdapter;

    OnItemInDlgClickListener onItemInDlgClickListener;

    public MyTransitDialog(MapFragment mapFragment, Context context, int theme) {
        super(context, theme);
        this.mapFragment = mapFragment;
        this.context = mapFragment.getActivity();
    }

    public MyTransitDialog(MapFragment mapFragment, List<? extends RouteLine> transitRouteLines, RouteLineAdapter.Type
            type) {
        this(mapFragment, mapFragment.getActivity(), 0);
        mtransitRouteLines = transitRouteLines;
        mTransitAdapter = new RouteLineAdapter(context, mtransitRouteLines, type);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transit_dialog);

        transitRouteList = (ListView) findViewById(R.id.transitList);
        transitRouteList.setAdapter(mTransitAdapter);

        transitRouteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemInDlgClickListener.onItemClick(position);
                dismiss();
                mapFragment.mapFragmentAuxiliary.hasShownDialogue = false;
            }
        });
    }

    public void setOnItemInDlgClickLinster(OnItemInDlgClickListener itemListener) {
        onItemInDlgClickListener = itemListener;
    }

}

// 响应DLg中的List item 点击
interface OnItemInDlgClickListener {
    public void onItemClick(int position);
}