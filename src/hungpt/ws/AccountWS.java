package hungpt.ws;

import hungpt.entities.AccountEntity;
import hungpt.services.AccountService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/account")
public class AccountWS {
    private static AccountService accountService = new AccountService();

    @POST()
    @Path("/checkLogin")
    @Produces({MediaType.APPLICATION_XML})
    public AccountEntity findLikeByName(@QueryParam("username") String username,
                                        @QueryParam("password") String password) {
        return accountService.checkLogin(username, password);
    }

}
