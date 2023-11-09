package view.screens;

import view.UIManager;
import view.ImageResourceManager;
import manager.GameEngine;

import java.awt.Graphics2D;

public class AboutScreenRenderer implements ScreenRenderer {

    private UIManager uiManager;

    public AboutScreenRenderer(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    @Override
    public void render(Graphics2D g2) {
        ImageResourceManager irm = uiManager.getImageResourceManager();

        g2.drawImage(irm.getAboutScreenImage(), 0, 0, null);
    }
}