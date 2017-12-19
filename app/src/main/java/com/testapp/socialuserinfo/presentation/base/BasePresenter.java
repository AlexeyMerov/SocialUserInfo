package com.testapp.socialuserinfo.presentation.base;

public abstract class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    private V mView;

    public void attachWithView(V view) {
        mView = view;
    }

    public V getView() {
        return mView;
    }
}
