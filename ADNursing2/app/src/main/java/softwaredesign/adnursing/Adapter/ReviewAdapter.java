package softwaredesign.adnursing.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import softwaredesign.adnursing.Custom.MyListView;
import softwaredesign.adnursing.Utils.HttpUtils;
import softwaredesign.adnursing.R;
import softwaredesign.adnursing.Data.ReviewData;
import softwaredesign.adnursing.Utils.ImageUtils;

public class ReviewAdapter extends BaseAdapter {

    private Context myContext;                      // 上下文
    private ArrayList<ReviewData> myReviewData;     // 数据列表


    public ReviewAdapter(Context myContext, ArrayList<ReviewData> myReviewData) {
        this.myContext = myContext;
        this.myReviewData = myReviewData;
    }

    @Override
    public int getCount() {
        return myReviewData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(myContext).inflate(R.layout.layout_review, viewGroup, false);
            holder = new ViewHolder();
            holder.vSculpture = (ImageView) convertView.findViewById(R.id.review_scul);
            holder.vImage[0] = (ImageView) convertView.findViewById(R.id.review_image_1);
            holder.vImage[1] = (ImageView) convertView.findViewById(R.id.review_image_2);
            holder.vImage[2] = (ImageView) convertView.findViewById(R.id.review_image_3);
            holder.vName = (TextView) convertView.findViewById(R.id.review_name);
            holder.vContent = (TextView) convertView.findViewById(R.id.review_content);
            holder.vFloor = (TextView) convertView.findViewById(R.id.review_floor);
            holder.vTime = (TextView) convertView.findViewById(R.id.review_time);
            holder.vImagefield = (LinearLayout) convertView.findViewById(R.id.review_image_field);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        System.out.println("getView " + i + ": " + convertView);

        holder.vName.setText(myReviewData.get(i).getUser().getName());
        holder.vContent.setText(myReviewData.get(i).getContent());
        holder.vTime.setText(myReviewData.get(i).getModifiedTime());

        if(viewGroup instanceof MyListView){
            if(((MyListView) viewGroup).isOnMeasure()){
                return convertView;
            }
        }

        if ((myReviewData.get(i).getImageDir().equals(""))) {
            holder.vImagefield.setVisibility(View.GONE);
        } else {
            holder.vImagefield.setVisibility(View.VISIBLE);
            List<ImageView> vImages = Arrays.asList(holder.vImage[0], holder.vImage[1], holder.vImage[2]);
            ImageUtils.glideGetImages(myContext, myReviewData.get(i).getImageDir(), vImages, R.mipmap.image_default);
        }

        if (myReviewData.get(i).getUser().getImageDir().equals("")) {
            holder.vSculpture.setImageResource(R.mipmap.sculpture_unknown_default);
        } else {
            ImageUtils.glideGetImage(myContext, myReviewData.get(i).getUser().getImageDir(), holder.vSculpture, R.mipmap.sculpture_unknown_default);
        }

        return convertView;
    }

    /**
     * 创建静态类用来保存View
     */
    private static class ViewHolder {
        ImageView vSculpture;
        TextView vName;
        TextView vContent;
        TextView vFloor;
        TextView vTime;
        ImageView[] vImage = new ImageView[3];
        LinearLayout vImagefield;
    }

    public void add(ReviewData data) {
        if (myReviewData == null) {
            myReviewData = new ArrayList<>();
        }
        myReviewData.add(data);
        notifyDataSetChanged();
    }

    private void glideGetImage(String imagesDir, ImageView imageView, int defaultImage) {
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
        Glide.with(myContext).load(tmpString).placeholder(defaultImage).centerCrop().into(imageView);
    }


    private void glideGetImages(String imagesDir, ViewHolder holder, int defaultImage) {
        if (imagesDir.equals("")) {
            return;
        }
        String dir[] = imagesDir.split("\\|");
        for (int i = 0; i < dir.length; i++) {
            String tmpString = HttpUtils.getIp()+"/ADNursingServer/res/image/"+dir[i];
            Glide.with(myContext).load(tmpString).placeholder(defaultImage).centerCrop().into(holder.vImage[i]);
        }
    }
}
