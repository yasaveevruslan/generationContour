import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GenerationContours {

    private static ArrayList<Contour> contours = new ArrayList<>();

    private static BufferedImage imageCort = null;

    public String addressOpen, addressSave;

    public void openImage() throws IOException, InterruptedException {

        imageCort = ImageIO.read(new File(addressOpen));

        assert imageCort != null;

        openContour(imageCort);

//        for (Contour c : contours)
//        {
//            System.out.println(c.positionX + " " + c.positionY + " " + c.height + " " + c.width);
//        }
    }

    private static void openContour(BufferedImage image) throws InterruptedException {

        int height = image.getHeight();
        int width = image.getWidth();


        for (int h = 0; h < height; h ++)
        {
            for (int w = 0; w < width; w ++)
            {
                boolean isBlack = isBlackPixel(image, w, h);

                if (isBlack)
                {
                    if (!Contours(w, h))
                    {
                        addContour(image, w, h);
                    }
                }
            }
        }
    }

    public static boolean inRangeBool(float in, float min, float max)
    {
        return in >= min && in <= max;
    }

    public static boolean isBlackPixel(BufferedImage image, int w, int h)
    {
        int colorPixel = image.getRGB(w, h);

        int blue = colorPixel & 0xff;
        int green = (colorPixel & 0xff00) >> 8;
        int red = (colorPixel & 0xff0000) >> 16;
        int alpha = (colorPixel & 0xff000000) >>> 24;

        boolean isBlack = inRangeBool(blue, 0, 200)
                && inRangeBool(green, 0, 200)
                && inRangeBool(red, 0, 200);

        return isBlack;
    }

    private static void addContour(BufferedImage image, int w, int h) throws InterruptedException {
        int heightImage = image.getHeight();
        int widthImage = image.getWidth();

        int heightContour = 0; int widthContour = 0;

        for (int width = w; width < widthImage; width++)
        {
            if (!isBlackPixel(image, width, h))
            {
                widthContour = (width) - w;
                break;
            }
        }

        for (int height = h; height < heightImage; height++)
        {
            if (!isBlackPixel(image, w, height))
            {
                heightContour = (height) - h;
                break;
            }
            if (height+1 == heightImage)
            {
                heightContour = (height) - h;
                break;
            }
        }

//        System.out.println(w + " " + h + " " + heightContour + " " + widthContour);
//        Thread.sleep(500);
        contours.add(new Contour(w, h, heightContour, widthContour));

    }

    private static boolean Contours(int w, int h)
    {

        boolean isContour;
        for (Contour c : contours)
        {
            isContour = checkContour(w, h, c);
            if (isContour)
            {
                return true;
            }
        }

        return false;
    }

    private static boolean checkContour(int w, int h, Contour c)
    {
        int posX = c.positionX;
        int posY = c.positionY;
        int height = c.height;
        int width = c.width;

        for (int i = posX; i <= posX + width; i ++)
        {
            for (int j = posY; j <= posY + height; j ++)
            {
                if (i == w && j == h)
                {
                    return true;
                }
            }
        }

        return false;
    }

    public void createDocs(String name) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(name));
        for (Contour c : contours)
        {
            String stuffToWrite = c.positionX + " " + c.positionY + " " + c.height + " " + c.width;
            writer.write(stuffToWrite + '\n');
        }
        writer.close();
    }
}
