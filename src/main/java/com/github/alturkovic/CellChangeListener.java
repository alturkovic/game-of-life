package com.github.alturkovic;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import lombok.Data;

@Data
public class CellChangeListener implements EventHandler<MouseEvent> {
    private final LifeGridPane root;

    @Override
    public void handle(final MouseEvent e) {
        for (final Node node : root.getChildren()) {
            if (node instanceof Cell) {
                if (node.getBoundsInParent().contains(e.getSceneX(), e.getSceneY())) {
                    if (e.isPrimaryButtonDown() && !((Cell) node).isAlive()) {
                        ((Cell) node).setAlive(true);
                    } else if (e.isSecondaryButtonDown() && ((Cell) node).isAlive()) {
                        ((Cell) node).setAlive(false);
                    }
                    e.consume();
                    return;
                }
            }
        }
    }
}
