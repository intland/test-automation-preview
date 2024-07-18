package com.intland.codebeamer.integration.ui.nextgen.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ToastAssertions  extends AbstractCodebeamerComponentAssert<ToastComponent, ToastAssertions> {
    protected ToastAssertions(ToastComponent component) {
        super(component);
    }

    public ToastAssertions hasSuccess() {
        return assertAll("Toast success message should have been displayed", () -> assertThat(getComponent().getToastSuccess()).isVisible());
    }
    
    public ToastAssertions hasError() {
        return assertAll("Toast error message should have been displayed", () -> assertThat(getComponent().getToastError()).isVisible());
    }
}
