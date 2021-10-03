package wiwy.covid.config.jwt;

public interface JwtProperties {
    String SECRET = "wiwy";
//    int EXPIRATION_TIME = 60000*60; // 10분
    int EXPIRATION_TIME = 60000*60*6*24;
    String TOKEN_PREFIX ="Bearer ";
    String HEADER_STRING = "Authorization";
}
