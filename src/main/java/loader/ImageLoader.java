package loader;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
/**
 * 图像处理工具类
 */

public class ImageLoader {

        // 旋转给定的图像指定的角度
        public static BufferedImage rotateImage(final BufferedImage bufferedimage,
                                                final int degree) {
                //获取原始图像的宽度和高度
                int w = bufferedimage.getWidth();
                int h = bufferedimage.getHeight();
                //获取图像的透明度类型
                int type = bufferedimage.getColorModel().getTransparency();

                // 创建新的BufferedImage对象：
                BufferedImage img;

                // 创建Graphics2D对象：
                Graphics2D graphics2d;
                (graphics2d = (img = new BufferedImage(w, h, type))//新的图像上绘制内容
                        .createGraphics()).setRenderingHint(//设置了图像渲染时使用的插值方法
                        RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);//使用双线性插值，它可以在旋转图像时提供更平滑的视觉效果。
                graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);//将Graphics2D对象的坐标系统旋转指定的角度。旋转的中心点是图像的中心
                graphics2d.drawImage(bufferedimage, 0, 0, null);//将原始图像绘制到新的图像对象上
                graphics2d.dispose();//释放Graphics2D对象的资源
                return img;
        }
}

