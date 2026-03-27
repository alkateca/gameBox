package com.example.gamebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class GameListActivity extends AppCompatActivity {

    private static class GameItem {
        String name;
        int imageResId;

        GameItem(String name, int imageResId){
            this.name = name;
            this.imageResId = imageResId;
        }

    }

    private final GameItem[] myGames = {
            new GameItem("Disco Elysium", R.drawable.disco_elysium),
            new GameItem("Hollow Knight", R.drawable.hollow_knight),
            new GameItem("Slay the Spire", R.drawable.slay_the_spire)
    };
    private LinearLayout gameList;

    private final ArrayList<String> sendGameList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_list);

        gameList = findViewById(R.id.containerGames);

        generateGameList();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private int dpToPx (int dp){
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
    private void generateGameList(){

        int boxWidth = dpToPx(200);
        int boxHeigth = dpToPx(160);

        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(boxWidth, boxHeigth);

        for (GameItem game : myGames){

            LinearLayout gameItemLayout = new LinearLayout(this);


            LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            itemParams.setMargins(0, 0, 0, 40);
            gameItemLayout.setLayoutParams(itemParams);

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(game.imageResId);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setLayoutParams(imageParams);

            CheckBox cb = new CheckBox(this);
            cb.setText(game.name);
            cb.setPadding(0, 10, 0, 10);
            cb.setTextSize(18);

            cb.setOnCheckedChangeListener((v, isChecked) -> {
                if (isChecked) {
                    sendGameList.add(game.name);
                }
                else {
                    sendGameList.remove(game.name);
                }
            });

            gameList.addView(imageView);
            gameList.addView(cb);
        };
    }

    public void  onClickBtnGameList (View view){

        StringBuilder emailBody =  new StringBuilder();

        emailBody.append("Segue minha lista de recomendações:\n\n");

        for (String game : sendGameList) {
            emailBody.append("- ").append(game).append("\n");
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Veja minha lista de recomendações!");
        intent.putExtra(Intent.EXTRA_TEXT, emailBody.toString());
        startActivity(intent);

    }


}