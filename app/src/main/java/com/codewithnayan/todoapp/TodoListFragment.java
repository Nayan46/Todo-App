package com.codewithnayan.todoapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codewithnayan.todoapp.data.TodoDAO;
import com.codewithnayan.todoapp.data.TodoRoomDatabase;
import com.codewithnayan.todoapp.model.ETodo;
import com.codewithnayan.todoapp.viewmodel.TodoViewModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class TodoListFragment extends Fragment {

    View rootView;

    RecyclerView todoRecyclerView;
    TodoViewModel mTodoViewModel;
    TodoRoomDatabase database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);
        todoRecyclerView = rootView.findViewById(R.id.todo_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        todoRecyclerView.setLayoutManager(layoutManager);

        mTodoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);

        database = TodoRoomDatabase.getDatabase(getActivity().getApplicationContext());

        LoadRV();

        new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        List<ETodo> todoList = mTodoViewModel.getmAllTodos().getValue();
                        TodoAdapter adapter = new TodoAdapter(todoList);
                        ETodo todo = adapter.getTodoAt(viewHolder.getAdapterPosition());
                        mTodoViewModel.deleteById(todo);
                    }
                }).attachToRecyclerView(todoRecyclerView);
        return rootView;
    }


    void LoadRV() {

//

        mTodoViewModel.getmAllTodos().observe(this, new Observer<List<ETodo>>() {
            @Override
            public void onChanged(List<ETodo> todoList) {
                TodoAdapter adapter = new TodoAdapter(todoList);
                todoRecyclerView.setAdapter(adapter);
            }
        });


//        TodoDAO todoDAO = database.mTodoDAO();
//        List<ETodo> list = todoDAO.getAllTodos();
//        TodoAdapter adapter = new TodoAdapter(list);
//        todoRecyclerView.setAdapter(adapter);



    }


    private class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle, mDate;

        public TodoViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_todo, parent, false));
            mTitle = itemView.findViewById(R.id.list_item_tv_Name);
            mDate = itemView.findViewById(R.id.list_item_tv_Date);

        }


        public void bind(ETodo todo) {
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
            mTitle.setText(todo.getTitle());
            mDate.setText(dateFormater.format(todo.getTodo_date()));
        }

    }

    private class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
        List<ETodo> mETodoList;

        public TodoAdapter(List<ETodo> todoList) {
            mETodoList = todoList;
        }


        @NonNull
        @Override
        public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TodoViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
            ETodo todo = mETodoList.get(position);
            holder.bind(todo);
        }

        @Override
        public int getItemCount() {
            return mETodoList.size();
        }

        public ETodo getTodoAt(int index) {
            return mETodoList.get(index);
        }

    }
}
