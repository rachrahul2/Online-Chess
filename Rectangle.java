import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

class Rectangle extends JComponent {
	
    private static final long serialVersionUID = 1L;
    private Color rectangleColor;

    Rectangle(Color color){
    	this.rectangleColor = color;
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(50, 50);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 50);
    }

    @Override
    public void paintComponent(Graphics g) {
        int margin = 0;
        Dimension dim = getSize();
        super.paintComponent(g);
        
        g.setColor(this.rectangleColor);
        g.fillRect(0, 0, dim.width, dim.height);
    }
}
