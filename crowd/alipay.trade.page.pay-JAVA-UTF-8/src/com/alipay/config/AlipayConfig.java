package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016102500759778";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCB3922qLeK7zRTeLHW3t56/DCO6U1kSZvrzcN0AHUsECKX4xZaNk/kvHBO8PNBTfKaiIuGnYJ+Buvo0qHuGtaOQvhqruf5JmDdE+wKYL+MEjeg603hwIEBwH1Kn+y1CPqJ8vbFJkP3AMk/CTK5pdzxrXS2XOXRCrLQhK2l3MVE4bpXoArnFsXusV8jXPbNmeWueIHcj/xfxVganRokrtwg+v4iNTeoZ0mVW2rL3RF+BG5DHNpDQPERnizIkvm+SEpNTAr4aKccpUBlG7/zE8K1LpIuQERHGEB7g1ZbNm8w2s4VNiG9gxNfZIpdP8kZBd72/ZbNp1l0Rwet1hW7+VpFAgMBAAECggEBAIGlBIELKLVTFYvhpr1Umwhv6NUwRWZAKJqaO2r37f9/V9kBAlYpc1VlWkMjOeDNu8jRV16YqyO9Kw+t+6FhPx6h8bMlSEGpMvZdpbmyXGOp58gGPCeH7//cs/WjcTpCbT75v6aMDTprmXkIaI9z5/uE6EYGtA8Zu+h1GZbBrFaV9j5zgbedYPlKwIXM4ANRBkBfml9fdo8IU2aIyDJYhVWP4uXLP2bsH+DvFTrkJLIySVsFf/xsCjLsX410Q4YVnGKlvXVqh7+p2Kczf1zxZhrbqqgtLOdIcTfvtg0NafAgv8xlxe1ZN8qS/2OlxxDGirMfEDO3ap8Md4XWUVz0D50CgYEA9Spl2LhoIQW8xVN0LFzY98KE4b3iLsquU73aeWFPQ1hQ9HNtxbnGpIR7mrZ5ewCGIgeGzP+H1M8pgGq+SpZ+m0K2X+GnW1QP8CTEiOwqvgK3wXph7np56Fll/2I/RsTcmqBQ9v+obfhquzWcv1PpXLukgeKoxjrRM13pXTOmxh8CgYEAh50ouiqlvFHM+TI0jKjPErUUHviv5pfUXPy2TH5j5AhicQ+JxAdWN9S+1xuR/b+J++40b2fjVJrNyexH2+qMCeEh6/zAoFFdKbX66P9q4/2DwgBr5E8tpgG322WwiLS0LUb490+g/Fw/M10B2KV9smpUbICPJNRnWDvh4HX86xsCgYEAvJ9QGHtYcf+uCst4WCFfNM/N2Lh06rc5hGhPTI/XoeKy9kBQto2GDVF4n8HAmSgACvJrfhEjelGghpKZsV00L1QWaJJ+Zj+4u3QuWYIKKIKCyjLUYdyNKnyNgv0d3d7cM4Mgh8iO130G8N4AqRtwMc6UdPHkFA9woOqh/vkcWNUCfwbi9C5/++qUPNRquEBsUhehGmR4PHZ5P095hchcorXxtw4Gu/g4ZyhupUJHJCK+l0Mz1XXS0Lh7I/gdB4kjX/kgdZiGPUHm39Q+aX9reQIa3ZX8b34ohyjm7W82usA2bO/fqsTkRkiKZqs1DeLyhG+K9r6LyLkWEClq4Q2E8FMCgYEAmsXx/BttqyV6mNhcP6wSlt0aC/J3BoBiKfDIASCRAwWWgtSiKZ885P/Q4fpHHWtPoC600628ma1OBfpJRSYHcePiVvrdrys0RufjZm/8/C1LIMzOl+BzpvfQhzgUK6gi1dm7oACWjfvDw7Eay+VcQmGebLLDwfNmvEJ3XmKcyXU=";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqN/mdFBSYYYrPM5zdb6tIesjQP4zVu7blPOyjUYKeIqJ8BG4k62FebHfF9pRFqPTUXhYthb4j18yEjkCWfXMNoaavbUYJqai9yFgDMIXAwNUBcNyT6F2WS26oRMiWRurf7p+FxLyHkNGNX3ZRpfnHb6Fdm7YJBCt0R3yW2flzj2MsNTNOBLkFDrVT2OUZZ2e3Qfo8ze1o8ecZCY3HCIh8mmNjgVJIcbIxBqWBfpV47F4AjNUbq+S2T0H5k3ulzZzwuxR6os0h/fdCbMxMui7/+rmz+cgj7ZXC7294u5WJ9WcsIkXo2LGrsERKuLe+LybD0eYUa/YmYNiS7sMME9qwQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://uwzs6d.natappfree.cc/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

