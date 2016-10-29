package com.lb.dialog_shard_lib;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.app.Dialog;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class DialogShard implements ActivityLifecycleCallbacks {
    private boolean _enableShowAlsoAfterConfigurationChange;
    private Class<? extends Activity> _activityClassToMatchForAfterConfigurationChange;
    @SuppressWarnings("WeakerAccess")
    protected Activity _activity;
    @SuppressWarnings("WeakerAccess")
    protected Dialog _dialog;

    @NonNull
    public abstract Dialog onCreateDialog(Activity activity, final Bundle savedInstanceState);

    public void show(Activity activity, final boolean enableShowAlsoAfterConfigurationChange) {
        _enableShowAlsoAfterConfigurationChange = enableShowAlsoAfterConfigurationChange;
        if (activity == null)
            return;
        _activity = activity;
        final boolean allowShowNow = !(VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) &&
                !activity.isFinishing() && !activity.isChangingConfigurations();
        final Application application = activity.getApplication();
        if (allowShowNow) {
            _dialog = onCreateDialog(activity, null);
            _dialog.show();
        }
        application.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(final Activity activity, final Bundle savedInstanceState) {
        if (!activity.getClass().equals(_activityClassToMatchForAfterConfigurationChange))
            return;
        _activityClassToMatchForAfterConfigurationChange = null;
        _activity = activity;
        _dialog = onCreateDialog(activity, savedInstanceState);
        _dialog.show();
    }

    @Override
    public void onActivityStarted(final Activity activity) {
    }

    @Override
    public void onActivityResumed(final Activity activity) {
    }

    @Override
    public void onActivityPaused(final Activity activity) {
    }

    @Override
    public void onActivityStopped(final Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(final Activity activity, final Bundle bundle) {
    }

    /**
     * returns the currently activity that has shown the dialog, but if there isn't any yet, returns null
     */
    @Nullable
    public Activity getActivity() {
        return _activity;
    }

    @Override
    public void onActivityDestroyed(final Activity activity) {
        if (activity != _activity)
            return;
        //if dismissed by user, no need to re-show the dialog after configuration changed, even if we requested before
        boolean needToReShowDialogOnConfigurationChanged =
                _enableShowAlsoAfterConfigurationChange && activity.isChangingConfigurations() && (_dialog == null || _dialog.isShowing());
        if (_dialog != null)
            _dialog.dismiss();
        if (needToReShowDialogOnConfigurationChanged)
            _activityClassToMatchForAfterConfigurationChange = _activity.getClass();
        else
            activity.getApplication().unregisterActivityLifecycleCallbacks(this);
        _activity = null;
        _dialog = null;
    }
}
