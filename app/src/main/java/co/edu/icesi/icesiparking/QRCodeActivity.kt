package co.edu.icesi.icesiparking

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import co.edu.icesi.icesiparking.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class QRCodeActivity : AppCompatActivity() {

    private lateinit var ivQRCode : ImageView
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {

        ivQRCode = findViewById(R.id.ivQRCode)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        Firebase.firestore
            .collection("users").document(user.id).get()
            .addOnSuccessListener {
                val currentUser = it.toObject(User::class.java)
                val data = currentUser?.email

                val writer = QRCodeWriter()
                try {

                    val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bm = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                    for (i in 0 until width){
                        for (j in 0 until height){
                            bm.setPixel(i, j, if (bitMatrix[i, j]) Color.BLACK else Color.WHITE)
                        }
                    }

                    ivQRCode.setImageBitmap(bm)

                }catch (e: WriterException){
                    e.printStackTrace()
                }

            }
    }
}