package com.outliner.props.modulecommodityprops.view;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.outliner.props.R;
import com.outliner.props.modulecommodityprops.modle.ProductModel;
import com.outliner.props.modulecommodityprops.presenter.MainContract;
import com.outliner.props.modulecommodityprops.presenter.MainPresenter;
import com.outliner.props.commoncustomview.FlowLayout;
import com.outliner.props.commoncustomview.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/9/21.
 */
public class CommodityPropsAdapter extends RecyclerView.Adapter<CommodityPropsCellHolder> {

    private final MainContract.IMainView mainView;
    private final MainPresenter mainPresenter;
    private Activity context;
    private List<ProductModel.AttributesEntity> mList;
    private HashMap<Integer, CommodityPropsCellHolder> hashMap = new HashMap<>();


    public CommodityPropsAdapter(Activity con, MainContract.IMainView mainView, MainPresenter mainPresenter) {
        this.mainView = mainView;
        this.mainPresenter = mainPresenter;
        this.context = con;
        mList = new ArrayList<>();
    }

    public List<ProductModel.AttributesEntity> getmList() {
        return mList;
    }


    public void add(List<ProductModel.AttributesEntity> beans) {
        mList.addAll(beans);
        notifyDataSetChanged();
    }


    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }




    private void checkOtherDataState(int position, ProductModel.AttributesEntity.AttributeMembersEntity prop) {
       mainPresenter.checkOtherPropsData( position, prop);
    }

    private String[] changeToArray(List<ProductModel.AttributesEntity.AttributeMembersEntity> param) {
        String[] strings = new String[param.size()];
        for (int i = 0; i < param.size(); i++) {
            strings[i] = param.get(i).getName();
        }
        return strings;
    }

    @Override
    public CommodityPropsCellHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_grid_props, parent, false);
        return new CommodityPropsCellHolder(view, mainView);
    }

    @Override
    public void onBindViewHolder(CommodityPropsCellHolder holder, int i) {
        if (mList.size() > i) {
            final ProductModel.AttributesEntity param = mList.get(i);
            TextView tv_type_one = holder.tv_type_one;
            final TagFlowLayout gv_iv_txt = holder.gv_iv_txt;
            gv_iv_txt.setAdapter(new TagAdapter<String>(changeToArray(param.getAttributeMembers())) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    ProductModel.AttributesEntity.AttributeMembersEntity prop = param.getAttributeMembers().get(position);
                    //设置规格所在的分组ID
                    TextView tv_chima = (TextView) LayoutInflater.from(context).inflate(R.layout.shangpin_detail_type_one_gv,
                            gv_iv_txt, false);
                    tv_chima.setText(s);
                    if (prop.getStatus() == 2) {
                        tv_chima.setEnabled(false);
                        tv_chima.setTextColor(Color.parseColor("#a4a4a4"));
                    } else {
                        tv_chima.setEnabled(true);
                        tv_chima.setTextColor(Color.parseColor("#000000"));
                    }
                    return tv_chima;
                }

                public int getSelectState(int pos) {
                    ProductModel.AttributesEntity.AttributeMembersEntity prop = param.getAttributeMembers().get(pos);
                    return prop.getStatus();
                }

                @Override
                public boolean setSelected(int position, String s) {
                    ProductModel.AttributesEntity.AttributeMembersEntity prop = param.getAttributeMembers().get(position);
                    return prop.getStatus() == 1;
                }
            });
            gv_iv_txt.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                 return    mainPresenter.performPropsClickListener(view,position,param);

                }
            });
            tv_type_one.setText(param.getName());
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
