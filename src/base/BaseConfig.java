package base;
/**
 * 存从文件中获得的平台key
 * @author Dolomo
 *
 */
public class BaseConfig {
	public String ptName = "";
	public String api_key = "";
	public String secret_key = "";
	/**
	 * 网站基本url  get post
	 */
	public String baseUrl = "";
	public String depthUrl = "";
	/**
	 * 
	 */
	public String tradeUrl = "";
	public String userinfoUrl = "";
	public String batch_tradeUrl  = "";
	public String cancel_orderUrl = "";
	public String order_historyUrl = "";
	public String order_infoUrl = "";
	
	
	
	//huobi
	
	
	public BaseConfig()
	{
		ptName = "";
		api_key = "";
		secret_key = "";
	}
}
