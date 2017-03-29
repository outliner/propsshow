package com.outliner.props.modulecommodityprops.presenter;

import android.view.View;

import com.outliner.props.modulecommodityprops.modle.BaseSkuModel;
import com.outliner.props.modulecommodityprops.modle.ProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/23.
 */

public class MainPresenter implements MainContract.IPresent {

    private final MainContract.IMainView iMainView;
    private UiData mUiData;
    private ProductModel productModel;
    private ArrayList<ProductModel.AttributesEntity.AttributeMembersEntity> selectedProp = new ArrayList<ProductModel.AttributesEntity.AttributeMembersEntity>();

    public MainPresenter(MainContract.IMainView iMainView) {
        this.iMainView = iMainView;
        processInit();
    }

    private void processInit() {
        mUiData = new UiData();
        productModel = getPropsData();
    }

    @Override
    public ProductModel getPropsData() {
        // 设置模拟数据
        ProductModel testData = new ProductModel();
        testData.getProductStocks().put("1;4;8", new BaseSkuModel(13, 20));
        testData.getProductStocks().put("1;5;9", new BaseSkuModel(14, 10));
        testData.getProductStocks().put("1;7;10", new BaseSkuModel(13, 40));
        testData.getProductStocks().put("2;4;11", new BaseSkuModel(16, 70));
        testData.getProductStocks().put("2;6;8", new BaseSkuModel(17, 30));
        testData.getProductStocks().put("2;7;9", new BaseSkuModel(12, 22));
        testData.getProductStocks().put("3;5;11", new BaseSkuModel(11, 25));
        testData.getProductStocks().put("3;6;8", new BaseSkuModel(19, 21));
        testData.getProductStocks().put("3;7;9", new BaseSkuModel(10, 29));
        // 设置对应的品种和规格
        ProductModel.AttributesEntity group01 = new ProductModel.AttributesEntity();
        group01.setName("颜色");
        group01.setId(1);
        group01.getAttributeMembers().add(0, new ProductModel.AttributesEntity.AttributeMembersEntity(1, 1, "红色"));
        group01.getAttributeMembers().add(1, new ProductModel.AttributesEntity.AttributeMembersEntity(1, 2, "白色"));
        group01.getAttributeMembers().add(2, new ProductModel.AttributesEntity.AttributeMembersEntity(1, 3, "绿色测试长度"));
        testData.getAttributes().add(0, group01);//第一组
        ProductModel.AttributesEntity group02 = new ProductModel.AttributesEntity();
        group02.setName("尺寸");
        group02.setId(2);
        group02.getAttributeMembers().add(0, new ProductModel.AttributesEntity.AttributeMembersEntity(2, 4, "80cm"));
        group02.getAttributeMembers().add(1, new ProductModel.AttributesEntity.AttributeMembersEntity(2, 5, "90cm"));
        group02.getAttributeMembers().add(2, new ProductModel.AttributesEntity.AttributeMembersEntity(2, 6, "100cm"));
        group02.getAttributeMembers().add(3, new ProductModel.AttributesEntity.AttributeMembersEntity(2, 7, "110cm"));
        testData.getAttributes().add(1, group02);//第二组
        //一下测试数据打开。因为选中的不能做取消选择。会复现一旦完属性，会导致无法再选其他的
//        ProductModel.AttributesEntity group03 = new ProductModel.AttributesEntity();
//        group03.setName("规格");
//        group03.getAttributeMembers().add(0, new ProductModel.AttributesEntity.AttributeMembersEntity(3, 8, "东"));
//        group03.getAttributeMembers().add(1, new ProductModel.AttributesEntity.AttributeMembersEntity(3, 9, "西"));
//        group03.getAttributeMembers().add(2, new ProductModel.AttributesEntity.AttributeMembersEntity(3, 10, "南"));
//        group03.getAttributeMembers().add(3, new ProductModel.AttributesEntity.AttributeMembersEntity(3, 11, "北"));
//        testData.getAttributes().add(2, group03);//第二组
        return testData;
    }

