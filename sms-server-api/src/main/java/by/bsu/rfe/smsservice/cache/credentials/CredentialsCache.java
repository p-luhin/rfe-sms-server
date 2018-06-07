package by.bsu.rfe.smsservice.cache.credentials;

import by.bsu.rfe.smsservice.cache.SmsServerCache;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CredentialsCache extends SmsServerCache {

  List<CredentialsEntity> getAllUserCredentals(String username);

  Set<String> getSenderNamesForCurrentUser();

  CredentialsEntity getCredentialsBySenderNameForCurrentUser(String senderName);
}
