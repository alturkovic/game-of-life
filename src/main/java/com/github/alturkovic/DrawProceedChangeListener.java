package com.github.alturkovic;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import lombok.Data;

@Data
public class DrawProceedChangeListener implements EventHandler<MouseEvent> {
    private final LifeGridPane lifeGridPane;

    @Override
    public void handle(final MouseEvent event) {
        if (lifeGridPane.isRunningBeforeClick()) {
            lifeGridPane.start(lifeGridPane.getLastDefinedStep());
        }
    }
}
