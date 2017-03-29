package com.outliner.props.modulecommodityprops.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.outliner.props.R;
import com.outliner.props.modulecommodityprops.presenter.MainContract;
import com.outliner.props.commoncustomview.TagFlowLayout;


/**
 * Created by Administrator on 2015/9/21.
 */
public class CommodityPropsCellHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView tv_type_one;
    public final TagFlowLayout gv_iv_txt;
    private final MainContract.IMainView mainView;

    public CommodityPropsCellHolder(View itemView, MainContract.IMainView mainView) {
        super(itemView);
        this.mainView = mainView;
        itemView.setOnClickListener(this);
        tv_type_one = (TextView) itemView.findViewById(R.id.tv_type_one);
        gv_iv_txt = (TagFlowLayout) itemView.findViewById(R.id.gv_iv_txt);
    }

    @Override
    public void onClick(View v) {
        if (mainView != null) {
            mainView.performPropsClick(v, getAdapterPosition());
        }
    }
}
