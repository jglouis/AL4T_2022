package view.screens;

import view.UIManager;
import view.ImageResourceManager;
import view.MapSelection;
import manager.GameEngine;

import java.awt.*;

public class MapSelectionScreenRenderer implements ScreenRenderer {

    private UIManager uiManager;

    public MapSelectionScreenRenderer(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    @Override
    public void render(Graphics2D g2) {
        ImageResourceManager irm = uiManager.getImageResourceManager();

        g2.setFont(uiManager.getGameFont().deriveFont(50f));
        g2.setColor(Color.WHITE);
        uiManager.mapSelectionRender(g2);
        int row = uiManager.selectedMap;
        int y_location = row*100+300-irm.getSelectIcon().getHeight();
        g2.drawImage(irm.getSelectIcon(), 375, y_location, null);
    }
}