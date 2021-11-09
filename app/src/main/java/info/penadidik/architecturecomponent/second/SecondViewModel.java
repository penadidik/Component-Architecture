package info.penadidik.architecturecomponent.second;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.penadidik.architecturecomponent.second.data.DaoAccess;
import info.penadidik.architecturecomponent.second.data.MyRoomDb;
import info.penadidik.architecturecomponent.second.model.Todo;

@SuppressLint("StaticFieldLeak")
public class SecondViewModel extends ViewModel {

    MyRoomDb myRoomDb;
    DaoAccess dao;
    private final String[] categories = {
            "All",
            "Android",
            "iOS",
            "Kotlin",
            "Swift"
    };

    ArrayList<Todo> todoArrayList = new ArrayList<>();
    ArrayList<String> spinnerList = new ArrayList<>(Arrays.asList(categories));

    public static final int NEW_TODO_REQUEST_CODE = 200;
    public static final int UPDATE_TODO_REQUEST_CODE = 300;
    public MutableLiveData<Todo> eventFetchByIdAndInsertTodo = new MutableLiveData<>();
    public MutableLiveData<List<Todo>> eventFilterTodos = new MutableLiveData<>();
    public MutableLiveData<Todo> eventShowTodoById = new MutableLiveData<>();
    public MutableLiveData<Integer> eventUpdateTodoById = new MutableLiveData<>();
    public MutableLiveData<Integer> eventDeleteTodoById = new MutableLiveData<>();
    public MutableLiveData<Integer> eventUpdateRecycle = new MutableLiveData<>();

    public void onStart(Context context){
        checkIfAppLaunchedFirstTime(context);
    }

    public void loadAllTodos() {
        new AsyncTask<String, Void, List<Todo>>() {
            @Override
            protected List<Todo> doInBackground(String... params) {
                return myRoomDb.daoAccess().fetchAllTodos();
            }

            @Override
            protected void onPostExecute(List<Todo> todoList) {
                todoArrayList.clear();
                todoArrayList.addAll(todoList);
                eventUpdateRecycle.postValue(0);
            }
        }.execute();
    }

    public void insertList(List<Todo> todoList) {
        new AsyncTask<List<Todo>, Void, Void>() {
            @Override
            protected Void doInBackground(List<Todo>... params) {
                myRoomDb.daoAccess().insertTodoList(params[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void voids) {
                eventUpdateRecycle.postValue(0);
            }
        }.execute(todoList);

    }

    public void fetchTodoByIdAndInsert(int id) {
        new AsyncTask<Integer, Void, Todo>() {
            @Override
            protected Todo doInBackground(Integer... params) {
                return myRoomDb.daoAccess().fetchTodoListById(params[0]);

            }

            @Override
            protected void onPostExecute(Todo todoList) {
                eventFetchByIdAndInsertTodo.postValue(todoList);
            }
        }.execute(id);

    }

    public void loadFilteredTodos(String category) {
        new AsyncTask<String, Void, List<Todo>>() {
            @Override
            protected List<Todo> doInBackground(String... params) {
                return myRoomDb.daoAccess().fetchTodoListByCategory(params[0]);

            }

            @Override
            protected void onPostExecute(List<Todo> todoList) {
                eventFilterTodos.postValue(todoList);
            }
        }.execute(category);

    }

    public void fetchTodoById(final int todo_id) {
        new AsyncTask<Integer, Void, Todo>() {
            @Override
            protected Todo doInBackground(Integer... params) {

                return myRoomDb.daoAccess().fetchTodoListById(params[0]);

            }

            @Override
            protected void onPostExecute(Todo todo) {
                super.onPostExecute(todo);
//                inTitle.setText(todo.name);
//                inDesc.setText(todo.description);
//                spinner.setSelection(spinnerList.indexOf(todo.category));
//
//                updateTodo = todo;
                eventShowTodoById.postValue(todo);
            }
        }.execute(todo_id);

    }

    public void updateTodo(Todo todo) {
        new AsyncTask<Todo, Void, Integer>() {
            @Override
            protected Integer doInBackground(Todo... params) {
                return myRoomDb.daoAccess().updateTodo(params[0]);
            }

            @Override
            protected void onPostExecute(Integer number) {
                super.onPostExecute(number);
                eventUpdateTodoById.postValue(number);
//                Intent intent = getIntent();
//                intent.putExtra("isNew", false).putExtra("number", number);
//                setResult(RESULT_OK, intent);
//                finish();
            }
        }.execute(todo);

    }

    public void deleteTodo(Todo todo) {
        new AsyncTask<Todo, Void, Integer>() {
            @Override
            protected Integer doInBackground(Todo... params) {
                return myRoomDb.daoAccess().deleteTodo(params[0]);
            }

            @Override
            protected void onPostExecute(Integer number) {
                super.onPostExecute(number);

//                Intent intent = getIntent();
//                intent.putExtra("isDeleted", true).putExtra("number", number);
//                setResult(RESULT_OK, intent);
//                finish();
                eventDeleteTodoById.postValue(number);
            }
        }.execute(todo);

    }

    private void buildDummyTodos() {
        Todo todo = new Todo();
        todo.todo_id = 1;
        todo.name = "Android Retrofit Tutorial";
        todo.description = "Cover a tutorial on the Retrofit networking library using a RecyclerView to show the data.";
        todo.category = "Android";

        todoArrayList.add(todo);

        todo = new Todo();
        todo.todo_id = 2;
        todo.name = "iOS TableView Tutorial";
        todo.description = "Covers the basics of TableViews in iOS using delegates.";
        todo.category = "iOS";

        todoArrayList.add(todo);

        todo = new Todo();
        todo.todo_id = 3;
        todo.name = "Kotlin Arrays";
        todo.description = "Cover the concepts of Arrays in Kotlin and how they differ from the Java ones.";
        todo.category = "Kotlin";

        todoArrayList.add(todo);

        todo = new Todo();
        todo.todo_id = 4;
        todo.name = "Swift Arrays";
        todo.description = "Cover the concepts of Arrays in Swift and how they differ from the Java and Kotlin ones.";
        todo.category = "Swift";

        todoArrayList.add(todo);
        insertList(todoArrayList);
    }

    private void checkIfAppLaunchedFirstTime(Context context) {
        final String PREFS_NAME = "SharedPrefs";

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("firstTime", true)) {
            settings.edit().putBoolean("firstTime", false).apply();
            buildDummyTodos();
        } else {
            loadAllTodos();
        }
    }

}
