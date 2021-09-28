//package wiwy.covid.config.oauth;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import wiwy.covid.config.auth.PrincipalDetails;
//import wiwy.covid.config.oauth.provider.FacebookUserInfo;
//import wiwy.covid.config.oauth.provider.GoogleUserInfo;
//import wiwy.covid.config.oauth.provider.NaverUserInfo;
//import wiwy.covid.config.oauth.provider.OAuth2UserInfo;
//import wiwy.covid.domain.Member;
//import wiwy.covid.repository.MemberRepository;
//import wiwy.covid.service.MemberService;
//
//import java.util.Map;
//import java.util.Optional;
//import java.util.UUID;
//
//@Slf4j
//@Service
//public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    private MemberService memberService;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        log.debug("userRequest = {}", userRequest.getClientRegistration());
//        log.debug("getAccessToken = {}", userRequest.getAccessToken());
//
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//        // 구글로부터 받아온 정보를 기반으로 우리 웹사이트에 회원가입을 시켜야 함
//        // 회원가입을 강제로 진행해야 하는 과정
//
//        log.debug("getAttributes = {}", oAuth2User.getAttributes());
//
//        OAuth2UserInfo oAuth2UserInfo = null;
//        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) { // 구글 로그인
//            log.debug("구글 로그인 요청");
//            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
//        } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) { // 페이스북 로그인
//            log.debug("페이스북 로그인 요청");
//            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
//        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) { // 네이버 로그인
//            log.debug("네이버 로그인 요청");
//            oAuth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
//        } else {
//            log.debug("구글, 페이스북, 네이버 로그인만 가능합니다.");
//        }
//
//        String provider = oAuth2UserInfo.getProvider();
//        String providerId = oAuth2UserInfo.getProviderId();
//        String username = provider + "_" + providerId;
//        String password = bCryptPasswordEncoder.encode("우리들의졸업작품") + UUID.randomUUID().toString();
//        String email = oAuth2UserInfo.getEmail();
//        String role = "ROLE_USER";
//
//        Member memberEntity = memberService.findByUsername(username);
//
//        if (memberEntity == null) {
//            log.debug("OAuth 로그인이 최초입니다.");
//            memberEntity = Member.builder()
//                    .username(username)
//                    .password(password)
//                    .email(email)
//                    .role(role)
//                    .provider(provider)
//                    .providerId(providerId)
//                    .build();
//            memberService.join(memberEntity);
//        }
//
//        return new PrincipalDetails(memberEntity, oAuth2User.getAttributes());
//    }
//}
