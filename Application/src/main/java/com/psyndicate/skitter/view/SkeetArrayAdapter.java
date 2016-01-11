package com.psyndicate.skitter.view;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.psyndicate.skitter.R;
import com.psyndicate.skitter.model.Skeet;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;


/**
 * Array adapter for the complex type skeet
 */
public class SkeetArrayAdapter extends ArrayAdapter<Skeet> {
    private int layoutResourceId;

    private PrettyTime prettyTime = new PrettyTime();
    private static final String TAG = "SkeetArrayAdapter";

    public SkeetArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        layoutResourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            Skeet item = getItem(position);
            View v;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(layoutResourceId, null);
            } else {
                v = convertView;
            }

            TextView skeetText = (TextView) v.findViewById(R.id.skeet_text);
            TextView skeetTimestamp = (TextView) v.findViewById(R.id.skeet_timestamp);
            ImageView skeetImage = (ImageView) v.findViewById(R.id.skeet_image);

            skeetText.setText(createRichText(item.getText()), TextView.BufferType.SPANNABLE);
            skeetTimestamp.setText(prettyTime.format(new Date(item.getTimestamp())));

            // This is just for fake purposes - if this were real, I'd have bitmap images in the skeets
            if(item.getPoster().equals("user1")) {
                skeetImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.good_guy_greg));
            } else {
                skeetImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.good_girl_gina));
            }

            return v;
        } catch (Exception ex) {
            Log.e(TAG, "error", ex);
            return null;
        }
    }
    enum span_type { NONE, HASHTAG, MENTION }

    private void addSpan(SpannableString spannableString, int startSpan, int endSpan, span_type st) {
        int color;
        switch(st) {
            case NONE:
                return;
            case HASHTAG:
                color = getContext().getResources().getColor(R.color.hashtag);
                break;
            case MENTION:
                color = getContext().getResources().getColor(R.color.mention);
                break;
            default:
                return;
        }
        spannableString.setSpan(new ForegroundColorSpan(color), startSpan, endSpan, 0);
    }

    public SpannableString createRichText(String skeet) {
        SpannableString spannableString = new SpannableString(skeet);

        int start_span = -1;
        int end_span;
        span_type st = span_type.NONE;
        for(int index = 0; index < skeet.length(); index++) {
            switch (skeet.charAt(index)) {
                case '#':
                    if (st != span_type.NONE) {
                        end_span = index;
                        addSpan(spannableString, start_span, end_span, st);
                    }
                    start_span = index;
                    st = span_type.HASHTAG;
                    break;
                case '@':
                    if (st != span_type.NONE) {
                        end_span = index;
                        addSpan(spannableString, start_span, end_span, st);
                    }
                    start_span = index;
                    st = span_type.MENTION;
                    break;
                case ' ':
                case '\t':
                case '\r':
                case '\n':
                    if (st != span_type.NONE) {
                        end_span = index;
                        addSpan(spannableString, start_span, end_span, st);
                    }
                    start_span = -1;
                    st = span_type.NONE;
                    break;
            }
        }
        if (st != span_type.NONE) {
            end_span = skeet.length();
            addSpan(spannableString, start_span, end_span, st);
        }


        return spannableString;
    }
}
