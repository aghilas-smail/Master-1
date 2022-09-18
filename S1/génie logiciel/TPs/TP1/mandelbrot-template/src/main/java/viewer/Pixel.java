package viewer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Collection;

/**
 * A Pixel. Because of antialiasing, each pixel is further decomposed into
 * sub-pixels. Each sub-pixels has a color, the color of the pixel is the average
 * of the sub-pixels' colors.
 */
record Pixel(int x, int y, Collection<SubPixel> subPixels) {

    /**
     * Creates a pixel with given coordinates and sub-pixels.
     *
     * @param x         the horizontal coordinate of the pixel on the screen
     * @param y         the vertical coordinate of the pixel on the screen
     * @param subPixels a collection of sub-pixels for this pixel
     */
    Pixel {
    }


    /**
     * @return the list of sub-pixels in this pixel
     */
    Collection<SubPixel> getSubPixels() {
        return subPixels;
    }


    private Color getAverageColor() {
        double red = 0;
        double green = 0;
        double blue = 0;
        int count = 0;
        for (SubPixel subPixel : subPixels) {
            count++;
            Color col = subPixel.getColor();
            red += col.getRed();
            green += col.getGreen();
            blue += col.getBlue();
        }
        double c = count;
        return new Color(red / c, green / c, blue / c, 1.);
    }


    /**
     * Displays the pixel.
     *
     * @param context the context of the canvas on which to paint.
     */
    void render(GraphicsContext context) {
        context.setFill(getAverageColor());
        context.fillRect(x, y, 1, 1);
    }


}
