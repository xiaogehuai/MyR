package com.bwie.myr.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.myr.R;
import com.bwie.myr.base.BaseViewHolder;
import com.bwie.myr.bean.CommunityBean;
import com.bwie.myr.imp.ViewHolder_01;
import com.bwie.myr.imp.ViewHolder_02;
import com.bwie.myr.util.TypeUtil;


/**
 * RecyclerView的适配器
 * 
 *
 */
public class MAdapter extends RecyclerView.Adapter<BaseViewHolder> implements TypeUtil {

	private CommunityBean communityBean;
	
	/**
     * 类型集合，adapter对应的数据集合
     */
    List<Pair<Integer, Object>> superData;

	private Context context;

	/**
	 * 使用构造器将数据传过来
	 * @param context
	 * @param communityBean
	 */
	public MAdapter(Context context, CommunityBean communityBean) {
		this.context = context;
		this.communityBean = communityBean;
		
		superData=new ArrayList<Pair<Integer,Object>>();
		//添加问答结伴部分
		 superData.add(new Pair<Integer, Object>(COMMUNITY_TOP, communityBean));
		 
		 initOtherData();
		
	}

	/**
     * 初始化除头部以外的数据
     * <p/>
     * <p/>
     * superData.add(new Pair<Integer, Object>(TypeUtil.DESTINATION_OTHER_CITY,objectObjectPair));
     * superData_02.add(new Pair<Integer, Object>(TypeUtil.DESTINATION_OTHER_CITY,objectObjectPair));
     * }
     */
    private void initOtherData() {
        for (int i = 0; i < communityBean.data.forum_list.size(); i++) {

            communityBean.data.forum_list.get(i).group.get(0).setHasTitle(true,communityBean.data.forum_list.get(i).name);//设置有标头
            for (int j = 0; j < communityBean.data.forum_list.get(i).group.size(); j++) {
                j++;
                Pair<Object, Object> objectObjectPair;//两个数据的集合  超过两个可以用list集合
                if (j == communityBean.data.forum_list.get(i).group.size()) {
                    objectObjectPair = wrapData(communityBean.data.forum_list.get(i).group.get(j - 1), null);
                } else {
                    objectObjectPair = wrapData(communityBean.data.forum_list.get(i).group.get(j - 1), communityBean.data.forum_list.get(i).group.get(j));
                }

                superData.add(new Pair<Integer, Object>(COMMUNITY_OHTER, objectObjectPair));

            }
        }
    }
	
	
	@Override
	public int getItemCount() {
		return superData.size();
	}
	
	/*
	 * onBindViewHolder 根据ViewHolder_01获取多个view
	 * */
	@Override
	public void onBindViewHolder(BaseViewHolder holder, int position) {
		 switch (superData.get(position).first){
         case COMMUNITY_TOP:
        	 ((ViewHolder_01)holder).initData(communityBean);
             break;
         case COMMUNITY_OHTER:
             ((ViewHolder_02)holder).initData(superData.get(position));
             break;
     }
	}
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return superData.get(position).first;
	}
	
	/*
	 * onCreateViewHolder 根据类型绑定多个ViewHolder_01,加载多个不同view
	 * */
	@Override
	public BaseViewHolder onCreateViewHolder(ViewGroup arg0, int type) {
		
		switch (type) {
		case COMMUNITY_TOP:
			return new ViewHolder_01(View.inflate(context, R.layout.item_community_top, null),context);
		case COMMUNITY_OHTER:
			return new ViewHolder_02(View.inflate(context, R.layout.item_community_other, null),context);
		}
		
		return new ViewHolder_02(View.inflate(context, R.layout.item_community_other, null),context);
	}
	
	 /**
     * 给数据打包，两个一块
     *
     * @return
     */
    public Pair<Object, Object> wrapData(Object f, Object s) {
        return new Pair<Object, Object>(f, s);
    }

}
