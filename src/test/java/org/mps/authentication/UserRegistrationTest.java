package org.mps.authentication;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserRegistrationTest {



    @Test
    void Phase1_WhenValidatedRegisterCorrectly() {

        //montamos mocks
        Date date = Mockito.mock(Date.class);
        PasswordString pw = Mockito.mock(PasswordString.class);
        CredentialStore cs = Mockito.mock(CredentialStore.class);

        //montamos spy de UserRegistration
        UserRegistration urSpy = Mockito.spy(UserRegistration.class);

        Mockito.doReturn(CredentialValidator.ValidationStatus.VALIDATION_OK).when(urSpy).validate(date, pw, cs);

        urSpy.register(date, pw, cs);

        //verificamos que se ha llamado al register de CredentialStore
        Mockito.verify(cs).register(date, pw);
    }

    @Test
    void Phase1_WhenNotValidatedDontRegister() {

        //montamos mocks
        Date date = Mockito.mock(Date.class);
        PasswordString pw = Mockito.mock(PasswordString.class);
        CredentialStore cs = Mockito.mock(CredentialStore.class);

        //montamos spy de UserRegistration
        UserRegistration urSpy = Mockito.spy(UserRegistration.class);

        Mockito.doReturn(CredentialValidator.ValidationStatus.PASSWORD_INVALID).when(urSpy).validate(date, pw, cs);
        urSpy.register(date, pw, cs);

        Mockito.doReturn(CredentialValidator.ValidationStatus.BIRTHDAY_INVALID).when(urSpy).validate(date, pw, cs);
        urSpy.register(date, pw, cs);

        Mockito.doReturn(CredentialValidator.ValidationStatus.EXISTING_CREDENTIAL).when(urSpy).validate(date, pw, cs);
        urSpy.register(date, pw, cs);

        Mockito.verify(cs, Mockito.never()).register(date, pw);
    }

    @Test
    void Phase2_WhenValidatedRegisterCorrectly() {

        UserRegistration ur = new UserRegistration();

        //montamos mocks
        Date date = Mockito.mock(Date.class);
        Mockito.when(date.validate()).thenReturn(true);

        PasswordString pw = Mockito.mock(PasswordString.class);
        Mockito.when(pw.validate()).thenReturn(true);

        CredentialStore cs = Mockito.mock(CredentialStore.class);
        Mockito.when(cs.credentialExists(date, pw)).thenReturn(false);

        ur.register(date, pw, cs);

        Mockito.verify(cs).register(date, pw);
    }

    @Test
    void Phase2_WhenNotValidatedDontRegister() {

        UserRegistration ur = new UserRegistration();

        //montamos mocks
        Date date = Mockito.mock(Date.class);

        PasswordString pw = Mockito.mock(PasswordString.class);

        CredentialStore cs = Mockito.mock(CredentialStore.class);

        //ejemplo de parámetros con los que CredentialValidator no se pondria a VALIDATION_OK
        //asegurar Multiple Condition Coverage es trabajo de las pruebas unitarias
        Mockito.when(date.validate()).thenReturn(false);
        Mockito.when(pw.validate()).thenReturn(false);
        Mockito.when(cs.credentialExists(date, pw)).thenReturn(true);
        ur.register(date, pw, cs);

        Mockito.verify(cs, Mockito.never()).register(date, pw);
    }
}
