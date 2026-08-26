package com.gamefactory.hexflowconnect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class HexBoardView extends View {
    private HexBoard board;
    private PathValidator validator;
    private final Paint nodePaint = new Paint();
    private final Paint blockerPaint = new Paint();
    private final Paint strokePaint = new Paint();
    private final List<float[]> points = new ArrayList<>();
    private OnPathChangedListener listener;
    private static final float HEX_SIZE = 60f;
    private static final float[][] COLORS = {
        {1f, 0.4f, 0.4f}, {0.4f, 0.8f, 0.4f}, {0.4f, 0.6f, 1f},
        {1f, 0.9f, 0.4f}, {0.8f, 0.4f, 1f}, {0.4f, 1f, 1f}
    };

    public interface OnPathChangedListener {
        void onPathChanged(boolean victory, int pathLength);
    }

    public HexBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        nodePaint.setStyle(Paint.Style.FILL);
        blockerPaint.setColor(Color.DKGRAY);
        blockerPaint.setStyle(Paint.Style.FILL);
        strokePaint.setColor(Color.WHITE);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(8f);
    }

    public void setBoard(HexBoard board) {
        this.board = board;
        this.validator = new PathValidator(board);
        invalidate();
    }

    public void setOnPathChangedListener(OnPathChangedListener l) { this.listener = l; }

    public boolean undo() {
        if (validator != null && !validator.getPath().isEmpty()) {
            validator.undo();
            rebuildPoints();
            invalidate();
            notifyListener();
            return true;
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (board == null) return;
        canvas.drawColor(Color.parseColor("#101020"));
        for (int q = -board.radius; q <= board.radius; q++) {
            for (int r = -board.radius; r <= board.radius; r++) {
                if (Math.abs(q + r) > board.radius) continue;
                HexBoard.Cell c = board.cellAt(q, r);
                if (c == null) continue;
                drawHex(canvas, c);
            }
        }
        if (points.size() >= 2) {
            Path p = new Path();
            p.moveTo(points.get(0)[0], points.get(0)[1]);
            for (int i = 1; i < points.size(); i++) p.lineTo(points.get(i)[0], points.get(i)[1]);
            canvas.drawPath(p, strokePaint);
        }
    }

    private void drawHex(Canvas canvas, HexBoard.Cell c) {
        float cx = getWidth() / 2f + (float) (HEX_SIZE * 1.5 * c.q);
        float cy = getHeight() / 2f + (float) (HEX_SIZE * Math.sqrt(3) * (c.r + c.q / 2.0));
        for (int i = 0; i < 6; i++) {
            float angle = (float) (Math.PI / 3.0 * i);
            float x = (float) (cx + HEX_SIZE * Math.cos(angle));
            float y = (float) (cy + HEX_SIZE * Math.sin(angle));
            if (i == 0) {
                // start hex — handled below
            }
        }
        if (c.type == HexBoard.NODE) {
            float[] col = COLORS[(c.color - 1) % COLORS.length];
            nodePaint.setColor(Color.rgb((int)(col[0]*255), (int)(col[1]*255), (int)(col[2]*255)));
            canvas.drawCircle(cx, cy, HEX_SIZE * 0.5f, nodePaint);
        } else if (c.type == HexBoard.BLOCKER) {
            canvas.drawCircle(cx, cy, HEX_SIZE * 0.4f, blockerPaint);
        } else {
            nodePaint.setColor(Color.parseColor("#252535"));
            canvas.drawCircle(cx, cy, HEX_SIZE * 0.25f, nodePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (board == null) return false;
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            HexBoard.Cell hit = hitTest(e.getX(), e.getY());
            if (hit == null) return false;
            if (validator.getPath().isEmpty()) {
                validator.visit(hit);
                rebuildPoints();
                invalidate();
                notifyListener();
            }
            return true;
        }
        if (e.getAction() == MotionEvent.ACTION_MOVE) {
            HexBoard.Cell hit = hitTest(e.getX(), e.getY());
            if (hit == null) return true;
            HexBoard.Cell prev = validator.getPath().isEmpty() ? null :
                validator.getPath().get(validator.getPath().size() - 1);
            if (validator.canVisit(prev, hit)) {
                validator.visit(hit);
                rebuildPoints();
                invalidate();
                notifyListener();
            }
            return true;
        }
        return super.onTouchEvent(e);
    }

    private void notifyListener() {
        if (listener != null) listener.onPathChanged(validator.isVictory(), validator.getPath().size());
    }

    private void rebuildPoints() {
        points.clear();
        for (HexBoard.Cell c : validator.getPath()) {
            float cx = getWidth() / 2f + (float) (HEX_SIZE * 1.5 * c.q);
            float cy = getHeight() / 2f + (float) (HEX_SIZE * Math.sqrt(3) * (c.r + c.q / 2.0));
            points.add(new float[]{cx, cy});
        }
    }

    private HexBoard.Cell hitTest(float x, float y) {
        HexBoard.Cell best = null;
        float bestDist = HEX_SIZE;
        for (int q = -board.radius; q <= board.radius; q++) {
            for (int r = -board.radius; r <= board.radius; r++) {
                if (Math.abs(q + r) > board.radius) continue;
                HexBoard.Cell c = board.cellAt(q, r);
                if (c == null) continue;
                float cx = getWidth() / 2f + (float) (HEX_SIZE * 1.5 * c.q);
                float cy = getHeight() / 2f + (float) (HEX_SIZE * Math.sqrt(3) * (c.r + c.q / 2.0));
                float d = (float) Math.hypot(x - cx, y - cy);
                if (d < bestDist) { bestDist = d; best = c; }
            }
        }
        return best;
    }
}
