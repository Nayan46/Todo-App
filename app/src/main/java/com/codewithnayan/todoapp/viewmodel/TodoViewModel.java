package com.codewithnayan.todoapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.codewithnayan.todoapp.data.TodoRepository;
import com.codewithnayan.todoapp.model.ETodo;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository mTodoRepository;
    private LiveData<List<ETodo>> mAllTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);

        mTodoRepository = new TodoRepository(application);
        mAllTodos = mTodoRepository.getmAllTodoList();
    }

    public LiveData<List<ETodo>> getmAllTodos(){
        return mAllTodos;
    }

    public ETodo getTodoById(int id)
    {
        return mTodoRepository.getTodoById(id);
    }

    public void insert(ETodo todo)
    {
        mTodoRepository.insert(todo);
    }

    public void update (ETodo todo)
    {
        mTodoRepository.update(todo);
    }

    public void deleteById(ETodo todo)
    {
        mTodoRepository.deleteById(todo);
    }

    public void deleteAll() {
        mTodoRepository.deleteAll();
    }
}
