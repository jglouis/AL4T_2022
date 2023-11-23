package view.screens;

import view.UIManager;
import view.ImageResourceManager;
import manager.GameEngine;

import java.awt.Graphics2D;

public class StartScreenRenderer implements ScreenRenderer {

    private UIManager uiManager;

    public StartScreenRenderer(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    @Override
    public void render(Graphics2D g2) {
        ImageResourceManager irm = uiManager.getImageResourceManager();

        int row = uiManager.startScreenSelection.getLineNumber();
        g2.drawImage(irm.getStartScreenImage(), 0, 0, null);
        g2.drawImage(irm.getSelectIcon(), 375, row * 70 + 440, null);
    }
}