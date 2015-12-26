package com.yueyang.travel.manager;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;

import com.arrownock.exception.ArrownockException;
import com.arrownock.im.AnIM;
import com.arrownock.im.AnIMMessage;
import com.arrownock.im.callback.AnIMBinaryCallbackData;
import com.yueyang.travel.R;

import java.util.Observable;

/**
 * Created by Yang on 2015/12/24.
 */
public class IMManager extends Observable {
    private static IMManager sIMManager;
    private AnIM anIM;
    private Handler handler;
    private Context ct;
    private AlertDialog mActionDialog;

    private final static int RECONNECT_RATE = 1000;
    private String currentClientId;

    private boolean retryConnect = false;

    public static String WELCOME_MESSAGE_ID = "100000000";

    public enum UpdateType {
        Topic, Chat, FriendRequest, Like
    }

    private IMManager(Context ct) {
        this.ct = ct;
        handler = new Handler();
        try {
            anIM = new AnIM(ct, ct.getString(R.string.app_key));
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

    public void enableRetryConnect(boolean bool) {
        retryConnect = bool;
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

    public void notifyFriendRequest() {
        setChanged();
        notifyObservers(UpdateType.FriendRequest);
    }

    private void handleLikeNotice(Object data) {
        if (data instanceof AnIMBinaryCallbackData) {

        } else if (data instanceof AnIMMessage) {

        }
        notifyLike();
    }

    public void notifyLike() {
        setChanged();
        notifyObservers(UpdateType.Like);
    }


}
