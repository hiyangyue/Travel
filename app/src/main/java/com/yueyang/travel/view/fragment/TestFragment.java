package com.yueyang.travel.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yueyang.travel.R;

/**
 * Created by Yang on 2015/12/10.
 */
public class TestFragment extends Fragment {


    private int page = 0;
    public static final String ARG_PAGE = "page";

    public static TestFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page = getArguments().getInt(ARG_PAGE);
        Log.e("page","..." + page);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.test,container,false);
        TextView textView = (TextView) view.findViewById(R.id.test_tv);
        textView.setText("Pager..." + page);
        return view;
    }
}
