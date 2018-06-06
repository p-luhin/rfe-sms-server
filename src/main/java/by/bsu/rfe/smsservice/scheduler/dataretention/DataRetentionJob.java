package by.bsu.rfe.smsservice.scheduler.dataretention;

import by.bsu.rfe.smsservice.common.SpringContextHolder;
import by.bsu.rfe.smsservice.common.entity.AuthenticationTokenEntity;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.service.AuthenticationTokenService;
import by.bsu.rfe.smsservice.service.RecipientService;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataRetentionJob implements Job {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataRetentionJob.class);

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    LOGGER.info("Starting data retention...");
    dropTemporaryRecipients();
    dropExpiredTokens();
    LOGGER.info("Finished data retention");
  }

  private void dropTemporaryRecipients() {
    RecipientService recipientService = SpringContextHolder.getBean(RecipientService.class);

    List<GroupEntity> groupsToRemove = recipientService.findTemporaryGroups();
    LOGGER.info("Found {} groups to remove", groupsToRemove.size());

    groupsToRemove.stream().forEach(group -> {
      LOGGER.info("Removing {} group", group.getName());
      recipientService.removeGroup(group.getId());
    });

    List<PersonEntity> personsToRemove = recipientService.findTemporaryPersons();
    LOGGER.info("Found {} persons to remove", personsToRemove.size());

    personsToRemove.stream().forEach(person -> {
      LOGGER.info("Removing {} person", person.getFirstName() + " " + person.getLastName());
      recipientService.removePerson(person.getId());
    });
  }

  private void dropExpiredTokens() {
    AuthenticationTokenService tokenService = SpringContextHolder
        .getBean(AuthenticationTokenService.class);
    List<AuthenticationTokenEntity> tokens = tokenService.getExpiredTokens();

    LOGGER.info("Found {} expired tokens", tokens.size());

    tokens.stream().forEach(token -> {
      LOGGER.info("Removing token {}", token.getToken());
      tokenService.removeToken(token.getId());
    });
  }
}
