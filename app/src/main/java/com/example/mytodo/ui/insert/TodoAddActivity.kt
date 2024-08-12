package com.example.mytodo.ui.insert

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mytodo.R
import com.example.mytodo.data.Todo
import com.example.mytodo.databinding.ActivityTodoAddBinding
import com.example.mytodo.helper.DateHelper
import com.example.mytodo.ui.main.ViewModelFactory

class TodoAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoAddBinding

    private val todoAddViewModel by viewModels<TodoAddViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private var isEdit = false
    private var todo: Todo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityTodoAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        todo = intent.getParcelableExtra(EXTRA_NOTE)
        if (todo != null){
            isEdit = true
        }else{
            todo = Todo()
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit){
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (todo != null) {
                todo?.let { todo ->
                    binding.edtTitle.setText(todo.title)
                    binding.edtDescription.setText(todo.description)
                }
            }
        }else{
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSubmit.text = btnTitle


        binding.btnSubmit.setOnClickListener {
            val title = binding.edtTitle.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()
            when{
                title.isEmpty() -> {
                    binding.edtTitle.error = getString(R.string.empty)
                }
                description.isEmpty() -> {
                    binding.edtDescription.error = getString(R.string.empty)
                }
             else ->{
                 todo.let { todo ->
                     todo?.title = title
                     todo?.description = description
                 }
                 if (isEdit){
                     todoAddViewModel.update(todo as Todo)
                     showToast(getString(R.string.changed))
                 }else{
                     todo.let { todo->
                         todo?.date = DateHelper.getCurrentDate()
                     }
                     todoAddViewModel.insert(todo as Todo)
                     showToast(getString(R.string.added))
                 }
                 finish()
             }
            }
        }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showAlertDialog(ALERT_DIALOG_CLOSE)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit){
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    todoAddViewModel.delete(todo as Todo)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    companion object{
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }


}