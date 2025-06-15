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

        // equatorial triangle corner points
        size.width -= border;
        size.height -= border;

        double min = Math.min(size.width, (2 / Math.sqrt(3)) * size.height);
        double s = (Math.sqrt(3) / 2) * min;


        int y0 = (int)((size.height - s) / 2); // vertically centered
        int[] xPoints = {(size.width / 2), (int)((size.width / 2) - min / 2), (int)((size.width / 2) + min / 2)};
        int[] yPoints = {(int)((size.height - s) / 2), (int)(((size.height - s) / 2) + s), (int)(((size.height - s) / 2) + s)};


        for (int i = 0; i < xPoints.length; i++) {
            if ("0".equalsIgnoreCase(String.valueOf(xPoints[i]))) {
                xPoints[i] += border;
            }
        }

        int maxDepth =15;

        // start recursive
        drawSierpinski(gBuffer, xPoints, yPoints, maxDepth, maxDepth);


        return bufferedImage;
    }

    private void drawSierpinski(Graphics2D g, int[] x, int[] y, int depth, int maxDepth) {
        // test is triangle to small
        boolean isNotTri = (x[0] == x[1] && y[0] == y[1]) ||
                (x[1] == x[2] && y[1] == y[2]) ||
                (x[2] == x[0] && y[2] == y[0]);

        if (isNotTri) {
            depth = 0;
        }

        // midpoints calculation
        int x0 = (x[0] + x[1]) / 2;
        int y0 = (y[0] + y[1]) / 2;
        int x1 = (x[1] + x[2]) / 2;
        int y1 = (y[1] + y[2]) / 2;
        int x2 = (x[2] + x[0]) / 2;
        int y2 = (y[2] + y[0]) / 2;

        if (depth != 0) {
            //color according fo recursion depth
            float tone = (float)(maxDepth - depth)/ maxDepth;
            g.setColor(Color.getHSBColor(tone, 1.0f, 1.0f));
            g.fillPolygon(new int[]{x[0], x0, x2}, new int[]{y[2], y0, y2}, 3);
        }else {
            g.setColor(Color.getHSBColor(maxDepth+1, 1.0f, 1.0f));
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
