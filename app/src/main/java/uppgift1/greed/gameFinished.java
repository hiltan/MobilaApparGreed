package uppgift1.greed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class gameFinished extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finished);
        int score = getIntent().getIntExtra("score", 0);
        int turns = getIntent().getIntExtra("turn", 0);
        String scoreText = "You got " + score + " points after " + turns + " rounds";
        TextView turnScore = (TextView) findViewById(R.id.finish_score);
        turnScore.setText((CharSequence) scoreText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_finished, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    public void tryAgainPushed(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
