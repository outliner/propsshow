package com.outliner.props.modulecommodityprops.presenter;

import android.support.design.widget.BottomSheetDialog;

import com.outliner.props.modulecommodityprops.modle.BaseSkuModel;
import com.outliner.props.modulecommodityprops.modle.ProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UiData {
    BottomSheetDialog mBottomSheetDialog;
    //存放被选中的按钮对应的数据
    List<ProductModel.AttributesEntity.AttributeMembersEntity> selectedEntities = new ArrayList<>();
    //存放计算结果
    Map<String, BaseSkuModel> result;


    public Map<String, BaseSkuModel> getResult() {
        return result;
    }

    public void setResult(Map<String, BaseSkuModel> result) {
        this.result = result;
    }

    public BottomSheetDialog getBottomSheetDialog() {
        return mBottomSheetDialog;
    }

    public void setBottomSheetDialog(BottomSheetDialog bottomSheetDialog) {
        mBottomSheetDialog = bottomSheetDialog;
    }

    public List<ProductModel.AttributesEntity.AttributeMembersEntity> getSelectedEntities() {
        return selectedEntities;
    }

    public void setSelectedEntities(List<ProductModel.AttributesEntity.AttributeMembersEntity> selectedEntities) {
        this.selectedEntities = selectedEntities;
    }
}
