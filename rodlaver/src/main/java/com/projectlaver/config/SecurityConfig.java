/*******************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 PURPLE & GOLD, INC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.projectlaver.config;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.AllowFromStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.social.UserIdSource;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import com.projectlaver.util.SimpleSocialUsersDetailService;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Value("${security.doUseSecureRememberMeCookie}")
	private Boolean doUseSecureRememberMeCookie;

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
    	return new AccessDeniedHandlerImpl();
    }
	
     @Autowired
     private DataSource dataSource;
     
     @Autowired
     public void registerAuthentication( final AuthenticationManagerBuilder auth ) throws Exception {
             auth.jdbcAuthentication()
                             .dataSource(dataSource)
                             .usersByUsernameQuery("SELECT username, password, true FROM Users WHERE username = ?")
                             .authoritiesByUsernameQuery("SELECT u.username, r.description FROM Users u INNER JOIN Users_Roles ur ON u.id = ur.users_id INNER JOIN Roles r ON ur.roles_id = r.id  WHERE u.username=?")
                             .passwordEncoder(passwordEncoder());
     }
     
     @Override
     public void configure( final WebSecurity web ) throws Exception {
             web
                     .ignoring()
                             .antMatchers("/resources/**");
     }
    
     @Override
     protected void configure( final HttpSecurity http ) throws Exception {
             http
                     .formLogin()
                             .loginPage("/signin")
                             .loginProcessingUrl("/signin/authenticate")
                             .failureUrl("/signin?param.error=bad_credentials")
                     .and()
                             .logout()
                                     .logoutUrl("/signout")
                                     .deleteCookies("JSESSIONID")
                                     .logoutSuccessUrl("/signin")
                     .and()
                             .authorizeRequests()
                                     .antMatchers( "/resources/**", 
                                    		       "/favicon.ico", 
                                    		       "/", 
                                    		       "/about", 
                                    		       "/contact", 
                                    		       "/support", 
                                    		       "/privacy", 
                                    		       "/contestsAndPromotions", 
                                    		       "/sellOnFacebook", 
                                    		       "/sellOnTwitter", 
                                    		       "/terms",
                                    		       "/faq",
                                    		       //"/testEmail", // GARBAGE DO NOT COMMIT
                                    		       "/healthCheck", 
                                    		       "/listing/listingDetail/**", 
                                    		       "/registration/musician",
                                    		       "/registration/bypassFacebook",
                                    		       "/registration/bypassTwitter",
                                    		       "/signin/**", 
                                    		       "/signup/**",
                                    		       "/signout").permitAll()
                                     .antMatchers("/include").denyAll()
                                     .antMatchers("/**").authenticated()
                     .and()
                     		.headers()
                     				.addHeaderWriter( new XFrameOptionsHeaderWriter( XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN )) // allows multiDownload to work
                     .and()
                             .csrf()
                             		 .requireCsrfProtectionMatcher( new RequestMatcher() {

											private Pattern allowedHttpMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
											private RequestMatcher ipnMatcher = new AntPathRequestMatcher( "/paypalIpn/postNotification" );
											
											/**
											 * As per Spring Security defaults, configure CSRF Protection on any HTTP method except for 
											 * GET, TRACE, HEAD, or OPTIONS. Additionally, ignore requests to the URL /paypalIpn/postNotification, 
											 * since PayPal doesn't know anything about our CSRF tokens.
											 */
											@Override
											public boolean matches( HttpServletRequest request ) {
											
												// No CSRF necessary for allowedHttpMethods
											    if( allowedHttpMethods.matcher(request.getMethod()).matches()) {
											        return false;
											    }
												
												// no CSRF for Paypal IPN Notifications
											    if( ipnMatcher.matches( request )) {
											    	return false;
											    }
												
												// CSRF for everything else
												return true;
											}
									 })
                     .and()
                             .rememberMe()
                             		 .useSecureCookie( doUseSecureRememberMeCookie )
                             		 .tokenRepository(persistentTokenRepository())
                             		 .tokenValiditySeconds((int) TimeUnit.SECONDS.convert(14, TimeUnit.DAYS))
                             		 .withObjectPostProcessor( new ObjectPostProcessor<RememberMeAuthenticationFilter>() {

										@Override
										public <O extends RememberMeAuthenticationFilter> O postProcess( final O object) {
											
											RememberMeAuthenticationFilter rmaf = (RememberMeAuthenticationFilter)object;
											PersistentTokenBasedRememberMeServices rms = (PersistentTokenBasedRememberMeServices)rmaf.getRememberMeServices();
											rms.setAlwaysRemember( true );
											
											return object;
										}
                             			 
                             		 })
                     .and()
                     		 .exceptionHandling()
                             		 .accessDeniedPage( "/error" )
                     .and()
                             .apply( new SpringSocialConfigurer()
                     		 		 .postLoginUrl( "/userHome" ) );
     }
     
     @Bean
     public PersistentTokenRepository persistentTokenRepository() {
    	 JdbcTokenRepositoryImpl jtri = new JdbcTokenRepositoryImpl();
    	 jtri.setDataSource( this.dataSource );
    	 return jtri;
     }
    
     @Bean
     public SocialUserDetailsService socialUsersDetailService() {
             return new SimpleSocialUsersDetailService( super.userDetailsService() );
     }
     
     @Bean
     public UserIdSource userIdSource() {
             return new AuthenticationNameUserIdSource();
     }

     @Bean
     public PasswordEncoder passwordEncoder() {
             return NoOpPasswordEncoder.getInstance();
     }

     @Bean
     public TextEncryptor textEncryptor() {
             return Encryptors.noOpText();
     }
     
}
