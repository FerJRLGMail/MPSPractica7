package org.mps.authentication;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserRegistrationTest {



    @Test
    void WhenValidatedRegisterCorrectly() {

        //montamos mocks
        Date date = Mockito.mock(Date.class);
        PasswordString pw = Mockito.mock(PasswordString.class);
        CredentialStore cs = Mockito.mock(CredentialStore.class);

        //montamos spy de UserRegistration
        UserRegistration urSpy = Mockito.spy(UserRegistration.class);

        Mockito.doReturn(CredentialValidator.ValidationStatus.VALIDATION_OK).when(urSpy).validate(date, pw, cs);
        //Mockito.when(urSpy.validate(new CredentialValidator(date, pw, cs))).thenReturn(CredentialValidator.ValidationStatus.VALIDATION_OK);

        urSpy.register(date, pw, cs);

        //verificamos que se ha llamado al register de CredentialStore
        Mockito.verify(cs).register(date, pw);



    }
}
