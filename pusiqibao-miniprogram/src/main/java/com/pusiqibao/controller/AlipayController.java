package com.pusiqibao.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.pusiqibao.util.OrderIdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@Api("支付宝接口")
public class AlipayController {

    @GetMapping("/pay/alipay")
    @ApiOperation("支付宝当面付获取二维码")
    public JSONObject test_trade_precreate() throws Exception {
            return null;
//        return AlipayUtil.test_trade_precreate();
    }


    @GetMapping("/pay/pagePay")
    @ApiOperation("支付宝电脑网站支付")
    @CrossOrigin
    public void pagePay(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2016102700768995",
              "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDHbpltbfGzMfxtX3/N7sIIxQTEuHvhljiST9c3idUEfvVgFbqq/KOFvKjvZz9r8k7RRmDkKFjjImdOUgEYFFhtEC5i9y7QzB6Iukq1kianBo4QBSIZlsb4sTZYioOlilF6HFpnHa3ZbUb/HX7yfRiPQXJ9VdGYp4WUItwz0p7WjkynmmHjQMCPBtGYkNcX+cvOoleh/94+sgqJih3GJoTxo9IfTree6V2+pA3xh2kTd/reqCmWMl0fMtXg3w/VlLPxBC6+HXC/KRPGY3A1s1QE0H8/t/56EX3ivOVT8SVWYL+eoRXw6hHMha3ZDkTbtmXFsZsGfhylUF2DdH5DhDQzAgMBAAECggEATxIKwthUfDfVXYf/pCaFx/RVb6m1dDubMmd1bQ+45Y9wuifjWufP7bZfRIwrGSF5HoBI6i0VEPkS/HqG9IUciiFgQty+Mwq7EHrMJDFOIer3aGKgooSlXvTkFzbT+05b/44+SaP6yboJ7BxFuN7gXewU+JyN/Xht/c3UgLeBQo91gZ6mMnGdVZ97YFm95E+muBxBx8pKLlf6Y5PSkgm8j+nh4O2Yby6yWs+mu1DecNLSxYdDFyZC+XORt3GqJWHE9lbN6KD0miK6KbEGM/c9zH3WwWlpvY7J/7UvJ+am9gyzqvlcFML/6Cwv9Y63rcJ97/4/RTallYrTByoKCi9YAQKBgQDrdm/H5qc4nc+pSgkHyfC2lwAGly9GUHZBHItBstJ42JVzGStG35PvKr5UIjaPFjyBgpQjx/9zMPEgcmxQx67ODSFeW8QobGQ3hmYfN53zvt4KBcxcY6YgdYiXuVhniHoaGF1eWgBB964ewfvEjlks5e0BhTghSUkbF70wDLA9ewKBgQDY06XLoZEttBWSPUw9qzjlODw6nj4hdhgTPjIH1yXftQ1ABVr+VfaFLv2r1HFizNNmNBj7asvgymixOXMLwNrC+Q0EuYhJAJGWIzCMh5EaPw3awDllK+5xdgo5xUI19HxN9g6un29IeFk2e1dqRJgQBaNYhmrDHwT31u/ige16qQKBgCTKXKzfXUF7ZvcMmlydMP9WmKpu9PQHnnKOAzRAvKIlNTTGufxY8sRr6VE8B0pULyANRxhQJ5nYC9UGC+aTTLzlUFFuBThLt1z1ov89sXBkYk0umr/U0iBAPRd33lhO+sUZCX20klW8XXhw1uXfWA+r3VxfdGW/Da0uCGF5mhABAoGBAJ/mva9FcHY/B2V4lSAphGTuquQjzPgKSkjupiDIFqM6txHOoUNNi+a+N88fdZsCKKiHpSDMqpwVSU3haylwySPeXS5t0Mbdh4AsZWJNm8G6XHJFLk6hLlA4V+Qwx9pYec9YtY0sAPp13nziSx4Qhf/S2JqxTkylNJm9xdzKKqM5AoGATrSRWmjt0Jx/U1bNCCA5maXndJFjt4dJRFbXupYr6T+yYG50QuPoKkkAcEdO+cxJ7C0PQe8JFhB1vz1QuH1OkOaO9wsjeARnRXFAUly3tGweyZMuoj5jyUfngG2A0XsatHjFlKKkoyJL92Ap35tlI5kYCKGv208BtqdcfWg6Seo=",
                "json",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr0XSOk5TBwg95AHjlb2u8h7gIanusUh7TIXJ+A0x8YmFJgEVRVUe+i/ZtDWRM0U4KsYDDKiI1+0ctQ3F0fEE1r4Ku7T1qfXfpOqODI5Gl61TuupEL8diIJdhoZajm3khEjdutqmH1gc3oUOoSAnA37BZlvPjTEnM/NzQwGFKqjipDjU9KeMSFHxeetdaSPW1n6XrMrjQJpZtLuZlp5yVf5wBzEbeImo2HiZh6x/lVTHYwKTuC34xOE8QVNTaBA88/uxo+2uK5UxqbXoXOaG5O1x7D09jXwOylQrDqw93GZReOuR1s7febj0gPqqNw5HTPlQZ3/GQ6jqkt8fzJhHepwIDAQAB","RSA2"
        );

