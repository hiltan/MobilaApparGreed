package uppgift1.greed;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {
    static final String SCORE_THIS_TURN = "0";
    static final String TOTAL_SCORE = "0";
    static final String TURN_ROUND = "0";
    static final String TURN = "0";

    private int scoreThisTurn;
    private int totalScore;
    private boolean turnEnded;
    private int turnRound;
    private int turn;

    private ArrayList<Die> dice = new ArrayList<Die>();

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
//         savedInstanceState.putInt(SCORE_THIS_TURN, scoreThisTurn);
//         savedInstanceState.putInt(TOTAL_SCORE, totalScore);
        //        savedInstanceState.putInt(TURN_ROUND, turnRound);
        //    savedInstanceState.putInt(TURN, turn);
//
        // Always call the superclass so it can save the view hierarchy state
        //       super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("HEEEJ");
        super.onCreate(savedInstanceState);
        int displayInfo = getResources().getConfiguration().orientation;
        if(displayInfo == Configuration.ORIENTATION_LANDSCAPE){
            Log.d("Orientation", "Landscape mode");
            setContentView(R.layout.activity_main);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else {
            Log.d("Orientation", "Portrait mode");
            setContentView(R.layout.activity_main);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        if (savedInstanceState != null) {
            scoreThisTurn = savedInstanceState.getInt(SCORE_THIS_TURN);
            totalScore = savedInstanceState.getInt(TOTAL_SCORE);
            turnRound = savedInstanceState.getInt(TURN_ROUND);
            turn = savedInstanceState.getInt(TURN);
        } else {
            Die die1 = (Die) findViewById(R.id.first_dice);
            Die die2 = (Die) findViewById(R.id.second_dice);
            Die die3 = (Die) findViewById(R.id.third_dice);
            Die die4 = (Die) findViewById(R.id.fourth_dice);
            Die die5 = (Die) findViewById(R.id.fifth_dice);
            Die die6 = (Die) findViewById(R.id.sixth_dice);

            dice.add(die1);
            dice.add(die2);
            dice.add(die3);
            dice.add(die4);
            dice.add(die5);
            dice.add(die6);

            turnRound = 1;
            turn = 1;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void saveDice(View view) {
        totalScore = totalScore + scoreThisTurn;
        if(totalScore >= 10000) {
            Intent intent = new Intent(this, gameFinished.class);
            intent.putExtra("score", totalScore);
            intent.putExtra("turn", turn);
            startActivity(intent);
        }
        String turnText = "Score: " + totalScore;
        TextView turnScore = (TextView) findViewById(R.id.total_score);
        turnScore.setText((CharSequence) turnText);
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setEnabled(false);
        turnEnded = true;
    }

    public ArrayList<Die> getScoringDice(ArrayList<Die> dice) {
        ArrayList<Integer> dieValues = ScoreCalculator.getDiceValues(dice);
        ArrayList<Integer> threeOfAKind = ScoreCalculator.calculateThreeOfAKind(dieValues);
        ArrayList<Die> scoringDice = new ArrayList<Die>();
        for(Integer val: threeOfAKind) {
            int count = 0;
            for(Die die: dice) {
                if(val == die.getValue() && count < 3) {
                    count++;
                    die.setGivePoints();
                    scoringDice.add(die);
                    die.setLocked();
                }
            }
        }
        if(ScoreCalculator.calculateStraight(dieValues)) {
            for(Die die: dice) {
                die.setGivePoints();
                scoringDice.add(die);
                die.setLocked();
            }
        }
        for(Die die: dice) {
            if((die.getValue()==1 || die.getValue() == 5)) {
                die.setGivePoints();
                scoringDice.add(die);
                die.setLocked();
            }
        }
        return scoringDice;
    }

    public void scoreButton(View view) {
        ArrayList<Die> onHoldOrLockedDice = new ArrayList<Die>();
        ArrayList<Die> onHoldDice = new ArrayList<Die>();
        for(Die die:dice) {
            if(die.onHold && !die.isLocked()) {
                onHoldDice.add(die);
            }
        }
        ArrayList<Die> scoringDice = getScoringDice(onHoldDice);
        Integer score = scoreThisTurn + ScoreCalculator.calculateScore(scoringDice);
        if((turnRound == 1 && score >= 300) || (turnRound > 1 && score > scoreThisTurn)) {
            turnEnded = false;
            scoreThisTurn = score;
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
                turnEnded = false;
                die.givePoints = false;
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
        turnEnded = true;
        checkDice();
        for(Die die:dice) {
            if(!die.locked) {
                die.rollDie();
                rolledDice.add(die);
            }
        }

        if(turnRound == 1 && ScoreCalculator.calculateScore(dice) < 300) {
            saveButton.setEnabled(false);
            scoreButton.setEnabled(false);
        } else if(turnRound > 1 && ScoreCalculator.calculateScore(rolledDice) == 0) {
            saveButton.setEnabled(false);
            scoreButton.setEnabled(false);
        }
    }
}
