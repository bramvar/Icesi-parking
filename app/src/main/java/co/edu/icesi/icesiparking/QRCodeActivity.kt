package co.edu.icesi.icesiparking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class QRCodeActivity : AppCompatActivity() {

    private lateinit var ivQRCode : ImageView
    private lateinit var etData : EditText
    private lateinit var btnGenerateQRCode : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        ivQRCode = findViewById(R.id.ivQRCode)
        etData = findViewById(R.id.etData)
        btnGenerateQRCode = findViewById(R.id.btnGenerateQRCode)

        btnGenerateQRCode.setOnClickListener{
            val data = etData.text.toStirng().trim()

            if(data.isEmpty()){
                Toast.makeText(this, "Enter some data", Toast.LENGTH_SHORT).show()
            }else{
                val writer = QRWriter()
                try {
                    val bitMatrix = writer.encode(data, BarCodeFormat.QR_CODE, 512, 512)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                    for (x in 0 until width) {
                        for (y in 0 until height) {
                            bmp.setPixel(x, y, if(bitMatrix[x, y]) Color.BLACK else Color.White)
                        }
                    }
                    ivQRCode.setImageBitMap(bmp)
                }catch(e WriterException) {
                    e.printStackTrace()
                }

        }

    }
}