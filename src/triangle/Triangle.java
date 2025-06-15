package triangle;

import java.awt.*;
import java.awt.image.BufferedImage;
import static resizable.Debug.print;
import resizable.ResizableImage;

/**
 * Implement your Sierpinski Triangle here.
 *
 *
 * You only need to change the drawTriangle
 * method!
 *
 *
 * If you want to, you can also adapt the
 * getResizeImage() method to draw a fast
 * preview.
 *
 */
public class Triangle implements ResizableImage {
    int drawTriangle = 0;

    /**
     * change this method to implement the triangle!
     *
     * @param size the outer bounds of the triangle
     * @return an Image containing the Triangle
     */
    private BufferedImage drawTriangle(Dimension size) {
        print("drawTriangle: " + ++drawTriangle + "size: " + size);
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();
        gBuffer.setColor(Color.black);
        int border = 2;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        gBuffer.setColor(Color.darkGray);
        border = 8;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        gBuffer.drawString("Triangle goes here", border * 2, border * 4);

        // Triangle
        int[] xPoints = {(size.width -border) / 2, border, size.width -border};
        int[] yPoints = {border, size.height -border, size.height -border};

        int maxDepth =10;

        // Start recursive
        drawSierpinski(gBuffer, xPoints, yPoints, maxDepth, maxDepth);


        return bufferedImage;
    }

    /**
     * Recursively draws the Sierpinski Triangle on the given Graphics2D context.
     */
    private void drawSierpinski(Graphics2D g, int[] x, int[] y, int depth, int maxDepth) {
        // Midpoints calculation
        int x0 = (x[0] + x[1]) / 2;
        int y0 = (y[0] + y[1]) / 2;
        int x1 = (x[1] + x[2]) / 2;
        int y1 = (y[1]);
        int x2 = (x[2] + x[0]) / 2;
        int y2 = (y[2] + y[0]) / 2;
        
        //color according fo recursion depth
        float tone = (float)(maxDepth - depth)/ 20;
        g.setColor(Color.getHSBColor(tone, 1.0f, 1.0f));
        g.fillPolygon(new int[]{x[0], x0, x2}, new int[]{y[2], y0, y2}, 3);
        if (depth == 0) {
            g.setColor(Color.getHSBColor(maxDepth+1, 0.6f, 1.0f));
            g.fillPolygon(x, y, 3);
            return;
        }

        //drawSierpinski(g, new int[]{x[0], x0, x2}, new int[]{y[2], y0, y2}, depth - 1, maxDepth);

        drawSierpinski(g, new int[]{x[0], x0, x2}, new int[]{y[0], y0, y2}, depth - 1, maxDepth);
        drawSierpinski(g, new int[]{x0, x[1], x1}, new int[]{y0, y[1], y1}, depth - 1, maxDepth);
        drawSierpinski(g, new int[]{x2, x1, x[2]}, new int[]{y2, y1, y[2]}, depth - 1, maxDepth);

    }

    BufferedImage bufferedImage;
    Dimension bufferedImageSize;

    @Override
    public Image getImage(Dimension triangleSize) {
        if (triangleSize.equals(bufferedImageSize))
            return bufferedImage;
        bufferedImage = drawTriangle(triangleSize);
        bufferedImageSize = triangleSize;
        return bufferedImage;
    }
    @Override
    public Image getResizeImage(Dimension size) {
        BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gBuffer = (Graphics2D) bufferedImage.getGraphics();
        gBuffer.setColor(Color.pink);
        int border = 2;
        gBuffer.drawRect(border, border, size.width - 2 * border, size.height - 2 * border);
        return bufferedImage;
    }
}
