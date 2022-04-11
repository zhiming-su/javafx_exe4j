module com.zz.zfb {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires alipay.sdk.java;
    requires fastjson;
    //requires lombok;

    opens com.zz.zfb to javafx.fxml;
    exports com.zz.zfb;
    exports com.zz.zfb.controller;
    opens com.zz.zfb.controller to javafx.fxml;
    opens com.zz.zfb.mod to javafx.base ;
}