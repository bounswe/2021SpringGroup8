package com.example.facespace

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.facespaceextenstion.Data
import kotlinx.android.synthetic.main.activity_profile_page.*

class ProfilePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)
        supportActionBar?.hide()
        val btnGohome = findViewById<Button>(R.id.btnBackHome)
        val editProfile = findViewById<ImageView>(R.id.btnEdit)

        val btnMaps = findViewById<ImageView>(R.id.imageView6)

        val tvusername = findViewById<TextView>(R.id.tvUsername)
        val tvname = findViewById<TextView>(R.id.tvName)
        val tvsurname = findViewById<TextView>(R.id.tvSurname)
        val tvemail = findViewById<TextView>(R.id.tvMail)
        val tvbirth = findViewById<TextView>(R.id.tvDOB)
        val tvcity = findViewById<TextView>(R.id.tvCity)

        val infos: MutableMap<String, String> = Data().getAll()

        tvusername.text = "@${infos["username"]}"
        tvname.text = infos["name"]
        tvsurname.text = infos["surname"]
        tvemail.text = infos["email"]
        tvbirth.text = infos["birthdate"]
        tvcity.text = infos["city"]



        btnGohome.setOnClickListener{
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }

        btnEdit.setOnClickListener{
            Toast.makeText(this,"This is still under implementation", Toast.LENGTH_SHORT).show()
        }

        btnMaps.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            val loc:Location = Data().getLoc()
            intent.putExtra("lon", loc.longitude)
            intent.putExtra("lat", loc.latitude)
            startActivityForResult(intent, 2)
        }
    }
}