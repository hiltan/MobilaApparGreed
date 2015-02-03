package uppgift1.greed;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Marcus on 15-01-23.
 */
public class ScoreCalculator {
    public ScoreCalculator() {

    }
    public static int calculateScore(ArrayList<Die> dice) {
        int score = 0;
        ArrayList<Integer> values = getDiceValues(dice);
        ArrayList<Integer> threeOfAKindValues = calculateThreeOfAKind(values);
        for(Integer val :threeOfAKindValues) {
            if(val==1) {
                score = score + 1000;
                values.remove(val);
                values.remove(val);
                values.remove(val);
            } else {
                score = score + (val*100);
                values.remove(val);
                values.remove(val);
                values.remove(val);
            }
        }
        if(calculateStraight(values)) {
            score = 1000;
            return score;
        }
        for(Integer val: values) {
            if(val == 1) {
                score = score + 100;
            } else if (val == 5) {
                score = score + 50;
            }
        }

        return score;
    }

    public static ArrayList<Integer> getDiceValues(ArrayList<Die> dice) {
        ArrayList<Integer> values = new ArrayList<Integer>();
        for(Die die:dice) {
            values.add(die.getValue());
        }
        return values;
    }

    public static boolean calculateStraight(ArrayList<Integer> dice) {
        if(dice.contains(1) && dice.contains(2) && dice.contains(3) &&
                dice.contains(4) && dice.contains(5) && dice.contains(6)) {
            return true;
        }
        return false;
    }
    public static ArrayList<Integer> calculateThreeOfAKind(ArrayList<Integer> dice) {
        int ones = 0;
        int twos = 0;
        int threes = 0;
        int fours = 0;
        int fives = 0;
        int sixes = 0;
        for(Integer die: dice) {
            if(die==1) {
                ones++;
            } else if(die==2) {
                twos++;
            } else if(die==3) {
                threes++;
            } else if(die==4) {
                fours++;
            } else if(die==5) {
                fives++;
            } else if(die==6) {
                sixes++;
            }
        }
        ArrayList<Integer> threeOfAKindValues = new ArrayList<Integer>();
        if(ones >= 3) {
            threeOfAKindValues.add(1);
        } else if(twos >= 3) {
            threeOfAKindValues.add(2);
        } else if(threes >= 3) {
            threeOfAKindValues.add(3);
        } else if(fours >= 3) {
            threeOfAKindValues.add(4);
        } else if(fives >= 3) {
            threeOfAKindValues.add(5);
        } else if(sixes >= 3) {
            threeOfAKindValues.add(6);
        }
        if(ones == 6) {
            threeOfAKindValues.add(1);
        } else if(twos == 6) {
            threeOfAKindValues.add(2);
        } else if(threes == 6) {
            threeOfAKindValues.add(3);
        } else if(fours == 6) {
            threeOfAKindValues.add(4);
        } else if(fives == 6) {
            threeOfAKindValues.add(5);
        } else if(sixes == 6) {
            threeOfAKindValues.add(6);
        }
        return threeOfAKindValues;
    }
}
