package com.example.jeevan.covidslots.COVIDSlots;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jeevan.R;

import java.util.ArrayList;

public class CovidAdapter extends ArrayAdapter<Covid> {

    private static final String LOG_TAG = CovidAdapter.class.getSimpleName();

    public CovidAdapter(IndiaStatsActivity context, ArrayList<Covid> covid) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, covid);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_view, parent, false);
        }

        Covid current = getItem(position);

        TextView statename = listItemView.findViewById(R.id.state);
        String state = current.getmstate();
        statename.setText(state);

        TextView totalCount = listItemView.findViewById(R.id.total);
        int total = current.getmtotalcount();
        totalCount.setText(Integer.toString(total));

        TextView totalActive = listItemView.findViewById(R.id.active);
        int active = current.getmtotalactive();
        totalActive.setText(Integer.toString(active));

        TextView totalDeaths = listItemView.findViewById(R.id.deceased);
        int deaths = current.getmtotaldeaths();
        totalDeaths.setText(Integer.toString(deaths));

        TextView recovery = listItemView.findViewById(R.id.recovery);
        int recover = current.getmrecovery();
        recovery.setText(Integer.toString(recover));

        return listItemView;
    }
}
