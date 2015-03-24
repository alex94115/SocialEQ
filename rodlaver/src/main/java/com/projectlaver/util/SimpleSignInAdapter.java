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
package com.projectlaver.util;

import java.util.Collection;
import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.web.context.request.NativeWebRequest;


/**
 * Signs the user in by setting the currentUser property on the {@link SecurityContext}.
 *
 * @author Keith Donald
 * @see UserInterceptor
 */
public class SimpleSignInAdapter implements SignInAdapter {

	private final RequestCache requestCache;
	
	@Autowired
	SocialUserDetailsService socialUserDetailsService;

    @Inject
    public SimpleSignInAdapter(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
    
    @Override
    public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {
    	
    	// Create the securityUser object that the app expects as the security principal
    	SocialUserDetails socialUser = socialUserDetailsService.loadUserByUserId( localUserId );
    	Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_BUYER"); 
		SocialAuthenticationToken authToken = new SocialAuthenticationToken( connection, socialUser, new HashMap<String, String>(), authorities );

		// Programatically sign this user in
		SecurityContextHolder.getContext().setAuthentication( authToken );
		
        return this.extractOriginalUrl(request);
    }

    private String extractOriginalUrl(NativeWebRequest request) {
        HttpServletRequest nativeReq = request.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse nativeRes = request.getNativeResponse(HttpServletResponse.class);
        SavedRequest saved = requestCache.getRequest(nativeReq, nativeRes);
        if (saved == null) {
            return null;
        }
        requestCache.removeRequest(nativeReq, nativeRes);
        this.removeAutheticationAttributes(nativeReq.getSession(false));
     
        return saved.getRedirectUrl();
    }
             
    private void removeAutheticationAttributes(HttpSession session) {
        if (session == null) {
                return;
        }
        
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
