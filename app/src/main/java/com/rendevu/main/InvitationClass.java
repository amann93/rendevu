package com.rendevu.main;

/*
    Josh Davenport
    -Class separated from Main2Activity
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appinvite.AppInviteInvitation;

public class InvitationClass extends UncaughtExceptionActivity{

    Button invite;

    private static final int REQUEST_INVITE = 0;  //used for sending invites

    private static String good = "Preparing to send Invitation!";

    private InvitationClass(){  }

    //private InvitationClass sendInvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /**
         *
         * Listener and Handler for send invite button
         * */
        invite = findViewById(R.id.sendInvites);

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InvitationClass Inv = new InvitationClass();
                try {
                    Inv.onInviteClicked(v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void onInviteClicked(View v) {
        try {
            Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                    .setMessage(getString(R.string.invitation_message))
                    .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                    .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                    .setCallToActionText(getString(R.string.invitation_cta))
                    .build();
            startActivityForResult(intent, REQUEST_INVITE);
        } /*catch (InvalidClassException e) {
            e.printStackTrace();
            throw new InvalidClassException(e.getMessage(), "Invalid Object in InvitationClass");
        } */catch (Exception e) {
            e.printStackTrace();
        }/*finally{

        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            String TAG = "";
            Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

            if (requestCode == REQUEST_INVITE) {
                if (resultCode == RESULT_OK) {
                    // Get the invitation IDs of all sent messages
                    String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                    for (String id : ids) {
                        Log.d(TAG, "onActivityResult: sent invitation " + id);
                    }
                } else {
                    // Sending failed or it was canceled, show failure message to the user
                    // ...
                    Toast.makeText(getApplicationContext(), "Failed Invite!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
