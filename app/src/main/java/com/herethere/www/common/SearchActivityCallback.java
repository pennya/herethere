package com.herethere.www.common;

import com.herethere.www.autosearch.AutoCompleteItem;

/**
 * Created by KIM on 2017-09-08.
 */

public interface SearchActivityCallback {

    void showToast(String msg);

    void sendSearchedData(AutoCompleteItem item);

}
