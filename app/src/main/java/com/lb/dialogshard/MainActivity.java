package com.lb.dialogshard;

import android.Manifest.permission;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
        findViewById(R.id.showDialogFragmentButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new TestDialogFragment().show(getSupportFragmentManager(), TestDialogFragment.TAG);
            }
        });
        findViewById(R.id.showDialogShardButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new TestDialogShard().show(MainActivity.this, RESHOW_DIALOG_UPON_CONFIGURATION_CHANGE);
            }
        });
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            findViewById(R.id.showPermissionIssueOnDialogFragmentButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    Toast.makeText(MainActivity.this, "Please choose to deny the permission", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_DIALOG_FRAGMENT);
                        }
                    }, 1000);
                }
            });
            findViewById(R.id.showNoPermissionIssueOnDialogShardButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    Toast.makeText(MainActivity.this, "Please choose to deny the permission", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_DIALOG_SHARD);
                        }
                    }, 1000);

                }
            });
        } else {
            findViewById(R.id.showPermissionIssueOnDialogFragmentButton).setEnabled(false);
            findViewById(R.id.showNoPermissionIssueOnDialogShardButton).setEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        String url = null;
        switch (item.getItemId()) {
            case R.id.menuItem_all_my_apps:
                url = "https://play.google.com/store/apps/developer?id=AndroidDeveloperLB";
                break;
            case R.id.menuItem_all_my_repositories:
                url = "https://github.com/AndroidDeveloperLB";
                break;
            case R.id.menuItem_current_repository_website:
                url = "https://github.com/AndroidDeveloperLB/DialogShard";
                break;
        }
        if (url == null)
            return true;
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_DIALOG_FRAGMENT:
                new TestDialogFragment().show(getSupportFragmentManager(), TestDialogFragment.TAG);
                break;
            case REQUEST_CODE_DIALOG_SHARD:
                new TestDialogShard().show(MainActivity.this, RESHOW_DIALOG_UPON_CONFIGURATION_CHANGE);
                break;
        }
    }

    public static class TestDialogFragment extends android.support.v4.app.DialogFragment {
        public static final String TAG = TestDialogFragment.class.getCanonicalName();

        public TestDialogFragment() {
        }

        @Override
        public void onCreate(@Nullable final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(!RESHOW_DIALOG_UPON_CONFIGURATION_CHANGE);
        }

        @NonNull
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

    public static class TestDialogShard extends DialogShard {

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
