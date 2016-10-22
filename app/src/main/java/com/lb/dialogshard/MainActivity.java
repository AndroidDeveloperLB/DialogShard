package com.lb.dialogshard;

import android.Manifest.permission;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lb.dialog_shard_lib.DialogShard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.useDialogFragmentButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        });
        findViewById(R.id.useDialogShardButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE}, 2);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1)
            new RequestPermissionsDialogFragment().show(getSupportFragmentManager(), RequestPermissionsDialogFragment.TAG);
        else new RequestPermissionsDialogShard().show(this, true);
    }

    public static class RequestPermissionsDialogFragment extends DialogFragment {
        public static final String TAG = RequestPermissionsDialogFragment.class.getCanonicalName();

        public RequestPermissionsDialogFragment() {
        }

        @Override
        public Dialog onCreateDialog(final Bundle savedInstanceState) {
            final FragmentActivity activity = getActivity();
            AlertDialog.Builder builder = new Builder(activity);
            builder.setTitle("title");

            builder.setPositiveButton("OK", new OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                }
            });
            builder.setMessage("msg");
            return builder.create();
        }
    }

    public static class RequestPermissionsDialogShard extends DialogShard {

        @Override
        public Dialog onCreateDialog(final Activity activity, final Bundle savedInstanceState) {
            AlertDialog.Builder builder = new Builder(activity);
            builder.setTitle("title");

            builder.setPositiveButton("OK", new OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                    //activity.startActivity(new Intent(activity, MainActivity.class));
                }
            });
            builder.setMessage("msg");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.finish();
                }
            }, 1000);
            return builder.create();
        }
    }
}
