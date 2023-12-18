package com.ja_cabili.passifi;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import com.ja_cabili.passifi.R;

public class AllFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        LinearLayout eventContainer = view.findViewById(R.id.event_container);

        // Simulated list of events - replace this with your actual event data retrieval logic
        List<Event> eventList = getEventList();

        for (int i = 0; i < eventList.size(); i++) {
            View cardView = inflater.inflate(R.layout.card_event, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            // Add bottom margin to all cards except the last one
            if (i < eventList.size() - 1) {
                layoutParams.setMargins(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.event_margin_bottom));
            }

            cardView.setLayoutParams(layoutParams);

            Event event = eventList.get(i);

            ImageView eventImage = cardView.findViewById(R.id.image_event);
            TextView eventName = cardView.findViewById(R.id.text_event_name);
            TextView attendees = cardView.findViewById(R.id.text_attendees);
            TextView date = cardView.findViewById(R.id.text_date);
            TextView location = cardView.findViewById(R.id.text_location);
            TextView organizer = cardView.findViewById(R.id.text_organizer);

            // Populate the card with event data
            eventImage.setImageResource(event.avatar);
            eventName.setText(event.title);
            attendees.setText("Attendees: " + event.current_attendees + " / " + event.total_attendees);
            date.setText(event.date);
            location.setText(event.location);
            organizer.setText(event.organizer);

            cardView.setOnClickListener(v -> {
                // Handle card click if needed
                // You can implement what should happen when a card is clicked here
            });

            eventContainer.addView(cardView);
        }

        return view;
    }

    // Simulated method to retrieve a list of events - Replace this with your actual data retrieval logic
    private List<Event> getEventList() {
        // Simulated event data
        List<Event> events = new ArrayList<>();
        events.add(new Event("Event 1", R.drawable.conference, "Description 1", "01/01/2023", "Time 1", "Location 1", "Organizer 1", "Organizer Email 1", "Organizer Approval 1", "User ID 1", "Invite Code 1", 20, 50, 0));
        events.add(new Event("Event 2", R.drawable.conference, "Description 2", "05/02/2023", "Time 2", "Location 2", "Organizer 2", "Organizer Email 2", "Organizer Approval 2", "User ID 2", "Invite Code 2", 30, 50, 1));
        // Add more events as needed
        return events;
    }
}