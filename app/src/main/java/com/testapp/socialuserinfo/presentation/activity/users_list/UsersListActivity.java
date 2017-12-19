package com.testapp.socialuserinfo.presentation.activity.users_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.testapp.socialuserinfo.R;
import com.testapp.socialuserinfo.dagger.component.DaggerPresentersComponent;
import com.testapp.socialuserinfo.dagger.component.InteractorsComponent;
import com.testapp.socialuserinfo.dagger.module.PresentersModule;
import com.testapp.socialuserinfo.data.repository.model.User;
import com.testapp.socialuserinfo.presentation.activity.profile.ProfileActivity;
import com.testapp.socialuserinfo.presentation.adapter.UsersAdapter;
import com.testapp.socialuserinfo.presentation.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class UsersListActivity extends BaseActivity implements UsersListContract.View {

    @Inject
    UsersListPresenter mPresenter;

    @BindView(R.id.usersRecycler)
    RecyclerView usersRecycler;

    private UsersAdapter mUsersAdapter;


    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, UsersListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        unbinder = ButterKnife.bind(this);
        mPresenter.attachWithView(this);

        initializeToolbar();

        mPresenter.loadUsers();
    }

    @Override
    public void displayUsers(RealmResults<User> users) {
        if (mUsersAdapter == null) {
            mUsersAdapter = new UsersAdapter(users, getContext(), userIdLong -> {
                startActivity(ProfileActivity.newInstance(getContext(), userIdLong));
            });
            usersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            usersRecycler.setAdapter(mUsersAdapter);
        } else mUsersAdapter.updateData(users);
    }

    @Override
    public void onInjectDependencies(InteractorsComponent interactorsComponent) {
        DaggerPresentersComponent.builder()
                .interactorsComponent(interactorsComponent)
                .presentersModule(new PresentersModule())
                .build()
                .inject(this);
    }
}
