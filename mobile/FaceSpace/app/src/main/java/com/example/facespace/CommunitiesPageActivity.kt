package com.example.facespace

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.signin_signup.Community
import com.example.signin_signup.CommunityAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CommunitiesPageActivity : AppCompatActivity() {
    private lateinit var commAdapter: CommunityAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_communities_page)

        commAdapter = CommunityAdapter(mutableListOf())

        val btnCreate = findViewById<Button>(R.id.btnCreate)

        val rvComms = findViewById<RecyclerView>(R.id.rvCommunityItems)

        rvComms.adapter = commAdapter
        rvComms.layoutManager = LinearLayoutManager(this)

        val editTitle = findViewById<EditText>(R.id.Title)
        val editDesc = findViewById<EditText>(R.id.Desc)
        val editBy = findViewById<EditText>(R.id.By)
        btnCreate.setOnClickListener {

            val title = editTitle.text.toString()
            val desc = editDesc.text.toString()
            val by = editBy.text.toString()

            val current = LocalDateTime.now()

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val formatted = current.format(formatter) as String



            if(title.isNotEmpty() && desc.isNotEmpty() && by.isNotEmpty()) {
                val comm = Community(title,by,desc,false, formatted)
                commAdapter.addComm(comm)
                editTitle.text.clear()
                editDesc.text.clear()
                editBy.text.clear()

            } else {
                Toast.makeText(this, "Fill all cells", Toast.LENGTH_SHORT).show()
            }
        }
    }
}