package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;



public class GridPanel extends JPanel {

    private final int gamePanelWidth;
    private final int gamePanelHeight;

    private BufferedImage gameImage;

    private int gridSquareSize;

 
    public GridPanel(int gridWidth, int gridHeight, int gridSquareSize) {
        this.gridSquareSize = gridSquareSize;
        gamePanelWidth = gridWidth * gridSquareSize;
        gamePanelHeight = gridHeight * gridSquareSize;
        gameImage = new BufferedImage(gamePanelWidth, gamePanelHeight, BufferedImage.TYPE_INT_ARGB);
        setBackground(Color.WHITE);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(gamePanelWidth, gamePanelHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameImage != null) {
            g.drawImage(gameImage, 0, 0, null);
        }
    }


    public void clearCanvas() {
        Graphics g = gameImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, gameImage.getWidth(), getHeight());
        g.dispose();
    }


    public int getGridWidth() {
        return gamePanelWidth / gridSquareSize;
    }

    public int getGridHeight() {
        return gamePanelHeight / gridSquareSize;
    }


    public void drawGrid() {
        Graphics g = gameImage.getGraphics();
        g.setColor(Color.LIGHT_GRAY);

        // vertical grid
        for (int i = 0; i < gamePanelWidth / gridSquareSize; i++) {
            int lineX = i * gridSquareSize;
            g.drawLine(lineX, 0, lineX, gamePanelHeight);
        }
        // horizontal grid
        for (int i = 0; i < gamePanelHeight / gridSquareSize; i++) {
            int lineY = i * (gridSquareSize);
            g.drawLine(0, lineY, gamePanelWidth, lineY);
        }
        g.dispose();
    }

    public void drawSquare(int gridX, int gridY, Color color) {
        if (gridX < 0 || gridY < 0 ||
                gridX >= gamePanelWidth / gridSquareSize || gridY >= gamePanelHeight / gridSquareSize) {
            return;
        }
        Graphics g = gameImage.getGraphics();
        g.setColor(color);
        int x = gridX * gridSquareSize + 1;
        int y = gridY * gridSquareSize + 1;
        g.fillRect(x, y, gridSquareSize - 1, gridSquareSize - 1);
        g.dispose();
    }


    public void drawSmallSquare(int gridX, int gridY, Color color) {
        if (gridX < 0 || gridY < 0 ||
                gridX >= gamePanelWidth / gridSquareSize || gridY >= gamePanelHeight / gridSquareSize) {
            return;
        }
        Graphics g = gameImage.getGraphics();
        g.setColor(color);
        int x = gridX * gridSquareSize + 3;
        int y = gridY * gridSquareSize + 3;
        g.fillRect(x, y, gridSquareSize - 5, gridSquareSize - 5);
        g.dispose();
    }

}
