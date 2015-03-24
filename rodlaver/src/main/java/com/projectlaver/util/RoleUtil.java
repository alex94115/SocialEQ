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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.projectlaver.domain.Role;

public class RoleUtil {

	public static final int ROLE_BUYER_INT = 1;
	public static final int ROLE_SELLER_INT = 2;
	public static final int ROLE_ADMIN_INT = 3;
	public static final int ROLE_CHARITY_INT = 4;

	public static final String ROLE_BUYER = "ROLE_BUYER";
	public static final String ROLE_SELLER = "ROLE_SELLER";
	public  static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_CHARITY = "ROLE_CHARITY";
	
	private static List<Integer> allRolesNumerically;
	private static List<Integer> sellerRolesNumerically;

	/**
	 * Synchronized to avoid concurrent initialization
	 * @return
	 */
	public static final synchronized List<Integer> getAllRolesNumerically() {
		if( allRolesNumerically == null ) {
			allRolesNumerically = new ArrayList<Integer>();
			allRolesNumerically.add( RoleUtil.ROLE_BUYER_INT );
			allRolesNumerically.add( RoleUtil.ROLE_SELLER_INT );
			allRolesNumerically.add( RoleUtil.ROLE_ADMIN_INT );
			allRolesNumerically.add( RoleUtil.ROLE_CHARITY_INT );
		}
		
		return allRolesNumerically;
	}
	
	/**
	 * Synchronized to avoid concurrent initialization. Returns a list
	 * of the "seller" roles: seller and charity.
	 * 
	 * @return
	 */
	public static final synchronized List<Integer> getAllSellerRolesNumerically() {
		if( sellerRolesNumerically == null ) {
			sellerRolesNumerically = new ArrayList<Integer>();
			sellerRolesNumerically.add(RoleUtil.ROLE_SELLER_INT);
			sellerRolesNumerically.add(RoleUtil.ROLE_CHARITY_INT);
		}
		
		return sellerRolesNumerically;
	}
	
	public static List<String> getAssociatedRoleNames( Set<Role> roles ) {
		List<String> roleNames = new ArrayList<String>();
		
		for( Role role : roles ) {
			if (role.getRole().equals( 1 )) {
				roleNames.add( RoleUtil.ROLE_BUYER );
			} else if (role.getRole().equals( 2 )) {
				roleNames.add( RoleUtil.ROLE_SELLER );
			} else if (role.getRole().equals( 3 ) ) {
				roleNames.add( RoleUtil.ROLE_ADMIN );
			} else if (role.getRole().equals( 4 ) ) {
				roleNames.add( RoleUtil.ROLE_CHARITY );
			} else {
				throw new RuntimeException( "Unexpected role value: " + role.getRole() );
			}
		}
		
		return roleNames;
	}
	
	/**
	 * Converts a numerical role to an equivalent list of roles.
	 * 
	 * @param role the numerical role
	 * @return list of roles as as a list of {@link String}
	 */
	public static List<String> getAssociatedRoleNames(Integer role) {
		List<String> roles = new ArrayList<String>();
		
		if (role.intValue() == 1) {
			roles.add( RoleUtil.ROLE_BUYER );
		} else if (role.intValue() == 2) {
			roles.add( RoleUtil.ROLE_SELLER );
		} else if (role.intValue() == 3 ) {
			roles.add( RoleUtil.ROLE_ADMIN );
		} else if (role.intValue() == 4 ) {
			roles.add( RoleUtil.ROLE_CHARITY );
		} else {
			throw new RuntimeException( "Unexpected role value: " + role.intValue() );
		}
		
		return roles;
	}
}