        //发起电脑网页
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //页面跳转同步通知页面路径
        alipayRequest.setReturnUrl("");
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl("");
        AlipayTradePagePayModel alipayTradePagePayModel = new AlipayTradePagePayModel();
        alipayTradePagePayModel.setOutTradeNo("XHY"+ OrderIdUtil.getId());
        alipayTradePagePayModel.setTotalAmount("99.66");
        alipayTradePagePayModel.setSubject("普斯汽保 千斤顶应急车上");
        alipayTradePagePayModel.setProductCode("FAST_INSTANT_TRADE_PAY");
        alipayRequest.setBizModel(alipayTradePagePayModel);
        //封装参数
//        alipayRequest.setBizContent("{" +
//                "    \"out_trade_no\":\"20150329010101001\"," +
//                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
//                "    \"total_amount\":88.88," +
//                "    \"subject\":\"Iphone6 16G\"," +
//                "    \"body\":\"Iphone6 16G\"," +
//                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
//                "    \"extend_params\":{" +
//                "    \"sys_service_provider_id\":\"2088511833207846\"" +
//                "    }" +
//                "  }");
        String result = "";
        try {
            //3、请求支付宝进行付款，并获取支付结果
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }


        httpResponse.setContentType("text/html;charset=utf-8");
        httpResponse.getWriter().write(result);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();

    }

    @GetMapping("/pay/getPagePay")
    @ApiOperation("支付宝电脑网站支付查询")
    @CrossOrigin
    public AlipayTradeQueryResponse getPagePay(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2016102700768995",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDHbpltbfGzMfxtX3/N7sIIxQTEuHvhljiST9c3idUEfvVgFbqq/KOFvKjvZz9r8k7RRmDkKFjjImdOUgEYFFhtEC5i9y7QzB6Iukq1kianBo4QBSIZlsb4sTZYioOlilF6HFpnHa3ZbUb/HX7yfRiPQXJ9VdGYp4WUItwz0p7WjkynmmHjQMCPBtGYkNcX+cvOoleh/94+sgqJih3GJoTxo9IfTree6V2+pA3xh2kTd/reqCmWMl0fMtXg3w/VlLPxBC6+HXC/KRPGY3A1s1QE0H8/t/56EX3ivOVT8SVWYL+eoRXw6hHMha3ZDkTbtmXFsZsGfhylUF2DdH5DhDQzAgMBAAECggEATxIKwthUfDfVXYf/pCaFx/RVb6m1dDubMmd1bQ+45Y9wuifjWufP7bZfRIwrGSF5HoBI6i0VEPkS/HqG9IUciiFgQty+Mwq7EHrMJDFOIer3aGKgooSlXvTkFzbT+05b/44+SaP6yboJ7BxFuN7gXewU+JyN/Xht/c3UgLeBQo91gZ6mMnGdVZ97YFm95E+muBxBx8pKLlf6Y5PSkgm8j+nh4O2Yby6yWs+mu1DecNLSxYdDFyZC+XORt3GqJWHE9lbN6KD0miK6KbEGM/c9zH3WwWlpvY7J/7UvJ+am9gyzqvlcFML/6Cwv9Y63rcJ97/4/RTallYrTByoKCi9YAQKBgQDrdm/H5qc4nc+pSgkHyfC2lwAGly9GUHZBHItBstJ42JVzGStG35PvKr5UIjaPFjyBgpQjx/9zMPEgcmxQx67ODSFeW8QobGQ3hmYfN53zvt4KBcxcY6YgdYiXuVhniHoaGF1eWgBB964ewfvEjlks5e0BhTghSUkbF70wDLA9ewKBgQDY06XLoZEttBWSPUw9qzjlODw6nj4hdhgTPjIH1yXftQ1ABVr+VfaFLv2r1HFizNNmNBj7asvgymixOXMLwNrC+Q0EuYhJAJGWIzCMh5EaPw3awDllK+5xdgo5xUI19HxN9g6un29IeFk2e1dqRJgQBaNYhmrDHwT31u/ige16qQKBgCTKXKzfXUF7ZvcMmlydMP9WmKpu9PQHnnKOAzRAvKIlNTTGufxY8sRr6VE8B0pULyANRxhQJ5nYC9UGC+aTTLzlUFFuBThLt1z1ov89sXBkYk0umr/U0iBAPRd33lhO+sUZCX20klW8XXhw1uXfWA+r3VxfdGW/Da0uCGF5mhABAoGBAJ/mva9FcHY/B2V4lSAphGTuquQjzPgKSkjupiDIFqM6txHOoUNNi+a+N88fdZsCKKiHpSDMqpwVSU3haylwySPeXS5t0Mbdh4AsZWJNm8G6XHJFLk6hLlA4V+Qwx9pYec9YtY0sAPp13nziSx4Qhf/S2JqxTkylNJm9xdzKKqM5AoGATrSRWmjt0Jx/U1bNCCA5maXndJFjt4dJRFbXupYr6T+yYG50QuPoKkkAcEdO+cxJ7C0PQe8JFhB1vz1QuH1OkOaO9wsjeARnRXFAUly3tGweyZMuoj5jyUfngG2A0XsatHjFlKKkoyJL92Ap35tlI5kYCKGv208BtqdcfWg6Seo=",
                "json",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr0XSOk5TBwg95AHjlb2u8h7gIanusUh7TIXJ+A0x8YmFJgEVRVUe+i/ZtDWRM0U4KsYDDKiI1+0ctQ3F0fEE1r4Ku7T1qfXfpOqODI5Gl61TuupEL8diIJdhoZajm3khEjdutqmH1gc3oUOoSAnA37BZlvPjTEnM/NzQwGFKqjipDjU9KeMSFHxeetdaSPW1n6XrMrjQJpZtLuZlp5yVf5wBzEbeImo2HiZh6x/lVTHYwKTuC34xOE8QVNTaBA88/uxo+2uK5UxqbXoXOaG5O1x7D09jXwOylQrDqw93GZReOuR1s7febj0gPqqNw5HTPlQZ3/GQ6jqkt8fzJhHepwIDAQAB","RSA2"
        );

        //发起电脑网页
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest ();
        //页面跳转同步通知页面路径
        alipayRequest.setReturnUrl("");
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl("");
        alipayRequest.setBizContent("{" +
                "\"out_trade_no\":\"20150329010101001\"," +
                "\"trade_no\":\"2021062822001458660512457359\"," +
                "\"org_pid\":\"2088101117952222\"," +
                "      \"query_options\":[" +
                "        \"trade_settle_info\"" +
                "      ]" +
                "  }");
        //封装参数
        AlipayTradeQueryResponse response = alipayClient.execute(alipayRequest);
        return response;

    }


    @GetMapping("/pay/downloadurl")
    @ApiOperation("支付宝电脑网站对账账单查询")
    @CrossOrigin
    public AlipayDataDataserviceBillDownloadurlQueryResponse getDownLoadurl(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2016102700768995",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDHbpltbfGzMfxtX3/N7sIIxQTEuHvhljiST9c3idUEfvVgFbqq/KOFvKjvZz9r8k7RRmDkKFjjImdOUgEYFFhtEC5i9y7QzB6Iukq1kianBo4QBSIZlsb4sTZYioOlilF6HFpnHa3ZbUb/HX7yfRiPQXJ9VdGYp4WUItwz0p7WjkynmmHjQMCPBtGYkNcX+cvOoleh/94+sgqJih3GJoTxo9IfTree6V2+pA3xh2kTd/reqCmWMl0fMtXg3w/VlLPxBC6+HXC/KRPGY3A1s1QE0H8/t/56EX3ivOVT8SVWYL+eoRXw6hHMha3ZDkTbtmXFsZsGfhylUF2DdH5DhDQzAgMBAAECggEATxIKwthUfDfVXYf/pCaFx/RVb6m1dDubMmd1bQ+45Y9wuifjWufP7bZfRIwrGSF5HoBI6i0VEPkS/HqG9IUciiFgQty+Mwq7EHrMJDFOIer3aGKgooSlXvTkFzbT+05b/44+SaP6yboJ7BxFuN7gXewU+JyN/Xht/c3UgLeBQo91gZ6mMnGdVZ97YFm95E+muBxBx8pKLlf6Y5PSkgm8j+nh4O2Yby6yWs+mu1DecNLSxYdDFyZC+XORt3GqJWHE9lbN6KD0miK6KbEGM/c9zH3WwWlpvY7J/7UvJ+am9gyzqvlcFML/6Cwv9Y63rcJ97/4/RTallYrTByoKCi9YAQKBgQDrdm/H5qc4nc+pSgkHyfC2lwAGly9GUHZBHItBstJ42JVzGStG35PvKr5UIjaPFjyBgpQjx/9zMPEgcmxQx67ODSFeW8QobGQ3hmYfN53zvt4KBcxcY6YgdYiXuVhniHoaGF1eWgBB964ewfvEjlks5e0BhTghSUkbF70wDLA9ewKBgQDY06XLoZEttBWSPUw9qzjlODw6nj4hdhgTPjIH1yXftQ1ABVr+VfaFLv2r1HFizNNmNBj7asvgymixOXMLwNrC+Q0EuYhJAJGWIzCMh5EaPw3awDllK+5xdgo5xUI19HxN9g6un29IeFk2e1dqRJgQBaNYhmrDHwT31u/ige16qQKBgCTKXKzfXUF7ZvcMmlydMP9WmKpu9PQHnnKOAzRAvKIlNTTGufxY8sRr6VE8B0pULyANRxhQJ5nYC9UGC+aTTLzlUFFuBThLt1z1ov89sXBkYk0umr/U0iBAPRd33lhO+sUZCX20klW8XXhw1uXfWA+r3VxfdGW/Da0uCGF5mhABAoGBAJ/mva9FcHY/B2V4lSAphGTuquQjzPgKSkjupiDIFqM6txHOoUNNi+a+N88fdZsCKKiHpSDMqpwVSU3haylwySPeXS5t0Mbdh4AsZWJNm8G6XHJFLk6hLlA4V+Qwx9pYec9YtY0sAPp13nziSx4Qhf/S2JqxTkylNJm9xdzKKqM5AoGATrSRWmjt0Jx/U1bNCCA5maXndJFjt4dJRFbXupYr6T+yYG50QuPoKkkAcEdO+cxJ7C0PQe8JFhB1vz1QuH1OkOaO9wsjeARnRXFAUly3tGweyZMuoj5jyUfngG2A0XsatHjFlKKkoyJL92Ap35tlI5kYCKGv208BtqdcfWg6Seo=",
                "json",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr0XSOk5TBwg95AHjlb2u8h7gIanusUh7TIXJ+A0x8YmFJgEVRVUe+i/ZtDWRM0U4KsYDDKiI1+0ctQ3F0fEE1r4Ku7T1qfXfpOqODI5Gl61TuupEL8diIJdhoZajm3khEjdutqmH1gc3oUOoSAnA37BZlvPjTEnM/NzQwGFKqjipDjU9KeMSFHxeetdaSPW1n6XrMrjQJpZtLuZlp5yVf5wBzEbeImo2HiZh6x/lVTHYwKTuC34xOE8QVNTaBA88/uxo+2uK5UxqbXoXOaG5O1x7D09jXwOylQrDqw93GZReOuR1s7febj0gPqqNw5HTPlQZ3/GQ6jqkt8fzJhHepwIDAQAB","RSA2"
        );

        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        request.setBizContent("{" +
                "\"bill_type\":\"trade\"," +
                "\"bill_date\":\"2016-04-05\"" +
                "  }");
        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return response;

    }

    @GetMapping("/pay/mobilePay")
    @ApiOperation("支付宝手机网站支付")
    @CrossOrigin
    public void page(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2016102700768995",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDHbpltbfGzMfxtX3/N7sIIxQTEuHvhljiST9c3idUEfvVgFbqq/KOFvKjvZz9r8k7RRmDkKFjjImdOUgEYFFhtEC5i9y7QzB6Iukq1kianBo4QBSIZlsb4sTZYioOlilF6HFpnHa3ZbUb/HX7yfRiPQXJ9VdGYp4WUItwz0p7WjkynmmHjQMCPBtGYkNcX+cvOoleh/94+sgqJih3GJoTxo9IfTree6V2+pA3xh2kTd/reqCmWMl0fMtXg3w/VlLPxBC6+HXC/KRPGY3A1s1QE0H8/t/56EX3ivOVT8SVWYL+eoRXw6hHMha3ZDkTbtmXFsZsGfhylUF2DdH5DhDQzAgMBAAECggEATxIKwthUfDfVXYf/pCaFx/RVb6m1dDubMmd1bQ+45Y9wuifjWufP7bZfRIwrGSF5HoBI6i0VEPkS/HqG9IUciiFgQty+Mwq7EHrMJDFOIer3aGKgooSlXvTkFzbT+05b/44+SaP6yboJ7BxFuN7gXewU+JyN/Xht/c3UgLeBQo91gZ6mMnGdVZ97YFm95E+muBxBx8pKLlf6Y5PSkgm8j+nh4O2Yby6yWs+mu1DecNLSxYdDFyZC+XORt3GqJWHE9lbN6KD0miK6KbEGM/c9zH3WwWlpvY7J/7UvJ+am9gyzqvlcFML/6Cwv9Y63rcJ97/4/RTallYrTByoKCi9YAQKBgQDrdm/H5qc4nc+pSgkHyfC2lwAGly9GUHZBHItBstJ42JVzGStG35PvKr5UIjaPFjyBgpQjx/9zMPEgcmxQx67ODSFeW8QobGQ3hmYfN53zvt4KBcxcY6YgdYiXuVhniHoaGF1eWgBB964ewfvEjlks5e0BhTghSUkbF70wDLA9ewKBgQDY06XLoZEttBWSPUw9qzjlODw6nj4hdhgTPjIH1yXftQ1ABVr+VfaFLv2r1HFizNNmNBj7asvgymixOXMLwNrC+Q0EuYhJAJGWIzCMh5EaPw3awDllK+5xdgo5xUI19HxN9g6un29IeFk2e1dqRJgQBaNYhmrDHwT31u/ige16qQKBgCTKXKzfXUF7ZvcMmlydMP9WmKpu9PQHnnKOAzRAvKIlNTTGufxY8sRr6VE8B0pULyANRxhQJ5nYC9UGC+aTTLzlUFFuBThLt1z1ov89sXBkYk0umr/U0iBAPRd33lhO+sUZCX20klW8XXhw1uXfWA+r3VxfdGW/Da0uCGF5mhABAoGBAJ/mva9FcHY/B2V4lSAphGTuquQjzPgKSkjupiDIFqM6txHOoUNNi+a+N88fdZsCKKiHpSDMqpwVSU3haylwySPeXS5t0Mbdh4AsZWJNm8G6XHJFLk6hLlA4V+Qwx9pYec9YtY0sAPp13nziSx4Qhf/S2JqxTkylNJm9xdzKKqM5AoGATrSRWmjt0Jx/U1bNCCA5maXndJFjt4dJRFbXupYr6T+yYG50QuPoKkkAcEdO+cxJ7C0PQe8JFhB1vz1QuH1OkOaO9wsjeARnRXFAUly3tGweyZMuoj5jyUfngG2A0XsatHjFlKKkoyJL92Ap35tlI5kYCKGv208BtqdcfWg6Seo=",
                "json",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr0XSOk5TBwg95AHjlb2u8h7gIanusUh7TIXJ+A0x8YmFJgEVRVUe+i/ZtDWRM0U4KsYDDKiI1+0ctQ3F0fEE1r4Ku7T1qfXfpOqODI5Gl61TuupEL8diIJdhoZajm3khEjdutqmH1gc3oUOoSAnA37BZlvPjTEnM/NzQwGFKqjipDjU9KeMSFHxeetdaSPW1n6XrMrjQJpZtLuZlp5yVf5wBzEbeImo2HiZh6x/lVTHYwKTuC34xOE8QVNTaBA88/uxo+2uK5UxqbXoXOaG5O1x7D09jXwOylQrDqw93GZReOuR1s7febj0gPqqNw5HTPlQZ3/GQ6jqkt8fzJhHepwIDAQAB","RSA2"
        );
        // 发起App支付请求
//        AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        AlipayTradeWapPayModel alipayObject = new AlipayTradeWapPayModel();
        alipayObject.setOutTradeNo("XHY"+OrderIdUtil.getId());
        alipayObject.setTotalAmount("88.88");
        alipayObject.setProductCode("QUICK_WAP_PAY");
        alipayObject.setSubject("iPhone 12 蓝色 64G");
        alipayRequest.setBizModel(alipayObject);
//        alipayRequest.setBizContent("{" +
//                " \"out_trade_no\":\"20150320020101008\"," +
//                " \"total_amount\":\"88.88\"," +
//                " \"subject\":\"Iphone6 16G\"," +
//                " \"product_code\":\"QUICK_WAP_PAY\"" +
//                " }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=UTF-8");
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();


    }
}

