package com.outliner.props.modulecommodityprops.presenter;

import android.view.View;

import com.outliner.props.modulecommodityprops.modle.ProductModel;

/**
 * Created by Administrator on 2017/3/23.
 */

public interface MainContract {
    interface IMainView {

        void hiddenBottomSheetDialog(UiData mUiData);

        void showBottomSheetDialog(ProductModel productModel, UiData mUiData);

        void performPropsClick(View view, int postion);

        void resetGoosInventory(String inventory);
    }

    interface IPresent {
        ProductModel getPropsData();

        void checkOtherPropsData(int position, ProductModel.AttributesEntity.AttributeMembersEntity prop);

        void performShowBottomClick();

        void performHiddenBottomClick();

        void resetInventory();

        boolean performPropsClickListener(View view, int position, ProductModel.AttributesEntity param);
    }

}
