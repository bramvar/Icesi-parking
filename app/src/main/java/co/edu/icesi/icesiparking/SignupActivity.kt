package co.edu.icesi.icesiparking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import co.edu.icesi.icesiparking.databinding.ActivitySignupBinding
import co.edu.icesi.icesiparking.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goBackButton.setOnClickListener{
            finish()
        }

        binding.signupBtn.setOnClickListener(::registerUser)

    }

    private fun registerUser(view: View){
        val userId =binding.userIdTextField.text.toString()
        val userName = binding.userNameTextField.text.toString()
        val userLastname = binding.userLastnameTextField.text.toString()
        val email = binding.userEmailTextField.text.toString()
        val password = binding.userPasswdTextField.text.toString()
        val rePassword = binding.userRepPasswdTextField.text.toString()

        if(verifyBlankSignupFields(userId,userName,userLastname,email, password, rePassword)){
            if(password == rePassword){
                Firebase.auth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener {
                        val id = Firebase.auth.currentUser?.uid
                        val newUser = User(id!!,userId,userName,userLastname,email,password)

                        Firebase.firestore.collection("users").document(id).set(newUser)
                            .addOnSuccessListener {
                                Toast.makeText(this.baseContext,"Usuario registrado. Inicie sesión", Toast.LENGTH_LONG).show()
                                finish()
                            }.addOnFailureListener {
                                Toast.makeText(this.baseContext,"Algo salió mal. Por favor intente más tarde", Toast.LENGTH_LONG).show()
                            }

                    }.addOnFailureListener {
                        Toast.makeText(this.baseContext,it.message, Toast.LENGTH_LONG).show()
                    }
            }else{
                Toast.makeText(this.baseContext,"Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
            }
        }else {
            Toast.makeText(this.baseContext,"Faltan campos por diligenciar", Toast.LENGTH_LONG).show()

        }

    }

    private fun verifyBlankSignupFields(
        userId: String,
        userName: String,
        userLastname: String,
        email: String,
        password: String,
        rePassword: String
    ): Boolean {
        return userId.isNotBlank() && userName.isNotBlank() && userLastname.isNotEmpty() && email.isNotBlank() && password.isNotBlank() && rePassword.isNotBlank()
    }
}