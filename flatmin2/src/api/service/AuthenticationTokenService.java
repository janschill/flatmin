package api.service;

public class AuthenticationTokenService
{
	private RandomStringService randomStringService = new RandomStringService();

	public String issueToken()
	{
		return randomStringService.nextString();
	}
}
