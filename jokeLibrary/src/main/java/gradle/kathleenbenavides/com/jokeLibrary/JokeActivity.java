package gradle.kathleenbenavides.com.jokeLibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        TextView text = (TextView) findViewById(R.id.joke_text);

        Intent intent = getIntent();
        String joke = intent.getStringExtra(getString(R.string.jokeId));
        if(joke != null){
            text.setText(joke);
        } else {
            text.setText(getString(R.string.noJoke));
        }
    }
}
