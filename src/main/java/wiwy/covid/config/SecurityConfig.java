package wiwy.covid.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import wiwy.covid.apicall.comparator.AbrCoronaComparator;
import wiwy.covid.apicall.comparator.CoronaDataComparator;
import wiwy.covid.config.auth.PrincipalDetailsService;
import wiwy.covid.config.jwt.JwtAuthenticationFilter;
import wiwy.covid.config.jwt.JwtAuthorizationFilter;
import wiwy.covid.repository.MemberRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    PrincipalDetailsService principalDetailsService;

    @Bean
    public AbrCoronaComparator abrCoronaComparator() {
        return new AbrCoronaComparator();
    }

    @Bean
    public CoronaDataComparator coronaDataComparator() {
        return new CoronaDataComparator();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(principalDetailsService).passwordEncoder(encodePwd());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private CorsConfig corsConfig;
    private final MemberRepository memberRepository;

    // ?????? ???????????? ???????????? ??????????????? IoC??? ???????????????.
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsConfig.corsFilter())
                .formLogin().disable()
                .httpBasic().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), memberRepository))
                .authorizeRequests()
                .antMatchers("/api/post/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/member/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();
//        http.authorizeRequests()
//                .antMatchers("/member/**").authenticated()
//                .antMatchers("/board/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//                .antMatchers("/**/add").authenticated()
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/loginForm")
//                .loginProcessingUrl("/login") // /login ????????? ????????? ?????? ??????????????? ???????????? ?????? ???????????? ????????????.
//                .defaultSuccessUrl("/co")
//                .and()
//                .oauth2Login()
//                .loginPage("/loginForm")
//                // ?????? ???????????? ????????? ?????? ???????????? ?????????. 1. ????????????(??????), 2. ???????????????(??????),
//                // 3. ????????? ????????? ????????? ???????????? 4-1. ??? ????????? ????????? ??????????????? ???????????? ?????????????????? ???
//                // ?????? ???????????? ????????? ?????? ????????? ????????? ????????? ????????? ?????? + ????????? ?????????????????? ????????? ??????
//                .userInfoEndpoint()
//                .userService(principalOauth2UserService);



    }
}
