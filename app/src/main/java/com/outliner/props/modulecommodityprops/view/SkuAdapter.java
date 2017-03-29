package com.outliner.props.modulecommodityprops.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.outliner.props.R;
import com.outliner.props.modulecommodityprops.modle.ProductModel;

import java.util.List;

public class SkuAdapter extends RecyclerView.Adapter<SkuAdapter.ViewHolder> {

    private List<ProductModel.AttributesEntity.AttributeMembersEntity> mAttributeMembersEntities;
    OnClickListener mOnClickListener;
    private ProductModel.AttributesEntity.AttributeMembersEntity currentSelectedItem;

    public SkuAdapter(List<ProductModel.AttributesEntity.AttributeMembersEntity> attributeMembersEntities) {
        this.mAttributeMembersEntities = attributeMembersEntities;
    }

    public ProductModel.AttributesEntity.AttributeMembersEntity getCurrentSelectedItem() {
        return currentSelectedItem;
    }

    public void setCurrentSelectedItem(ProductModel.AttributesEntity.AttributeMembersEntity currentSelectedItem) {
        this.currentSelectedItem = currentSelectedItem;
    }

    public List<ProductModel.AttributesEntity.AttributeMembersEntity> getAttributeMembersEntities() {
        return mAttributeMembersEntities;
    }

    public OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sku_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mAttributeMembersEntities.get(position));
        holder.setClick(position);
    }

    @Override
    public int getItemCount() {
        return mAttributeMembersEntities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv);
        }

        public void setData(ProductModel.AttributesEntity.AttributeMembersEntity entity) {
            mTextView.setText(entity.getName());
            switch (entity.getStatus()) {
                case 0:
                    mTextView.setAlpha(1f);
                    mTextView.setBackgroundResource(android.R.color.darker_gray);
                    break;
                case 1:
                    mTextView.setAlpha(1f);
                    mTextView.setBackgroundResource(android.R.color.holo_red_light);
                    break;
                case 2:
                    mTextView.setAlpha(0.4f);
                    mTextView.setBackgroundResource(android.R.color.darker_gray);
                    break;
            }
        }

        public void setClick(final int position) {
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickListener != null)
                        mOnClickListener.onItemClickListener(position);
                }
            });
        }
    }

    public interface OnClickListener {
        void onItemClickListener(int position);
    }

}
