package com.karya.livedataviewmodel.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.karya.livedataviewmodel.R
import com.karya.livedataviewmodel.databinding.ActivityNoteDetailBinding
import es.dmoral.toasty.Toasty

class NoteDetailActivity : AppCompatActivity(R.layout.activity_note_detail) {

    lateinit var binding: ActivityNoteDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** Get Data From Adapter Class */
        /*val productData = intent
        val title  = productData.getStringExtra("tvNoteTitle")
        val description = productData.getStringExtra("tvNoteDescription")
        binding.detailActTitle.text = title
        binding.detailActDescription.text = description*/

        /** Get Data From Adapter Class */
        val productData = intent
        binding.detailActTitle.text = productData.getStringExtra("tvNoteTitle")
        binding.detailActDescription.text = productData.getStringExtra("tvNoteDescription")
        binding.currentDate.text = productData.getStringExtra("tvCurrentDate")


        /** Change Color of CardView by Just Click on Button */

        val color1 = "#a8f7cc"
        val color2 = "#aacaf2"
        val color3 = "#f2c3fa"

        binding.matBtn1.setOnClickListener {
            binding.cardView2.setBackgroundColor(Color.parseColor(color1))

        }
        binding.matBtn2.setOnClickListener {
            binding.cardView2.setBackgroundColor(Color.parseColor(color2))
        }
        binding.matBtn3.setOnClickListener {
            binding.cardView2.setBackgroundColor(Color.parseColor(color3))
        }

        binding.successToast.setOnClickListener {
            Toasty.success(applicationContext, "Success!", Toast.LENGTH_SHORT, true).show()
        }
        binding.warningToast.setOnClickListener {
            Toasty.warning(applicationContext, "Warning!", Toast.LENGTH_SHORT, true).show()
        }
        binding.customToast.setOnClickListener {
            Toasty.custom(applicationContext, "Custom Toast!",
                R.drawable.ic_baseline_search_24,
                R.color.teal_200,1000,true,true).show()
        }
        binding.normalToastWIcon.setOnClickListener {
            Toasty.normal(applicationContext, "With Icon!", R.drawable.ic_baseline_delete_24).show()
        }
        binding.failureToast.setOnClickListener {
            Toasty.error(applicationContext, "Error!", Toast.LENGTH_SHORT, true).show()
        }

    }

}