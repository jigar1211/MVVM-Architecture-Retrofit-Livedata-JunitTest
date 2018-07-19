package wars.star.com.starwars.Utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import wars.star.com.starwars.R;

/**
 * Created by T183 on 17-Jul-18.
 */

public class ProgressDialog extends Dialog {

    public Context c;
    public Dialog d;

    public ProgressDialog(@NonNull Context context) {
        super(context);
        this.c = context;
    }

    public ProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        c = context;
        d = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
    }
}
