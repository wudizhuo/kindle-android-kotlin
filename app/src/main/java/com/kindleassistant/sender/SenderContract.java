package com.kindleassistant.sender;

import com.kindleassistant.common.BasePresenter;


public class SenderContract {
    public interface View {
    }

    public interface Presenter extends BasePresenter<View> {
        void loadData();
    }
}
