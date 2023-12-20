package com.ja_cabili.passifi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ja_cabili.passifi.model.Event

class AllFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all, container, false)
        val eventContainer = view.findViewById<LinearLayout>(R.id.event_container)

        // Initialize the ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        // Observe the LiveData for approved events
        viewModel.events.observe(viewLifecycleOwner) { events ->
            eventContainer.removeAllViews()

            // Filter and display only approved events
            displayEvents(events, eventContainer)
            Log.d("TodayFragment", "Approved events: $events")
        }

        // Fetch approved events for the user
        viewModel.getApprovedEventsForUser()

        return view
    }

    private fun displayEvents(events: List<Event>?, eventContainer: LinearLayout) {
        events?.forEach { event ->
            val cardView = layoutInflater.inflate(R.layout.card_event, null)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            // Add bottom margin to all cards except the last one
            val lastIndex = events.indexOfLast { it == event }
            if (lastIndex < events.size - 1) {
                layoutParams.setMargins(
                    0,
                    0,
                    0,
                    resources.getDimensionPixelSize(R.dimen.event_margin_bottom)
                )
            }

            cardView.layoutParams = layoutParams

            val eventImage = cardView.findViewById<ImageView>(R.id.image_event)
            val eventName = cardView.findViewById<TextView>(R.id.text_event_name)
            val attendees = cardView.findViewById<TextView>(R.id.text_attendees)
            val date = cardView.findViewById<TextView>(R.id.text_date)
            val location = cardView.findViewById<TextView>(R.id.text_location)
            val organizer = cardView.findViewById<TextView>(R.id.text_organizer)

            // Populate the card with event data
            // Assuming your Event class has properties like current_attendees, total_attendees, and avatar
            // Modify this accordingly based on your actual Event class
            eventImage.setImageResource(R.drawable.conference)
            eventName.text = event.title
            attendees.text = "Attendees: ${event.current_attendees} / ${event.total_attendees}"
            date.text = event.date
            location.text = event.location
            organizer.text = event.organizer

            cardView.setOnClickListener {
                navigateToEventQR(event)
            }

            eventContainer.addView(cardView)
        }
    }

    private fun navigateToEventQR(event: Event) {
        val intent = Intent(requireContext(), EventQR::class.java);
        val guest = event.guests?.get(0);

        // Pass event data to EventQR activity
        intent.putExtra("eventId", event.id)
        intent.putExtra("eventName", event.title)
        intent.putExtra("eventDate", event.date)
        intent.putExtra("eventLocation", event.location)
        intent.putExtra("eventOrganizer", event.organizer)
        intent.putExtra("guestId", guest?.id)
        intent.putExtra("qrcode", guest?.toJsonString())
        // Add more properties as needed

        startActivity(intent)
    }
}