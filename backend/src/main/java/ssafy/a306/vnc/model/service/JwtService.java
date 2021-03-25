package ssafy.a306.vnc.model.service;

public interface JwtService {
	<T> String create(String key, T data, String subject);
	String get(String key);
	boolean isUsable(String jwt);
	
}
