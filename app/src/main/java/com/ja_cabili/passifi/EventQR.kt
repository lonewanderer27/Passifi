package com.ja_cabili.passifi

import MainViewModelFactory
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.ja_cabili.passifi.model.Guest
import com.ja_cabili.passifi.repository.Repository

class EventQR : AppCompatActivity() {

    private lateinit var userTextView: TextView
    private lateinit var guestIdTextView: TextView
    private lateinit var qrCodeImageView: ImageView
    private lateinit var eventNameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_qr)

        // Initialize the ViewModel
        val viewModelFactory = MainViewModelFactory(Repository(), applicationContext)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        userTextView = findViewById(R.id.user)
        guestIdTextView = findViewById(R.id.guestId)
        qrCodeImageView = findViewById(R.id.qrCodeImageView)
        eventNameTextView = findViewById(R.id.eventName)


        // Retrieve event data from the intent
        val eventId = intent.getStringExtra("eventId")
        val eventName = intent.getStringExtra("eventName")
        val eventDate = intent.getStringExtra("eventDate")
        val eventLocation = intent.getStringExtra("eventLocation")
        val eventOrganizer = intent.getStringExtra("eventOrganizer")
        val guestId = intent.getStringExtra("guestId")
        val qrcode = intent.getStringExtra("qrcode")

        // Display event data
        userTextView.text = viewModel.user.value?.name
        eventNameTextView.text = eventName
        guestIdTextView.text = "Guest ID: ${guestId}"

        // Generate QR Code for event data
        val qrcodeData = qrcode!!
        val qrCodeBitmap = generateQRCode(qrcodeData)
        qrCodeImageView.setImageBitmap(qrCodeBitmap)
    }

    private fun generateQRCode(data: String): Bitmap? {
        try {
            val bitMatrix: BitMatrix = MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500)
            val width: Int = bitMatrix.width
            val height: Int = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
                }
            }

            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return null
    }
}
