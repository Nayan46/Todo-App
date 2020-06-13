package com.codewithnayan.todoapp.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.codewithnayan.todoapp.model.ETodo;

import java.util.List;

public class TodoRepository {

    private TodoDAO mTodoDAO;
    private LiveData<List<ETodo>> mAllTodoList;

    public TodoRepository(Application application) {
        TodoRoomDatabase database = TodoRoomDatabase.getDatabase(application);
        mTodoDAO = database.mTodoDAO();
        mAllTodoList = mTodoDAO.getAllTodos();
    }

    public LiveData<List<ETodo>> getmAllTodoList() {
        return mAllTodoList;
    }

    public void insert(ETodo todo)
    {
        new insertTodoAsynchTask(mTodoDAO).execute(todo);
    }

    private static class insertTodoAsynchTask extends AsyncTask<ETodo, Void, Void>

    {
        private TodoDAO mTodoDAO;
        private insertTodoAsynchTask(TodoDAO todoDAO)
        {
            mTodoDAO = todoDAO;
        }

            //Generating Override Method (doInBackground)


        @Override
        protected Void doInBackground(ETodo... todos) {

            mTodoDAO.insert(todos[0]);
            return null;
        }
    }


}
