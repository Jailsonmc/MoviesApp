package movies.flag.pt.moviesapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import movies.flag.pt.moviesapp.R;

/**
 * Created by jailsoncavalcanti on 15/10/2017.
 */

public class MainScreen  extends Screen{

    private Button openMovies;
    private Button openTVSeries;
    private Button openSearch;
    private EditText textInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_screen);
        findViews();
        addListeners();

    }

    private void addListeners() {
        openMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this, MovieScreen.class));
            }
        });

        openSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this, SearchMovieScreen.class);
                String text = textInput.getText().toString();
                intent.putExtra( SearchMovieScreen.DETAILS_TEXT_EXTRA, text);
                startActivity(intent);
            }
        });
        openTVSeries.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreen.this, TvSeriesScreen.class));
            }
        });

        textInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                openSearch.setEnabled(s.length() > 0);
            }
        });


    }

    private void findViews() {
        openMovies = (Button) findViewById(R.id.main_screen_open_movies_button);
        openTVSeries = (Button) findViewById(R.id.main_screen_open_series_button);
        openSearch = (Button) findViewById(R.id.main_screen_open_search_button);
        textInput = (EditText) findViewById(R.id.main_screen_input_search);
    }


}
