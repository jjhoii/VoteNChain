package ssafy.a306.vnc.model.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService{
	public static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

	private static final String TK = "VncSecretToken";
	private static final int EXPIRE_MINUTES = 60;

	@Override
	public <T> String create(String key, T data, String subject) {
		String jwt = Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("regDate", System.currentTimeMillis())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * EXPIRE_MINUTES))
				.setSubject(subject).claim(key, data).signWith(SignatureAlgorithm.HS256, this.generateKey()).compact();
		return jwt;
	}

	private byte[] generateKey() {
		byte[] key = null;
		try {
			key = TK.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if (logger.isInfoEnabled()) {
				e.printStackTrace();
			} else {
				logger.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}

		return key;
	}

//	전달 받은 토큰이 제대로 생성된것인지 확인 하고 문제가 있다면 UnauthorizedException을 발생.
	@Override
	public boolean isUsable(String jwt) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			
			return false;
		}
	}

	@Override
	public String get(String key) {
		Claims claims = null;
		String email="";
		try {
			claims = Jwts.parser().setSigningKey(TK.getBytes("UTF-8")).parseClaimsJws(key).getBody();
			email=(String) claims.get("userEmail");
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				e.printStackTrace();
			} else {
				logger.error(e.getMessage());
			}
		}
		return email;
	}
}
