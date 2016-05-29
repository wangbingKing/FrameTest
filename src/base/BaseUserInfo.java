package base;

public class BaseUserInfo {
	/**
	 * 账户资产，包含净资产及总资产
	 */
	public String asset;//:
	/**
	 * 账户借款信息(只有在账户有借款信息时才会返回)
	 */
	public String borrow;
	/**
	 * 账户余额
	 */
	public String free;
	/**
	 * :账户冻结余额
	 */
	public String freezed;
	/**
	 * :账户理财信息(只有在账户有理财信息时才返回)
	 */
	public String union_fund;
}
