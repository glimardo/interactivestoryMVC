package com.example.gian.interactivestory.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gian.interactivestory.R;
import com.example.gian.interactivestory.model.Page;
import com.example.gian.interactivestory.model.Story;

public class StoryActivity extends AppCompatActivity {

    public static final String TAG = StoryActivity.class.getSimpleName();

    private Story mStory = new Story();
    private ImageView mImageView;
    private TextView mTextView;
    private Button mChoise1;
    private Button mChoise2;
    private String mName;
    private Page mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Intent intent = getIntent();
        mName = intent.getStringExtra("name");

        if (mName == null)
        {
            mName = "Friend";
        }

        Log.d(TAG, mName);

        mImageView = (ImageView) findViewById(R.id.storyImageView);
        mTextView = (TextView) findViewById(R.id.storyTextView);
        mChoise1 = (Button) findViewById(R.id.choiceButton1);
        mChoise2 = (Button) findViewById(R.id.choiceButton2);

        loadPage(0);

    }

    private void loadPage(int choice) {

        mCurrentPage = mStory.getPage(choice);

        Drawable drawable = getResources().getDrawable(mCurrentPage.getImageId());
        mImageView.setImageDrawable(drawable);

        String pageText = mCurrentPage.getText();
        pageText = String.format(pageText, mName);
        mTextView.setText(pageText);

        if (mCurrentPage.isFinal()) {

            mChoise1.setVisibility(View.INVISIBLE);
            mChoise2.setText("PLAY AGAIN");
            mChoise2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    finish();
                }
            });

        }
            else
        {

            mChoise1.setText(mCurrentPage.getChoice1().getText());

            mChoise2.setText(mCurrentPage.getChoice2().getText());

            mChoise1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int nextPage = mCurrentPage.getChoice1().getNextPage();
                    loadPage(nextPage);
                }
            });

            mChoise2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int nextPage = mCurrentPage.getChoice2().getNextPage();
                    loadPage(nextPage);
                }
            });

        }

    }
}
