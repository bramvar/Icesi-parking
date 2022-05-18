package co.edu.icesi.icesiparking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.edu.icesi.icesiparking.databinding.ActivityUserHomeBinding

class UserHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userSettingsButton.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
        }
    }
}