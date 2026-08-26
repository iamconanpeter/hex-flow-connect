package com.gamefactory.hexflowconnect;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends Activity {
    private HexBoardView boardView;
    private TextView status;
    private long undoTokens = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = findViewById(R.id.hexBoard);
        status = findViewById(R.id.status);
        Button undoBtn = findViewById(R.id.undoBtn);
        Button restartBtn = findViewById(R.id.restartBtn);

        Calendar cal = Calendar.getInstance();
        long seed = SeededBoardFactory.dailySeed(
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        SeededBoardFactory factory = new SeededBoardFactory(seed);
        HexBoard board = factory.generate(3, 6, 3);
        boardView.setBoard(board);

        boardView.setOnPathChangedListener((victory, len) -> {
            if (victory) status.setText("Victory! Path length: " + len);
            else status.setText("Path: " + len);
        });

        undoBtn.setOnClickListener(v -> {
            if (undoTokens > 0) {
                if (boardView.undo()) {
                    undoTokens--;
                    status.setText("Undo used. Remaining: " + undoTokens);
                }
            } else status.setText("No undo tokens left.");
        });

        restartBtn.setOnClickListener(v -> {
            boardView.setBoard(factory.generate(3, 6, 3));
            undoTokens = 1;
            status.setText("New daily board. 1 undo token granted.");
        });
    }
}
