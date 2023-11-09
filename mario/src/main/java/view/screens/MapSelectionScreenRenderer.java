package view.screens;

import view.UIManager;
import view.ImageResourceManager;
import view.MapSelection;
import manager.GameEngine;

import java.awt.*;

public class MapSelectionScreenRenderer implements ScreenRenderer {

    private UIManager uiManager;
    private MapSelection mapSelection;

    public MapSelectionScreenRenderer(UIManager uiManager, MapSelection mapSelection) {
        this.uiManager = uiManager;
        this.mapSelection = mapSelection;
    }

    @Override
    public void render(Graphics2D g2) {
        ImageResourceManager irm = uiManager.getImageResourceManager();
        GameEngine engine = uiManager.getGameEngine();

        g2.setFont(uiManager.getGameFont().deriveFont(50f));
        g2.setColor(Color.WHITE);
        mapSelection.draw(g2);
        int row = engine.getSelectedMap();
        int y_location = row*100+300-irm.getSelectIcon().getHeight();
        g2.drawImage(irm.getSelectIcon(), 375, y_location, null);
    }
}