package com.example.moka.yummybaking;

import android.content.Intent;
import android.widget.RemoteViewsService;



public class BakingAppWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new BakingAppRemoteViewsFactory(this.getApplicationContext(), intent));

    }
}
