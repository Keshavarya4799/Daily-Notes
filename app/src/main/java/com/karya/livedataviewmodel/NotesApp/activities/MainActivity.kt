package com.karya.livedataviewmodel.NotesApp.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.karya.livedataviewmodel.NotesApp.room.entity.Notes
import com.karya.livedataviewmodel.R
import com.karya.livedataviewmodel.NotesApp.viewmodel.ViewModel
import com.karya.livedataviewmodel.NotesApp.adapter.NotesAdapter
import com.karya.livedataviewmodel.databinding.ActivityMainBinding
import java.text.SimpleDateFormat


import java.util.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var viewModel: ViewModel
    private lateinit var noteAdapter: NotesAdapter
    private lateinit var notes: Notes
    private lateinit var binding: ActivityMainBinding
    var oldListNotes = arrayListOf<Notes>()
    private var clicked = false

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_open
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_close
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.from_botoom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.to_bottom_anil
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteAdapter = NotesAdapter(applicationContext)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = noteAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        //binding.recyclerView.layoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProvider(this)[ViewModel::class.java]
        viewModel.readAllNotes.observe(this) { notes -> noteAdapter.setData(notes) }

        //observables()
        hideScrollViewWhileScrolling()

        binding.floatingActionButton.setOnClickListener {
            //showAddNoteDialog()
            showMiniFabBtn()

        }
        binding.fabBtnAddNote.setOnClickListener{
            showAddNoteDialog()
        }
        binding.tvAddNote.setOnClickListener {
            showAddNoteDialog()
        }

        binding.fabBtnNoteDetail.setOnClickListener{
            val intent = Intent(this, NoteDetailActivity::class.java)
            startActivity(intent)
        }
        binding.tvNoteDetail.setOnClickListener {
            val intent = Intent(this, NoteDetailActivity::class.java)
            startActivity(intent)
        }

        /*adapter.onItemClick = { notes ->
            showActionDialog(notes)
        }*/

        noteAdapter.onItemClick = { notes ->
            val intent = Intent(this, NoteDetailActivity::class.java)
            startActivity(intent)
        }

        noteAdapter.onImageClick = { notes ->
            showActionDialog(notes)
        }
    }

    private fun showMiniFabBtn() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }
    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.fabBtnNoteDetail.startAnimation(fromBottom)
            binding.fabBtnAddNote.startAnimation(fromBottom)


            binding.tvAddNote.startAnimation(fromBottom)
            binding.tvNoteDetail.startAnimation(fromBottom)

            binding.floatingActionButton.startAnimation(rotateOpen)
        } else {
            binding.fabBtnNoteDetail.startAnimation(toBottom)
            binding.fabBtnAddNote.startAnimation(toBottom)


            binding.tvAddNote.startAnimation(toBottom)
            binding.tvNoteDetail.startAnimation(toBottom)

            binding.floatingActionButton.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.fabBtnAddNote.visibility = View.VISIBLE
            binding.fabBtnNoteDetail.visibility = View.VISIBLE


            binding.tvAddNote.visibility = View.VISIBLE
            binding.tvNoteDetail.visibility = View.VISIBLE


        } else {
            binding.fabBtnAddNote.visibility = View.GONE
            binding.fabBtnNoteDetail.visibility = View.GONE


            binding.tvAddNote.visibility = View.GONE
            binding.tvNoteDetail.visibility = View.GONE
        }

    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked) {
            binding.fabBtnAddNote.isClickable = true
            binding.fabBtnNoteDetail.isClickable = true


            binding.tvAddNote.isClickable = true
            binding.tvNoteDetail.isClickable = true

        } else {
            binding.fabBtnAddNote.isClickable = false
            binding.fabBtnNoteDetail.isClickable = false


            binding.tvAddNote.isClickable = false
            binding.tvNoteDetail.isClickable = false
        }
    }

    private fun hideScrollViewWhileScrolling() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding.floatingActionButton.hide()
                }else{
                    binding.floatingActionButton.show()
                }
            }
        })

    }

    /* private fun observables() {
         noteAdapter = NotesAdapter(this@MainActivity)
         val itemTouchHelper =ItemTouchHelper(simpleCallback)
         itemTouchHelper.attachToRecyclerView(binding.recyclerView)

     }*/


    private fun showActionDialog(notes: Notes) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Any Option")
        builder.setPositiveButton("Delete") { _, _ ->

            viewModel.deleteNote(notes)
            Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show()

        }
        builder.setNegativeButton("Update") { _, _ ->
            showUpdateDialog(notes)
        }
        builder.setNeutralButton("Cancel") { _, _ ->
            Toast.makeText(
                this,
                "Cancel",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.create().show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun showUpdateDialog(notes: Notes) {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_update_note)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        val editNoteTitle: EditText = dialog.findViewById<EditText>(R.id.etNoteTitle)
        val editNoteDescription: EditText = dialog.findViewById<EditText>(R.id.etNoteDescription)


        editNoteTitle.setText(notes.noteTitle)
        editNoteDescription.setText(notes.noteDescription)


        /** Current Date While Updating Notes. */

        val sdf = SimpleDateFormat("MMMM d, yyyy  HH:mm:ss ")
        val currentDate: String = sdf.format(Date())

        dialog.show()
        dialog.window?.attributes = layoutParams

        dialog.findViewById<Button>(R.id.btnUpdateNote).setOnClickListener {
            if (inputCheck(editNoteTitle.text.toString(), editNoteDescription.text.toString())) {
                //Toast.makeText(this,editNoteTitle.text.toString()+"\n"+editNoteDescription.text.toString(),Toast.LENGTH_SHORT).show()
                val notes = Notes(
                    notes.id,
                    editNoteTitle.text.toString(),
                    editNoteDescription.text.toString(),
                    currentDate.toString()

                )
                viewModel.updateNote(notes)
                Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please Enter Data", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.findViewById<Button>(R.id.btnCancelNote).setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun showAddNoteDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_add_new_note)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        val editNoteTitle: EditText = dialog.findViewById<EditText>(R.id.etNoteTitle)
        val editNoteDescription: EditText = dialog.findViewById<EditText>(R.id.etNoteDescription)


        /** Current Date While Creating Notes. */
        val date = Date()
        val currentDate: CharSequence = DateFormat.format("MMMM d, yyyy HH:mm:ss ", date.time)

        dialog.findViewById<Button>(R.id.btnAddNote).setOnClickListener {
            if (inputCheck(editNoteTitle.text.toString(), editNoteDescription.text.toString())) {
                //Toast.makeText(this,editNoteTitle.text.toString()+"\n"+editNoteDescription.text.toString(),Toast.LENGTH_SHORT).show()

                val notes = Notes(
                    0,
                    editNoteTitle.text.toString(),
                    editNoteDescription.text.toString(),
                    currentDate.toString()
                )
                viewModel.addData(notes)
                /*oldListNotes = notes as ArrayList<Notes>*/

                Toast.makeText(this, "Data Added", Toast.LENGTH_SHORT).show()
                dialog.dismiss()

            } else {
                Toast.makeText(this, "Please Enter Data", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
        dialog.window?.attributes = layoutParams
        dialog.findViewById<Button>(R.id.btnCancelNote).setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun inputCheck(editNoteTitle: String, editNoteDescription: String): Boolean {

        return !(TextUtils.isEmpty(editNoteTitle) && TextUtils.isEmpty(editNoteDescription))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.delete_all_notes, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.search_bar -> {
                val searchView = item.actionView as? SearchView
                searchView?.queryHint = "Search by Note Title..."
                searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            searchData(query)
                        }
                        return false
                    }

                    override fun onQueryTextChange(query: String?): Boolean {
                        if (query != null) {
                            searchData(query)
                        }
                        return true
                    }

                    private fun searchData(query: String) {
                        val searchQuery = "%$query%"
                        viewModel.searchData(searchQuery).observe(this@MainActivity) { list ->
                            list.let {
                                viewModel.searchData(query)
                            }
                        }
                    }
                })
                true
            }

            /** Delete All Notes */
            R.id.deleteAllNotes -> {
                //Log.i("Main", "deleteAllNotes")
                viewModel.deleteAllNotes()
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    /*override fun onPause() {
        super.onPause()
        Log.i("Main",":On Pause")
    }
    override fun onStop() {
        super.onStop()
        Log.i("Main",":On Stop")
    }*/

    /*var simpleCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val dialog = MaterialAlertDialogBuilder(applicationContext)
                            .setTitle("Delete")
                            .setMessage("Do You Want to Delete " + notes.noteTitle)
                            .setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
                                viewModel.deleteNote(notes)
                            }
                            .setNegativeButton("No") { dialog: DialogInterface?, which: Int ->
                                dialog?.dismiss()
                            }
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerview: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dx: Float,
                dy: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerview,
                    viewHolder,
                    dx,
                    dy,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
                    .addSwipeLeftLabel("Delete")
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .setSwipeLeftLabelColor(ContextCompat.getColor(applicationContext, R.color.white))
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,recyclerview,viewHolder,dx,dy,actionState,isCurrentlyActive
                )
            }
        }*/
}


