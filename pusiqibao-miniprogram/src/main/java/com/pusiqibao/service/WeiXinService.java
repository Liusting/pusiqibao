package com.pusiqibao.service;

import java.util.Map;

public interface WeiXinService {
    Map wxPay(String spbill_create_ip, String openId, String orderNumber);
}
