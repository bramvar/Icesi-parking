package co.edu.icesi.icesiparking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class IntroActivity : AppCompatActivity() {

    private lateinit var introStartBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        introStartBtn = findViewById(R.id.introStartBtn)

        introStartBtn.setOnClickListener {
            val qr = Intent(this, QRCodeActivity::class.java)
            startActivity(qr)
        }

    }
}