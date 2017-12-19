package com.testapp.socialuserinfo.presentation.activity.main;

import android.accounts.Account;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Gender;
import com.google.api.services.people.v1.model.Person;
import com.testapp.socialuserinfo.R;
import com.testapp.socialuserinfo.dagger.component.DaggerPresentersComponent;
import com.testapp.socialuserinfo.dagger.component.InteractorsComponent;
import com.testapp.socialuserinfo.dagger.module.PresentersModule;
import com.testapp.socialuserinfo.presentation.activity.users_list.UsersListActivity;
import com.testapp.socialuserinfo.presentation.base.BaseActivity;
import com.testapp.socialuserinfo.utils.DLog;
import com.testapp.socialuserinfo.utils.DateHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements MainContract.View {

    private static final String HAS_CARD = "has_card";
    private static final int RC_SIGN_IN = 1;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    @Inject
    MainPresenter mPresenter;

    @BindView(R.id.sign_in_button)
    SignInButton gpLoginButton;
    @BindView(R.id.login_button)
    LoginButton fbLoginButton;

    @BindView(R.id.firstNameLayout)
    TextInputLayout firstNameLayout;
    @BindView(R.id.firstNameEditText)
    TextInputEditText firstNameEditText;

    @BindView(R.id.lastNameLayout)
    TextInputLayout lastNameLayout;
    @BindView(R.id.lastNameEditText)
    TextInputEditText lastNameEditText;

    @BindView(R.id.emailLayout)
    TextInputLayout emailLayout;
    @BindView(R.id.emailEditText)
    TextInputEditText emailEditText;

    @BindView(R.id.phoneNumberLayout)
    TextInputLayout phoneNumberLayout;
    @BindView(R.id.phoneNumberEditText)
    TextInputEditText phoneNumberEditText;

    @BindView(R.id.genderLayout)
    View genderLayout;
    @BindView(R.id.genderTextView)
    TextView genderTextView;

    @BindView(R.id.birthdayLayout)
    View birthdayLayout;
    @BindView(R.id.birthdayTextView)
    TextView birthdayTextView;

    @BindView(R.id.buttonLayout)
    View buttonLayout;
    @BindView(R.id.additionalFieldsLayout)
    View additionalFieldsLayout;

    @BindView(R.id.continueButton)
    Button continueButton;

    @BindColor(R.color.black)
    int mColorBlack;
    @BindColor(R.color.grey)
    int mColorGrey;
    @BindColor(R.color.white)
    int mColorWhite;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;

    private String mAvatarUrl, mEmail, mFirstName, mLastName, mPhoneNumber, mBirthdayDate, mGender;

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        mPresenter.attachWithView(this);

        initializeToolbar();
        initGoogleLogin();
        initFBLogin();
        initViews();
    }

    private void initViews() {
        initCalendar();
        initGender();
    }

    private void initGender() {
        PopupMenu popup = new PopupMenu(getContext(), genderLayout, Gravity.TOP);
        popup.getMenuInflater().inflate(R.menu.gender_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            genderTextView.setText(item.getTitle());
            mGender = item.getTitle().toString();
            return true;
        });

        genderLayout.setOnClickListener(view -> popup.show());
    }

    private void initCalendar() {
        Calendar calendar = Calendar.getInstance();
        int currentSelectedDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentSelectedMonth = calendar.get(Calendar.MONTH);
        int currentSelectedYear = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (calendarView, year, month, dayOfMonth) -> {
                    Calendar resultCalendar = Calendar.getInstance();
                    resultCalendar.set(Calendar.YEAR, year);
                    resultCalendar.set(Calendar.MONTH, month);
                    resultCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    resultCalendar.setTime(resultCalendar.getTime());

                    String stringFromDate = DateHelper.getStringFromDate(
                            resultCalendar.getTime(),
                            DateHelper.FORMAT_DD_MM_YY);
                    birthdayTextView.setText(stringFromDate);
                    mBirthdayDate = stringFromDate;

                }, currentSelectedYear, currentSelectedMonth, currentSelectedDay);

        birthdayLayout.setOnClickListener(view -> datePickerDialog.show());
    }

    private void initGoogleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        gpLoginButton.setOnClickListener(view -> signIn());
    }

    private void initFBLogin() {
        mCallbackManager = CallbackManager.Factory.create();
        fbLoginButton.setReadPermissions("email");
        fbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        (object, response) -> {
                            try {
                                String userId = object.optString("id");
                                mAvatarUrl = String.format("https://graph.facebook.com/%s/picture?type=large", userId);
                                mFirstName = object.optString("first_name");
                                mLastName = object.optString("last_name");
                                mEmail = object.optString("email");
                                mBirthdayDate = object.optString("birthday"); // 01/31/1980 format
                                mGender = object.optString("gender");
                                proceedToNextScreen();
                            } catch (Exception e) {
                                DLog.e(e);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                DLog.d("onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                DLog.e(exception);
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            mFirstName = account.getGivenName();
            mLastName = account.getFamilyName();
            mEmail = account.getEmail();
            mAvatarUrl = String.valueOf(account.getPhotoUrl());

            List<String> scopes = new ArrayList<>();
            scopes.add(Scopes.PROFILE);

            Observable.fromCallable(() -> {
                GoogleAccountCredential credential =
                        GoogleAccountCredential.usingOAuth2(MainActivity.this, scopes);
                credential.setSelectedAccount(new Account(account.getEmail(), "com.google"));
                PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                        .setApplicationName("SocialUserInfo")
                        .build();
                try {
                    Person meProfile = service.people().get("people/me")
                            .setPersonFields("genders,phoneNumbers,birthdays")
                            .execute();
                    List<Gender> genders = meProfile.getGenders();
                    if (genders != null && genders.size() > 0) return genders.get(0).getValue();

                } catch (IOException e) {
                    DLog.e(e);
                }
                return "";
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(gender -> {
                        mGender = gender;
                        proceedToNextScreen();
                    });
        } catch (ApiException e) {
            DLog.e(e);
        }
    }

    private void proceedToNextScreen() {
        if (checkFields()) {
            mPresenter.saveUser(mAvatarUrl, mEmail, mFirstName, mLastName, mPhoneNumber, mBirthdayDate, mGender);
            startActivity(UsersListActivity.newInstance(getContext()));
        } else continueButton.setOnClickListener(view -> {
            setDataFromFields();
            proceedToNextScreen();
        });
    }

    private void setDataFromFields() {
        if (TextUtils.isEmpty(mFirstName)) mFirstName = firstNameEditText.getText().toString();
        if (TextUtils.isEmpty(mLastName)) mLastName = lastNameEditText.getText().toString();
        if (TextUtils.isEmpty(mEmail)) mEmail = emailEditText.getText().toString();
        if (TextUtils.isEmpty(mPhoneNumber))
            mPhoneNumber = phoneNumberEditText.getText().toString();
        if (TextUtils.isEmpty(mGender)) mGender = genderTextView.getText().toString();
        if (TextUtils.isEmpty(mBirthdayDate)) mBirthdayDate = birthdayTextView.getText().toString();
    }

    private boolean checkFields() {
        boolean fNameCorrect = checkFieldNotEmpty(mFirstName, firstNameLayout);
        boolean lNameCorrect = checkFieldNotEmpty(mLastName, lastNameLayout);
        boolean emailCorrect = checkFieldNotEmpty(mEmail, emailLayout);
        boolean phoneCorrect = checkFieldNotEmpty(mPhoneNumber, phoneNumberLayout);
        boolean genderCorrect = checkFieldNotEmpty(mGender, genderLayout);
        boolean birthdayCorrect = checkFieldNotEmpty(mBirthdayDate, birthdayLayout);

        boolean allCorrect = fNameCorrect && lNameCorrect && emailCorrect && phoneCorrect
                && genderCorrect && birthdayCorrect;

        additionalFieldsLayout.setVisibility(allCorrect ? View.GONE : View.VISIBLE);
        buttonLayout.setVisibility(!allCorrect ? View.GONE : View.VISIBLE);

        return allCorrect;
    }

    private boolean checkFieldNotEmpty(String string, View view) {
        if (TextUtils.isEmpty(string)) {
            view.setVisibility(View.VISIBLE);
            return false;
        } else if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        }
        return true;
    }

    private void resetToolbarState() {
        invalidateOptionsMenu();
        if (getActionBar() != null) {
            getActionBar().setSubtitle(null);
            getActionBar().setDisplayHomeAsUpEnabled(false);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        hideKeyboard();
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment != null) currentFragment.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    private Fragment getCurrentFragment() {
        try {
            FragmentManager fragmentManager = getFragmentManager();
            String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
            return fragmentManager.findFragmentByTag(fragmentTag);
        } catch (Exception e) {
            DLog.e(e);
            return null;
        }
    }

}
