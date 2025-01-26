import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

class DrawingPanel extends JPanel {
    private Graphics2D graphics;
    private BufferedImage bfImage;
    private int nextX, nextY, currX, currY;
    private Color color = Color.RED;
    private int radius = 1;

    DrawingPanel() {
        bfImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
        graphics = bfImage.createGraphics();
        graphics.fillRect(0, 0, 800, 800);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currX = e.getX();
                currY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                nextX = e.getX();
                nextY = e.getY();
                graphics.drawLine(currX, currY, nextX, nextY);
                graphics.setColor(color);
                graphics.setStroke(new BasicStroke(radius));
                currX = nextX;
                currY = nextY;
                repaint();
            }
        });
    }

    public BufferedImage getMyImage() {
        return bfImage;
    }

    public void setMyGraphics(Graphics2D g) {
        graphics = g;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setMyImage(BufferedImage image) {
        bfImage = image;


    }

    public void clear() {
        bfImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
        graphics = bfImage.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        setPreferredSize(new Dimension(800, 800));
        revalidate();
        repaint();
    }

    public void changeRadius(int radius) {
        if (radius < 1) {
            throw new IllegalArgumentException("Incorrect size radius");
        }
        this.radius = radius;
        graphics.setStroke(new BasicStroke(radius));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bfImage, 0, 0, this);
    }
}