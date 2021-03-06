package info.penadidik.architecturecomponent.second;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import info.penadidik.architecturecomponent.R;
import info.penadidik.architecturecomponent.second.model.Todo;

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.ViewHolder> {

    private final List<Todo> todoList;
    private final SecondAdapter.ClickListener clickListener;

    public SecondAdapter(ClickListener clickListener, ArrayList<Todo> todos) {
        this.clickListener = clickListener;
        this.todoList = todos;
    }

    @Override
    public SecondAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_second, parent, false);
        SecondAdapter.ViewHolder viewHolder = new SecondAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SecondAdapter.ViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.txtName.setText(todo.name);
        holder.txtNo.setText("#" + todo.todo_id);
        holder.txtDesc.setText(todo.description);
        holder.txtCategory.setText(todo.category);

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }


    public void updateTodoList(List<Todo> data) {
        todoList.clear();
        todoList.addAll(data);
        notifyDataSetChanged();
    }

    public void addRow(Todo data) {
        todoList.add(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public TextView txtNo;
        public TextView txtDesc;
        public TextView txtCategory;
        public CardView cardView;

        public ViewHolder(View view) {
            super(view);

            txtNo = view.findViewById(R.id.txtNo);
            txtName = view.findViewById(R.id.txtName);
            txtDesc = view.findViewById(R.id.txtDesc);
            txtCategory = view.findViewById(R.id.txtCategory);
            cardView = view.findViewById(R.id.cardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.launchIntent(todoList.get(getAdapterPosition()).todo_id);
                }
            });
        }
    }

    public interface ClickListener {
        void launchIntent(int id);
    }
}
