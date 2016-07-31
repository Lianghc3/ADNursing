package softwaredesign.adnursing.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import softwaredesign.adnursing.Adapter.ReviewPreviewAdapter;
import softwaredesign.adnursing.Custom.MyListView;
import softwaredesign.adnursing.Custom.RefreshLayout;
import softwaredesign.adnursing.Data.ReviewData;
import softwaredesign.adnursing.HttpApplication;
import softwaredesign.adnursing.R;
import softwaredesign.adnursing.Utils.HttpUtils;

public class MyReviewSetActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView top_bar_back_icon;
    private TextView top_bar_info_txt;
    private RefreshLayout refresh_ly;
    private MyListView my_review_set_list;

    private ArrayList<ReviewData> reviewDatas;
    private ReviewPreviewAdapter reviewPreviewAdapter;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    reviewPreviewAdapter = new ReviewPreviewAdapter(MyReviewSetActivity.this, reviewDatas);
                    my_review_set_list.setAdapter(reviewPreviewAdapter);

                    my_review_set_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(MyReviewSetActivity.this, ForumActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("postId", reviewPreviewAdapter.getItem(i).getPostId());
                            bundle.putString("imageDir", reviewPreviewAdapter.getItem(i).getPostImageDir());
                            intent.putExtras(bundle);
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_none, R.anim.anim_none);
                        }
                    });

                    my_review_set_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                            final int finalI = i;
                            AlertDialog.Builder builder = new AlertDialog.Builder(MyReviewSetActivity.this);
                            builder.setMessage("确定要删除该帖吗?");
                            builder.setTitle("提示");
                            builder.setPositiveButton("确认",
                                    new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            deleteReview(reviewPreviewAdapter.getItem(finalI).getReview_id(), finalI);
                                        }
                                    });
                            builder.setNegativeButton("取消",
                                    new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            builder.create().show();
                            return true;
                        }
                    });
                    refresh_ly.setRefreshing(false);
                    break;
                case 0x002:
                    reviewPreviewAdapter.remove(msg.arg1);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review_set);

        initView();
        initTile();
        requestReview();
        setReview();
    }

    private void initView() {
        top_bar_back_icon = (ImageView) findViewById(R.id.top_bar_back_icon);
        top_bar_info_txt = (TextView) findViewById(R.id.top_bar_info_txt);
        refresh_ly = (RefreshLayout) findViewById(R.id.refresh_ly);
        my_review_set_list = (MyListView) findViewById(R.id.my_review_set_list);

        refresh_ly.setRefreshing(true);

        top_bar_back_icon.setOnClickListener(this);

        refresh_ly.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestReview();
            }
        });
        refresh_ly.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                refresh_ly.setLoading(false);
            }
        });
    }

    private void initTile() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        top_bar_info_txt.setText(title);
    }

    private void requestReview() {
        new Thread() {
            public void run() {
                reviewDatas = HttpUtils.getReciewsWithUserId(HttpApplication.getUserId());
                handler.sendEmptyMessage(0x001);
            };
        }.start();
    }

    private void setReview() {

    }


    private void deleteReview(final int review_id, final int i) {
        new Thread() {
            public void run() {
                System.out.println("delete review");
                int result = HttpUtils.deleteReview(HttpApplication.getUserId(), review_id);
                System.out.println(result);
                if (result == review_id) {
                    Message msg = Message.obtain();
                    msg.arg1 = i;
                    msg.what = 0x002;
                    handler.sendMessage(msg);
                }
            };
        }.start();
    }

    /**
     * View的OnClick监听器
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back_icon:
                finish();
                overridePendingTransition(R.anim.anim_none, R.anim.anim_none);
                break;
        }
    }
}
