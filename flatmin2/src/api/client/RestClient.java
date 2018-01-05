package api.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.mindrot.jbcrypt.BCrypt;

import api.model.Ausgabe;
import api.model.Einnahme;
import api.model.ShoppingItem;
import api.model.UserCredentials;
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
		WebTarget expenseTarget = baseTarget.path("expenses");
		WebTarget singleExpenseTarget = expenseTarget.path("{id}");
		WebTarget shoppingListTarget = baseTarget.path("shoppinglist");
		WebTarget singleShoppingListTarget = shoppingListTarget.path("{id}");
		WebTarget authenticationTarget = baseTarget.path("authenticate");
		WebTarget singleAuthenticationTarget = authenticationTarget.path("{id}");

		Einnahme einnahme = singleIncomeTarget.resolveTemplate("id", "15").request(MediaType.APPLICATION_JSON)
				.get(Einnahme.class);
		System.out.println(einnahme.getBetrag());

		Users user1 = singleUserTarget.resolveTemplate("id", "5").request(MediaType.APPLICATION_JSON).get(Users.class);
		System.out.println(user1.getUsername());

		Ausgabe ausgabe = singleExpenseTarget.resolveTemplate("id", "41").request(MediaType.APPLICATION_JSON)
				.get(Ausgabe.class);
		System.out.println(ausgabe.getBetrag());

		ShoppingItem shoppingItem = singleShoppingListTarget.resolveTemplate("id", "12")
				.request(MediaType.APPLICATION_JSON).get(ShoppingItem.class);
		System.out.println(shoppingItem.getItem());

		System.out.println(login("janschill", "50cent", singleAuthenticationTarget));

		// createNewUser("Marius", "Surf", "marius@surf.de", "marius", "surf",
		// usersTarget);

	}

	@SuppressWarnings("unused")
	private static String login(String username, String password, WebTarget authenticationTarget)
	{
		UserCredentials userCredentials = new UserCredentials();
		userCredentials.setUsername(username);
		userCredentials.setPassword(password);
		Response postResponse = authenticationTarget.request().post(Entity.json(userCredentials));
		System.out.println(postResponse.getHeaderString("token"));
		if (postResponse.getStatus() != 201)
			return "Not able to login";
		return "Succesfully loggedin";
	}

	@SuppressWarnings("unused")
	private static String insertShoppingItem(String item, WebTarget shoppingListTarget)
	{
		ShoppingItem shoppingItem = new ShoppingItem();
		shoppingItem.setItem(item);
		Response postResponse = shoppingListTarget.request().post(Entity.json(shoppingItem));
		if (postResponse.getStatus() != 201)
			return "Not able to insert new shoppingitem";
		return "Succesfully insert new shoppingitem";
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
