package com.example.ToDo

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter(
    private val todos: MutableList<todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    class TodoViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    fun addTodo(todo_ : todo){
        todos.add(todo_)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos(){
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(TodoTitle : TextView, isChecked : Boolean) {
        if(isChecked){
            TodoTitle.paintFlags = TodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        }
        else{
            TodoTitle.paintFlags = TodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.itemView.apply {
            TodoTitle.text = curTodo.title
            checked.isChecked = curTodo.isChecked
            toggleStrikeThrough(TodoTitle, checked.isChecked)
            checked.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(TodoTitle, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    fun getAllTodos() : MutableList<todo>{
        return todos
    }
}