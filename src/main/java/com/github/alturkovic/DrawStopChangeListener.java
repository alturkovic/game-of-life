package com.github.alturkovic;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import lombok.Data;

@Data
public class DrawStopChangeListener implements EventHandler<MouseEvent> {
    private final LifeGridPane lifeGridPane;

    @Override
    public void handle(final MouseEvent event) {
        if (lifeGridPane.isRunning()) {
            lifeGridPane.setRunningBeforeClick(true);
            lifeGridPane.stop();
        } else {
            lifeGridPane.setRunningBeforeClick(false);
        }
    }
}
