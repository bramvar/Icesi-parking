package co.edu.icesi.icesiparking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import co.edu.icesi.icesiparking.databinding.ActivitySettingsBinding
import co.edu.icesi.icesiparking.util.UtilDomi
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import java.util.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = loadUser()!!

        getUpdatedUser()

        val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),::onGalleryResult)

        binding.settingsGoBackButton.setOnClickListener {
            finish()
        }

        binding.updateUserImage.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            galleryLauncher.launch(intent)
        }
    }

    private fun onGalleryResult(activityResult: ActivityResult){
        if(activityResult.resultCode == RESULT_OK){
            val uri = activityResult.data?.data
            binding.updateUserImage.setImageURI(uri)

            //image upload
            val fileName = UUID.randomUUID().toString()
            Firebase.storage.getReference().child("user-image-profile").child(fileName).putFile(uri!!)
            Firebase.firestore.collection("users").document(user.id).update("imageID",fileName)
        }
    }

    fun getUpdatedUser(){
        Firebase.firestore.collection("users").document(user.id).get()
            .addOnSuccessListener {
                val updatedUser = it.toObject(User::class.java)
                val imageID = updatedUser?.imageID
                downloadProfileImage(imageID)

            }
    }

    fun downloadProfileImage(imageID: String?){

        if(imageID == null) return

        Firebase.storage.getReference().child("user-image-profile").child(imageID!!).downloadUrl
            .addOnSuccessListener {
                Glide.with(binding.updateUserImage).load(it).into(binding.updateUserImage)
            }
    }

    fun loadUser(): User?{
        val sp = getSharedPreferences("icesi-parking", MODE_PRIVATE)
        val json = sp.getString("user","NO_USER")

        if(json == "NO_USER"){
            return null
        } else{
            return Gson().fromJson(json,User::class.java)
        }
    }
}