package com.github.alturkovic;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Cell extends Rectangle {
    private boolean alive;

    public void setAlive(final boolean alive) {
        this.alive = alive;

        if (alive) {
            setFill(Color.RED);
        } else {
            setFill(Color.WHITE);
        }
    }
}
