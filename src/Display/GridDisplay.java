package Display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import Movement.RobotPosition;

@SuppressWarnings("serial")
public class GridDisplay extends JPanel{

	private final int GRID_SIZE = 20;
	private final int WINDOW_SIZE = 960;
	private final float CM_TO_PX_CONSTANT = (WINDOW_SIZE / GRID_SIZE) / 10.0f; // 1 cm = 4.8 pixels
	private final float RECTANGLE_WIDTH = 10 * CM_TO_PX_CONSTANT;
	private final float RECTANGLE_HEIGHT = 1.5f * CM_TO_PX_CONSTANT;
	
	
	private final BasicStroke HEAVY_STROKE = new BasicStroke(2f);
	private final BasicStroke LIGHT_STROKE = new BasicStroke(1f);
	
	private final Color ROBOT_COLOR = new Color(255, 0, 0);
	private final Color BASIC_COLOR = new Color(255, 255, 255);

	private ArrayList<RobotPosition> positions = null;

	
	public GridDisplay() {
    	GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        positions = new ArrayList<RobotPosition>();
	}
	
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(WINDOW_SIZE, WINDOW_SIZE);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();

        drawMapGrid(g2d);
        g2d.translate(WINDOW_SIZE / 2, WINDOW_SIZE / 2);
        drawPositions(g2d);
    }

    /**
     * Add a position to display
     * @param pos The position to be added
     */
    public void addPosition(RobotPosition pos) {
    	this.positions.add(pos);
    	this.repaint();
    }
    
    private void drawPositions(Graphics2D g2d) {
    	if (positions.isEmpty()) {
    		return;
    	}
		g2d.setColor(ROBOT_COLOR);
		AffineTransform original = g2d.getTransform();
		
		for (int i = 1; i < positions.size(); i++) {
			RobotPosition pos1 = positions.get(i - 1);
			RobotPosition pos2 = positions.get(i);
			
			// If neither of the position represents an obstacle, draw a line between them to show robot movement
			if (!pos1.isObstackle() && !pos2.isObstackle()) {
				// Multiply coordinates with the constant to convert them from cm to px
	    		float x1 = pos1.getX() * CM_TO_PX_CONSTANT;
	    		float y1 = pos1.getY() * CM_TO_PX_CONSTANT;
	    		float x2 = pos2.getX() * CM_TO_PX_CONSTANT;
	    		float y2 = pos2.getY() * CM_TO_PX_CONSTANT;
				Shape line = new Line2D.Float(x1, y1, x2, y2);
				g2d.draw(line);
				g2d.drawOval(Math.round(x2 - 3), Math.round(y2 - 3), 6, 6);
			}
			else {
				try {
					float x1 = (pos1.isObstackle()? pos1.getX() : pos2.getX()) * CM_TO_PX_CONSTANT;
		    		float y1 = (pos1.isObstackle()? pos1.getY() : pos2.getY()) * CM_TO_PX_CONSTANT;
					float heading = (pos1.isObstackle()? pos1.getHeading() : pos2.getHeading());
				
	    		
		    		Shape rect = new Rectangle2D.Float(x1 - RECTANGLE_HEIGHT / 2, y1 - RECTANGLE_WIDTH / 2, RECTANGLE_HEIGHT, RECTANGLE_WIDTH);
		    		g2d.rotate(heading, x1, y1);
		    		g2d.draw(rect);
		    		g2d.setTransform(original);
		    		
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					continue;
				}
			}
			
		}
		g2d.setColor(BASIC_COLOR);
    }
    
    private void drawMapGrid(Graphics2D g2d) {
        int i;
        int width = getSize().width;
        int height = getSize().height;

        // draw the rows
        int rowHt = height / (GRID_SIZE);
        for (i = 0; i < GRID_SIZE; i++) {
        	if(i == GRID_SIZE / 2) { // Draw vertical axis line
        		g2d.setStroke(HEAVY_STROKE);
        		g2d.drawLine(0, i * rowHt, width, i * rowHt);
        		g2d.setStroke(LIGHT_STROKE);
        	}
        	else {
        		g2d.drawLine(0, i * rowHt, width, i * rowHt);
        	}
        }
        // draw the columns
        int rowWid = width / (GRID_SIZE);
        for (i = 0; i < GRID_SIZE; i++) {
        	if(i == GRID_SIZE / 2) { // Draw horizontal axis line
        		g2d.setStroke(HEAVY_STROKE);
        		g2d.drawLine(i * rowWid, 0, i * rowWid, height);
        		g2d.setStroke(LIGHT_STROKE);
        	}
        	else {
        		g2d.drawLine(i * rowWid, 0, i * rowWid, height);
        	}
        }
	}
    
}
