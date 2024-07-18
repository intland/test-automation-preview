/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.nextgen.page.login.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class LoginFormAssertions extends AbstractCodebeamerComponentAssert<LoginFormComponent, LoginFormAssertions> {
    protected LoginFormAssertions(LoginFormComponent component) {
        super(component);
    }

    public LoginFormAssertions usernameNotCleared() {
        return assertAll("User field should have been cleared", () -> assertThat(getComponent().getUsernameField()).not().isEmpty());
    }
    
    public LoginFormAssertions passwordNotCleared() {
        return assertAll("Password field should have not been cleared", () -> assertThat(getComponent().getPasswordField()).not().isEmpty());
    }

    public LoginFormAssertions isReady() {
        return assertAll("Login form is ready", () -> {
            assertThat(getComponent().getUsernameField()).isEditable();
            assertThat(getComponent().getPasswordField()).isEditable();
            assertThat(getComponent().getForgotPasswordLink()).isVisible();
            assertThat(getComponent().getUserRegistrationLink()).isVisible(); // TODO it could be configurable
            assertThat(getComponent().getLoginButton()).isVisible();
        });
    }
}
