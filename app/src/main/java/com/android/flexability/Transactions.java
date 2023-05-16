/*
    This is a static implementation of the R10
 */

package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Transactions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        // Get the scroll view layout:
        ScrollView sv =  (ScrollView)findViewById(R.id.mainFinancialMoves);

        // Get the outer Linear Layout. Here new boxes will be added
        // if any info exists in the db:
        LinearLayout outerLL = (LinearLayout)findViewById(R.id.contentsFinancialMoves);

        // Create the contents. They are CardView objects.

        for(int i = 0; i < 8; i++) {
            // The card view
            CardView cv = new CardView(this);

            CardView.LayoutParams cvParams = new FrameLayout.LayoutParams(
                    CardView.LayoutParams.MATCH_PARENT,
                    CardView.LayoutParams.WRAP_CONTENT
            );
            cvParams.setMargins(7, 7, 7, 7);

            // Change radius
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(12);

            cv.setBackground(gd);
            cv.setCardElevation(5);
            cv.setLayoutParams(cvParams);

            // The two containers of card view
            ConstraintLayout colorLayout = new ConstraintLayout(this);
            ConstraintLayout infoLayout = new ConstraintLayout(this);
            infoLayout.setId(View.generateViewId());

            ConstraintLayout.LayoutParams clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );

            colorLayout.setLayoutParams(clParams);
            colorLayout.setBackgroundColor(getColor(androidx.cardview.R.color.cardview_dark_background));

            clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    80
            );
            clParams.setMargins(7, 0, 0, 0);

            infoLayout.setBackgroundColor(getColor(androidx.cardview.R.color.cardview_light_background));
            infoLayout.setLayoutParams(clParams);
            infoLayout.setPadding(5, 5, 5, 5);

            // Now insert the text views in the last layout:
            TextView phName = new TextView(this);
            phName.setId(View.generateViewId());

            TextView date = new TextView(this);
            date.setId(View.generateViewId());

            TextView price = new TextView(this);
            price.setId(View.generateViewId());

            Typeface fontName = Typeface.createFromAsset(
                    getAssets(),
                    "font/manrope_extra_bold.ttf"
            );

            phName.setText(getText(R.string.physio_name));
            phName.setTextSize(18);
            phName.setTypeface(fontName);

            Typeface fontDate = Typeface.createFromAsset(
                    getAssets(),
                    "font/manrope_extra_light.ttf"
            );

            date.setTextSize(14);
            date.setText(getText(R.string.date));
            date.setTypeface(fontDate);

            Typeface fontPrice = Typeface.createFromAsset(
                    getAssets(),
                    "font/manrope_regular.ttf"
            );

            price.setText(getText(R.string.price_txt));
            price.setTextSize(20);
            price.setTypeface(fontPrice);

            infoLayout.addView(phName);
            infoLayout.addView(date);
            infoLayout.addView(price);

            clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );

            clParams.startToStart = infoLayout.getId();
            clParams.topToTop = infoLayout.getId();

            phName.setLayoutParams(clParams);

            clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            clParams.bottomToBottom = infoLayout.getId();

            date.setLayoutParams(clParams);

            clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            clParams.setMargins(0, 0, 24, 0);
            clParams.topToTop = infoLayout.getId();
            clParams.bottomToBottom = infoLayout.getId();
            clParams.endToEnd = infoLayout.getId();

            price.setLayoutParams(clParams);

            colorLayout.addView(infoLayout);

            cv.addView(colorLayout);

            outerLL.addView(cv, LinearLayout.FOCUS_BACKWARD);
        }
    }
}