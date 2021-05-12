package com.aabhishekpatel.jj.Utility;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.aabhishekpatel.jj.R;

public class NetworkChangeListerner extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (!Common.isConnectedToInternet(context)) //Internet is not connected
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.check_internet, null);
            builder.setView(layout_dialog);

            /*----------- Hooks -----------*/
            AppCompatButton btn_retry = layout_dialog.findViewById(R.id.btn_retry);

            /*----------- Show Dialog -----------*/
            final AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);
            dialog.getWindow().setGravity(Gravity.CENTER);

            /*----------- btn_retry -----------*/
            btn_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    onReceive(context, intent);
                }
            });

        }
    }
}
