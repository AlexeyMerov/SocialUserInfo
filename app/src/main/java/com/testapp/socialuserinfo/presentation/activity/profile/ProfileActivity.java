package com.testapp.socialuserinfo.presentation.activity.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;
import android.widget.TextView;

import com.testapp.socialuserinfo.R;
import com.testapp.socialuserinfo.dagger.component.DaggerPresentersComponent;
import com.testapp.socialuserinfo.dagger.component.InteractorsComponent;
import com.testapp.socialuserinfo.dagger.module.PresentersModule;
import com.testapp.socialuserinfo.data.repository.model.User;
import com.testapp.socialuserinfo.presentation.base.BaseActivity;
import com.testapp.socialuserinfo.utils.GlideLoader;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity implements ProfileContract.View {

    private final static String USER_ID = "user_id";

    @BindView(R.id.avatarImageView)
    ImageView avatarImageView;

    @BindView(R.id.firstNameTextView)
    TextView firstNameTextView;

    @BindView(R.id.lastNameTextView)
    TextView lastNameTextView;

    @BindView(R.id.emailTextView)
    TextView emailTextView;

    @BindView(R.id.phoneTextView)
    TextView phoneTextView;

    @BindView(R.id.genderTextView)
    TextView genderTextView;

    @BindView(R.id.birthdayTextView)
    TextView birthdayTextView;


    @Inject
    ProfilePresenter mPresenter;

    public ProfileActivity() {
        // Required empty public constructor
    }

    public static Intent newInstance(Context context, long userId) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(USER_ID, userId);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        unbinder = ButterKnife.bind(this);
        mPresenter.attachWithView(this);

        initializeToolbar();

        if (getIntent().getExtras() != null) {
            mPresenter.loadUser(getIntent().getExtras().getLong(USER_ID, 0));
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onInjectDependencies(InteractorsComponent interactorsComponent) {
        DaggerPresentersComponent.builder()
                .interactorsComponent(interactorsComponent)
                .presentersModule(new PresentersModule())
                .build()
                .inject(this);
    }

    @Override
    public void displayUser(User user) {
        GlideLoader.loadImage(avatarImageView, user.getAvatar());

        firstNameTextView.setText(user.getFirstName());
        lastNameTextView.setText(user.getLastName());
        emailTextView.setText(user.getEmail());
        phoneTextView.setText(user.getPhoneNumber());
        genderTextView.setText(user.getGender());
        birthdayTextView.setText(user.getBirtday());
    }
}
