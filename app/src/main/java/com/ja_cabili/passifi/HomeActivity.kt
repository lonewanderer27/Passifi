package com.ja_cabili.passifi

import MainViewModelFactory
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.gson.Gson
import com.ja_cabili.passifi.model.ErrorResponse
import com.ja_cabili.passifi.repository.Repository


class HomeActivity : AppCompatActivity() {
    private var viewModel: MainViewModel? = null // Assuming your ViewModel is named MainViewModel
    private var progressDialog: ProgressDialog? = null // Loading indicator
    private var dialog: AlertDialog? = null // Custom dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize the ViewModel
        val viewModelFactory = MainViewModelFactory(Repository(), applicationContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        // Initialize ProgressDialog
        // Initialize the progress dialog
        progressDialog = ProgressDialog(this);
        progressDialog!!.setMessage("Loading...");
        progressDialog!!.setCancelable(false); // Prevent dismissing by tapping outside

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager)
        val myViewPagerAdapter = MyViewPagerAdapter(this)
        viewPager2.adapter = myViewPagerAdapter

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)!!.select()
            }
        })
    }

    private val popupWindow: PopupWindow? = null
    private val isPopupShowing = false

    // Method for handling click on the plus button
    fun onPlusButtonClick(view: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.custom_popup_menu, null)
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.animationStyle = R.style.PopupAnimation
        popupWindow.isOutsideTouchable = true // Enable to dismiss when touching outside

        // Show the popup menu at the plus button's position
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1] + view.height)

        // Handle click events on popup menu items
        val enterInviteCodeOption = popupView.findViewById<TextView>(R.id.enter_invite_code_option)
        val scanQROption = popupView.findViewById<TextView>(R.id.scan_qr_option)
        enterInviteCodeOption.setOnClickListener { //                popupWindow.dismiss();
            // Handle Enter Invite Code click

            // Create an AlertDialog
            val builder = AlertDialog.Builder(this@HomeActivity)
            builder.setTitle("Enter Invite Code")

            // Set up the input
            val input = EditText(this@HomeActivity)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("OK") { dialog: DialogInterface?, which: Int ->
                val inviteCode = input.text.toString().trim { it <= ' ' }
                if (inviteCode.isEmpty()) {
                    // Set an error on the input box
                    input.error = "Invite code cannot be empty"
                } else {
                    // Clear any previous error
                    input.error = null

                    progressDialog!!.show();
                    viewModel?.joinUsingInviteCode(inviteCode)
                }
            }
            builder.setNegativeButton("Cancel") { dialog: DialogInterface, which: Int -> dialog.cancel() }

            // Show the AlertDialog
            builder.show()
        }
        scanQROption.setOnClickListener {
            popupWindow.dismiss()
            // Handle Scan QR click
        }

        // Dismiss the popup window when clicking outside or clicking the plus button again
        findViewById<View>(android.R.id.content).setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (!isPointInsideView(event.rawX, event.rawY, popupView)
                    && !isPointInsideView(event.rawX, event.rawY, view)
                ) {
                    popupWindow.dismiss()
                }
            }
            false
        }

        viewModel!!.eventResponse.observe(this) { response ->
            progressDialog!!.dismiss();
            if (response.isSuccessful) {
                val event = response.body()?.event
                Log.d("Response", response.body()?.event.toString())
                if (event != null) {
                    Log.d("Response", response.body()?.message.toString())
                    showResponseDialog(response.body()?.message)
                }
            } else {
                val errorResponse: ErrorResponse? = response.errorBody()?.string()?.let { Gson().fromJson(it, ErrorResponse::class.java) }
                showResponseDialog(errorResponse?.error, "Error")
                Log.d("Error: ", response.errorBody()?.string().toString())
            }
        }
    }

    // Check if the touch point is inside the specified view
    private fun isPointInsideView(x: Float, y: Float, view: View): Boolean {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val viewX = location[0]
        val viewY = location[1]
        val viewWidth = view.width
        val viewHeight = view.height
        return x > viewX && x < viewX + viewWidth && y > viewY && y < viewY + viewHeight
    }

    // Method to show a custom dialog with the response message
    private fun showResponseDialog(message: String?, title: String? = "Response") {
        val builder = AlertDialog.Builder(this@HomeActivity)
        builder.setTitle(title)

        val messageView = TextView(this)
        messageView.text = message
        messageView.textSize = 18f
        messageView.setPadding(50, 50, 50, 50)
        builder.setView(messageView)

        builder.setPositiveButton("OK") { dialog, which -> dialog.dismiss() }

        dialog = builder.create()
        dialog!!.show()
    }
}
