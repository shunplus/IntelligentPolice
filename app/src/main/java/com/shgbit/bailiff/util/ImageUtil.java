package com.shgbit.bailiff.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;

/**
 * 图像处理工具类
 * <p>
 * Created by db on 2018/9/22.
 */
public class ImageUtil {

    private static ImageUtil mInstance;

    private ImageUtil() {
    }

    //双重检查单例模式
    public static ImageUtil getInstance() {
        if (mInstance == null) {
            synchronized (ImageUtil.class) {
                if (mInstance == null) {
                    mInstance = new ImageUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 质量压缩图片
     *
     * @param bitmap  图片
     * @param quality 要压缩到的品质值(0-100)
     * @return 压缩后的图片
     */
    public Bitmap qualityCompression(Bitmap bitmap, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 采样率压缩
     */
    public Bitmap SamplingRateCompression(Bitmap bitmap) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 缩放压缩
     */
    public Bitmap ScaleCompression(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

    /**
     * RGB565压缩法
     */
    public Bitmap RGB565Compression(Bitmap bitmap) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }
}
