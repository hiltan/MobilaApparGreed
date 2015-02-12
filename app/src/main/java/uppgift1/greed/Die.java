package uppgift1.greed;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageButton;

import java.util.Random;

/**
 * Created by Marcus on 15-01-22.
 */
public class Die extends ImageButton implements Parcelable{
    int value;
    boolean locked = false;
    boolean onHold = false;
    boolean givePoints = false;

    public boolean isLocked() {
        return locked;
    }

    public void setGivePoints () {
        this.givePoints = true;
    }

    public void setNoPoints() {
        this.givePoints = false;
    }

    public void setLocked() {
        this.locked = true;
        setRed(value);
    }

    public void setUnlocked() {
        this.locked = false;
        setWhite(value);
    }

    public Die(Context context) {
        super(context);
    }

    public Die(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public Die(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    public void changeOnHold() {
        if (!onHold) {
            onHold = true;
            setGrey(value);
        }
        else {
            onHold = false;
            setWhite(value);
        }

    }

    public void rollDie() {
        Random randomGenerator = new Random();
        value = randomGenerator.nextInt(6) + 1;
        setWhite(value);
        onHold = false;
    }

    public void setWhite(int value) {
        if(value==1) {
            setImageResource(R.drawable.white1);
        } else if(value==2) {
            setImageResource(R.drawable.white2);
        }else if(value==3) {
            setImageResource(R.drawable.white3);
        }else if(value==4) {
            setImageResource(R.drawable.white4);
        }else if(value==5) {
            setImageResource(R.drawable.white5);
        }else if(value==6) {
            setImageResource(R.drawable.white6);
        }
    }

    public void setGrey(int value) {
        if(value==1) {
            setImageResource(R.drawable.grey1);
        } else if(value==2) {
            setImageResource(R.drawable.grey2);
        }else if(value==3) {
            setImageResource(R.drawable.grey3);
        }else if(value==4) {
            setImageResource(R.drawable.grey4);
        }else if(value==5) {
            setImageResource(R.drawable.grey5);
        }else if(value==6) {
            setImageResource(R.drawable.grey6);
        }
    }
    public void setRed(int value) {
        if(value==1) {
            setImageResource(R.drawable.red1);
        } else if(value==2) {
            setImageResource(R.drawable.red2);
        }else if(value==3) {
            setImageResource(R.drawable.red3);
        }else if(value==4) {
            setImageResource(R.drawable.red4);
        }else if(value==5) {
            setImageResource(R.drawable.red5);
        }else if(value==6) {
            setImageResource(R.drawable.red6);
        }
    }
    public int getValue(){
        return value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
