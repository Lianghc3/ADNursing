package softwaredesign.adnursing.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import softwaredesign.adnursing.Activity.MyPostSetActivity;
import softwaredesign.adnursing.Custom.MyListView;
import softwaredesign.adnursing.Utils.HttpUtils;
import softwaredesign.adnursing.Data.PostData;
import softwaredesign.adnursing.R;
import softwaredesign.adnursing.Utils.ImageUtils;
import softwaredesign.adnursing.Utils.ListViewImageUtils;

public class PostPreviewAdapter extends BaseAdapter {

    private Context myContext;                  // 上下文
    private ArrayList<PostData> myPostData;     // 数据列表
    private ViewHolder holder;

    public PostPreviewAdapter(Context myContext, ArrayList<PostData> myPostData) {
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
        if (convertView == null) {
            convertView = LayoutInflater.from(myContext).inflate(R.layout.layout_post_preview, viewGroup, false);
            holder = new ViewHolder();
            holder.vImage = (ImageView) convertView.findViewById(R.id.preview_img);
            holder.vTitle = (TextView) convertView.findViewById(R.id.preview_title);
            holder.vContent = (TextView) convertView.findViewById(R.id.preview_content);
            holder.vTime = (TextView) convertView.findViewById(R.id.preview_time);
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

        holder.vTitle.setText(myPostData.get(i).getTitle());
        holder.vContent.setText(myPostData.get(i).getContent());
        holder.vTime.setText(myPostData.get(i).getModifiedTime());

        return convertView;
    }

    /**
     * 创建静态类用来保存View
     */
    private static class ViewHolder {
        ImageView vImage;
        TextView vTitle;
        TextView vContent;
        TextView vTime;
    }

    public void add(PostData data) {
        if (myPostData == null) {
            myPostData = new ArrayList<>();
        }
        myPostData.add(data);
        notifyDataSetChanged();
    }

    public void remove(int i) {
        myPostData.remove(i);
        notifyDataSetChanged();
    }
}



