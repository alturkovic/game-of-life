package com.github.alturkovic;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Timer;
import java.util.TimerTask;

@Data
@EqualsAndHashCode(callSuper = true)
public class LifeGridPane extends Pane {

    private final int size;
    private final int count;

    private final Cell[][] grid;
    private Timer updateGridTimer;
    private int lastDefinedStep;

    private boolean running;
    private boolean runningBeforeClick;

    public LifeGridPane(final int size, final int count) {
        this.size = size;
        this.count = count;

        grid = getEmptyGrid();
        updateGridTimer = new Timer();

        final CellChangeListener cellChangeListener = new CellChangeListener(this);
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, cellChangeListener);
        this.addEventFilter(MouseEvent.MOUSE_DRAGGED, cellChangeListener);

        this.addEventFilter(MouseEvent.MOUSE_PRESSED, new DrawStopChangeListener(this));
        this.addEventFilter(MouseEvent.MOUSE_RELEASED, new DrawProceedChangeListener(this));
    }

    private Cell[][] getEmptyGrid() {
        final Cell[][] grid = new Cell[count][count];
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                grid[i][j] = new Cell();
                grid[i][j].setX(i * size);
                grid[i][j].setY(j * size);
                grid[i][j].setWidth(size);
                grid[i][j].setHeight(size);
                grid[i][j].setFill(null);
                grid[i][j].setAlive(false);
                grid[i][j].setStroke(Color.BLACK);
                this.getChildren().add(grid[i][j]);
            }
        }
        return grid;
    }

    public void update() {

        final boolean[][] next = new boolean[count][count];

        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                final int neighbors = countNeighbors(grid, i, j);

                if ((neighbors < 2) || (neighbors > 3)) {
                    next[i][j] = false;
                }

                if (neighbors == 2) {
                    next[i][j] = grid[i][j].isAlive();
                }

                if (neighbors == 3) {
                    next[i][j] = true;
                }
            }
        }

        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[i].length; ++j) {
                grid[i][j].setAlive(next[i][j]);
            }
        }
    }

    private int countNeighbors(final Cell[][] current, final int row, final int col) {
        int neighbors = 0;

        // Look NW
        if ((row - 1 >= 0) && (col - 1 >= 0)) {
            neighbors = current[row - 1][col - 1].isAlive() ? neighbors + 1 : neighbors;
        }
        // Look W
        if ((row >= 0) && (col - 1 >= 0)) {
            neighbors = current[row][col - 1].isAlive() ? neighbors + 1 : neighbors;
        }
        // Look SW
        if ((row + 1 < current.length) && (col - 1 >= 0)) {
            neighbors = current[row + 1][col - 1].isAlive() ? neighbors + 1 : neighbors;
        }
        // Look S
        if ((row + 1 < current.length) && (col < current[0].length)) {
            neighbors = current[row + 1][col].isAlive() ? neighbors + 1 : neighbors;
        }
        // Look SE
        if ((row + 1 < current.length) && (col + 1 < current[0].length)) {
            neighbors = current[row + 1][col + 1].isAlive() ? neighbors + 1 : neighbors;
        }
        // Look E
        if ((row < current.length) && (col + 1 < current[0].length)) {
            neighbors = current[row][col + 1].isAlive() ? neighbors + 1 : neighbors;
        }
        // Look NE
        if ((row - 1 >= 0) && (col + 1 < current[0].length)) {
            neighbors = current[row - 1][col + 1].isAlive() ? neighbors + 1 : neighbors;
        }
        // Look N
        if ((row - 1 >= 0) && (col < current[0].length)) {
            neighbors = current[row - 1][col].isAlive() ? neighbors + 1 : neighbors;
        }

        return neighbors;
    }

    public void start(final int step) {
        updateGridTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, step);

        lastDefinedStep = step;
        running = true;
    }

    public void stop() {
        updateGridTimer.cancel();
        updateGridTimer = new Timer();

        running = false;
    }

    public void reset() {
        for (final Node node : getChildren()) {
            if (node instanceof Cell) {
                ((Cell) node).setAlive(false);
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (int j = 0; j < count; j++) {
            for (int i = 0; i < count; i++) {
                sb.append(grid[i][j].isAlive() ? 1 : 0);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
