package softwaredesign.adnursing.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huacan liang on 2016/7/31.
 */
public class ImageUtils {
    public static void glideGetImage(Context context, String imagesDir, ImageView imageView, int defaultImage) {
        if (imagesDir.equals("")) {
            imageView.setImageResource(defaultImage);
            return;
        }
        String tmpString;
        if (imagesDir.indexOf("|") == -1) {
            tmpString = imagesDir;
        } else {
            tmpString = imagesDir.substring(0, imagesDir.indexOf("|"));
        }
        tmpString = HttpUtils.getIp()+"/ADNursingServer/res/image/"+tmpString;
        Glide.with(context).load(tmpString).placeholder(defaultImage).centerCrop().into(imageView);
    }


    public static void glideGetImages(Context context, String imagesDir, List<ImageView> vImage, int defaultImage) {
        if (imagesDir.equals("")) {
            return;
        }
        String dir[] = imagesDir.split("\\|");
        for (int i = 0; i < dir.length; i++) {
            String tmpString = HttpUtils.getIp()+"/ADNursingServer/res/image/"+dir[i];
            Glide.with(context).load(tmpString).placeholder(defaultImage).centerCrop().into(vImage.get(i));
        }
    }
}
