package com.codewithnayan.todoapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;


public class EditTodoFragment extends Fragment {

    View rootView;
    EditText txtTitle, txtDescription, txtDate;
    RadioGroup rgPriority;
    RadioButton rbHigh, rbMedium, rbLow;
    CheckBox chkIsComplete;
    Button btnSave, btnCancel;



    AlertDialog.Builder mAlertDialog;
    DatePickerDialog mDatePicker;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_edit_todo, container, false);

        txtTitle = rootView.findViewById(R.id.edit_txt_title);
        txtDescription = rootView.findViewById(R.id.edit_txt_description);
        txtDate = rootView.findViewById(R.id.edit_txt_date);

        rgPriority = rootView.findViewById(R.id.edit_rg_priority);
        rbHigh = rootView.findViewById(R.id.edit_rb_high);
        rbMedium = rootView.findViewById(R.id.edit_rb_medium);
        rbLow = rootView.findViewById(R.id.edit_rb_low);
        chkIsComplete = rootView.findViewById(R.id.edit_chk_iscomplete);

        btnSave = rootView.findViewById(R.id.edit_btn_save);
        btnCancel = rootView.findViewById(R.id.edit_btn_cancel);


        //onclicklistner for Save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //Onclicklistner for Cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayAlertDialog();
            }
        });


        // OnTouchListner
        txtDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    DisplayTodoDate();
                }

                return false;
            }
        });

        return rootView;
    }


    void DisplayAlertDialog()
    {
        mAlertDialog = new AlertDialog.Builder(getContext());

        mAlertDialog.setMessage(getString(R.string.edit_cancel_prompt))
                .setCancelable(false)
                .setTitle(getString(R.string.app_name))
                .setIcon(R.drawable.todo);

        mAlertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        mAlertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        mAlertDialog.show();
    }

    void DisplayTodoDate()
    {
        Calendar calendar = Calendar.getInstance();
        int cDay = calendar.get(Calendar.DAY_OF_MONTH);
        int cMonth = calendar.get(Calendar.MONTH);
        int cYear = calendar.get(Calendar.YEAR);

        mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txtDate.setText(year + "-" + month + "-" + dayOfMonth);
            }
        }, cYear,cMonth,cDay);
        mDatePicker.show();

    }

    void SaveTodo()
    {

    }

}
