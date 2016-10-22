package com.lb.dialogshard;

import android.Manifest.permission;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.lb.dialog_shard_lib.DialogShard;

public class MainActivity extends AppCompatActivity {
    private static final boolean RESHOW_DIALOG_UPON_CONFIGURATION_CHANGE = true;
    public static final int REQUEST_CODE_DIALOG_FRAGMENT = 1;
    public static final int REQUEST_CODE_DIALOG_SHARD = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.useDialogFragmentButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (VERSION.SDK_INT >= VERSION_CODES.M) {
                    Toast.makeText(MainActivity.this, "Please choose to deny the permission", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_DIALOG_FRAGMENT);
                } else {
                    Toast.makeText(MainActivity.this, "Cannot demonstrate issue pre-M versions of Android", Toast.LENGTH_SHORT).show();
                    new RequestPermissionsDialogFragment().show(getSupportFragmentManager(), RequestPermissionsDialogFragment.TAG);
                }

            }
        });
        findViewById(R.id.useDialogShardButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (VERSION.SDK_INT >= VERSION_CODES.M) {
                    Toast.makeText(MainActivity.this, "Please choose to deny the permission", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_DIALOG_SHARD);
                } else {
                    Toast.makeText(MainActivity.this, "Cannot demonstrate issue pre-M versions of Android", Toast.LENGTH_SHORT).show();
                    new RequestPermissionsDialogShard().show(MainActivity.this, RESHOW_DIALOG_UPON_CONFIGURATION_CHANGE);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_DIALOG_FRAGMENT:
                new RequestPermissionsDialogFragment().show(getSupportFragmentManager(), RequestPermissionsDialogFragment.TAG);
                break;
            case REQUEST_CODE_DIALOG_SHARD:
                new RequestPermissionsDialogShard().show(this, RESHOW_DIALOG_UPON_CONFIGURATION_CHANGE);
                break;
        }
    }

    public static class RequestPermissionsDialogFragment extends DialogFragment {
        public static final String TAG = RequestPermissionsDialogFragment.class.getCanonicalName();

        public RequestPermissionsDialogFragment() {
        }

        @Override
        public void onCreate(@Nullable final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(RESHOW_DIALOG_UPON_CONFIGURATION_CHANGE);
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
            //new Handler().postDelayed(new Runnable() {
            //    @Override
            //    public void run() {
            //        activity.finish();
            //    }
            //}, 1000);
            return builder.create();
        }
    }
}
