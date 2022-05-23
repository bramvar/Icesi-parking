package co.edu.icesi.icesiparking

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class QRCodeActivity : AppCompatActivity() {

    private lateinit var ivQRCode : ImageView
    private lateinit var etData : EditText
    private lateinit var btnGenerateQR : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        ivQRCode = findViewById(R.id.ivQRCode)
        etData = findViewById(R.id.etData)
        btnGenerateQR = findViewById(R.id.btnGenerateQR)

        btnGenerateQR.setOnClickListener {
            val data = etData.text.toString().trim()

            if (data.isEmpty()){
                Toast.makeText(this, "Ingrese su correo", Toast.LENGTH_SHORT).show()
            }else{
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
}