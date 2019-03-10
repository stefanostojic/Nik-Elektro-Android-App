package com.example.importingexcell;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.widget.TextView;

import org.w3c.dom.Text;


public class ONamaDialog extends DialogFragment {



    @NonNull
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {


//        Pretraga_Activity_V2..setMovementMethod(new ScrollingMovementMethod());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.o_nama_layout,null));
//        builder.setPositiveButton("Zatvori")
        return builder.create();

    }

}
































