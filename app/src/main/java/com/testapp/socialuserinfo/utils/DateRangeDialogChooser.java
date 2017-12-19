package com.testapp.socialuserinfo.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StyleRes;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

public class DateRangeDialogChooser {

    private Context mContext;

    @StyleRes
    private int mThemeResId;

    private Date mMinDateOriginal;
    private Date mMaxDateOriginal;

    private Date mMinDate;
    private Date mMaxDate;

    private String mPositiveButtonText;
    private String mNegativeButtonText;

    private View mCancelView;

    private OnDatePickedListener mOnDatePickedListener;

    public DateRangeDialogChooser(Context context, @StyleRes int themeResId, String positiveButtonText, String negativeButtonText,
                                  Date minDateOriginal, Date maxDateOriginal,
                                  OnDatePickedListener onDatePickedListener, View cancelView) {
        mContext = context;
        mThemeResId = themeResId;
        mPositiveButtonText = positiveButtonText;
        mNegativeButtonText = negativeButtonText;
        mMinDateOriginal = minDateOriginal;
        mMaxDateOriginal = maxDateOriginal;
        mOnDatePickedListener = onDatePickedListener;
        mCancelView = cancelView;
    }

    private static void setCalendarDayMinimum(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
    }

    private static void setCalendarDayMaximum(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));
    }

    public void showDatePicker(final boolean isMaxDate) {
        if (mMinDate == null) mMinDate = mMinDateOriginal;
        if (mMaxDate == null) mMaxDate = mMaxDateOriginal;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(isMaxDate ? mMaxDate : mMinDate);
        int currentSelectedDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentSelectedMonth = calendar.get(Calendar.MONTH);
        int currentSelectedYear = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, mThemeResId, (view, year, month, dayOfMonth) -> {
            Calendar resultCalendar = Calendar.getInstance();
            resultCalendar.set(Calendar.YEAR, year);
            resultCalendar.set(Calendar.MONTH, month);
            resultCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            resultCalendar.setTime(resultCalendar.getTime());

            if (isMaxDate) {
                setCalendarDayMaximum(resultCalendar);
                mMaxDate = resultCalendar.getTime();
            } else {
                setCalendarDayMinimum(resultCalendar);
                mMinDate = resultCalendar.getTime();
            }

            mOnDatePickedListener.onCall(mMinDate, mMaxDate);

            mCancelView.setVisibility(View.VISIBLE);
            if (!mCancelView.hasOnClickListeners()) {
                mCancelView.setOnClickListener(v -> {
                    mOnDatePickedListener.onCall(mMinDateOriginal, mMaxDateOriginal);
                    mCancelView.setVisibility(View.GONE);
                });
            }

        }, currentSelectedYear, currentSelectedMonth, currentSelectedDay);

        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, mPositiveButtonText, datePickerDialog);
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, mNegativeButtonText, datePickerDialog);

        calendar.setTime(!isMaxDate ? mMinDateOriginal : mMinDate);
        setCalendarDayMinimum(calendar);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        calendar.setTime(isMaxDate ? mMaxDateOriginal : mMaxDate);
        setCalendarDayMaximum(calendar);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        datePickerDialog.show();
    }

    public interface OnDatePickedListener {
        void onCall(Date minDate, Date maxDate);
    }
}
