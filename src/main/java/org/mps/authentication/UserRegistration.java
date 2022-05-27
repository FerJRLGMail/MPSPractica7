package org.mps.authentication;

import org.mps.authentication.CredentialValidator.ValidationStatus;

public class UserRegistration {


  protected ValidationStatus validate(Date birthDate, PasswordString passwordString,
                                   CredentialStore credentialStore) {

    var credentialValidator = new CredentialValidator(birthDate, passwordString, credentialStore);

    return credentialValidator.validate();
  }

  public void register(Date birthDate, PasswordString passwordString,
      CredentialStore credentialStore) {

    ValidationStatus status = this.validate(birthDate, passwordString, credentialStore);

    if (status == ValidationStatus.VALIDATION_OK) {
      credentialStore.register(birthDate, passwordString);
    }
  }

}
