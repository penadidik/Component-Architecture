package info.penadidik.architecturecomponent.second.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import info.penadidik.architecturecomponent.second.model.Todo;

@Database(entities = {Todo.class}, version = 1, exportSchema = false)
public abstract class MyRoomDb extends RoomDatabase {

    public static final String DB_NAME = "app_db";
    public static final String TABLE_NAME_TODO = "todo";

    public abstract DaoAccess daoAccess();

}
