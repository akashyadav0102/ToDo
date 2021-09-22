package com.example.ToDo

import ToDo.R
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val shPref  = getSharedPreferences("shPref",Context.MODE_PRIVATE)
        todoAdapter = TodoAdapter(mutableListOf())
        todolist.adapter = todoAdapter
        todolist.layoutManager = LinearLayoutManager(this)

        if(shPref != null){
            val initList = shPref.all

            val i: Iterator<*> = initList.iterator()
            while (i.hasNext()) {
                val key = i.next().toString().replace("=","")

                val todo_ = todo(key)
                todoAdapter.addTodo(todo_)
            }
        }

        addtodo.setOnClickListener{
            val todoTitle = entertext.text.toString()
            if (todoTitle.isNotEmpty()){
                val todo_ = todo(todoTitle)
                todoAdapter.addTodo(todo_)
                entertext.text.clear()
            }
        }

        deletetodo.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }
    }

    override fun onStop() {
        val listToSave = todoAdapter.getAllTodos()

        val shPref = getSharedPreferences("shPref",Context.MODE_PRIVATE)
        shPref.edit().clear().apply()

        for(item in 0 until listToSave.size){
            shPref.edit().putString(listToSave[item].title,"").apply()
        }

        super.onStop()
    }

}