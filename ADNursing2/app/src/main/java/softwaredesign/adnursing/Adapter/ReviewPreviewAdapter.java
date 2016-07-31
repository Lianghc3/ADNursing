package softwaredesign.adnursing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import softwaredesign.adnursing.Custom.MyListView;
import softwaredesign.adnursing.Data.PostData;
import softwaredesign.adnursing.Data.ReviewData;
import softwaredesign.adnursing.R;
import softwaredesign.adnursing.Utils.ImageUtils;

/**
 * Created by huacan liang on 2016/7/31.
 */
public class ReviewPreviewAdapter extends BaseAdapter {

    private Context myContext;                  // 上下文
    private ArrayList<ReviewData> myReviewData;     // 数据列表
    private ViewHolder holder;

    public ReviewPreviewAdapter(Context myContext, ArrayList<ReviewData> myReviewData) {
        this.myContext = myContext;
        this.myReviewData = myReviewData;
    }

    @Override
    public int getCount() {
        return myReviewData.size();
    }

    @Override
    public ReviewData getItem(int i) {
        return myReviewData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(myContext).inflate(R.layout.layout_review_preview, viewGroup, false);
            holder = new ViewHolder();
            holder.vImage = (ImageView) convertView.findViewById(R.id.review_preview_image);
            holder.vTitle = (TextView) convertView.findViewById(R.id.review_preview_title);
            holder.vContent = (TextView) convertView.findViewById(R.id.review_preview_content);
            holder.vTime = (TextView) convertView.findViewById(R.id.review_preview_time);
            convertView.setTag(holder);
        } else {
            if(viewGroup instanceof MyListView){
                if(((MyListView) viewGroup).isOnMeasure()){
                    return convertView;
                }
            }
            holder = (ViewHolder) convertView.getTag();
        }

        ImageUtils.glideGetImage(myContext, myReviewData.get(i).getPostImageDir(), holder.vImage, R.mipmap.image_default);

        holder.vTitle.setText(myReviewData.get(i).getPostTitle());
        holder.vContent.setText(myReviewData.get(i).getContent());
        holder.vTime.setText(myReviewData.get(i).getModifiedTime());

        return convertView;
    }

    private static class ViewHolder {
        ImageView vImage;
        TextView vTitle;
        TextView vContent;
        TextView vTime;
    }


    public void add(ReviewData data) {
        if (myReviewData == null) {
            myReviewData = new ArrayList<>();
        }
        myReviewData.add(data);
        notifyDataSetChanged();
    }


    public void remove(int i) {
        myReviewData.remove(i);
        notifyDataSetChanged();
    }
}
