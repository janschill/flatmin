package api.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.mindrot.jbcrypt.BCrypt;

import api.model.Einnahme;
import api.model.Users;

public class RestClient
{

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Client client = ClientBuilder.newClient();

		WebTarget baseTarget = client.target("http://localhost:8080/flatmin2/app/");
		WebTarget usersTarget = baseTarget.path("users");
		WebTarget singleUserTarget = usersTarget.path("{id}");
		WebTarget incomeTarget = baseTarget.path("income");
		WebTarget singleIncomeTarget = incomeTarget.path("{id}");
		WebTarget expenseTarget = baseTarget.path("income");
		WebTarget singleExpenseTarget = incomeTarget.path("{id}");

		Users user1 = singleUserTarget.resolveTemplate("id", "5").request(MediaType.APPLICATION_JSON).get(Users.class);

		System.out.println(user1.getUsername());

		// createNewUser("Marius", "Surf", "marius@surf.de", "marius", "surf",
		// usersTarget);

	}

	@SuppressWarnings("unused")
	private static String createNewUser(String first, String last, String email, String username, String password,
			WebTarget usersTarget)
	{
		Users user = new Users();
		user.setFirst(first);
		user.setLast(last);
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
		Response postResponse = usersTarget.request().post(Entity.json(user));
		if (postResponse.getStatus() != 201)
			return "Not able to insert new user";
		return "Succesfully inserted new user";

		// Users createdUser = postResponse.readEntity(Users.class);
		// System.out.println(createdUser.getUsername());
	}

	@SuppressWarnings("unused")
	private static String insertIncome(Double betrag, Long idUsers, String notiz, WebTarget incomeTarget)
	{
		Einnahme einnahme = new Einnahme();
		einnahme.setBetrag(betrag);
		einnahme.setIdusers(idUsers);
		einnahme.setNotiz(notiz);
		Response postResponse = incomeTarget.request().post(Entity.json(einnahme));
		if (postResponse.getStatus() != 201)
			return "Not able to insert new income";
		return "Succesfully insert new income";
	}

	@SuppressWarnings("unused")
	private static String insertExpense(Double betrag, Long idUsers, String notiz, WebTarget expenseTarget)
	{
		Einnahme einnahme = new Einnahme();
		einnahme.setBetrag(betrag);
		einnahme.setIdusers(idUsers);
		einnahme.setNotiz(notiz);
		Response postResponse = expenseTarget.request().post(Entity.json(einnahme));
		if (postResponse.getStatus() != 201)
			return "Not able to insert new income";
		return "Succesfully insert new income";
	}

}
