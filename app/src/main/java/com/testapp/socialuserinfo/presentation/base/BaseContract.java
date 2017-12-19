package com.testapp.socialuserinfo.presentation.base;

import android.content.Context;

public interface BaseContract {

    interface View {

        Context getContext();

    }

    interface Presenter<V extends View> {

    }

}
