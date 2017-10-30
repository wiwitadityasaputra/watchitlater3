package info.wiwitadityasaputra.user.api.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.user.entity.User;
import info.wiwitadityasaputra.user.entity.UserRepository;
import info.wiwitadityasaputra.util.api.AbstractCtrl;
import info.wiwitadityasaputra.util.api.exception.BadRequestException;
import info.wiwitadityasaputra.util.api.exception.ConflictException;
import info.wiwitadityasaputra.util.api.exception.ForbiddenException;
import info.wiwitadityasaputra.util.config.AuthModel;

@RestController
@RequestMapping(value = AbstractCtrl.API_PATH_USER_AUTH)
public class AuthController extends AbstractCtrl {

	private Logger logger = LogManager.getLogger(AuthController.class);

	@Autowired
	private UserRepository userRepo;

	@RequestMapping(method = RequestMethod.GET, value = AbstractCtrl.ME)
	public Object me() throws Exception {
		logger.info("GET " + AbstractCtrl.API_PATH_USER_AUTH + ME);
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@RequestMapping(method = RequestMethod.POST, value = AbstractCtrl.SIGNIN)
	public void signIn(@RequestBody @Valid SignUpInput input) throws Exception {
		logger.info("POST " + AbstractCtrl.API_PATH_USER_AUTH + SIGNIN);

		User user = userRepo.findByEmailAndPassword(input.getEmail(), input.getPassword());
		if (user == null) {
			logger.info("  cant find user by email & password");
			throw new ForbiddenException("Unable to find user with " + input.getEmail());
		}

		AuthModel am = new AuthModel(UUID.randomUUID().toString(), input.getEmail());

		List<GrantedAuthority> grantedAuth = new ArrayList<GrantedAuthority>();
		grantedAuth.add(new SimpleGrantedAuthority("ROLE_USER"));
		Authentication auth = new UsernamePasswordAuthenticationToken(am, "password", grantedAuth);

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(auth);
		SecurityContextHolder.setContext(context);
		logger.info("success login, create auth");
	}

	@RequestMapping(method = RequestMethod.POST, value = AbstractCtrl.SIGNOUT)
	public void signOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("POST " + AbstractCtrl.API_PATH_USER_AUTH + SIGNOUT);
	}

	@RequestMapping(method = RequestMethod.POST, value = AbstractCtrl.SIGNUP)
	public void signUp(@RequestBody @Valid NewUserInput input) {
		logger.info("POST " + AbstractCtrl.API_PATH_USER_AUTH + SIGNUP);

		if (!input.getPassword().equals(input.getRePassword())) {
			logger.info("  password not same");
			throw new BadRequestException();
		}

		User user = userRepo.findByEmail(input.getEmail());
		if (user != null) {
			logger.info("  duplicated");
			throw new ConflictException("Duplicate email address");
		} else {
			User newUser = new User();
			newUser.setUserId(UUID.randomUUID().toString());
			newUser.setEmail(input.getEmail());
			newUser.setPassword(input.getPassword());
			newUser.setFullName(input.getFullName());
			userRepo.save(newUser);
			logger.info("  new user created");
		}
	}
}