    @Override
    public void checkOtherPropsData(int position, ProductModel.AttributesEntity.AttributeMembersEntity prop) {
        //TOO 处理点击事件后检查其他属性是否可点击
        //遍历所有属性存储已经被选中的属性
        selectedProp.clear();
        Map<String, BaseSkuModel> skumap = mUiData.getResult();
        for (int i = 0; i < productModel.getAttributes().size(); i++) {
            ProductModel.AttributesEntity param = productModel.getAttributes().get(i);
            for (int j = 0; j < param.getAttributeMembers().size(); j++) {
                ProductModel.AttributesEntity.AttributeMembersEntity mprop = param.getAttributeMembers().get(j);
                if (mprop.getStatus() == 1) {
                    selectedProp.add(mprop);
                }
            }
        }
        //处理未选中的
        for (int i = 0; i < productModel.getAttributes().size(); i++) {
            ProductModel.AttributesEntity param = productModel.getAttributes().get(i);
            if (param.getId() == prop.getAttributeGroupId()) {
                continue;
            }
            //当前行不需要判断
            for (int j = 0; j < param.getAttributeMembers().size(); j++) {
                ProductModel.AttributesEntity.AttributeMembersEntity mprop = param.getAttributeMembers().get(j);
                if (skumap.get(mprop.getAttributeMemberId() + "") == null || skumap.get(mprop.getAttributeMemberId() + "").getStock() <= 0) {
                    mprop.setStatus(2);
                }
                List<ProductModel.AttributesEntity.AttributeMembersEntity> cacheSelected = new ArrayList<>();
                cacheSelected.add(mprop);
                cacheSelected.addAll(selectedProp);
                // 冒泡排序,也可以使用comparator自定义排序规则，要和sku差分时的排序规则保持一致
                sortAttributeMembersEntityList(cacheSelected);
                for (int m = 0; m < cacheSelected.size() - 1; m++) {
                    for (int k = 0; k < cacheSelected.size() - 1 - m; k++) {
                        if (cacheSelected.get(k).getAttributeGroupId() == cacheSelected.get(k + 1).getAttributeGroupId()) {
                            //如果判断是选中属性直接去掉；如果是费选中属性去掉选中的那个属性
                            if (cacheSelected.get(k).getAttributeMemberId() == cacheSelected.get(k + 1).getAttributeMemberId()) {
                                cacheSelected.remove(k + 1);
                            } else {
                                if (cacheSelected.get(k).getStatus() == 1) {
                                    cacheSelected.remove(k);
                                } else {
                                    cacheSelected.remove(k + 1);
                                }
                            }
                        }
                    }
                }
                StringBuffer buffer = new StringBuffer();
                for (ProductModel.AttributesEntity.AttributeMembersEntity selectedEntity : cacheSelected) {
                    buffer.append(selectedEntity.getAttributeMemberId() + ";");
                }
                //TOD 检查数据
                if (skumap.get(buffer.substring(0, buffer.length() - 1)) != null && skumap.get(buffer.substring(0, buffer.length() - 1)).getStock() > 0) {
                    mprop.setStatus(mprop.getStatus() == 1 ? 1 : 0);
                } else {
                    mprop.setStatus(2);
                }
            }
        }
    }

    private void sortAttributeMembersEntityList(List<ProductModel.AttributesEntity.AttributeMembersEntity> cacheSelected) {
        for (int n = 0; n < cacheSelected.size() - 1; n++) {
            for (int k = 0; k < cacheSelected.size() - 1 - n; k++) {
                ProductModel.AttributesEntity.AttributeMembersEntity cacheEntity;
                if (cacheSelected.get(k).getAttributeMemberId() < cacheSelected.get(k + 1).getAttributeMemberId()) {
                    //交换数据
                    cacheEntity = cacheSelected.get(k);
                    cacheSelected.set(k, cacheSelected.get(k + 1));
                    cacheSelected.set(k + 1, cacheEntity);
                }
            }
        }
    }

    @Override
    public void performShowBottomClick() {
        preProgressData();
        iMainView.showBottomSheetDialog(productModel, mUiData);
    }

    private void preProgressData() {
        if (mUiData.getBottomSheetDialog() == null) {
            mUiData.getSelectedEntities().clear();
            // SKU 计算
            mUiData.setResult(Sku.skuCollection(productModel.getProductStocks()));
            // 初始化按钮
            for (int i = 0; i < productModel.getAttributes().size(); i++) {
                for (ProductModel.AttributesEntity.AttributeMembersEntity entity : productModel.getAttributes().get(i).getAttributeMembers()) {
                    if (mUiData.getResult().get(entity.getAttributeMemberId() + "") == null || mUiData.getResult().get(entity.getAttributeMemberId() + "").getStock() <= 0) {
                        entity.setStatus(2);
                    }
                }
            }
        }
    }

    @Override
    public void performHiddenBottomClick() {
        iMainView.hiddenBottomSheetDialog(mUiData);
    }

    @Override
    public void resetInventory() {
        // 显示正确的库存数
        String memberId = "";
        String name = "";
        sortAttributeMembersEntityList(selectedProp);
        for (ProductModel.AttributesEntity.AttributeMembersEntity attributeMembersEntity : selectedProp
                ) {
            name += attributeMembersEntity.getName();
            memberId += attributeMembersEntity.getAttributeMemberId() + ";";
        }
        Map<String, BaseSkuModel> baseSkuModel = mUiData.getResult();
        long stock = baseSkuModel == null ? -1 : baseSkuModel.get(memberId.substring(0, memberId.length() - 1)).getStock();
        iMainView.resetGoosInventory("属性：" + name + "库存数:" + stock);
    }

    private void resetOtherCheckedDate(List<ProductModel.AttributesEntity.AttributeMembersEntity> props) {
        for (int i = 0; i < props.size(); i++) {
            ProductModel.AttributesEntity.AttributeMembersEntity prop = props.get(i);
            if (prop.getStatus() == 1) {
                prop.setStatus(0);
            }
        }
    }

    @Override
    public boolean performPropsClickListener(View view, int position, ProductModel.AttributesEntity param) {
        if (param.getAttributeMembers().get(position).getStatus() == 1 || param.getAttributeMembers().get(position).getStatus() == 2) {
            return true;
        } else {
            //初始化其他属性为未选中状态
            resetOtherCheckedDate(param.getAttributeMembers());
            param.getAttributeMembers().get(position).setStatus(1);
        }
        //  根据所选属性设置相关属性的点击状态
        checkOtherPropsData(position, param.getAttributeMembers().get(position));
        resetInventory();
        iMainView.performPropsClick(view, position);
        return true;
    }
}
