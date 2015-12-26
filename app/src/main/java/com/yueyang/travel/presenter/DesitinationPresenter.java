package com.yueyang.travel.presenter;

import android.util.Log;

import com.yueyang.travel.model.Impl.IDesitinationImpl;
import com.yueyang.travel.model.Impl.IDesitinationModel;
import com.yueyang.travel.model.bean.Desitination;
import com.yueyang.travel.model.callBack.DesitinationCallback;
import com.yueyang.travel.view.IDestinationView;

import java.util.List;

/**
 * Created by Yang on 2015/12/17.
 */
public class DesitinationPresenter {

    private IDesitinationModel desitinationModel;
    private IDestinationView destinationView;

    public DesitinationPresenter(IDestinationView destinationView) {
        this.destinationView = destinationView;
        desitinationModel = new IDesitinationImpl();
    }

    public void loadDesitinations(){
        desitinationModel.loadDesitination(new DesitinationCallback() {
            @Override
            public void onSuccess(List<Desitination> desitinationList) {
                destinationView.loadDesitinations(desitinationList);
            }

            @Override
            public void onError() {
                Log.e("des_load","error");
            }
        });
    }
}


