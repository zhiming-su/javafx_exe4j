package com.zz.zfb.mod;

// lombok.Data;

//@Data
public class TransInfo {
    private String remarkT,status,nameT,transAmountT,identityT,orderTitleT;

    public String getRemarkT() {
        return remarkT;
    }

    public void setRemarkT(String remarkT) {
        this.remarkT = remarkT;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNameT() {
        return nameT;
    }

    public void setNameT(String nameT) {
        this.nameT = nameT;
    }

    public String getTransAmountT() {
        return transAmountT;
    }

    public void setTransAmountT(String transAmountT) {
        this.transAmountT = transAmountT;
    }

    public String getIdentityT() {
        return identityT;
    }

    public void setIdentityT(String identityT) {
        this.identityT = identityT;
    }

    public String getOrderTitleT() {
        return orderTitleT;
    }

    public void setOrderTitleT(String orderTitleT) {
        this.orderTitleT = orderTitleT;
    }
}
