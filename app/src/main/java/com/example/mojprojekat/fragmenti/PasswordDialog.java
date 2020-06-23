package com.example.mojprojekat.fragmenti;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.mojprojekat.R;
import com.example.mojprojekat.service.AccountService;

public class PasswordDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        String link="";
        EditText txtEmail=(EditText) getActivity().findViewById(R.id.txtRgUserName);
        String e=txtEmail.getText().toString().split("@")[1];
        if(e.equals("gmail.com")){
            link="https://support.google.com/accounts/answer/185833?hl=en";
        }else if(e.equals("yahoo.com")){
            link="https://help.yahoo.com/kb/generate-third-party-passwords-sln15241.html";
        }
        builder.setTitle("Dodatna lozinka");
        builder.setMessage("Generišite dodatnu lozinku za vašu e-mail adresu!" +
                "\nPrilikom svake prijave koristite standardnu lozinku za vašu e-mail adresu!" +
                "\nPomoć za generisanje dodatne lozinke je na sledećem linku: "+"\n"+"->"+link);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.text_input_password, null))
                // Add action buttons
                .setPositiveButton("Potvrdi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        /*TextView t2 = (TextView) getDialog().findViewById(R.id.text2);
                        t2.setText(link);
                        t2.setMovementMethod(LinkMovementMethod.getInstance());*/
                        EditText txtEmail=(EditText) getActivity().findViewById(R.id.txtRgUserName);
                        EditText txtPassword=(EditText) getActivity().findViewById(R.id.psRgPasword);
                        String email=txtEmail.getText().toString();
                        String password=txtPassword.getText().toString();
                        EditText input=(EditText) getDialog().findViewById(R.id.input);
                        String appPass=input.getText().toString();
                        System.out.println("Lozinka glasi: "+password+"|"+appPass);
                        Intent intent2 = new Intent(getActivity(), AccountService.class);
                        intent2.putExtra("email_adress",email);
                        intent2.putExtra("password",password+"|"+appPass);
                        intent2.putExtra("option","add");
                        getActivity().startService(intent2);
                    }
                })
                .setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PasswordDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
