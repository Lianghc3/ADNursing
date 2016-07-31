package softwaredesign.adnursing.Adapter;

import android.app.Activity;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import softwaredesign.adnursing.Custom.MyListView;
import softwaredesign.adnursing.Utils.HttpUtils;
import softwaredesign.adnursing.Data.PostData;
import softwaredesign.adnursing.R;
import softwaredesign.adnursing.Utils.ImageUtils;

public class PostPreviewDetailAdapter extends BaseAdapter {

    private Context myContext;              // 上下文
    private ArrayList<PostData> myPostData; // 数据列表


    public PostPreviewDetailAdapter(Context myContext, ArrayList<PostData> myPostData) {
        this.myContext = myContext;
        this.myPostData = myPostData;
    }

    @Override
    public int getCount() {
        return myPostData.size();
    }

    @Override
    public PostData getItem(int i) {
        return myPostData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(myContext).inflate(R.layout.layout_post_preview_detail, viewGroup, false);
            holder = new ViewHolder();
            holder.vSculpture = (ImageView) convertView.findViewById(R.id.post_preview_detail_sculpture);
            holder.vImage = (ImageView) convertView.findViewById(R.id.post_preview_detail_image);
            holder.vName = (TextView) convertView.findViewById(R.id.post_preview_detail_name);
            holder.vTitle = (TextView) convertView.findViewById(R.id.post_preview_detail_title);
            holder.vContent = (TextView) convertView.findViewById(R.id.post_preview_detail_content);
            holder.vTime = (TextView) convertView.findViewById(R.id.post_preview_detail_time);
            holder.vType = (TextView) convertView.findViewById(R.id.post_preview_detail_type);
            holder.vVisitorNum = (TextView) convertView.findViewById(R.id.post_preview_detail_visitor_num);
            convertView.setTag(holder);
        } else {
            if(viewGroup instanceof MyListView){
                if(((MyListView) viewGroup).isOnMeasure()){
                    return convertView;
                }
            }
            holder = (ViewHolder) convertView.getTag();
        }

        ImageUtils.glideGetImage(myContext, myPostData.get(i).getImagesDir(), holder.vImage, R.mipmap.image_default);
        ImageUtils.glideGetImage(myContext, myPostData.get(i).getUser().getImageDir(), holder.vSculpture, R.mipmap.sculpture_unknown_default);

        holder.vName.setText(myPostData.get(i).getUser().getName());
        holder.vTitle.setText(myPostData.get(i).getTitle());
        holder.vContent.setText(myPostData.get(i).getContent());
        holder.vTime.setText(myPostData.get(i).getModifiedTime());
        holder.vType.setText(myPostData.get(i).getType());
        holder.vVisitorNum.setText(myPostData.get(i).getVisitorNum()+"");

        return convertView;
    }

    /**
     * 创建静态类用来保存View
     */
    private static class ViewHolder {
        ImageView vSculpture;
        ImageView vImage;
        TextView vName;
        TextView vTitle;
        TextView vContent;
        TextView vTime;
        TextView vType;
        TextView vVisitorNum;
    }

    public void add(PostData data) {
        if (myPostData == null) {
            myPostData = new ArrayList<>();
        }
        myPostData.add(data);
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
}
