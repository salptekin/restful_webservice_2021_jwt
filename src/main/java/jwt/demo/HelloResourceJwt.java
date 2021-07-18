package jwt.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import models.AuthenticationRequestJwt;
import models.AuthenticationResponseJwt;

@RestController
public class HelloResourceJwt {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsServiceJwt myUserDetailsServiceJwt;
	@Autowired
	private UtilJwt utilJwt;
	
	@RequestMapping("/hello")
	public String hello() {
		return "Hello world!";
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestJwt authenticationRequestJwt) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequestJwt.getUsername(), authenticationRequestJwt.getPassword())); 

		}catch(BadCredentialsException e) {
			throw new Exception("Incorrect username or password");
		}
		
		final UserDetails userDetails = myUserDetailsServiceJwt.loadUserByUsername(authenticationRequestJwt.getUsername());
		
        final String jwt = utilJwt.generateToken(userDetails);
        
        return ResponseEntity.ok(new AuthenticationResponseJwt(jwt));
	}

}
