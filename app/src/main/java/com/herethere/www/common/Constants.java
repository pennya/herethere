package com.herethere.www.common;

import com.herethere.www.activity.ActivityManager;

/**
 * Created by jnshim on 2017-09-04.
 */

public interface Constants {

    public static final boolean isDebug = true;
    public static int INTRO_FINISH_TIME = 300;
    public ActivityManager activityManager = ActivityManager.getInstance();

    //requestcode
    public static final int SELECTED_ACTIVITY_REQUEST_CODE = 2001;

}
