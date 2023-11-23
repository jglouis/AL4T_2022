package view.screens;

import view.UIManager;
import view.ImageResourceManager;
import manager.GameEngine;

import java.awt.*;

public class PauseScreenRenderer implements ScreenRenderer {

    private UIManager uiManager;

    public PauseScreenRenderer(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setFont(uiManager.getGameFont().deriveFont(50f));
        g2.setColor(Color.WHITE);
        String displayedStr = "PAUSED";
        int stringLength = g2.getFontMetrics().stringWidth(displayedStr);
        g2.drawString(displayedStr, (uiManager.getWidth()-stringLength)/2, uiManager.getHeight()/2);
    }
}