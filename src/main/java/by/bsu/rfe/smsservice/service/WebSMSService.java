package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.sms.SmsDTO;

/**
 * Created by pluhin on 12/27/15.
 */
public interface WebSMSService {
    SMSResultDTO sendSMS(SmsDTO smsDTO);
}
