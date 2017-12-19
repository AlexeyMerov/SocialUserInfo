package com.testapp.socialuserinfo.presentation.base;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.testapp.socialuserinfo.R;
import com.testapp.socialuserinfo.dagger.component.InteractorsComponent;
import com.testapp.socialuserinfo.presentation.App;
import com.testapp.socialuserinfo.utils.DLog;

import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements BaseContract.View {

    protected Toolbar toolbar;
    protected Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDaggerDependencies();
    }

    private void createDaggerDependencies() {
        onInjectDependencies(((App) getApplication()).getInteractorsComponent());
    }

    public abstract void onInjectDependencies(InteractorsComponent interactorsComponent);

    protected void initializeToolbar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Nullable
    protected Toolbar getToolbar() {
        return toolbar;
    }

    protected void setToolbarTitle(CharSequence title) {
        setToolbarTitle(title.toString());
    }

    protected void setToolbarTitle(String title) {
        if (toolbar != null) toolbar.setTitle(title);
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View v = getCurrentFocus();
        if (v instanceof EditText) {
            View newTouchedView = findViewAt((ViewGroup) v.getRootView(), (int) event.getRawX(), (int) event.getRawY());
            if (newTouchedView instanceof EditText) return super.dispatchTouchEvent(event);

            Rect outRect = new Rect();
            v.getGlobalVisibleRect(outRect);
            if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) hideKeyboard();
        }

        return super.dispatchTouchEvent(event);
    }

    private View findViewAt(ViewGroup viewGroup, int x, int y) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                View foundView = findViewAt((ViewGroup) child, x, y);
                if (foundView != null && foundView.isShown()) return foundView;
            } else {
                int[] location = new int[2];
                child.getLocationOnScreen(location);
                Rect rect = new Rect(location[0], location[1], location[0] + child.getWidth(), location[1] + child.getHeight());
                if (rect.contains(x, y)) return child;
            }
        }
        return null;
    }

    protected void hideKeyboard() {
        try {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            DLog.e(e);
        }
    }
}
