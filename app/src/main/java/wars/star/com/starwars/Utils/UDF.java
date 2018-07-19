package wars.star.com.starwars.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

import cn.pedant.SweetAlert.SweetAlertDialog;
import wars.star.com.starwars.R;

/**
 * Created by T183 on 16-Jul-18.
 */

public class UDF {

    /**
     * Show the error dialog with given error-message.
     *
     * @param mErrorMessage Message of the dialog
     * @param activity      the activity in which the dialog should be displayed.
     */
    public static void showErrorSweetDialog(String mErrorMessage, Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(activity.getResources().getString(R.string.error_message))
                    .setContentText(mErrorMessage)
                    .show();
        }
    }

    /**
     * Show the warning dialog.
     *
     * @param context             the context in which the dialog should be displayed.
     * @param okClickListener     Click listener for the confirm button.
     * @param cancelClickListener Click listener for the cancel button.
     */
    public static void showWarningSweetDialog(Activity context, SweetAlertDialog.OnSweetClickListener okClickListener,
                                              SweetAlertDialog.OnSweetClickListener cancelClickListener) {
        if (context != null && !context.isFinishing()) {
            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(context.getResources().getString(R.string.connection_fail))
                    .setContentText(context.getResources().getString(R.string.server_problem))
                    .setCancelText(context.getResources().getString(R.string.try_again))
                    .setConfirmText(context.getResources().getString(R.string.cancel))
                    .setConfirmClickListener(okClickListener)
                    .showCancelButton(true)
                    .setCancelClickListener(cancelClickListener)
                    .show();
        }
    }

    /**
     * To check Internet Availability
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected();
    }
}


