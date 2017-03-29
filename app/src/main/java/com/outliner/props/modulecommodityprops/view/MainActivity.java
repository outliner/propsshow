package com.outliner.props.modulecommodityprops.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.outliner.props.R;
import com.outliner.props.commoncustomview.MaxHightRecycleView;
import com.outliner.props.modulecommodityprops.modle.ProductModel;
import com.outliner.props.modulecommodityprops.presenter.MainContract;
import com.outliner.props.modulecommodityprops.presenter.MainPresenter;
import com.outliner.props.modulecommodityprops.presenter.UiData;

public class MainActivity extends AppCompatActivity implements MainContract.IMainView, View.OnClickListener {

    private Button btnBottom;

    private CommodityPropsAdapter commodityPropsAdapter;
    private MainPresenter mainPresenter;
    private MaxHightRecycleView llList;
    private View v_bottom_sheet_background;
    private View cart;
    private View buy;
    private View rl_bottom_sheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBottom = (Button) findViewById(R.id.btn_bottom);
        btnBottom.setOnClickListener(this);
        mainPresenter = new MainPresenter(this);
        commodityPropsAdapter = new CommodityPropsAdapter(this, this, mainPresenter);
        rl_bottom_sheet = findViewById(R.id.rl_bottom_sheet);
        v_bottom_sheet_background = findViewById(R.id.v_bottom_sheet_background);
        cart = findViewById(R.id.cart);
        buy = findViewById(R.id.buy);
        llList = (MaxHightRecycleView) findViewById(R.id.ll_list);//列表

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        llList.setHasFixedSize(true);
        llList.setLayoutManager(manager);
        llList.setAdapter(commodityPropsAdapter);
        v_bottom_sheet_background.setOnClickListener(this);
        cart.setOnClickListener(this);
        buy.setOnClickListener(this);
    }


    @Override
    public void hiddenBottomSheetDialog(UiData mUiData) {
        mUiData.getBottomSheetDialog().hide();
    }

    @Override
    public void showBottomSheetDialog(ProductModel productModel, UiData mUiData) {
        if (commodityPropsAdapter.getItemCount() == 0) {
            commodityPropsAdapter.add(productModel.getAttributes());
            commodityPropsAdapter.notifyDataSetChanged();
        }
        rl_bottom_sheet.setVisibility(View.VISIBLE);
    }

    @Override
    public void performPropsClick(View view, int postion) {
        //TODO 执行属性点击相关,更新UI
        commodityPropsAdapter.notifyDataSetChanged();
    }

    @Override
    public void resetGoosInventory(String inventory) {
        Toast.makeText(this, inventory, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bottom:
                mainPresenter.performShowBottomClick();
                break;
            case R.id.v_bottom_sheet_background:
            case R.id.buy:
            case R.id.cart:
                rl_bottom_sheet.setVisibility(View.GONE);
                break;
        }
    }
}
