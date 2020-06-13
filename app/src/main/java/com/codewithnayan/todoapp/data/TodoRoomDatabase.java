package com.codewithnayan.todoapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.codewithnayan.todoapp.model.ETodo;

@Database(entities = {ETodo.class}, version = 1, exportSchema = false)

public abstract class TodoRoomDatabase extends RoomDatabase {

    public  abstract TodoDAO mTodoDAO();

    private static TodoRoomDatabase INSTANCE;
    public static TodoRoomDatabase getDatabase(Context context)
    {
        if(INSTANCE==null)
        {
            synchronized (TodoRoomDatabase.class)
            {
                if (INSTANCE==null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoRoomDatabase.class, "todo.db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
