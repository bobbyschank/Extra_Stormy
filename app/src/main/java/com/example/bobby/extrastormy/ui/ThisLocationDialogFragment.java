package com.example.bobby.extrastormy.ui;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.bobby.extrastormy.R;

/**
 * Created by bobby on 11/28/15.
 */
public class ThisLocationDialogFragment extends DialogFragment {

    public static ThisLocationDialogFragment newInstance(int title) {
        ThisLocationDialogFragment frag = new ThisLocationDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }


    public static class MyDialogFragment extends DialogFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Set title for this dialog
            getDialog().setTitle("My Dialog Title");

            View v = inflater.inflate(R.layout.activity_maps, container, false);

            return v;
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        super.onStart();


        int title = getArguments().getInt("title");


        return new AlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.bg_temperature)
                .setTitle(title)
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MapsActivity) getActivity()).doPositiveClick();
                            }
                        }
                )
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MapsActivity)getActivity()).doNegativeClick();
                            }
                        }
                )
                .create();
    }
}