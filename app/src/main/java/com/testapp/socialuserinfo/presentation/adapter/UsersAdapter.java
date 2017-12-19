package com.testapp.socialuserinfo.presentation.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.testapp.socialuserinfo.R;
import com.testapp.socialuserinfo.data.repository.model.User;
import com.testapp.socialuserinfo.utils.DLog;
import com.testapp.socialuserinfo.utils.GlideLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class UsersAdapter extends RealmRecyclerViewAdapter<User, UsersAdapter.ViewHolder> {

    private Context mContext;
    private Consumer<Long> mConsumer;

    public UsersAdapter(@Nullable OrderedRealmCollection<User> data, Context context, Consumer<Long> consumer) {
        super(data, true, true);
        mContext = context;
        mConsumer = consumer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_user, parent, false));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            User item = getItem(position);
            holder.bind(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mainLayout)
        View mainLayout;

        @BindView(R.id.avatarImageView)
        ImageView avatarImageView;

        @BindView(R.id.firstNameTextView)
        TextView firstNameTextView;

        @BindView(R.id.lastNameTextView)
        TextView lastNameTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(User item) {
            try {
                GlideLoader.loadImage(avatarImageView, item.getAvatar());

                firstNameTextView.setText(item.getFirstName());
                lastNameTextView.setText(item.getLastName());

                mainLayout.setOnClickListener(view -> {
                    if (mConsumer != null) {
                        try {
                            mConsumer.accept(item.getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                DLog.e(e);
            }
        }
    }
}
