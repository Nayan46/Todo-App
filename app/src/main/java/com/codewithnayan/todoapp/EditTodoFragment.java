package com.codewithnayan.todoapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.Toast;

import com.codewithnayan.todoapp.data.TodoRoomDatabase;
import com.codewithnayan.todoapp.model.ETodo;
import com.codewithnayan.todoapp.viewmodel.TodoViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class EditTodoFragment extends Fragment {

    View rootView;
    EditText txtTitle, txtDescription, txtDate;
    RadioGroup rgPriority;
    RadioButton rbHigh, rbMedium, rbLow, rbSelected;
    CheckBox chkIsComplete;
    Button btnSave, btnCancel;



    AlertDialog.Builder mAlertDialog;
    DatePickerDialog mDatePicker;


    public static final int HIGH_PRIORITY=1;
    public static final int MEDIUM_PRIORITY=2;
    public static final int LOW_PRIORITY=3;

    private TodoViewModel mTodoViewModel;

    TodoRoomDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_edit_todo, container, false);

        database=TodoRoomDatabase.getDatabase(getActivity().getApplicationContext());

        mTodoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);

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
                SaveTodo();
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
        ETodo todo = new ETodo();
        Date todoDate;
        int priority=1;
        int checkedPriority=-1;

        todo.setTitle(txtTitle.getText().toString());
        todo.setDescription(txtDescription.getText().toString());

        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            todoDate = (Date)formatter.parse(txtDate.getText().toString());
            todo.setTodo_date(todoDate);
        }

        catch (ParseException e)
        {
            e.printStackTrace();
        }

        checkedPriority=rgPriority.getCheckedRadioButtonId();
        switch (checkedPriority)
        {
            case R.id.edit_rb_high:
                priority=HIGH_PRIORITY;
                break;

            case R.id.edit_rb_medium:
                priority=MEDIUM_PRIORITY;
                break;

            case R.id.edit_rb_low:
                priority=LOW_PRIORITY;
                break;
        }

        // setIS_completed is from ETOdo class and chkIsComplete is from variable.
        todo.setPriority(priority);
        todo.setIs_completed(chkIsComplete.isChecked());

        //Save  Object into database
        mTodoViewModel.insert(todo);


        Toast.makeText(getActivity(),getText(R.string.crud_save), Toast.LENGTH_SHORT).show();



        Intent intent= new Intent(getActivity(), MainActivity.class);
        startActivity(intent);

    }

}
