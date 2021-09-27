package wiwy.covid.config.oauth.provider;

public interface OAuth2UserInfo {
    String getProviderId(); // 페이스북, 구글의 pk
    String getProvider(); // 구글인지 페이스북인지 등등
    String getEmail();
    String getName();
}
