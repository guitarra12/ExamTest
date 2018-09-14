package mx.iteso.examentest;

import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

public class ActivityMain extends AppCompatActivity {
    private int rgColorChosed = -1;
    private int rgSizeChosed = -1;
    private boolean addedToCart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null) {
            restoreUserChoices(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("color", rgColorChosed);
        outState.putInt("size", rgSizeChosed);
        outState.putBoolean("button", addedToCart);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreUserChoices(savedInstanceState);
    }

    private void restoreUserChoices(Bundle bundle) {
        rgColorChosed = bundle.getInt("color");
        rgSizeChosed = bundle.getInt("size");
        addedToCart = bundle.getBoolean("button");

        if(rgColorChosed==-1||rgSizeChosed==-1)
            return;

        //Restaura los botones
        int []arrIdBtns = new int[4];
        arrIdBtns[0] = R.id.am_btn_medium;
        arrIdBtns[1] = R.id.am_btn_small;
        arrIdBtns[2] = R.id.am_btn_large;
        arrIdBtns[3] = R.id.am_btn_xlarge;

        for(int i=0;i<4;i++) {
            Button button = findViewById(arrIdBtns[i]);
            if(arrIdBtns[i]==rgSizeChosed) {
                button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                button.setTextColor(getResources().getColor(android.R.color.white));
            } else {
                button.setBackgroundColor(getResources().getColor(android.R.color.white));
                button.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }

        //Restaura los radiobuttons
        int []arrIdRBtns = new int[4];
        arrIdRBtns[0] = R.id.am_rb_gray;
        arrIdRBtns[1] = R.id.am_rb_brown;
        arrIdRBtns[2] = R.id.am_rb_pink;
        arrIdRBtns[3] = R.id.am_rb_black;

        for(int i=0;i<4;i++) {
            RadioButton button = findViewById(arrIdRBtns[i]);
            button.setChecked(arrIdBtns[i]==rgColorChosed);
        }

        //Restaura el botón
        Button button = findViewById(R.id.am_btn_addtocart);
        if(addedToCart) {
            button.setText(getResources().getString(R.string.msg_added));
        } else {
            button.setText(getResources().getString(R.string.text_btn_addtocart));
        }
    }

    public void btnLikeOnAction(View v) {
        Toast.makeText(this, getResources().getString(R.string.like_message),Toast.LENGTH_SHORT).show();
    }

    public void rgColorChanged(View v) {
        rgColorChosed = v.getId();
    }

    public void rgSizeChanged(View v) {
        int []arrIdBtns = new int[4];

        rgSizeChosed = v.getId();

        arrIdBtns[0] = R.id.am_btn_medium;
        arrIdBtns[1] = R.id.am_btn_small;
        arrIdBtns[2] = R.id.am_btn_large;
        arrIdBtns[3] = R.id.am_btn_xlarge;

        for(int i=0;i<4;i++) {
            Button button = findViewById(arrIdBtns[i]);
            if(arrIdBtns[i]==rgSizeChosed) {
                button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                button.setTextColor(getResources().getColor(android.R.color.white));
            } else {
                button.setBackgroundColor(getResources().getColor(android.R.color.white));
                button.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    }

    public void addToCartOnAction(View v) {
        Button button = findViewById(v.getId());

        if(!addedToCart) { //será añadido
            button.setText(getResources().getString(R.string.msg_added));
            final ScrollView sv = findViewById(R.id.am_scroll_view);
            Snackbar.make(sv,getResources().getString(R.string.msg_added), Snackbar.LENGTH_LONG)
            .setAction(getResources().getString(R.string.undo), (View view)->{
                button.setText(getResources().getString(R.string.text_btn_addtocart));
                addedToCart = !addedToCart;
            }).show();
            addedToCart = !addedToCart;
        } else {
            Toast.makeText(this, getResources().getString(R.string.msg_already),Toast.LENGTH_SHORT).show();
        }
    }
}
