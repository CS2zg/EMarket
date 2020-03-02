package com.example.e_market.beans;
/*
* 由于之前设计商品类时没有考虑到购物车时的CheckBox的选中情况
* 修改商品类过于麻烦（引用太多了）。现创建该类作为含状态的商品类
*
* */
public class StatusGoodsBean {
    private GoodsBean goodsBean;
    private boolean isChecked;

    public StatusGoodsBean(GoodsBean goodsBean) {
        this.goodsBean = goodsBean;
        this.isChecked=false;//选中状态默认为false
    }
    public boolean isChecked() {
        return isChecked;
    }

    public GoodsBean getGoodsBean() {
        return goodsBean;
    }

    public void setGoodsBean(GoodsBean goodsBean) {
        this.goodsBean = goodsBean;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
