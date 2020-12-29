package com.nls.util;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ThumbnailGenerator {

    public enum StrictMode {
        COVER,
        FIT
    }

    public enum Rotation {
        NORMAL(0),
        DG_90(90),
        DG_180(180),
        DG_270(270);

        private final int degrees;

        Rotation(int degrees) {
            this.degrees = degrees;
        }

        public static Rotation valueOf(int degrees) {
            for (Rotation rotation : values()) {
                if (rotation.degrees == degrees) {
                    return rotation;
                }
            }
            return Rotation.NORMAL;
        }
    }


    public static final String FORMAT_PNG = "png";
    public static final String FORMAT_JPG = "jpg";

    private String format = FORMAT_PNG;
    private boolean strictSize = true;
    private int width = 64;
    private int height = 64;
    private Rotation rotate = Rotation.NORMAL;

    private StrictMode strictMode = StrictMode.FIT;

    public void createThumbnail(File input, File output) throws IOException {
        createThumbnail(IOUtils.toByteArray(new FileInputStream(input)), new FileOutputStream(output));
    }

    public void createThumbnail(File input, OutputStream output) throws IOException {
        createThumbnail(IOUtils.toByteArray(new FileInputStream(input)), output);
    }

    public Image createThumbnail(byte[] input, OutputStream output) throws IOException {
        return createThumbnail(new ImageIcon(input).getImage(), output);
    }

    public Image createThumbnail(Image image, OutputStream output) throws IOException {
        if (image == null) {
            throw new IllegalStateException("image is null, perhaps file format was invalid or file was corrupted");
        }

        double thumbRatio = (double) width / (double) height;
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double imageRatio = (double) imageWidth / (double) imageHeight;

        boolean fitToWidth = strictSize && strictMode == StrictMode.COVER ? imageRatio < thumbRatio : thumbRatio < imageRatio;
        if (fitToWidth) {
            imageHeight = (int) (width / imageRatio);
            imageWidth = width;
        } else {
            imageWidth = (int) (height * imageRatio);
            imageHeight = height;
        }

        int canvasWidth = strictSize ? width : imageWidth;
        int canvasHeight = strictSize ? height : imageHeight;

        // Create empty BufferedImage, sized to Image
        BufferedImage buffImage =
                new BufferedImage(
                        canvasWidth,
                        canvasHeight,
                        BufferedImage.TYPE_INT_RGB);

        int xOffset = 0;
        int yOffset = 0;

        if (strictSize) {
            xOffset = (width - imageWidth) / 2;
            yOffset = (height - imageHeight) / 2;
        }

        // Draw Image into BufferedImage
        Graphics2D g2 = getGraphics2D(buffImage);
        g2.fillRect(0, 0, width, height);
        //g2.drawImage(image, xOffset, yOffset, imageWidth, imageHeight, 0, 0, image.getWidth(null), image.getHeight(null),  null)
        g2.drawImage(image, xOffset, yOffset, imageWidth, imageHeight, null);
        g2.dispose();

        buffImage = rotate(buffImage);
        ImageIO.write(buffImage, format, output);
        return buffImage;
    }

    private BufferedImage rotate(BufferedImage buffImage) {
        if (rotate != Rotation.NORMAL) {
            AffineTransform tx = new AffineTransform();
            if (rotate == Rotation.DG_270) {
                tx.rotate(Math.toRadians(rotate.degrees), buffImage.getWidth() / 2, buffImage.getHeight() / 2);
            } else {
                tx.rotate(Math.toRadians(rotate.degrees), buffImage.getHeight() / 2, buffImage.getWidth() / 2);
            }
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            buffImage = op.filter(buffImage, null);
        }
        return buffImage;
    }

    private Graphics2D getGraphics2D(BufferedImage buffImage) {
        Graphics2D g2 = (Graphics2D) buffImage.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        return g2;
    }

    public void create(InputStream in, OutputStream out) throws IOException {
        Thumbnails.of(in)
                .size(width, height)
                .useExifOrientation(false)
                .keepAspectRatio(true)
                .forceSize(width, height)
                .rotate(rotate.degrees)
                .outputFormat(format)
                .toOutputStream(out);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isStrictSize() {
        return strictSize;
    }

    public void setStrictSize(boolean strictSize) {
        this.strictSize = strictSize;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setRotate(Rotation rotate) {
        this.rotate = rotate;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public StrictMode getStrictMode() {
        return strictMode;
    }

    public void setStrictMode(StrictMode strictMode) {
        this.strictMode = strictMode;
    }
}
