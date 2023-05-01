package com.webshoprsmex.config;

/**
 * 支付宝沙箱功能配置类
 */
public class AlipayConfig {

	//应用ID，您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2021000118653732";
	
	//商户私钥，您的PKCS8格式RSA2私钥
	public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCvJwbyNgNcVLggGdpU4vZbFAkGwwLJx+mGgn/0JFNJF4qZ2VYVXzYkKqpQBEuVVqFcAnOMu7+/xN/jZf0Z/eNgQBYJ7ufZsXrAH2PCto6RnHEHF0Vr2gUo7ezV+rjDNonfNdm6uqchsBwUsHGsOm9ykCJ6szPG2vGsTISN4xGgnS3NguoZ/jqU9RFjxRXklYJOBopF9omhLyAMDbTEnKZe82Q1d2yNvAIGh5P9Vnj261RLMXIGZrouXjYhRhWf9dV0unWNfTQTOOM50rhXL3O0yoPZA/Jpvvuz2zCZxmszxyoOhMm2IcEVqnvY0gf+P2j0Hxcsrkgg2QGO5Fkm/YJVAgMBAAECggEAWwchaQgBPx8YgjZtbeh2uGb1OfpnnI61Ix73eN7YGgiOE98MChdtf+ZclTFmRrq7KaJsgxugT/31ja0XmUaFwSQNUCRFgrHmZarqoetWtP5lD5ZckezA+cCNcbfYBjx18vd9rLoM2kk4jtgUe7ny6ctW4AX6RsLLDwzFZyg3pQyt728duJ+m934gqRDUtnUpMntODweYzOA3Ec/013eE0gbNMhw7LG0jqn7ro1ZWT5QSycPHFD+/fGiU4mfL2mYafWJ8jD7VHyaYH6Lcf/qxX+6WYnEe943Q5sjbM5FItpbxZY48p+gCpmYxf2fJWxJ8sctuiL2bST9fIeiKZCtAkQKBgQDpxHledd7oLmJEsuTOuYLNgOgO+VH1BFojhmRfymHNpXroZKLHaLSrKa/uGJDPIK8L/ivn60gBGLUWDy4u9kxynjZg/ItVwPj4M8mGiSe0AQT696UCyOo+Yj/iEL4DNNCFFan8ESQHtD59jjjX0MOEvYf/S/4pMwJw/N0RDSD2IwKBgQC/z3SoLKKlp+z2Tm7zgPvMtI1yLBm+Vra2N0KkM7XtycS7uekjti2cX+9DQ8qcmBL0tR20fy8M0DX/jGQZCNTaUYhs2Gm5xySYRODoL3ITvjwAeDVoAHXpkcBU2H1qXzuc2dSu/hj5ODod7/lb7iOX7+/95oysqYLsE9Hax+ShJwKBgG0pvrOJv4oU/vatw4N1xctJgxRbARa17ZHhVEYXMAzkVbzoIcuU7alwUiORfS38SUIEc2RdzsYUT5v5gsuTYKe1khdAV6C3+IuPQ3B74/p+72By/K+MOzbeko5y2wiMRUOgljyLlJO0a+od3BWL+4GlMDjbt9htZZp1upYX12iZAoGBAKcWjybZCy6HZDMEDD7vA2pFdloY9KwMWcnNxa3IphjohQqLXv6O+piYMZNFAMD88LZe9+t+R3w//GoK4D+2o8Uy9UbsFt2RDt3eUpWwjw2H0DkQfmWE0TKQlxzSIh6Bqyptz4pJK3QeePIPVAezISqAB24EYlMerz/72/lb3FxNAoGAMJ/2lP88rA/Rjx26w5VOit9sApCURmVqvJyIFenqKWdsmyLEEIKk9M1YJW8JDsSb2OPaNbT53ob2HktGMWv7U9M12LcKnTrApCspfDauxMqG8ilXCs7FM23/LmAI7MvhFfAHkP+sfqtWrIVzNkeSxGiGKH8fnH5LcoMf7aLo4V8=";
  
	//支付宝公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAydNhSYddFlUQMuq/wDjUVne/bAxyhNob4ZzIOgOmIiVfPKEpSMHOxQMMHEhy4CBEWlMdGf2x2fO5IfG/suj967tzVyu9naxexa8RKj4iOYuIvsW7WsTlqt9vykDxGlUAUvNa1Se/1UVU4+4ryiqyEwXgaZSMmAVVvojQHQCjwL97jKQc7uT6XIPr6LX70rtZRAj93jZWoUDA43SSkuWx2JSYWhCh5352ywa1PoABxAgqQg0CGMWWN/oOFappOowuL0sXjmriwRnkOoHgvxWFOTCj9jbSuBr0a0oiWK7CWt4cEdZVgjTqOkVozS5ke+ArJckrS4QWQSnZeALccxvtHwIDAQAB";
  
	//服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	//必须是公网ip
	public static String notify_url = "";
	
	//页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/WebShopRSMEx/front/order/doPayReturn";
	
	//签名方式
	public static String sign_type = "RSA2";
	
	//字符编码格式
	public static String charset = "utf-8";
	
	//支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
}
