package com.github.alturkovic;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.function.Supplier;

@Data
@EqualsAndHashCode(callSuper = true)
public class Cell extends Rectangle {
    public static final Supplier<Color> DEFAULT_STATIC_COLOR_SUPPLIER = () -> Color.RED;

    private Supplier<Color> colorSupplier;
    private boolean alive;

    public Cell() {
        this.colorSupplier = DEFAULT_STATIC_COLOR_SUPPLIER;
    }

    public void setAlive(final boolean alive) {
        this.alive = alive;

        if (alive) {
            setFill(colorSupplier.get());
        } else {
            setFill(Color.WHITE);
        }
    }
}
