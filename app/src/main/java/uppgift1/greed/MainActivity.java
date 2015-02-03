package uppgift1.greed;

import android.support.annotation.DrawableRes;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class MainActivity extends ActionBarActivity {
    private int scoreThisTurn;
    private int totalScore;
    private boolean turnEnded;
    private int turnRound;
    private int turn;

    private ArrayList<Die> dice = new ArrayList<Die>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Die die1 = (Die) findViewById(R.id.first_dice);
        Die die2 = (Die) findViewById(R.id.second_dice);
        Die die3 = (Die) findViewById(R.id.third_dice);
        Die die4 = (Die) findViewById(R.id.fourth_dice);
        Die die5 = (Die) findViewById(R.id.fifth_dice);
        Die die6 = (Die) findViewById(R.id.sixth_dice);

        dice.add(die1);
        dice.add( die2);
        dice.add(die3);
        dice.add(die4);
        dice.add(die5);
        dice.add(die6);

        turnRound = 1;
        turn = 1;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveDice(View view) {
        totalScore = totalScore + scoreThisTurn;
        String turnText = "Score: " + totalScore;
        TextView turnScore = (TextView) findViewById(R.id.total_score);
        turnScore.setText((CharSequence) turnText);
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setEnabled(false);
        turnEnded = true;
    }

    public void scoreButton(View view) {
        ArrayList<Die> onHoldDice = new ArrayList<Die>();
        for(Die die:dice) {
            if(die.onHold && !die.locked) {
                onHoldDice.add(die);
                die.setLocked();
            }
        }
        Integer score = ScoreCalculator.calculateScore(onHoldDice);
        if((turnRound == 1 && score >= 300) || (turnRound > 1 && score > 0)) {
            turnEnded = false;
            scoreThisTurn = scoreThisTurn + score;
            String turnText = "Turn score: " + scoreThisTurn;
            TextView turnScore = (TextView) findViewById(R.id.turn_score);
            turnScore.setText((CharSequence) turnText);
            turnRound++;
            Button scoreButton = (Button) findViewById(R.id.score_button);
            scoreButton.setEnabled(false);
        }
    }

    public void holdDice(View view) {
        Die die = (Die) findViewById(view.getId());
        if(!die.locked) {
            die.changeOnHold();
        }
    }

    public void newTurn() {
        turn ++;
        scoreThisTurn = 0;
        turnRound = 1;
        turnEnded = false;
        for(Die die:dice) {
            die.setUnlocked();
            die.onHold = false;
        }
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setEnabled(false);
        String turnText = "Turn: " + turn;
        TextView turnNumber = (TextView) findViewById(R.id.turn_number);
        turnNumber.setText((CharSequence) turnText);
        String turnText1 = "Turn score: 0";
        TextView turnScore = (TextView) findViewById(R.id.turn_score);
        turnScore.setText((CharSequence) turnText1);
    }

    public void checkDice() {
        int count = 0;
        for(Die die:dice) {
            if(die.locked) {
                count++;
            }
        }
        if(count == 6) {
            for(Die die:dice) {
                die.setUnlocked();
                die.onHold = false;
            }
        }
    }

    public void throwDice(View view) {
        ArrayList<Die> rolledDice = new ArrayList<Die>();
        if(turnEnded) {
            newTurn();
        }
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setEnabled(true);
        Button scoreButton = (Button) findViewById(R.id.score_button);
        scoreButton.setEnabled(true);
        checkDice();
        for(Die die:dice) {
            if(!die.locked) {
                die.rollDie();
                rolledDice.add(die);
            }
        }
        turnEnded = true;

        if(turnRound == 1 && ScoreCalculator.calculateScore(dice) < 300) {
            saveButton.setEnabled(false);
            scoreButton.setEnabled(false);
        } else if(turnRound > 1 && ScoreCalculator.calculateScore(rolledDice) == 0) {
            saveButton.setEnabled(false);
            scoreButton.setEnabled(false);
        }
    }
}
