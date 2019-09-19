package com.hadiftech.hamba.core.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import androidx.annotation.ArrayRes;
import androidx.appcompat.widget.AppCompatSpinner;
import com.hadiftech.hamba.R;

import java.util.List;

public class HambaSpinner extends AppCompatSpinner {

    public HambaSpinner(Context context) {
        super(context);
    }

    public HambaSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void populate(Context context, List<String> spinnerItems) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item_dark, spinnerItems);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        this.setAdapter(adapter);
    }

    public void populate(Context context, @ArrayRes int textArrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, textArrayResId, R.layout.spinner_item_dark);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        this.setAdapter(adapter);
    }
}