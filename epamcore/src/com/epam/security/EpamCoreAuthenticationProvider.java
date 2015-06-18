/**
 *
 */
package com.epam.security;

import de.hybris.platform.jalo.user.User;
import de.hybris.platform.jalo.user.UserManager;
import de.hybris.platform.spring.security.CoreAuthenticationProvider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Roman_Rozhko
 *
 */

public class EpamCoreAuthenticationProvider extends CoreAuthenticationProvider {
	@Override
	protected Authentication createSuccessAuthentication(final Authentication authentication, final UserDetails userDetails) {
		final User user = UserManager.getInstance().getUserByLogin(userDetails.getUsername());
		final UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(new SimpleUserDetails(user), authentication.getCredentials(),
				userDetails.getAuthorities());
		result.setDetails(authentication.getDetails());
		return result;
	}

	private class SimpleUserDetails {
		private final String login;
		private final String displayName;

		public SimpleUserDetails(final User user) {
			this.login = user.getLogin();
			this.displayName = user.getDisplayName();
		}

		@SuppressWarnings("unused")
		public String getLogin() {
			return login;
		}

		@SuppressWarnings("unused")
		public String getDisplayName() {
			return displayName;
		}

		@Override
		public String toString() {
			return this.login;
		}
	}

}
