package com.atguigu.crowd.handler;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.crowd.api.MySQLRemoteService;
import com.atguigu.crowd.config.PayProperties;
import com.atguigu.crowd.entity.vo.OrderProjectVo;
import com.atguigu.crowd.entity.vo.OrderVO;
import com.atguigu.crowd.util.ResultEntity;

@Controller
public class PayHandler {

	@Autowired
	private PayProperties payProperties;
	
	@Autowired
	private MySQLRemoteService mySQLRemoteService;
	
	private  Logger logger = LoggerFactory.getLogger(PayHandler.class);
	
	/**
	 * 把表单提交过来得数据整理存放进orderVO对象中，也把之前存入session域中得orderProjectVO对象加入到orderVO对象中
	 * 其中还设置自动生成了订单号并计算了改订单得总金额
	 * 最后一并存入session域中
	 * 然后掉用我们写好得支付宝接口进行支付
	 *
	 * @param session
	 * @param orderVO
	 * @return
	 * @throws AlipayApiException
	 */
	@ResponseBody
	@RequestMapping("/generate/order")
	public String generateOrder(HttpSession session,OrderVO orderVO) throws AlipayApiException{
		
		//1.从session域中取出之前存储的OrderProjectVo
		OrderProjectVo orderProjectVo = (OrderProjectVo) session.getAttribute("orderProjectVo");
		
		//2.将OrderProjectVo和OrderVO整合到一起
		orderVO.setOrderProjectVo(orderProjectVo);
		
		//3.生成订单号并设置到ordervo对象中
		//根据当前日期生成字符串
		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		//使用UUID生成用户id部分
		String user = UUID.randomUUID().toString().replace("_", "").toUpperCase();
		//将生成的时间日期字符串和用户id进行拼接
		String orderNum = time + user;
		//将orderNum添加到orderVO
		orderVO.setOrderNum(orderNum);
		
		//4.计算订单总金额并设置到ordervo对象中
		Double orderAmount = (double) (orderProjectVo.getSupportPrice()*orderProjectVo.getReturnCount() + orderProjectVo.getFreight());
		orderVO.setOrderAmount(orderAmount);
		
		//5.将保存好得orderVO对象放进session域中
		session.setAttribute("orderVO", orderVO);
		
		//5.调用我们封装好的支付宝接口，并传入数据（sendRequestToAliPay）
		return sendRequestToAliPay(orderNum, orderAmount, orderProjectVo.getProjectName(), orderProjectVo.getReturnContent());
	}
	
	
	/**
	 * 为了调用支付宝接口专门封装的接口
	 * @param outTradeNo
	 * @param totalAmount
	 * @param subject
	 * @param body
	 * @return
	 * @throws AlipayApiException
	 */
	private String sendRequestToAliPay(
			//商品订单号
			String outTradeNo, 
			//商品总金额
			Double totalAmount, 
			//商品订单名称
			String subject, 
			//商品备注
			String body) throws AlipayApiException{
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(
				payProperties.getGatewayUrl(), 
				payProperties.getAppId(), 
				payProperties.getMerchantPrivateKey(), 
				"json", 
				payProperties.getCharset(), 
				payProperties.getAlipayPublicKey(), 
				payProperties.getSignType());
		
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(payProperties.getReturnUrl());
		alipayRequest.setNotifyUrl(payProperties.getNotifyUrl());
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ outTradeNo +"\"," 
				+ "\"total_amount\":\""+ totalAmount +"\"," 
				+ "\"subject\":\""+ subject +"\"," 
				+ "\"body\":\""+ body +"\"," 
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		
		//若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
		//alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
		//		+ "\"total_amount\":\""+ total_amount +"\"," 
		//		+ "\"subject\":\""+ subject +"\"," 
		//		+ "\"body\":\""+ body +"\"," 
		//		+ "\"timeout_express\":\"10m\"," 
		//		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		//请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节
		
		//请求
		return alipayClient.pageExecute(alipayRequest).getBody();
	}
	
	
	
	/**
	 * @throws AlipayApiException 
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	@ResponseBody
	@RequestMapping("/return")
	public String returnUrlMethod(HttpServletRequest request,HttpSession session) throws AlipayApiException, UnsupportedEncodingException{
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(
				params, 
				payProperties.getAlipayPublicKey(), 
				payProperties.getCharset(),
				payProperties.getSignType()); //调用SDK验证签名

		//——请在这里编写您的程序（以下代码仅作参考）——
		if(signVerified) {
			//商户订单号
			String orderNum = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//支付宝交易号
			String payOrderNum = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//付款金额
			String orderAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
			
			//保存到数据库
			
			//1.从session域中获取ordervo对象
			OrderVO orderVO = (OrderVO) session.getAttribute("orderVO");
			
			//2.将支付宝交易号存入OrderVO对象中
			orderVO.setPayOrderNum(payOrderNum);
			
			//3.将取出得ordervo对象发送给MySQL远程接口
			ResultEntity<String> resultEntity = mySQLRemoteService.saveOrderRemote(orderVO);
			logger.info("Order save result"+resultEntity.getOperationResult());
			
			return "trade_no:"+payOrderNum+"<br/>out_trade_no:"+orderNum+"<br/>total_amount:"+orderAmount;
		}else {
			
			//如果校验失败
			//页面显示信息
			return "校验失败";
		}
	}
	
	
	/**
	 * notify
	 * @param request
	 * @throws AlipayApiException
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping("/notify")
	public void notifyUrlMethod(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException{
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(
				params, 
				payProperties.getAlipayPublicKey(), 
				payProperties.getCharset(), 
				payProperties.getSignType()); //调用SDK验证签名

		//——请在这里编写您的程序（以下代码仅作参考）——
		
		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
		if(signVerified) {//验证成功
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			
			logger.info("out_trade_no="+out_trade_no);
			logger.info("trade_no="+trade_no);
			logger.info("trade_status="+trade_status);
			
		}else {//验证失败
			//调试用，写文本函数记录程序运行情况是否正常
			//String sWord = AlipaySignature.getSignCheckContentV1(params);
			//AlipayConfig.logResult(sWord);
			logger.info("验证失败");
		}
	}
}
