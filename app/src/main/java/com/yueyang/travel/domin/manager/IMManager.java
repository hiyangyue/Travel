package com.yueyang.travel.domin.manager;

import android.content.Context;
import android.util.Log;

import com.arrownock.exception.ArrownockException;
import com.arrownock.im.AnIM;
import com.arrownock.im.callback.AnIMCallbackAdapter;
import com.arrownock.im.callback.AnIMMessageCallbackData;
import com.arrownock.im.callback.AnIMMessageSentCallbackData;
import com.arrownock.im.callback.AnIMNoticeCallbackData;
import com.arrownock.im.callback.IAnIMPushBindingCallback;
import com.yueyang.travel.R;
import com.yueyang.travel.domin.Utils.DBug;
import com.yueyang.travel.domin.application.IMppApp;

import java.util.Observable;

/**
 * Created by Yang on 2015/12/24.
 */
public class IMManager extends Observable {
    private static IMManager sIMManager;
    private AnIM anIM;
    private Context ct;

    private String currentClientId;

    private boolean retryConnect = false;

    private IMManager(Context ct) {
        this.ct = ct;
        try {
            anIM = new AnIM(ct, ct.getString(R.string.app_key));
            anIM.setCallback(callbackAdapter);
        } catch (ArrownockException e) {
            e.printStackTrace();
        }
    }

    public static IMManager getInstance(Context ct) {
        if (sIMManager == null) {
            sIMManager = new IMManager(ct);
        }
        return sIMManager;
    }

    @Override
    public void setChanged() {
        super.setChanged();
    }

    public AnIM getAnIM() {
        return anIM;
    }

    public void connect(String clientId) {
        this.currentClientId = clientId;
        retryConnect = true;
        try {
            anIM.connect(clientId);
        } catch (ArrownockException e) {

            e.printStackTrace();
        }
    }

    public void disconnect(boolean logout) {
        retryConnect = false;
        if (logout) {
            currentClientId = null;
        }
        try {
            anIM.disconnect();
        } catch (ArrownockException e) {

            e.printStackTrace();
        }
    }

    private void checkCoonnection() {
        if (!anIM.isOnline() && currentClientId != null) {
            connect(currentClientId);
        }
    }

    public String getCurrentClientId() {
        return currentClientId;
    }

    private AnIMCallbackAdapter callbackAdapter = new AnIMCallbackAdapter(){

        @Override
        public void messageSent(AnIMMessageSentCallbackData data) {
            DBug.e("IMManergerManager", data.getMsgId());
        }

        @Override
        public void receivedMessage(AnIMMessageCallbackData data) {
            DBug.e("IMManergerManager", "2");
            Log.e("message","....");
        }

        @Override
        public void receivedNotice(AnIMNoticeCallbackData anIMNoticeCallbackData) {
            DBug.e("IMManergerManager", "3t");
            Log.e("received", ".............." + anIMNoticeCallbackData.toString());
        }
    };

    public void bindAnPush() {
        IMppApp app = (IMppApp) ct.getApplicationContext();
        anIM.bindAnPushService(app.anPush.getAnID(), ct.getString(R.string.app_key), SpfHelper.getInstance(ct).getMyClientId(),
                new IAnIMPushBindingCallback() {
                    @Override
                    public void onSuccess() {
                        Log.e("bind_push_success", "...");

                    }

                    @Override
                    public void onError(ArrownockException arg0) {
                        Log.e("bind_push_error", "...");
                    }
                });
    }


}
