package com.karya.livedataviewmodel.NotesApp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.karya.livedataviewmodel.NotesApp.activities.NoteDetailActivity
import com.karya.livedataviewmodel.NotesApp.room.entity.Notes
import com.karya.livedataviewmodel.R

class NotesAdapter(private val c : Context,private var notes: List<Notes> = emptyList<Notes>()): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {


    //private var notes= emptyList<Notes>()
    var onItemClick : ((Notes) -> Unit)? = null
    var onImageClick  : ((Notes) -> Unit)?  = null

    /*private val diffCallBack = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
           return true
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallBack)*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_notes, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItems = notes[position]
        holder.tvNoteTitle.text = currentItems.noteTitle
        holder.tvNoteDescription.text = currentItems.noteDescription
        holder.tvDate.text = currentItems.noteDate
        holder.itemView.findViewById<TextView>(R.id.itemNoteTitle)

        /** RecyclerView Item onClickListener */
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(notes[position])
        }
        holder.moreItemImage.setOnClickListener {
            onImageClick?.invoke(notes[position])
        }

        /*if(currentItems != null){
            holder.bind(notes)
        }*/


        /** Put Data to Activity */
        /*holder.itemView.setOnClickListener {
            val title: String = currentItems.noteTitle
            val description: String = currentItems.noteDescription
            val itemIntent = Intent(c,NoteDetailActivity::class.java)
            itemIntent.putExtra("tvNoteTitle",title)
            itemIntent.putExtra("tvNoteDescription",description)
            c.startActivity(itemIntent)
        }*/

        /** Put Data to Activity */
        holder.itemView.setOnClickListener {
            val itemIntent = Intent(c, NoteDetailActivity::class.java)
            itemIntent.putExtra("tvNoteTitle",currentItems.noteTitle)
            itemIntent.putExtra("tvNoteDescription",currentItems.noteDescription)
            itemIntent.putExtra("tvCurrentDate",currentItems.noteDate)
            // here flag is required because we are calling Intent outSide an Activity.
            itemIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            c.startActivity(itemIntent)
        }

        // Color Condition On Recycler View items.
        var color = "#a8c5f0"
        if ( position % 2 == 0){
            color = "#678abf"
        }
        holder.container.setBackgroundColor(Color.parseColor(color))
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(notes: List<Notes>){
        this.notes = notes
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

       /* fun bind(modelClass: List<Notes>){
            notes = modelClass
        }*/

        val tvNoteTitle: TextView =itemView.findViewById(R.id.itemNoteTitle)
        val tvNoteDescription: TextView =itemView.findViewById(R.id.itemNoteDescription)
        val moreItemImage: ImageView = itemView.findViewById(R.id.itemMore)
        val tvDate: TextView = itemView.findViewById(R.id.itemNoteDate)
        val container = itemView.findViewById<ConstraintLayout>(R.id.consLayout)!!
    }
}