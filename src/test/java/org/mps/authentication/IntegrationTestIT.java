package org.mps.authentication;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class IntegrationTestIT {

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

    /**
     * Fase 3: CredentialStore implementado
     */
    @Test
    void Phase3_credentialStoreImplementedCorrectRegistration(){
        Date date = Mockito.mock(Date.class);
        PasswordString pw = Mockito.mock(PasswordString.class);

        CredentialStore cs = new CredentialStoreSet();
        UserRegistration ur = new UserRegistration();

        Mockito.when(date.validate()).thenReturn(true);
        Mockito.when(pw.validate()).thenReturn(true);

        ur.register(date, pw, cs);

        //verificamos que se ha llamado al register de CredentialStore
        Mockito.verify(cs).register(date, pw);
    }

    @Test
    void Phase3_credentialStoreImplementedDateIncorrectRegistration(){
        Date date = Mockito.mock(Date.class);
        PasswordString pw = Mockito.mock(PasswordString.class);

        CredentialStore cs = new CredentialStoreSet();
        UserRegistration ur = new UserRegistration();

        Mockito.when(date.validate()).thenReturn(false);
        Mockito.when(pw.validate()).thenReturn(true);

        ur.register(date, pw, cs);

        //verificamos que se ha llamado al register de CredentialStore
        Mockito.verify(cs, Mockito.never()).register(date, pw);
    }
    @Test
    void Phase3_credentialStoreImplementedPasswordIncorrectRegistration(){
        Date date = Mockito.mock(Date.class);
        PasswordString pw = Mockito.mock(PasswordString.class);

        CredentialStore cs = new CredentialStoreSet();
        UserRegistration ur = new UserRegistration();

        Mockito.when(date.validate()).thenReturn(true);
        Mockito.when(pw.validate()).thenReturn(false);

        ur.register(date, pw, cs);

        //verificamos que se ha llamado al register de CredentialStore
        Mockito.verify(cs, Mockito.never()).register(date, pw);
    }

    @Test
    void Phase3_credentialStoreImplementedAlreadyRegisteredIncorrectRegistration(){
        Date date = Mockito.mock(Date.class);
        PasswordString pw = Mockito.mock(PasswordString.class);

        CredentialStore cs = new CredentialStoreSet();
        UserRegistration ur = new UserRegistration();

        Mockito.when(date.validate()).thenReturn(true);
        Mockito.when(pw.validate()).thenReturn(true);
        ur.register(date, pw, cs);
        ur.register(date, pw, cs);

        //verificamos que se ha llamado al register de CredentialStore
        Mockito.verify(cs, Mockito.times(1)).register(date, pw);
    }

    /**
     * Fase 4: Everything implementado
     */
    @Test
    void Phase4_everythingImplementedCorrectRegistration() {
        Date date = new Date(25,2,1990);
        PasswordString pw = new PasswordString("1234.1234.");

        CredentialStore cs = new CredentialStoreSet();
        UserRegistration ur = new UserRegistration();

        ur.register(date, pw, cs);

        //verificamos que se ha llamado al register de CredentialStore
        Mockito.verify(cs, Mockito.times(1)).register(date, pw);
    }

    @Test
    void Phase4_everythingImplementedDateIncorrectRegistration() {
        Date date = new Date(30,2,1990);
        PasswordString pw = new PasswordString("1234.1234.");

        CredentialStore cs = new CredentialStoreSet();
        UserRegistration ur = new UserRegistration();

        ur.register(date, pw, cs);

        //verificamos que se ha llamado al register de CredentialStore
        Mockito.verify(cs, Mockito.never()).register(date, pw);
    }

    @Test
    void Phase4_everythingImplementedPasswordIncorrectRegistration() {
        Date date = new Date(10,2,1990);
        PasswordString pw = new PasswordString("12345678");

        CredentialStore cs = new CredentialStoreSet();
        UserRegistration ur = new UserRegistration();

        ur.register(date, pw, cs);

        //verificamos que se ha llamado al register de CredentialStore
        Mockito.verify(cs, Mockito.never()).register(date, pw);
    }

    @Test
    void Phase4_everythingImplementedAlreadyRegisteredIncorrectRegistration() {
        Date date = new Date(10,2,1990);
        PasswordString pw = new PasswordString("1234.1234.");

        CredentialStore cs = new CredentialStoreSet();
        UserRegistration ur = new UserRegistration();

        ur.register(date, pw, cs);
        ur.register(date, pw, cs);

        //verificamos que se ha llamado al register de CredentialStore
        Mockito.verify(cs, Mockito.times(1)).register(date, pw);
    }
}
