package com.example.nexcab.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class PlaceAutocompleteAdapter extends ArrayAdapter<AutocompletePrediction> implements Filterable {

    private static final String TAG = "PlaceAutocompleteAdapter";
    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);

    private List<AutocompletePrediction> mResultList;
    private PlacesClient placesClient;

    public PlaceAutocompleteAdapter(Context context, PlacesClient placesClient) {
        super(context, android.R.layout.simple_expandable_list_item_2, android.R.id.text1);
        this.placesClient = placesClient;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = super.getView(position, convertView, parent);

        AutocompletePrediction item = getItem(position);

        TextView textView1 = row.findViewById(android.R.id.text1);
        TextView textView2 = row.findViewById(android.R.id.text2);
        assert item != null;
        textView1.setText(item.getPrimaryText(STYLE_BOLD));
        textView2.setText(item.getSecondaryText(STYLE_BOLD));

        return row;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint != null) {
                    mResultList = getPredictions(constraint);

                    if (mResultList != null) {
                        results.values = mResultList;
                        results.count = mResultList.size();
                    }
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                if (resultValue instanceof AutocompletePrediction) {
                    return ((AutocompletePrediction) resultValue).getFullText(null);
                } else {
                    return super.convertResultToString(resultValue);
                }
            }
        };
    }

    private List<AutocompletePrediction> getPredictions(CharSequence constraint) {
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setTypeFilter(TypeFilter.ADDRESS)
                .setSessionToken(AutocompleteSessionToken.newInstance())
                .setQuery(constraint.toString())
                .build();

        Task<FindAutocompletePredictionsResponse> task = placesClient.findAutocompletePredictions(request);
        try {
            Tasks.await(task, 60, TimeUnit.SECONDS);
            if (task.isSuccessful()) {
                FindAutocompletePredictionsResponse response = task.getResult();
                if (response != null) {
                    return response.getAutocompletePredictions();
                }
            } else {
                Log.d("PlaceAutocompleteAdapter", "Prediction fetching task unsuccessful: " + task.getException().getMessage());
            }
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
            Log.e("PlaceAutocompleteAdapter", "Exception: " + e.getMessage());
        }
        return null;
    }
}
