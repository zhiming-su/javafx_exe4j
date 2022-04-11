package com.zz.zfb.util;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TransferUtil {


    private static final String APPID = "appId.txt";
    private static final String PRIVATEKEY = "privateKey.txt";
    private static final String CERTPATH = "appCertPublicKey_2021002128635452.crt";
    private static final String ALIPAYPUBLICCERTPATH = "alipayCertPublicKey_RSA2.crt";
    private static final String ROOTCERTPATH = "alipayRootCert.crt";


    public static void main(String[] args) throws AlipayApiException, IOException {
        //System.out.println(certPath);
        /*AlipayFundTransUniTransferResponse response = transfer(
                "1",
                "18131839467",
                "李帆",
                "河南易云",
                "测试",
                "测试"
        );
        if (response.isSuccess()) {
            System.out.println(JSON.toJSONString(response, SerializerFeature.PrettyFormat));
            System.out.println("调用成功");
        } else {
            System.out.println(JSON.toJSONString(response, SerializerFeature.PrettyFormat));
            System.out.println("调用失败");
        }*/
    }

    public static AlipayFundTransUniTransferResponse transfer(
            String trans_amount, String identity, String name, String payer_show_name, String order_title, String remark
    ) throws IOException, AlipayApiException {

        //System.out.println(rootCertPath);
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl("https://openapi.alipay.com/gateway.do");
        certAlipayRequest.setAppId(readLineByPath(APPID));
        certAlipayRequest.setPrivateKey(readLineByPath(PRIVATEKEY));
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset("utf-8");
        certAlipayRequest.setSignType("RSA2");
        certAlipayRequest.setCertPath(getClassLoaderPath(CERTPATH));
        certAlipayRequest.setAlipayPublicCertPath(getClassLoaderPath(ALIPAYPUBLICCERTPATH));
        certAlipayRequest.setRootCertPath(getClassLoaderPath(ROOTCERTPATH));
        DefaultAlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();

        Map<String, Object> bizCountMap = new HashMap<>();
        bizCountMap.put("out_biz_no", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        bizCountMap.put("trans_amount", trans_amount);
        bizCountMap.put("product_code", "TRANS_ACCOUNT_NO_PWD");
        bizCountMap.put("biz_scene", "DIRECT_TRANSFER");
        bizCountMap.put("order_title", order_title);
        bizCountMap.put("remark", remark);

        Map<String, String> payeeInfoMap = new HashMap<>();
        payeeInfoMap.put("identity", identity);
        payeeInfoMap.put("identity_type", "ALIPAY_LOGON_ID");
        payeeInfoMap.put("name", name);
        bizCountMap.put("payee_info", payeeInfoMap);

        Map<String, String> businessParamsMap = new HashMap<>();
        businessParamsMap.put("payer_show_name", payer_show_name);
        bizCountMap.put("business_params", businessParamsMap);


        request.setBizContent(JSON.toJSONString(bizCountMap));
        return alipayClient.certificateExecute(request);
    }

    private static String readLineByPath(String path) throws IOException {
        return Files.readAllLines(Paths.get(getClassLoaderPath(path))).get(0);
    }

    private static String getClassLoaderPath(String path) {
        //System.out.println(System.getProperty("user.dir"));
        //return System.getProperty("user.dir")+"\\src\\"+path;
        return System.getProperty("user.dir") + "\\" + path;
        //return Objects.requireNonNull(Application.class.getProtectionDomain().getClass().getResource(path).getPath());
    }
/*
    private static String readLineByPath(String fileName) throws IOException {
        Path path= Paths.get(fileName);
       // System.out.println(path);
        return Files.readAllLines(path).get(0);
    }

    private static String getClassLoaderPath(String path) {
        return Objects.requireNonNull(TransferUtil.class.getClassLoader().getResource(path)).getPath().substring(1);
    }*/
}
