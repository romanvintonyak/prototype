/**
 *
 */
package com.epam.security;

import de.hybris.platform.jalo.user.User;
import de.hybris.platform.jalo.user.UserManager;
import de.hybris.platform.spring.security.CoreAuthenticationProvider;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Roman_Rozhko
 *
 */

public class EpamCoreAuthenticationProvider extends CoreAuthenticationProvider {
    @Override
    protected Authentication createSuccessAuthentication(final Authentication authentication, final UserDetails userDetails) {
        final User user = UserManager.getInstance().getUserByLogin(userDetails.getUsername());
        final EpamAuthenticationToken result = new EpamAuthenticationToken(userDetails.getUsername(), authentication.getCredentials(),
                userDetails.getAuthorities());
        result.setDetails(authentication.getDetails());
        result.setDisplayName(user.getDisplayName());
        result.setUserName(userDetails.getUsername());
        return result;
    }

    private class EpamAuthenticationToken extends UsernamePasswordAuthenticationToken {
        private String displayName;
        private String userName;

        public EpamAuthenticationToken(final Object principal, final Object credentials, final Collection<? extends GrantedAuthority> authorities) {
            super(principal, credentials, authorities);
        }

        @SuppressWarnings("unused")
        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(final String displayName) {
            this.displayName = displayName;
        }

        @SuppressWarnings("unused")
        public String getUserName() {
            return userName;
        }

        public void setUserName(final String userName) {
            this.userName = userName;
        }

    }

}
